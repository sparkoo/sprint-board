package cz.sparko.sprintBoard.repository.dao

import cz.sparko.sprintBoard.entity.Sprint
import org.springframework.data.mongodb.repository.MongoRepository

trait SprintDao extends MongoRepository[Sprint, String] {
    def findByCurrent(current: Boolean): java.util.List[Sprint]
}