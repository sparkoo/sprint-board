package cz.sparko.sprintBoard.repository.service

import java.time._

import cz.sparko.sprintBoard.entity.Sprint
import cz.sparko.sprintBoard.repository.dao.{ConfigurationDao, SprintDao}
import cz.sparko.sprintBoard.repository.dto.{ConfigurationKeys, SprintEntity}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

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

    def saveName(sprintName: String): Sprint = {
        transferFromEntity(sprintDao.save(getCurrentEntity.copy(name = sprintName)))
    }

    def save(sprint: Sprint): Sprint = {
        transferFromEntity(sprintDao.save(transferToEntity(sprint)))
    }

    def saveAsCurrent(sprint: Sprint): Unit = {
        configurationService.saveOrReplace(ConfigurationKeys.CURRENT_SPRINT, sprintDao.findByName(sprint.name).id)
    }

    private def transferToEntity(sprint: Sprint): SprintEntity = {
        SprintEntity(sprint.name, sprint.from.toEpochSecond, sprint.to.toEpochSecond)
    }

    private def transferFromEntity(sprintEntity: SprintEntity): Sprint = {
        Sprint(sprintEntity.name,
            ZonedDateTime.ofInstant(Instant.ofEpochSecond(sprintEntity.from), ZoneOffset.UTC.normalized()),
            ZonedDateTime.ofInstant(Instant.ofEpochSecond(sprintEntity.to), ZoneOffset.UTC.normalized()))
    }

    def defaultSprint: Sprint = {
        Sprint("Sprint", ZonedDateTime.now(), ZonedDateTime.now().plusDays(14))
    }
}
