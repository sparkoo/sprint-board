package cz.sparko.sprintBoard.repository.service

import cz.sparko.sprintBoard.entity.Release
import cz.sparko.sprintBoard.repository.dao.ReleaseDao
import cz.sparko.sprintBoard.repository.entity.ReleaseEntityFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import scala.collection.JavaConversions._

@Service
class ReleaseService @Autowired()(releaseDao: ReleaseDao) {

    def save(release: Release) = releaseDao.save(ReleaseEntityFactory(release))

    def getAll = releaseDao.findAll(new Sort("codefreezeTimestamp")).toList.map(r => r.toCoreEntity)

    def remove(id: String) = releaseDao.delete(id)
}
