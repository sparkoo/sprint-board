package cz.sparko.sprintBoard.repository.dto

import scala.beans.BeanProperty

case class TeamEntity(@BeanProperty name: String) extends MongoEntity("", 0)
