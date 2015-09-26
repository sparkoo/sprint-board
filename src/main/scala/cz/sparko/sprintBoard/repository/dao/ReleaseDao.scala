package cz.sparko.sprintBoard.repository.dao

import cz.sparko.sprintBoard.repository.entity.ReleaseEntity
import org.springframework.data.mongodb.repository.MongoRepository

trait ReleaseDao extends MongoRepository[ReleaseEntity, String]
