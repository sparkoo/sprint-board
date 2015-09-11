package cz.sparko.sprintBoard.repository.entity

import java.util

import org.bson.types.ObjectId
import org.springframework.data.annotation.{Id, Version}
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document(collection = "sprints")
case class SprintEntity(@BeanProperty @Indexed(unique = true) name: String,
                        @BeanProperty from: Long,
                        @BeanProperty to: Long,
                        @BeanProperty @Id id: String = null,
                        @BeanProperty @Version version: Long = 0,
                        @BeanProperty goals: util.List[GoalEntity] = new util.ArrayList())

case class GoalEntity(@BeanProperty name: String, @BeanProperty @Id id: String = new ObjectId().toString)