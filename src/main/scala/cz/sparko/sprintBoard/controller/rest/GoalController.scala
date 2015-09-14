package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.entity.Goal
import cz.sparko.sprintBoard.repository.service.SprintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
import scala.collection.JavaConversions._

@RestController
class GoalController @Autowired()(sprintService: SprintService) {
    def getCurrentSprintGoals(): java.util.List[Goal] = {
        sprintService.getCurrent.goals.toList
    }
}
