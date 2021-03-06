package cz.sparko.sprintBoard

import cz.sparko.sprintBoard.repository.dao.{ConfigurationDao, GoalDao}
import cz.sparko.sprintBoard.repository.entity.{ConfigurationEntity, ConfigurationKeys}
import cz.sparko.sprintBoard.repository.service.{SprintService, TeamService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class Initialization @Autowired()(configurationDao: ConfigurationDao,
                                  sprintService: SprintService,
                                  teamService: TeamService,
                                  goalDao: GoalDao) extends ApplicationListener[ContextRefreshedEvent] {
    override def onApplicationEvent(event: ContextRefreshedEvent): Unit = {
        if (configurationDao.count() == 0) {
            configurationDao.save(ConfigurationEntity(ConfigurationKeys.TEAM_NAME, teamService.defaultTeam.name))
            sprintService.saveAsCurrent(sprintService.save(sprintService.defaultSprint))
        }
    }
}