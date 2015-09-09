package cz.sparko.sprintBoard.entity

import org.springframework.data.mongodb.core.index.Indexed

import scala.beans.BeanProperty

case class Sprint(@BeanProperty @Indexed(unique = true) name: String,
                  @BeanProperty from: Long,
                  @BeanProperty to: Long,
                  @BeanProperty current: Boolean)
    extends MongoEntity
