/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, WebExts.*, wcode.*

/** HTML documentation page for the Dev Module. */
trait DevPageBase extends OSDocumentationPage, PageUpdaterOS
{
  //def miscContents: RArr[XCon] = RArr()

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

  
}