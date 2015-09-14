package cz.sparko.sprintBoard.repository.dao

import cz.sparko.sprintBoard.repository.entity.GoalEntity
import org.springframework.data.mongodb.repository.MongoRepository

trait GoalDao extends MongoRepository[GoalEntity, String]
