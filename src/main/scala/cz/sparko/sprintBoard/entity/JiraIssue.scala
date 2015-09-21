package cz.sparko.sprintBoard.entity

import scala.beans.BeanProperty

case class JiraIssue(@BeanProperty task: String,
                     @BeanProperty name: String,
                     @BeanProperty assignee: String,
                     @BeanProperty fixVersion: String,
                     @BeanProperty state: String)
