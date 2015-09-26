package cz.sparko.sprintBoard.entity

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.{SerializerProvider, JsonSerializer}

import scala.beans.BeanProperty

@JsonSerialize(using = classOf[ReleaseSerializer])
case class Release(@BeanProperty id: Option[String],
                   @BeanProperty version: String,
                   @BeanProperty codefreeze: ZonedDateTime,
                   @BeanProperty release: ZonedDateTime)

class ReleaseSerializer extends JsonSerializer[Release] {
    val dateFormatter = DateTimeFormatter.ofPattern("d.M.yyy")

    override def serialize(value: Release, jgen: JsonGenerator, provider: SerializerProvider): Unit = {
        jgen.writeStartObject()
        jgen.writeStringField("id", value.id.orNull)
        jgen.writeStringField("version", value.version)
        jgen.writeStringField("codefreeze", value.codefreeze.format(dateFormatter))
        jgen.writeStringField("release", value.release.format(dateFormatter))
        jgen.writeEndObject()
    }
}