package cz.sparko.sprintBoard.entity

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

case class Sprint(id: Option[String], name: String, from: ZonedDateTime, to: ZonedDateTime, goals: List[Goal]) {
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyy")

    def getFormattedFrom: String = {
        from.format(dateFormatter)
    }

    def getFormattedTo: String = {
        to.format(dateFormatter)
    }
}

case class Goal(name: String)