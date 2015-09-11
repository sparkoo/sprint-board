package cz.sparko.sprintBoard.repository.entity

import org.springframework.data.annotation.{Version, Id}
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document(collection = "configuration")
case class ConfigurationEntity(@BeanProperty @Indexed(unique = true) key: String,
                               @BeanProperty value: String,
                               @BeanProperty @Id id: String = null,
                               @BeanProperty @Version version: Long = 0)