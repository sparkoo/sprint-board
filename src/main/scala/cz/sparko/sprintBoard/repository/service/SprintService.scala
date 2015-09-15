package cz.sparko.sprintBoard.repository.service

import java.time._

import cz.sparko.sprintBoard.entity.{Goal, Sprint}
import cz.sparko.sprintBoard.repository.dao.{ConfigurationDao, SprintDao}
import cz.sparko.sprintBoard.repository.entity._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * methods always works with 'current' sprint
 */
@Service
class SprintService @Autowired()(sprintDao: SprintDao,
                                 configurationDao: ConfigurationDao,
                                 configurationService: ConfigurationService,
                                 goalService: GoalService) {

    def getCurrent: Sprint = {
        findById(configurationDao.findByKey(ConfigurationKeys.CURRENT_SPRINT).value).getOrElse(save(defaultSprint))
    }

    def findById(id: String): Option[Sprint] = {
        Option(sprintDao.findOne(id)).map(s => s.toCoreEntity.copy(goals = goalService.findByIds(s.goals)))
    }

    private def getCurrentEntity: SprintEntity = {
        Option(sprintDao.findOne(configurationDao.findByKey(ConfigurationKeys.CURRENT_SPRINT).value)) match {
            case Some(sprint) => sprint
            case None => SprintEntityFactory(save(defaultSprint))
        }
    }

    def addGoal(sprintId: String, goal: Goal) = {
        findById(sprintId).map(s => save(s.copy(goals = goal :: s.goals)))
    }

    def removeGoal(sprintId: String, goalId: String) = {
        findById(sprintId).map(s => save(s.copy(goals = s.goals.filter(g => g.id.getOrElse("") != goalId))))
            .foreach(_ => goalService.removeGoal(goalId))
    }

    def saveNameToCurrent(sprintName: String): Sprint = {
        sprintDao.save(getCurrentEntity.copy(name = sprintName)).toCoreEntity
    }

    def save(sprint: Sprint): Sprint = {
        sprintDao.save(SprintEntityFactory(sprint.copy(goals = goalService.save(sprint.goals)))).toCoreEntity
    }

    def saveAsCurrent(sprint: Sprint): Unit = {
        sprint.id.foreach(id => configurationService.saveOrReplace(ConfigurationKeys.CURRENT_SPRINT, id))
    }

    def defaultSprint: Sprint = {
        Sprint(None, "Sprint", ZonedDateTime.now(), ZonedDateTime.now().plusDays(14), List())
    }
}
