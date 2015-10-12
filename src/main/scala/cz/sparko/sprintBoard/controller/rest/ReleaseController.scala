package cz.sparko.sprintBoard.controller.rest

import java.time.{Instant, ZoneId, ZonedDateTime}

import cz.sparko.sprintBoard.entity.Release
import cz.sparko.sprintBoard.repository.service.{JiraClientService, ReleaseService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.{RequestMapping, RequestParam, RestController}

import scala.collection.JavaConverters._

@RestController
@RequestMapping(Array("/rest/release"))
class ReleaseController @Autowired()(releaseService: ReleaseService,
                                     jiraClient: JiraClientService) {

    @RequestMapping(value = Array("/save"), method = Array(POST))
    def save(@RequestParam(value = "version", required = true) version: String,
             @RequestParam(value = "codefreeze", required = true) codefreeze: String,
             @RequestParam(value = "release", required = true) release: String) = {
        val splittedCodefreeze = codefreeze.split("\\.")
        val codefreezeDate = ZonedDateTime.of(splittedCodefreeze(2).toInt, splittedCodefreeze(1).toInt, splittedCodefreeze(0).toInt, 0, 0, 0, 0, ZoneId.of("Z"))

        val splittedRelease = release.split("\\.")
        val releaseDate = ZonedDateTime.of(splittedRelease(2).toInt, splittedRelease(1).toInt, splittedRelease(0).toInt, 0, 0, 0, 0, ZoneId.of("Z"))

        releaseService.save(Release(None, version, codefreezeDate, releaseDate))
    }

    @RequestMapping(value = Array("/getAll"))
    def getAll: java.util.List[Release] = {
        jiraClient.release
            .map(r => Release(None, r.getName,
                makeDateFromDescription(r.getDescription).getOrElse(ZonedDateTime.ofInstant(Instant.ofEpochMilli(0), ZoneId.of("Z"))),
                ZonedDateTime.ofInstant(r.getReleaseDate.toDate.toInstant, ZoneId.systemDefault())))
            .sortWith((r1, r2) => r1.codefreeze.compareTo(r2.codefreeze) < 0)
            .asJava
    }

    @RequestMapping(value = Array("/remove"), method = Array(POST))
    def remove(@RequestParam(value = "id", required = true) id: String) = {
        releaseService.remove(id)
        true
    }

    def makeDateFromDescription(description: String) = {
        val regex = """(\d{1,2})\. ?{1,2}(\d{1,2})\. ?{1,2}(\d{4})""".r
        regex.findFirstMatchIn(description).map(m => ZonedDateTime.of(m.group(3).toInt, m.group(2).toInt, m.group(1).toInt, 0, 0, 0, 0, ZoneId.of("Z")))
    }
}