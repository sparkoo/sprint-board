package cz.sparko.sprintBoard.entity

import java.time.ZonedDateTime

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import cz.sparko.sprintBoard.entity.GoalState.GoalState

import scala.beans.BeanProperty

case class Sprint(id: Option[String], name: String, from: ZonedDateTime, to: ZonedDateTime, goals: List[Goal])

@JsonSerialize(using = classOf[GoalSerializer])
case class Goal(@BeanProperty id: Option[String],
                @BeanProperty name: String,
                @BeanProperty owners: String = "",
                @BeanProperty state: GoalState = GoalState.Open)

object GoalState extends Enumeration {
    type GoalState = Value
    val Open, InProgress, Finished = Value
}

class GoalSerializer extends JsonSerializer[Goal] {
    override def serialize(value: Goal, jgen: JsonGenerator, provider: SerializerProvider): Unit = {
        jgen.writeStartObject()
        jgen.writeStringField("id", value.id.orNull)
        jgen.writeStringField("name", value.name)
        jgen.writeStringField("owners", value.owners)
        jgen.writeStringField("state", value.state.toString)
        jgen.writeEndObject()
    }
}