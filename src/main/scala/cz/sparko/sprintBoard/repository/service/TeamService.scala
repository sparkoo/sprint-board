package cz.sparko.sprintBoard.repository.service

import cz.sparko.sprintBoard.entity.Team
import cz.sparko.sprintBoard.repository.dao.ConfigurationDao
import cz.sparko.sprintBoard.repository.dto.{ConfigurationEntity, ConfigurationKeys}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.stereotype.Service

/**
 * Service ensure that there is only one entry in Team document
 */
@Service
class TeamService @Autowired()(configurationDao: ConfigurationDao,
                               mongoOperation: MongoTemplate) {
    def save(name: String): Team = {
        mongoOperation.updateFirst(query(where("key").is(ConfigurationKeys.TEAM_NAME)), update("value", name), classOf[ConfigurationEntity])
        Team(mongoOperation.findOne(query(where("key").is(ConfigurationKeys.TEAM_NAME)), classOf[ConfigurationEntity]).value)
//        Option(configurationDao.findByKey(ConfigurationKeys.TEAM_NAME)) match {
//            case Some(conf) => {
//                println(conf)
//                Team(configurationDao.save(conf.copy(value = name)).value)
//            }
//            case None => Team(configurationDao.save(ConfigurationEntity(ConfigurationKeys.TEAM_NAME, name)).value)
//        }
    }

    def get: Team = {
        Option(configurationDao.findByKey(ConfigurationKeys.TEAM_NAME)) match {
            case Some(conf) => Team(conf.value)
            case None => defaultTeam
        }
    }

    def defaultTeam: Team = {
        Team("Empty team")
    }
}
