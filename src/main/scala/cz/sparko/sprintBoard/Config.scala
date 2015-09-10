package cz.sparko.sprintBoard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(Array(classOf[MongoConfig]))
class Config extends WebMvcAutoConfiguration {

}