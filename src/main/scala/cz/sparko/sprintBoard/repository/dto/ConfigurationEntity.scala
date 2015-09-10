package cz.sparko.sprintBoard.repository.dto

import org.springframework.data.mongodb.core.index.Indexed

import scala.beans.BeanProperty

case class ConfigurationEntity(@BeanProperty @Indexed(unique = true) key: String,
                               @BeanProperty value: String)
    extends MongoEntity

