package cz.sparko.sprintBoard

import com.mongodb.{BasicDBObject, Mongo, MongoClient}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories
class MongoConfig extends AbstractMongoConfiguration {

    @Bean
    override def mongo(): Mongo = {
        val mongo = new MongoClient()
//        mongo.getDB(getDatabaseName).getCollection("sprintEntity").createIndex(new BasicDBObject("name", 1), new BasicDBObject("unique", true))
        mongo.getDB(getDatabaseName).getCollection("configuration").createIndex(new BasicDBObject("key", 1), new BasicDBObject("unique", true))
        mongo
    }

    override def getDatabaseName: String = "sprint-board"

    override def getMappingBasePackage: String = "cz.sparko.sprintBoard.repository.dto"

    @Bean
    def mongoOperation(): MongoTemplate = {
        new MongoTemplate(mongo(), getDatabaseName)
    }
}

