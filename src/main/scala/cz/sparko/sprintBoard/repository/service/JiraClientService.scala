package cz.sparko.sprintBoard.repository.service

import java.net.URI
import javax.annotation.PreDestroy

import com.atlassian.jira.rest.client.api.JiraRestClient
import com.atlassian.jira.rest.client.api.domain.Issue
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory

import scala.collection.JavaConversions._

class JiraClientService(jiraClient: JiraRestClient) {

    def searchIssues(jqlQuery: String): List[Issue] = jiraClient.getSearchClient.searchJql(jqlQuery).claim().getIssues.toList

    def release = jiraClient.getProjectClient.getProject("SQ").claim().getVersions.toList
        .filter(p => !p.isReleased && p.getReleaseDate != null)

    @PreDestroy
    def close = jiraClient.close()
}

object JiraClientServiceFactory {
    def apply(url: String, username: String, password: String) = {
        val factory = new AsynchronousJiraRestClientFactory()
        val client = factory.createWithBasicHttpAuthentication(new URI(url), username, password)
        new JiraClientService(client)
    }
}