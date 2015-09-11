package cz.sparko.sprintBoard.repository.dao

import cz.sparko.sprintBoard.repository.dto.SprintEntity
import org.springframework.data.mongodb.repository.MongoRepository

trait SprintDao extends MongoRepository[SprintEntity, String]