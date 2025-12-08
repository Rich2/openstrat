/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, wcode.*

/** Miscellaneous dev stuff. */
object DevMisc extends HtmlSection
{ override def contents: RArr[XCon] = RArr(intellij, git, jvms, sbtInstall, chrome, sublime)

  def intellij = SectionH2("Intellij IDEA",
    BashLine("sudo tar -xzf idea-2025.3.tar.gz -C /opt"),
    HtmlUlWithLH("For IntelliJ useful options:",
    HtmlLi("File => Editor => General -> Other -> tick Show quick documentation on mouse move."),
    HtmlLi("File => 'Build, Execution, Deployment' => Compiler -> Build project automatically"),
    HtmlLi("Project-Pane => Options -> 'Flatten packages'"))
  )

  def git: SectionH2 = SectionH2(
    "Git and Github",
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
    BashLine("git init --bare myrepo.git")
  )

  def jvms: SectionH2 = SectionH2("JVMs",
    "So at least recent versions of Kubuntu the java command on the path, is at", dirOut("/usr/bin/java", "."), "It is a link to",
    dirOut("/etc/alternatives/java", "."), "This is also a link. To install a different java, install the JDK root folder in", dirOut("usr/lib/jvm", "."),
    """It doesn't have to be here, but it makes it easier to go with convention. Run""".stripMargin,
    BashLine("sudo update-alternatives --config java"),
    "In my example this gives",

    HtmlTable(
      HtmlRowHead.strs4("Selection", "Path", "Priority", "Status"),
      HtmlRow.strs4("0", "/usr/lib/jvm/java-25-openjdk-amd64/bin/java", "2511", "auto mode"),
      HtmlRow.strs4("1", "/usr/lib/jvm/java-21-openjdk-amd64/bin/java", "2111", "manual mode"),
      HtmlRow.strs4("2", "/usr/lib/jvm/java-25-openjdk-amd64/bin/java", "2511", "manual mode")
    ),

     HtmlP("So leave the number as it is, then add to alternatives. I put the number 3 at then end because in my case slots 0 to 2 are already taken.",
     BashLine("sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/java-17-openjdk-amd64/bin/java 3"),
     "then repeat",
     BashLine("sudo update-alternatives --config java")
    )
  )

  def sbtInstall = SectionH2("Sbt install",
  BashLine("""echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | sudo tee /etc/apt/sources.list.d/sbt.list"""),
  BashLine("""echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | sudo tee /etc/apt/sources.list.d/sbt_old.list"""),
  BashLine("""curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | sudo tee /etc/apt/trusted.gpg.d/sbt.asc"""),
  BashLine("sudo apt update"),
  BashLine("sudo apt install sbt"))

  def chrome: SectionH2 = SectionH2("Chrome",
    BashLine("wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"),
    BashLine("sudo apt install ./google-chrome-stable_current_amd64.deb"),
    "If any errors appear about missing dependencies you may need to ‘force install.",
    BashLine("sudo apt -f install")
  )

  def sublime = SectionH2(
  "Sublime Text 4",
  BashLine("curl -fSsL https://download.sublimetext.com/sublimehq-pub.gpg | gpg --dearmor | sudo tee /usr/share/keyrings/sublimehq-pub.gpg > /dev/null"),
  BashLine("""echo 'deb [signed-by=/usr/share/keyrings/sublimehq-pub.gpg] https://download.sublimetext.com/ apt/stable/' | sudo tee -a /etc/apt/sources.list.d/sublime-text.list""".stripMargin),
  BashLine("sudo apt update"),
  BashLine("sudo apt install sublime-text"),
  BashLine("subl --version"),
  CodeOutputLine("Sublime Text Build 4200"),

  HtmlCodeLine("// These settings override both User and Default settings for the Scala syntax"),
  HtmlCodeLine("{"),
  HtmlCodeLine(""""tab_size": 2,"""),
  HtmlCodeLine(""""translate_tabs_to_spaces": true,"""),
  HtmlCodeLine(""""rulers": [100, 160]"""),
  HtmlCodeLine("}")
  )
}
