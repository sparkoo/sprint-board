package cz.sparko.sprintBoard.repository.dao

import cz.sparko.sprintBoard.repository.entity.{GoalEntity, SprintEntity}
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria._
import org.springframework.data.mongodb.core.query.Query._
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service

@Service
class GoalDao @Autowired()(mongoOperation: MongoTemplate) {
    def update(sprintId: String, goal: GoalEntity) = {
        mongoOperation.updateFirst(query(where("_id").is(new ObjectId(sprintId)).and("goals._id").is(new ObjectId(goal.id))),
            new Update().unset("goals.$.name").unset("goals.$.owners").unset("goals.$.state"), classOf[SprintEntity])

        mongoOperation.updateFirst(query(where("_id").is(new ObjectId(sprintId)).and("goals._id").is(new ObjectId(goal.id))),
            new Update().set("goals.$.name", goal.name).set("goals.$.owners", goal.owners).set("goals.$.state", goal.state),
            classOf[SprintEntity])
    }

    def add(sprintId: String, goal: GoalEntity) = {
        ???
    }
}
