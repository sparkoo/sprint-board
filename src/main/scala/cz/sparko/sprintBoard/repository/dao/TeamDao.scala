package cz.sparko.sprintBoard.repository.dao

import cz.sparko.sprintBoard.entity.Team
import org.springframework.data.mongodb.repository.MongoRepository

trait TeamDao extends MongoRepository[Team, String]
