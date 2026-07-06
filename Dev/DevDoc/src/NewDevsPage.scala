/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, wcode.*

/** HTML page for new developers. */
object NewDevsPage extends DevPageBase
{ override def titleStr: String = "New Developers Info"
  override def fileStemStr: String = "newdevs"
  override def body: BodyHtml = BodyHtml("New Developers Info".h1, central, ScriptHtml.jsSrc("newdevs.js"))

  def central: DivHtml = DivHtml.classAtt("central", contrib, sysUpdate, pUpdaters, jvms, jvmsAlt, sbtInstall, intellij, git, gitCommands, sbtCommands, chrome,
    sublime, sshServer)

  def contrib = PHtml("""The easier way to make a contribution is through the Github web site. Either way will require a Github membership. If you are not
  |experienced with Scala, you have found this site and want to experiment, you will need to install Java JDK11+ and sbt. more complete documentation. For
  |getting started on Linux / Windows / Mac will come later. The basic build has been tested on Linux and  Windows 7. Jdk 17 preferred.""".stripMargin)

  def pUpdaters: PHtml = PHtml(updaterExplain, LabelInputsLine(opSysLTI, javaVerLNI))
  
  val sysUpdate = DivHtml.listenOptHtml(opSysIUT){ opt =>    
    val code: RArr[XCon] = opt match
    { case UbuntuDeriv => RArr(BashLine("apt sudo update", "sudo apt upgrade"))
      case ArchDeriv => RArr(BashLine("sudo pacman -Syu"))
      case _ => RArr("No code available")
    }
    DivHtml("System update") %: code 
  }

  val jvms: Section = javaInstall(Section)

  val sbtDiv: DivHtml = DivHtml.listenOptHtml(opSysIUT){
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
  
  def intellij: Section = Section("Intellij IDEA".h2,
    BashLine("sudo tar -xzf idea-2026.1.4.tar.gz -C /opt"),
    UlSection("For IntelliJ useful options:",
      LiHtml("File => Editor => General -> Other -> tick Show quick documentation on mouse move."),
      LiHtml("File => 'Build, Execution, Deployment' => Compiler -> Build project automatically"),
      LiHtml("Project-Pane => Options -> 'Flatten packages'"))
  )
  
  def git: Section = Section("Git and Github".h2,
    "Set git user name",
    BashLine("""git config --global user.name "MonaLisa""""),
    "Check user name properly set",
    BashLine("git config --global user.name"),
    CodeOutputLine("MonaLisa"),
    "Set git email",
    BashLine("""git config --global user.email "YourEmail""""),
    "Check email properly set",
    BashLine("git config --global user.email"),
    CodeOutputLine("YourEmail"),
    "Store Github username and token and other useful git commands.",
    BashLine("git config --global credential.helper store"),
    BashLine("git remote show origin"),
    BashLine("git init --bare myrepo.git"),
    BashLine("git push -u origin NewBranch")
  )
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

  def chrome: Section = Section.listenOptHtml(opSysIUT){ ops =>
    val last = ops match
    { case UbuntuDeriv => RArr(
        BashLine ("wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"),
        BashLine ("sudo apt install ./google-chrome-stable_current_amd64.deb"),
        "If any errors appear about missing dependencies you may need to ‘force install.",
        BashLine ("sudo apt -f install")
      )
      case ArchDeriv => RArr(BashLine("sudo pacman -S chromium"))
      case _ => RArr("No code available")
    }
    "Chrome / Chromium".h2 %: last
  }

  def sublime: Section = Section("Sublime Text 4".h2,
    BashLine("wget -qO - https://download.sublimetext.com/sublimehq-pub.gpg | sudo tee /etc/apt/keyrings/sublimehq-pub.asc > /dev/null"),
    BashLine("""echo -e 'Types: deb\nURIs: https://download.sublimetext.com/\nSuites: apt/stable/\nSigned-By: /etc/apt/keyrings/sublimehq-pub.asc' |""" --
    "sudo tee /etc/apt/sources.list.d/sublime-text.sources"),
    BashLine("sudo apt update"),
    BashLine("sudo apt install sublime-text"),
    BashLine("subl --version"),
    CodeOutputLine("Sublime Text Build 4200"),

    CodeLineHtml("// These settings override both User and Default settings for the Scala syntax"),
    CodeLineHtml("{"),
    CodeLineHtml(""""tab_size": 2,"""),
    CodeLineHtml(""""translate_tabs_to_spaces": true,"""),
    CodeLineHtml(""""rulers": [100, 160]"""),
    CodeLineHtml("}")
  )

  def sshServer: Section = Section("SSH Server".h2,
    "This is normally installed on Linux VPSs, but for home machines",
    DivHtml.listenOptHtml(opSysIUT){
      case UbuntuDeriv => RArr(
        BashLine("Sudo apt install openssh-server"),
        BashLine("sudo systemctl enable --now ssh")
      )
      case ArchDeriv => RArr(
        BashLine("sudo pacman -S openssh"),
        BashLine("sudo systemctl enable --now ssh"),
        BashLine("sudo ufw allow 22/tcp")
      )
      case _ => RArr("No code available")
    }
  )
}