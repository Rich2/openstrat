/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, wcode.*

object NewDevsPage extends DevPageBase
{ override def titleStr: String = "New Developers Info"
  override def fileStemStr: String = "newdevs"
  override def body: BodyHtml = BodyHtml("New Developers Info".h1, central, ScriptHtml.jsSrc("newdevs.js"))

  def central: DivHtml = DivHtml.classAtt("central", contrib, pUpdaters, jvms, sbtInstall, gitCommands, sbtCommands)

  def contrib = PHtml("""The easier way to make a contribution is through the Github web site. Either way will require a Github membership. If you are not
  |experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK11+ and sbt. more complete documentation. For
  |getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and  Windows 7. Jdk 17 preferred.""".stripMargin)

  def pUpdaters: PHtml = PHtml(updaterExplain, LabelInputsLine(opNameLTI, javaVerLNI))
  
  val jvms = javaInstall

  val sbtDiv: DivHtml = DivHtml.listenOption(opNameIUT){
    case UbuntuDeriv => RArr(
      BashLine("""echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list"""),
      BashLine("""echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt_old.list"""),
      "Curl is installed by default in Kubuntu 26.04 and 25.10, it is not in Kubuntu 24.04 so if curl is not installed you need",
      BashLine("sudo apt install curl"),
      BashLine("""curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo tee
      |/etc/apt/trusted.gpg.d/sbt.asc""".stripMargin),
      BashLine("sudo apt update"),
      BashLine("sudo apt install sbt")
    )
    case ArchDeriv => RArr(BashLine("sudo pacman -S sbt"))
    case _ => RArr(DivHtml("No code available."))
  }
  val sbtInstall: Section = Section("Sbt install".h2, sbtDiv)


  def gitCommands: Section = Section("Git Commands".h2,
    "For transferring files from the master repository to your local machine and back again.",
    gitCommandList)

  def gitCommandList =  UlHtml(
    LiHtml("git clone https://github.com/Rich2/openstrat.git".htmlBash, """clone Used when you want to copy all the files locally (for the 1st time or when
    you've deleted the project directory) to grab your own copy of openstrat from github""".stripMargin,
    AHtml("https://github.com/Richtype/openstrat"), "to your local folder."),

    LiHtml("cd openstrat".htmlBash, "The change to the newly created openstrat project folder."),
    LiHtml("git pull origin master".htmlBash, "Bring your local copy up to date with Github."),
    LiHtml("git add -A".htmlBash, "Staging: tell git you've made new files to add to the project."),
    LiHtml("""git commit -m "A description of this commit."""".htmlBash, "Describe the changes."),
    LiHtml("git push origin master".htmlBash, "Now push your local changes to master up to Github."),
    LiHtml("""git commit -a -m "A description of this commit."""".htmlBash, """Staging without adding new files## push alternative method (you've only made
    |changes to existing files shorthand version of the above but will not take into account any new files you may have created."""),
    LiHtml("git status".htmlBash, "To check the status of your copy of master."),
    LiHtml("""git config --global credential.helper "cache --timeout=3600"""".htmlBash, """Cache user name / password store username/password for a set number",
    |of seconds, the next time you push""")
  )

  def sbtCommands = Section("Sbt".h2, """A build utility. We also use Mill. Other well known build utilities are Ant, Maven and Make. It is for compiling and
  |running the applications and other tasks.""".stripMargin,
  UlHtml(
    LiHtml("clean".htmlSbt, "Gets rid of the cache"),
    LiHtml("Util/clean".htmlSbt, "To clean an individual module")
  ))
}
