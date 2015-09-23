package cz.sparko.sprintBoard.repository.entity

import java.time.{ZoneOffset, Instant, ZonedDateTime}

import cz.sparko.sprintBoard.entity.Release
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
case class ReleaseEntity(@BeanProperty version: String,
                         @BeanProperty codefreezeTimestamp: Long,
                         @BeanProperty releaseTimestamp: Long,
                         @BeanProperty id: String = null)
    extends MongoEntity[Release](id) {
    override def toCoreEntity: Release = Release(Option(id), version,
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(codefreezeTimestamp), ZoneOffset.UTC.normalized()),
        ZonedDateTime.ofInstant(Instant.ofEpochSecond(releaseTimestamp), ZoneOffset.UTC.normalized()))
}

object ReleaseEntityFactory {
    def apply(release: Release) =
        ReleaseEntity(release.version, release.codefreeze.toEpochSecond, release.release.toEpochSecond, release.id.orNull)
}