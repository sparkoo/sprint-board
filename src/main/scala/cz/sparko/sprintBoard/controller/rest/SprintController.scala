package cz.sparko.sprintBoard.controller.rest

import java.time.format.DateTimeFormatter

import cz.sparko.sprintBoard.entity.{Sprint, Goal}
import cz.sparko.sprintBoard.repository.service.SprintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod.{POST, GET}
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, ResponseBody, RestController}

@RestController
@RequestMapping(Array("/rest/sprint"))
class SprintController @Autowired()(val sprintService: SprintService) {
    @RequestMapping(method = Array(POST), value = Array("/saveName"))
    def saveName(@RequestParam("value") name: String) = {
        sprintService.saveNameToCurrent(name).name
    }

    @RequestMapping(Array("/getAll"))
    def getAll() = {

    }

    def getCurrentSprintName() = {
        sprintService.getCurrent.name
    }

    @RequestMapping(Array("/getCurrent"))
    def getCurrentSprintId() = {
        sprintService.getCurrent.id.getOrElse("")
    }

    @RequestMapping(method = Array(POST, GET), value = Array("/addGoal"))
    @ResponseBody
    def addGoal(@RequestParam("sprintId") sprintId: String,
                @RequestParam("goalName") goalName: String,
                @RequestParam("goalOwners") goalOwners: String): Sprint = {
        sprintService.addGoal(sprintId, Goal(None, goalName, goalOwners)).get
    }

    @RequestMapping(method = Array(POST), value = Array("/removeGoal"))
    def removeGoal(@RequestParam("sprintId") sprintId: String, @RequestParam("goalId") goalId: String): Boolean = {
        sprintService.removeGoal(sprintId, goalId)
        true
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyy")

    def getCurrentSprintFromFormatted(): String = {
        sprintService.getCurrent.from.format(dateFormatter)
    }

    def getCurrentSprintToFormatted(): String = {
        sprintService.getCurrent.to.format(dateFormatter)
    }


}
