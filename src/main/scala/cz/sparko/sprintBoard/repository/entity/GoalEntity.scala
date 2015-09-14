package cz.sparko.sprintBoard.repository.entity

import cz.sparko.sprintBoard.entity.{GoalState, Goal}
import org.springframework.data.mongodb.core.mapping.Document

import scala.beans.BeanProperty

@Document
case class GoalEntity(@BeanProperty name: String,
                      @BeanProperty owners: String,
                      @BeanProperty state: String,
                      @BeanProperty id: String = null)
    extends MongoEntity[Goal](id) {

    override def toCoreEntity: Goal = Goal(Option(id), name, owners, GoalState.withName(state))
}

object GoalEntityFactory {
    def apply(goal: Goal) = GoalEntity(goal.name, goal.owners, goal.state.toString, goal.id.orNull)
}