package cz.sparko.sprintBoard.controller.rest

import java.time.ZonedDateTime

import cz.sparko.sprintBoard.entity.{Goal, Sprint}
import cz.sparko.sprintBoard.repository.service.SprintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod.{GET, POST}
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, ResponseBody, RestController}

@RestController
@RequestMapping(Array("/rest/sprint"))
class SprintController @Autowired()(val sprintService: SprintService) {
    @RequestMapping(method = Array(POST), value = Array("/updateFrom"))
    def updateFrom(@RequestParam(value = "sprintId", required = true) sprintId: String,
                   @RequestParam(value = "fromValue", required = true) fromValue: String) = {
        sprintService.findById(sprintId)
            .map(s => sprintService.save(
            s.copy(from = ZonedDateTime.parse(fromValue))))
    }

    @RequestMapping(method = Array(POST), value = Array("/updateTo"))
    def updateTo(@RequestParam(value = "sprintId", required = true) sprintId: String,
                 @RequestParam(value = "toValue", required = true) toValue: String) = {
        sprintService.findById(sprintId)
            .map(s => sprintService.save(
            s.copy(to = ZonedDateTime.parse(toValue))))
    }

    @RequestMapping(method = Array(POST), value = Array("/saveName"))
    def saveName(@RequestParam("value") name: String) = {
        sprintService.saveNameToCurrent(name).name
    }

    def getCurrentSprintName = {
        sprintService.getCurrent.name
    }

    @RequestMapping(Array("/getCurrent"))
    def getCurrentSprint = {
        sprintService.getCurrent match {
            case s => (s.id.getOrElse(""), s.name)
        }
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

    @RequestMapping(Array("/dates"))
    def getCurrentSprintFrom = {
        (sprintService.getCurrent.from.toString, sprintService.getCurrent.to.toString)
    }


}
