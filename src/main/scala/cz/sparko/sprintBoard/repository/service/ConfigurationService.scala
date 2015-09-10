package cz.sparko.sprintBoard.repository.service

import cz.sparko.sprintBoard.repository.dao.ConfigurationDao
import cz.sparko.sprintBoard.repository.dto.ConfigurationEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ConfigurationService @Autowired()(configurationDao: ConfigurationDao) {
    def saveOrReplace(key: String, value: String) {
        Option(configurationDao.findByKey(key)) match {
            case Some(conf) => {
                print(conf)
                configurationDao.save(conf.copy(value = value))
            }
            case None => configurationDao.save(ConfigurationEntity(key, value))
        }
    }
}
