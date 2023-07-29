/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

object NewDevsPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("New Developers Info", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("New Developers Info"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", contrib, gitCommands, sbtCommands)

  def contrib = HtmlP(
  """The easier way to make a contribution is through the Github web site. Either way will require a Github membership. If you are not experienced
  | with Scala, you have found this site and want to experiment, you will need to install Java JDK11+ and sbt. more complete documentation. For
  | getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and  Windows 7. Jdk 17 preferred.
  """.stripMargin)

  def gitCommands: HtmlUlWithLH = HtmlUlWithLH("<h2>Git Commands</h2> For transferring files from the master repository to your local machine and" --
    "back again.",
    HtmlLi.bashAndText("""git clone https://github.com/Rich2/openstrat.git""", "clone Used when you want to copy all the files locally (for the" --
      "1st time or when you've deleted the project directory) to grab your own copy of openstrat from github" --
      """<a href="https://github.com/Richtype/openstrat"></a> to your local folder."""),
    HtmlLi.bashAndText("cd openstrat", "The change to the newly created openstrat project folder."),
    HtmlLi.bashAndText("git pull origin master", "Bring your local copy up to date with Github."),
    HtmlLi.bashAndText("git add -A", "Staging: tell git you've made new files to add to the project."),
    HtmlLi.bashAndText("""git commit -m "A description of this commit."""", "Describe the changes."),
    HtmlLi.bashAndText("git push origin master", "Now push your local changes to master up to Github."),
    HtmlLi.bashAndText("""git commit -a -m "A description of this commit."""", "Staging without adding new files## push alternative method (you've" --
      "only made changes to existing files shorthand version of the above but will not take into account any new files you may have created."),
    HtmlLi.bashAndText("git status", "To check the status of your copy of master."),
    HtmlLi.bashAndText("""git config --global credential.helper "cache --timeout=3600"""", "Cache user name / pasword store username/password for" --
      "a set number of seconds, the next time you push")
  )

  def sbtCommands = HtmlUlWithLH("<h3>Sbt</h3> A build utility. We also use Mill. Other well known build utilities are Ant, Maven and Make. It is" --
    "for compiling and running the applications and other tasks.",
    HtmlLi.sbtAndText("clean", "Gets rid of the cache"),
    HtmlLi.sbtAndText("Util/clean", "To clean an individual module")
  )
}
