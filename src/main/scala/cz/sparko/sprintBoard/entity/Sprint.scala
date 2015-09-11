package cz.sparko.sprintBoard.entity

import java.time.ZonedDateTime

case class Sprint(id: String, name: String, from: ZonedDateTime, to: ZonedDateTime)
