/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*, wcode.*

object DevMisc extends HtmlSection
{ override def contents: RArr[XCon] = RArr(miscTitle, intellij, p6, table, p7)

  def miscTitle = HtmlH2("Place to put various notes, so as stuff doesn't get lost. It can be sorted into proper documentation later.")

  def intellij: HtmlUlWithLH = HtmlUlWithLH("For IntelliJ useful options:",
    HtmlLi("File => Editor => General -> Other -> tick Show quick documentation on mouse move."),
    HtmlLi("File => 'Build, Execution, Deployment' => Compiler -> Build project automatically"),
    HtmlLi("Project-Pane => Options -> 'Flatten packages'")
  )

  def git = HtmlP(
    BashLine("""git config --global user.name "MonaLisa""""),
    BashLine("git config --global user.name"),
    CodeOutputLine("MonaLisa"),
    BashLine("""git config --global user.email "YourEmail""""),
    BashLine("git config --global user.email"),
    CodeOutputLine("YourEmail")
  )

  def p6: HtmlP = HtmlP("So at least recent versions of Kubuntu the java command on the path, is at", dirOut("/usr/bin/java", "."), "It is a link to",
    dirOut("/etc/alternatives/java", "."), "This is also a link. To install a different java, install the JDK root folder in", dirOut("usr/lib/jvm", "."),
    """It
      |doesn't have to be here, but it makes it easier to go with convention. Run""".stripMargin,
    BashLine("sudo update-alternatives --config java"),
    "In my example this gives")

  def table = HtmlTable(
    HtmlRowHead.strs4("Selection", "Path", "Priority", "Status"),
    HtmlRow.strs4("0", "/usr/lib/jvm/java-11-openjdk-amd64/bin/java", "1111", "auto mode"),
    HtmlRow.strs4("1", "/usr/lib/jvm/java-11-openjdk-amd64/bin/java", "1111", "manual mode"),
    HtmlRow.strs4("2", "/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java", "1081", "manual mode")
  )

  def p7 = HtmlP("So leave the number as it is, then add to alternatives. I put the number 3 at then end because in my case slots 0 to 2 are already taken.",
    BashLine("sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk1.8.0_212/bin/java 3"),
    "then repeat",
    BashLine("sudo update-alternatives --config java"))
}
