package cz.sparko.sprintBoard.repository.service

import java.time._

import cz.sparko.sprintBoard.entity.{GoalState, Goal, Sprint}
import cz.sparko.sprintBoard.repository.dao.{ConfigurationDao, SprintDao}
import cz.sparko.sprintBoard.repository.entity.{GoalEntity, ConfigurationKeys, SprintEntity}
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import scala.collection.JavaConversions._

/**
 * methods always works with 'current' sprint
 */
@Service
class SprintService @Autowired()(sprintDao: SprintDao,
                                 configurationDao: ConfigurationDao,
                                 configurationService: ConfigurationService,
                                 mongoOperation: MongoTemplate) {
    def getCurrent: Sprint = {
        Option(sprintDao.findOne(configurationDao.findByKey(ConfigurationKeys.CURRENT_SPRINT).value)) match {
            case Some(sprint) => transferFromEntity(sprint)
            case None => transferFromEntity(sprintDao.save(transferToEntity(defaultSprint)))
        }
    }

    private def getCurrentEntity: SprintEntity = {
        Option(sprintDao.findOne(configurationDao.findByKey(ConfigurationKeys.CURRENT_SPRINT).value)) match {
            case Some(sprint) => sprint
            case None => sprintDao.save(transferToEntity(defaultSprint))
        }
    }

    def saveNameToCurrent(sprintName: String): Sprint = {
        transferFromEntity(sprintDao.save(getCurrentEntity.copy(name = sprintName)))
    }

    def save(sprint: Sprint): Sprint = {
        transferFromEntity(sprintDao.save(transferToEntity(sprint)))
    }

    def saveAsCurrent(sprint: Sprint): Unit = {
        sprint.id.foreach(id => configurationService.saveOrReplace(ConfigurationKeys.CURRENT_SPRINT, id))
    }

    private def transferToEntity(sprint: Sprint): SprintEntity = {
        SprintEntity(sprint.name, sprint.from.toEpochSecond, sprint.to.toEpochSecond, id = sprint.id.orNull,
            goals = sprint.goals.map(goal => GoalEntity(goal.name, goal.owners,
                goal.state.toString, id = goal.id.getOrElse(new ObjectId().toString))))
    }

    private def transferFromEntity(sprintEntity: SprintEntity): Sprint = {
        Sprint(Option(sprintEntity.id), sprintEntity.name,
            ZonedDateTime.ofInstant(Instant.ofEpochSecond(sprintEntity.from), ZoneOffset.UTC.normalized()),
            ZonedDateTime.ofInstant(Instant.ofEpochSecond(sprintEntity.to), ZoneOffset.UTC.normalized()),
            sprintEntity.goals.toList.map((goalEntity) => Goal(Option(goalEntity.id), goalEntity.name, goalEntity.owners, GoalState.withName(goalEntity.state))))
    }

    def defaultSprint: Sprint = {
        Sprint(None, "Sprint", ZonedDateTime.now(), ZonedDateTime.now().plusDays(14), List(Goal(None, "asd"), Goal(None, "sdf")))
    }
}
