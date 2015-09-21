package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.entity.JiraIssue
import cz.sparko.sprintBoard.repository.service.{SprintService, TeamService, JiraClientService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import scala.collection.JavaConversions._

@RestController
@RequestMapping(Array("/rest/issue"))
class JiraController @Autowired()(jiraClient: JiraClientService,
                                  teamService: TeamService,
                                  sprintService: SprintService) {
    @RequestMapping(Array("/sla"))
    def getSla: java.util.List[JiraIssue] =
        jiraClient.searchIssues(s"assignee in membersOf('${teamService.get.name}') and 'SLA deadline' is not EMPTY and sprint = '${sprintService.getCurrent.name}'")
            .map(issue => JiraIssue(issue.getKey, issue.getSummary, issue.getAssignee.getDisplayName, issue.getFixVersions.map(f => f.getName).mkString(", "), issue.getStatus.getName))
}
