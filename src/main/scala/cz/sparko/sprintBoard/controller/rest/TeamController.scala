package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.repository.service.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/rest/team"))
class TeamController @Autowired()(val teamService: TeamService) {
    @RequestMapping(method = Array(GET))
    def get = {
        teamService.get
    }

    @RequestMapping(method = Array(POST))
    def save(@RequestParam("value") name: String) = {
        teamService.save(name)
    }
}
