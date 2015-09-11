package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.repository.service.SprintService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

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

    @RequestMapping(Array("/getCurrent"))
    def getCurrent() = {
        sprintService.getCurrent.name
    }
}
