package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.entity.Goal
import cz.sparko.sprintBoard.repository.service.{GoalService, SprintService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.{RequestParam, RequestMapping, RestController}

import scala.collection.JavaConversions._

@RestController
@RequestMapping(Array("/rest/goal"))
class GoalController @Autowired()(sprintService: SprintService,
                                  goalService: GoalService) {
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

    @RequestMapping(Array("/toggleState"))
    def toggleState(@RequestParam(value = "goalId", required = true) goalId: String): String = {
        goalService.toggleState(goalId).toString
    }

    @RequestMapping(value = Array("/updateName"), method = Array(POST))
    def updateName(@RequestParam(value = "goalId", required = true) goalId: String,
                   @RequestParam(value = "name", required = true) goalName: String): String = {
        goalService.updateName(goalId.split("-")(0), goalName).map(g => g.name).orNull
    }

    @RequestMapping(value = Array("/updateOwners"), method = Array(POST))
    def updateOwners(@RequestParam(value = "goalId", required = true) goalId: String,
                   @RequestParam(value = "owners", required = true) owners: String): String = {
        goalService.updateOwners(goalId.split("-")(0), owners).map(g => g.owners).orNull
    }

}
