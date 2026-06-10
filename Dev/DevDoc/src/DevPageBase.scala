/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, wcode.*

/** HTML documentation page for the Dev Module. */
trait DevPageBase extends OSDocumentationPage, PageUpdaterOS
{
  def miscContents: RArr[XCon] = RArr(git, intellij, chrome, sublime)

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

  def javaInstall = LiHtml("Install Java. Currently suggesting Java 25 LTS. Note the jdk at the end of the version.",
    DivHtml.listenOptionNum(opNameIUT, javaVerIUN){ (ops, vNum) =>
      ops match {
        case UbuntuDeriv => RArr(BashLine(s"sudo apt install openjdk-${vNum.str0}-jdk -y"))
        case ArchDeriv => RArr(BashLine(s"sudo pacman -Syu ${vNum.str0}-jdk"))
        case _ => RArr("No code available.")
      }
    },
    "Check the version",
    BashLine("java -version"),
    CodeOutputLines("""openjdk version "25.0.2" 2025-09-16""",
      "OpenJDK Runtime Environment (build 25+36-Ubuntu-1)",
      "OpenJDK 64-Bit Server VM (build 25+36-Ubuntu-1, mixed mode, sharing)"),
    "Open the all users environment configuration file",
    BashLine("sudo nano /etc/environment"),
    "Add line",
    BashLine("JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64"),
    "Save and exit (Ctrl-X and then Y)",
    BashLine("sudo reboot"),
    "After reboot or logging in again for remote server",
    BashLine("echo $JAVA_HOME"),
    CodeOutputLine("/usr/lib/jvm/java-25-openjdk-amd64")
  )

  def jvmsOld: Section = Section("JVMs".h2,
    BashLine("sudo apt install openjdk-25-jdk"),
    "For Kubuntu/ Ubuntu add the following line to", HtmlDirPath("/etc/environment"), "file.",
    CodeLineHtml("JAVA_HOME=/usr/lib/jvm/java-1.25.0-openjdk-amd64"),
    "For Arch, CachyOs",
    CodeLineHtml("JAVA_HOME=/usr/lib/jvm/java-25-openjdk"),
    "So at least recent versions of Kubuntu the java command on the path, is at", dirOut("/usr/bin/java", "."), "It is a link to",
    dirOut("/etc/alternatives/java", "."), "This is also a link. To install a different java, install the JDK root folder in", dirOut("usr/lib/jvm", "."),
    """It doesn't have to be here, but it makes it easier to go with convention. Run""".stripMargin,
    BashLine("sudo update-alternatives --config java"),
    "In my example this gives",

    TableHtml(
      RowHeadHtml.strs4("Selection", "Path", "Priority", "Status"),
      RowHtml.strs4("0", "/usr/lib/jvm/java-25-openjdk-amd64/bin/java", "2511", "auto mode"),
      RowHtml.strs4("1", "/usr/lib/jvm/java-21-openjdk-amd64/bin/java", "2111", "manual mode"),
      RowHtml.strs4("2", "/usr/lib/jvm/java-25-openjdk-amd64/bin/java", "2511", "manual mode")
    ),

    PHtml("So leave the number as it is, then add to alternatives. I put the number 3 at then end because in my case slots 0 to 2 are already taken.",
      BashLine("sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-17-openjdk-amd64/bin/java 3"),
      "then repeat",
      BashLine("sudo update-alternatives --config java")
    )
  )

  def intellij: Section = Section("Intellij IDEA".h2,
    BashLine("sudo tar -xzf idea-2026.1.2.tar.gz -C /opt"),
    UlSection("For IntelliJ useful options:",
      LiHtml("File => Editor => General -> Other -> tick Show quick documentation on mouse move."),
      LiHtml("File => 'Build, Execution, Deployment' => Compiler -> Build project automatically"),
      LiHtml("Project-Pane => Options -> 'Flatten packages'"))
  )

  def chrome: Section = Section("Chrome".h2,
    BashLine("wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"),
    BashLine("sudo apt install ./google-chrome-stable_current_amd64.deb"),
    "If any errors appear about missing dependencies you may need to ‘force install.",
    BashLine("sudo apt -f install")
  )

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
}