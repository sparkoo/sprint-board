package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.entity.Goal
import cz.sparko.sprintBoard.repository.service.SprintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestParam, RequestMapping, RestController}
import scala.collection.JavaConversions._

@RestController
@RequestMapping(Array("/rest/goal"))
class GoalController @Autowired()(sprintService: SprintService) {
    @RequestMapping(Array("/currentGoals"))
    def getCurrentSprintGoals(): java.util.List[Goal] = {
        sprintService.getCurrent.goals.toList
    }

    @RequestMapping(Array("/goals"))
    def getSprintGoals(@RequestParam(value = "sprintId", required = true) sprintId: String): java.util.List[Goal] = {
        sprintService.findById(sprintId) match {
            case Some(sprint) => sprint.goals.toList
            case None => List().toList
        }
    }
}
