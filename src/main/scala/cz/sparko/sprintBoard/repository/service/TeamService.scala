package cz.sparko.sprintBoard.repository.service

import cz.sparko.sprintBoard.entity.Team
import cz.sparko.sprintBoard.repository.dao.TeamDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import scala.collection.JavaConversions._

/**
 * Service ensure that there is only one entry in Team document
 * @param teamDao
 */
@Service
class TeamService @Autowired()(teamDao: TeamDao) {
    def save(name: String) = {
        teamDao.findAll.toList match {
            case teams if teams.nonEmpty => teamDao.save(teams.head.copy(name = name))
            case _ => teamDao.save(Team(name))
        }
    }

    def get = {
        teamDao.findAll.toList match {
            case teams if teams.nonEmpty => teams.head.name
            case _ => ""
        }
    }
}
