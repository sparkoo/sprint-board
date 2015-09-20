package cz.sparko.sprintBoard.repository.service

import java.util

import cz.sparko.sprintBoard.entity.GoalState.GoalState
import cz.sparko.sprintBoard.entity.{Goal, GoalState}
import cz.sparko.sprintBoard.repository.dao.GoalDao
import cz.sparko.sprintBoard.repository.entity.GoalEntityFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

@Service
class GoalService @Autowired()(goalDao: GoalDao) {

    def findByIds(ids: util.List[String]): List[Goal] =
        goalDao.findAll(ids).asScala.map(g => g.toCoreEntity).toList

    def save(goals: List[Goal]): List[Goal] = goals.map(g => goalDao.save(GoalEntityFactory(g))).map(g => g.toCoreEntity)

    def removeGoal(goalId: String) = goalDao.delete(goalId)

    def toggleState(goalId: String): GoalState =
        Option(goalDao.findOne(goalId))
            .map(g => g.toCoreEntity)
            .map(g => goalDao.save(GoalEntityFactory(g.copy(state = GoalState.toggle(g.state)))).toCoreEntity.state).orNull

    def updateName(goalId: String, goalName: String): Option[Goal] =
        Option(goalDao.findOne(goalId))
            .map(g => goalDao.save(GoalEntityFactory(g.toCoreEntity.copy(name = goalName))).toCoreEntity)

    def updateOwners(goalId: String, owners: String): Option[Goal] =
        Option(goalDao.findOne(goalId))
            .map(g => goalDao.save(GoalEntityFactory(g.toCoreEntity.copy(owners = owners))).toCoreEntity)
}
