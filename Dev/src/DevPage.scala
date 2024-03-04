/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** HTML page for Dev Module. */
object DevPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Dev Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Dev Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", list, p1, p2, p3, p4, sbt1, sbt2, p5, sbtJs, intellij, miscTitle, p6, table, p7, credits)

  def list: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("The Dev module contains"), appSel, siteGen)

  def appSel: HtmlLi = HtmlLi("JavaFx application selection and developer settings for the different apps.")

  def siteGen: HtmlLi = HtmlLi("Generates the HTML files for the website, including this file.")

  def miscTitle = HtmlH2("Place to put various notes, so as stuff doesn't get lost. It can be sorted into proper documentation later.")

  def p1: HtmlP = HtmlP("It currently works on JavaFx and web page. Using canvas on both platforms. See" --
    """<a href="../api/index.html">Scala Docs</a> and See <a href="../apiJs/index.html">Scala Docs for JavaScript target.</a>")""")

  def p2: HtmlP = HtmlP("The Strategy games was the original motivation for the project, but the geometry and graphics library have far wider" --
    "applicability. The geometry and graphics are far more developed, while the tiling and strategy games are still in a far more experimental" --
    "stage. This is in accordance with the original vision, part of which was to explore the possibilities of an Algebra of Tiling.")

  def p3: HtmlP = HtmlP("I would welcome input from developers with greater experience and knowledge than my own. One of the goals of the project" --
    "is to explore, where it is best to compose with trait / class inheritance and where to use functions. When to use mutation and when to use" --
    "immutability. When to use smart, garbage collected heap based objects and when to use dumb data values. Balancing the competing priorities of" --
    "elegance, succinctness, readability, run-time performance, compile time performance and accessibility for inexperienced programmers. I feel" --
    "Scala is, and in particular Scala 3 will be the ideal language to explore these questions.")

  def p4: HtmlP = HtmlP("Scala currently set to 3.4.0. Jdk 11+, 11 preferred. Scala.Js set to 1.15.0. Scala native set to 0.4.16. Sbt currently" --
    "set to 1.9.9 (uses the openstrat.sbt file). Note(probably due to the JavaFx dependency). Sbt will not work running on Windows in Git Bash. Update" --
    "your Mill to 0.11.6.")

  def sbt1: HtmlOlWithLH = HtmlOlWithLH("Run <code>sbt</code> in bash from project's root folder.<br>From within the sbt console run:")

  def sbt2: HtmlUl = HtmlUl(
    HtmlLi.sbtAndText("~ Dev/reStart", "To launch a ScalaFx window. The most useful command for development."),

    HtmlLi.sbtAndText("~ DicelessJs/fastOptJS", "To rebuild a fast optimised JavaScript file. Use with" --
      "Dev/DevPages/DicelessSbtFast.html".htmlPath),

    HtmlLi.sbtAndText("DicelessJs/fullOptJS", "To build a full optimised JavaScript file. Use with" -- "Dev/DevPages/DicelessSbtFull.html".htmlPath),
    HtmlLi.sbtAndText("~ Util/test", "Rerun tests on Util module."),
    HtmlLi.sbtAndText("~ Tiling/test", "Rerun tests on Tiling module."),
    HtmlLi.sbtAndText("~ Dev/test", "Rerun tests on, Dev module."),
    HtmlLi.sbtAndText("~ Util/test; Tiling/test; Dev/test", "Rerun tests on Util module."),

    HtmlLi.sbtAndText("DocMain/doc", "Will produce docs for all the main code in all the modules for the Jvm platform. They can be found in" --
      "Dev/SbtDir/DocMain/target/scala-3.3.1/api/".htmlPath),

    HtmlLi.sbtAndText("DocJs/doc", "Will produce docs for all the main code in all the modules for the Javascript platform. They can be found in" --
      "Dev/SbtDir/DocJs/target/DocMain/target/scala-3.3.1/api/".htmlPath),

    HtmlLi.sbtAndText("bothDoc", "Will perform both the above tasks.")
  )

  def p5: HtmlP = HtmlP("The tilde <code>~</code> tells sbt to rerun the command every time you modify and save a source file. The first command" --
    "will build and launch a ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes.Copy the" --
    "Dev/Misc/DevSettings.rson".htmlPath -- "file to the" -- "Dev/User".htmlPath -- "folder. Creating the directory and its" +
    " parents if not already existing. Change the appStr setting in"-- "Dev/User/DevSettings.rson".htmlPath -- "to change the application. All the" --
    "examples on the richstrat.com website are available plus others.The second command will also rebuild on source changes in similar manner." --
    "However unlike with the reStart command, when you make a source file edit and save it, you will have to manually refresh the browser window" --
    "after the fastOptJS command has finished the rebuild.")

  def sbtJs: HtmlUlWithLH = HtmlUlWithLH("For sbt js apps add the relevant source directory for the app you want to build",
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/EG1300App""""),
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/EG1000App""""),

    HtmlLi("""set Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/EG640App"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.EG640JsApp")""".stripMargin),

    HtmlLi("""set Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/DicelessApp"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.DicelessJsApp")""".stripMargin),

    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/UnitLocApp""""),
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/BC305App""""),
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/GlApp""""),
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/ZugApp""""),

    HtmlLi("""set Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/WW1App"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.WW1JsApp")""".stripMargin),

    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/WW2App""""),

    HtmlLi("""set Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/IndRevApp"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.IndRevJsApp")""".stripMargin),

    HtmlLi("""set Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/DungeonApp"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.DungeonJsApp")""".stripMargin),

    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/SorsApp""""),
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/PericuloApp""""),
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/Peri2App""""),
    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/Y1492App""""),

    HtmlLi("""set Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/PlanetsApp"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.PlanetsJsApp")""".stripMargin),

    HtmlLi(""" set Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/FlagsApp"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.FlagsJsApp")""".stripMargin),

    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/CivRiseApp"
    |<br>set Compile/mainClass:= Some("ostrat.pSJs.CivRiseJsApp")""".stripMargin),

    HtmlLi("""Compile/unmanagedSourceDirectories += (ThisBuild/baseDirectory).value / "Apps/JsAppsSrc/ChessApp"""")
  )

  def intellij: HtmlUlWithLH = HtmlUlWithLH("For IntellliJ useful options:",
    HtmlLi("File => Editor => General -> Other -> tick Show quick documentation on mouse move."),
    HtmlLi("File => 'Build, Execution, Deployment' => Compiler -> Build project automatically"),
    HtmlLi("Project-Pane => Options -> 'Flatten packages'")
  )

  def p6: HtmlP = HtmlP("So at least recent versions of Kubuntu the java command on the path, is at" -- "/usr/bin/java".htmlPath +". It is a link" --
    "to" --   "/etc/alternatives/java".htmlPath + ". This is also a link. To install a different java, install the JDK root folder in" --
    "usr/lib/jvm".htmlPath + ". It doesn't have to be here, but it makes it easier to go with convention. Run<br>" ---
    "sudo update-alternatives --config java".htmlBash ---
    "<br>In my example this gives<br>")

  def table = HtmlTable(
    HtmlRowHead.strs4("Selection", "Path", "Priority", "Status"),
    HtmlRow.strs4("0", "/usr/lib/jvm/java-11-openjdk-amd64/bin/java", "1111", "auto mode"),
    HtmlRow.strs4("1", "/usr/lib/jvm/java-11-openjdk-amd64/bin/java", "1111", "manual mode"),
    HtmlRow.strs4("2", "/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java", "1081", "manual mode")
  )

  def p7 = HtmlP("So leave the number as it is, then add to alternatives. I put the number 3 at then end because in my case slots 0 to 2 are" --
    "already taken.<br>" ---
    "sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk1.8.0_212/bin/java 3".htmlBash + "<br>" ---
    "then repeat<br>" --- "sudo update-alternatives --config java".htmlBash)

  def credits: HtmlUlWithLH = HtmlUlWithLH("<h3>Credits</h3>",
    HtmlLi.linkAndText("https://lampwww.epfl.ch/~doeraene/thesis/", "Sébastien Doeraene, Ph.D. thesis", "for Scala.js"),
    HtmlLi.linkAndText("https://www.patreon.com/lihaoyi", "Li Haoyi", "for Mill and uTest.")
  )
}