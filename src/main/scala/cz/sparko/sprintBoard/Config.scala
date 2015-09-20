package cz.sparko.sprintBoard

import cz.sparko.sprintBoard.repository.service.{JiraClientServiceFactory, JiraClientService}
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration
import org.springframework.context.annotation.{Bean, Import}

@SpringBootApplication
@Import(Array(classOf[MongoConfig]))
class Config extends WebMvcAutoConfiguration {

    @Bean
    def jiraClientService(@Value("${jira.url}") url: String,
                          @Value("${jira.username}") username: String,
                          @Value("${jira.password}") password: String): JiraClientService =
        JiraClientServiceFactory(url, username, password)
}