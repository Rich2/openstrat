/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, wcode.*

object NewDevsPage extends OSDocumentationPage
{ override def titleStr: String = "New Developers Info"
  override def fileNameStem: String = "newdevs"

  override def body: HtmlBody = HtmlBody(HtmlH1("New Developers Info"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", contrib, gitCommands, sbtCommands)

  def contrib = HtmlP(
  """The easier way to make a contribution is through the Github web site. Either way will require a Github membership. If you are not experienced
  | with Scala, you have found this site and want to experiment, you will need to install Java JDK11+ and sbt. more complete documentation. For
  | getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and  Windows 7. Jdk 17 preferred.
  """.stripMargin)

  def gitCommands: HtmlUlWithLH = HtmlUlWithLH("<h2>Git Commands</h2> For transferring files from the master repository to your local machine and" --
    "back again.",
    HtmlLi("git clone https://github.com/Rich2/openstrat.git".htmlBash, """clone Used when you want to copy all the files locally (for the 1st time or when
    |you've deleted the project directory) to grab your own copy of openstrat from github""", HtmlA("https://github.com/Richtype/openstrat"), """to your local
    |folder.""".stripMargin),
    HtmlLi("cd openstrat".htmlBash, "The change to the newly created openstrat project folder."),
    HtmlLi("git pull origin master".htmlBash, "Bring your local copy up to date with Github."),
    HtmlLi("git add -A".htmlBash, "Staging: tell git you've made new files to add to the project."),
    HtmlLi("""git commit -m "A description of this commit."""".htmlBash, "Describe the changes."),
    HtmlLi("git push origin master".htmlBash, "Now push your local changes to master up to Github."),
    HtmlLi("""git commit -a -m "A description of this commit."""".htmlBash, """Staging without adding new files## push alternative method (you've only made
    |changes to existing files shorthand version of the above but will not take into account any new files you may have created."""),
    HtmlLi("git status".htmlBash, "To check the status of your copy of master."),
    HtmlLi("""git config --global credential.helper "cache --timeout=3600"""".htmlBash, """Cache user name / password store username/password for a set number",
    |of seconds, the next time you push""")
  )

  def sbtCommands = HtmlUlWithLH("<h3>Sbt</h3> A build utility. We also use Mill. Other well known build utilities are Ant, Maven and Make. It is" --
    "for compiling and running the applications and other tasks.",
    HtmlLi("clean".htmlSbt, "Gets rid of the cache"),
    HtmlLi("Util/clean".htmlSbt, "To clean an individual module")
  )
}
