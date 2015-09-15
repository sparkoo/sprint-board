package cz.sparko.sprintBoard.repository.service

import cz.sparko.sprintBoard.entity.Goal
import cz.sparko.sprintBoard.repository.dao.GoalDao
import cz.sparko.sprintBoard.repository.entity.GoalEntityFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._
import java.util

@Service
class GoalService @Autowired()(goalDao: GoalDao) {
    def findByIds(ids: util.List[String]): List[Goal] = {
        goalDao.findAll(ids).asScala.map(g => g.toCoreEntity).toList
    }

    def save(goals: List[Goal]): List[Goal] = {
        goals.map(g => goalDao.save(GoalEntityFactory(g))).map(g => g.toCoreEntity)
    }

    def removeGoal(goalId: String) = goalDao.delete(goalId)
}
