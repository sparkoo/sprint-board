package cz.sparko.sprintBoard.repository.dao

import cz.sparko.sprintBoard.repository.entity.ConfigurationEntity
import org.springframework.data.mongodb.repository.MongoRepository

trait ConfigurationDao extends MongoRepository[ConfigurationEntity, String] {
    def findByKey(key: String): ConfigurationEntity
}
