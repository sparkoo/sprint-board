package cz.sparko.sprintBoard.repository.entity

import java.time.{ZoneOffset, Instant, ZonedDateTime}
import java.util

import cz.sparko.sprintBoard.entity.Sprint
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty
import scala.collection.JavaConversions._

@Document
case class SprintEntity(@BeanProperty @Indexed(unique = true) name: String,
                        @BeanProperty from: Long,
                        @BeanProperty to: Long,
                        @BeanProperty goals: util.List[String],
                        @BeanProperty id: String = null)
    extends MongoEntity[Sprint](id) {

    override def toCoreEntity: Sprint = toCoreEntity(List())

    def toCoreEntity(goalsEntities: List[GoalEntity]) = Sprint(Option(id), name,
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(from), ZoneOffset.UTC.normalized()),
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(from), ZoneOffset.UTC.normalized()),
        goalsEntities.map(g => g.toCoreEntity))

    def this(coreEntity: Sprint) = this(coreEntity.name, coreEntity.from.toEpochSecond, coreEntity.to.toEpochSecond,
        coreEntity.goals.map(g => g.id.orNull).toList, coreEntity.id.orNull)
}