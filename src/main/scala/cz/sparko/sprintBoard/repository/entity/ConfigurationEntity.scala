package cz.sparko.sprintBoard.repository.entity

import cz.sparko.sprintBoard.entity.ConfigurationProperty
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
case class ConfigurationEntity(@BeanProperty @Indexed(unique = true) key: String,
                               @BeanProperty value: String,
                               @BeanProperty id: String = null)
    extends MongoEntity[ConfigurationProperty](id) {

    override def toCoreEntity: ConfigurationProperty = ConfigurationProperty(key, value)
}

object ConfigurationEntityFactory {
    def apply(configurationProperty: ConfigurationProperty) = ConfigurationEntity(configurationProperty.key, configurationProperty.value)
}
