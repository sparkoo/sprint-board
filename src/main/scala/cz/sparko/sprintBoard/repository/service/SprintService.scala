package cz.sparko.sprintBoard.repository.service

import cz.sparko.sprintBoard.entity.Sprint
import cz.sparko.sprintBoard.repository.dao.SprintDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import scala.collection.JavaConversions._

/**
 * methods always works with 'current' sprint
 */
@Service
class SprintService @Autowired()(sprintDao: SprintDao) {
    def getCurrent = {
        sprintDao.findByCurrent(true).toList match {
            case sprints if sprints.nonEmpty => sprints.head
            case _ => sprintDao.save(Sprint("sprint", 123, 234, current = true))
        }
    }

    def saveName(name: String) = {
        sprintDao.save(getCurrent.copy(name = name))
    }
}
