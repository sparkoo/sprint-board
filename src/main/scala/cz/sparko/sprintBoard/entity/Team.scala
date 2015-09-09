package cz.sparko.sprintBoard.entity

import scala.beans.BeanProperty

case class Team(@BeanProperty name: String) extends MongoEntity {

}
