package cz.sparko.sprintBoard.repository.dto

import org.springframework.data.annotation.{Version, Id}
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
case class SprintEntity(@BeanProperty @Indexed(unique = true) name: String,
                        @BeanProperty from: Long,
                        @BeanProperty to: Long,
                        @BeanProperty @Id id: String = null,
                        @BeanProperty @Version version: Long = 0)
    extends MongoEntity(id, version)
