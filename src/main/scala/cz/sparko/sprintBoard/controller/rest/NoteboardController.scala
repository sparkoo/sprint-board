package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.repository.entity.ConfigurationKeys
import cz.sparko.sprintBoard.repository.service.ConfigurationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

@RestController
@RequestMapping(Array("/rest/noteboard"))
class NoteboardController @Autowired()(val configurationService: ConfigurationService) {
    @RequestMapping(method = Array(GET))
    def get = configurationService.get(ConfigurationKeys.NOTEBOARD).value

    @RequestMapping(method = Array(POST), value = Array("/save"))
    def save(@RequestParam("text") text: String) = configurationService.saveOrReplace(ConfigurationKeys.NOTEBOARD, text)
}
