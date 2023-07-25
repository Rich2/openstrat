/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

object NewDevsPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("New Developers Info", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("New Developers Info"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", contrib, p1, centralStr.xCon)

  def contrib = HtmlP("The easier way to make a contribution is through the Github web site. Either way will require a Github membership.")

  def p1 = HtmlP("If you are not experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK11 and" --
    "sbt. more complete documentation. For getting started on Linux / Windows / Mac will come later.The basic build has been tested on Linux and" --
    "Windows 7. Jdk 17 preferred.")

  def centralStr: String ="""
      |<h2>Git Commands and Sbt</h2>
      |
      |<h3>Git</h3> For transferring files from the master repository to your local machine and back again
      |<ul><li><code>git clone https://github.com/Rich2/openstrat.git</code> clone Used when you want to copy all the files locally (for the 1st time or when you've deleted the project directory) to grab your own copy of openstrat from github <a href="https://github.com/Richtype/openstrat"></a> to your local folder.</li>
      |<li><code>cd openstrat</code> The change to the newly created openstrat project folder.</li>
      |<li><code>git pull origin master</code> Bring your local copy up to date with Github.</li>
      |<li><code>git add -A</code> Staging: tell git you've made new files to the project</li>
      |<li><code>git commit -m "A description of this commit."</code> Describe the changes</li>
      |<li><code>git push origin master</code> Now push your local changes to master up to Github.</li>
      |<li><code>git commit -a -m "A description of this commit."</code> Staging without adding new files## push alternative method (you've only made changes to existing files shorthand version of the above but will not take into account any new files you may have created.</li>
      |<li><code>git status</code> To check the status of your copy of master</li>
      |<li><code>git config --global credential.helper "cache --timeout=3600"</code> Cache user/pswd store username/password for a set number of seconds, the next time you push</li>
      |</ul>
      |
      |
      |<h3>Sbt</h3> A build utility. We also use Mill. Other well known build utilities are Ant, Maven and Make. It is for compiling and running the applications and other tasks.
      |<ul>
      |  <li><code class=sbt>clean</code> Gets rid of the cache.</li>
      |  <li><code class=sbt>Util/clean</code> To clean an individual module.</li>
      |</ul>
      |""".stripMargin
}
