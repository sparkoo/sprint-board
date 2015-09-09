package cz.sparko.sprintBoard.entity

import org.springframework.data.annotation.Id

import scala.beans.BeanProperty

abstract class MongoEntity {
    @Id @BeanProperty var id: String = _
}
