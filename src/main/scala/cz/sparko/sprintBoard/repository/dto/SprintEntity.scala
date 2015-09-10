package cz.sparko.sprintBoard.repository.dto

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
case class SprintEntity(@BeanProperty @Indexed(unique = true) name: String,
                        @BeanProperty from: Long,
                        @BeanProperty to: Long)
    extends MongoEntity
