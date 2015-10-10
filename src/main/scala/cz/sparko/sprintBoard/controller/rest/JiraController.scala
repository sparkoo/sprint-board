package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.entity.JiraIssue
import cz.sparko.sprintBoard.repository.service.{SprintService, TeamService, JiraClientService}
import org.springframework.beans.factory.annotation.{Value, Autowired}
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import scala.collection.JavaConversions._

@RestController
@RequestMapping(Array("/rest/issue"))
class JiraController @Autowired()(jiraClient: JiraClientService,
                                  teamService: TeamService,
                                  sprintService: SprintService) {
    @Value("${jira.label}") var label: String = _
    @Value("${jira.url}") var jiraUrl: String = ""

    @RequestMapping(Array("/sla"))
    def getSla: java.util.List[JiraIssue] =
        jiraClient.searchIssues(s"assignee in membersOf('${teamService.get.name}') and " +
            s"'SLA deadline' is not EMPTY and " +
            s"sprint = '${sprintService.getCurrent.name}' OR " +
            s"labels = '$label'")
            .map(issue => JiraIssue(issue.getKey, issue.getSummary,
                Option(issue.getAssignee) match {
                    case Some(a) => a.getDisplayName
                    case None => ""
                }, issue.getFixVersions.map(f => f.getName).mkString(", "), issue.getStatus.getName,
                jiraUrl + "/browse/" + issue.getKey))
}