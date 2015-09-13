package cz.sparko.sprintBoard.entity

import java.time.ZonedDateTime

import cz.sparko.sprintBoard.entity.GoalState.GoalState

import scala.beans.BeanProperty

case class Sprint(id: Option[String], name: String, from: ZonedDateTime, to: ZonedDateTime, goals: List[Goal])

case class Goal(@BeanProperty id: Option[String],
                @BeanProperty name: String,
                @BeanProperty owners: String = "",
                @BeanProperty state: GoalState = GoalState.Open)

object GoalState extends Enumeration {
    type GoalState = Value
    val Open, InProgress, Finished = Value
}