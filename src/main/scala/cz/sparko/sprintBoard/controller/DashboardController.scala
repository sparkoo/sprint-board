package cz.sparko.sprintBoard.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class DashboardController {
    @RequestMapping(Array("/")) def index() = "home"
}
