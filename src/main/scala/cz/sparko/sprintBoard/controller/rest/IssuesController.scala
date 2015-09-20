package cz.sparko.sprintBoard.controller.rest

import cz.sparko.sprintBoard.repository.service.JiraClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

import scala.collection.JavaConversions._

@RestController
@RequestMapping(Array("/rest/issue"))
class IssuesController @Autowired()(jiraClient: JiraClientService) {
    @RequestMapping(Array("/sla"))
    def getSla: java.util.List[String] = jiraClient.searchIssues("assignee in membersOf('SGS Team') and 'SLA deadline' is not EMPTY and sprint = 'Sprint 30'").map(issue => issue.getAssignee.getDisplayName)
}
