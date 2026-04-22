/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, HtmlStrExts.*, wcode.*

/** HTML documentation page for the Dev Module. */
object DevPage extends OSDocumentationPage, PageHtmlUpdater
{ override val titleStr: String = "Dev Module"
  override val fileNameStemStr: String = "dev"

  override def body: BodyHtml = BodyHtml(titleStr.h1, central)
  val scVer: String = "3.8.3"

  def central: DivHtml = DivHtml.classAtt("central", list, p1, p2, p3, p4, sbtCommands1, sbtCommands2, sbt3D, DevMisc, credits)
  def list: OlSection = OlSection("The Dev module contains".h2,
    LiHtml("JavaFx application selection and developer settings for the different apps.", AHtml("osapp.jar", "Runnable jar")),
    LiHtml("Generates the HTML and CSS files for the website, including this file."),
    LiHtml("Creates Servlets for Tomcat / Jetty Servers.")
  )

  def p1: PHtml = PHtml("It currently works on JavaFx and web page. Using canvas on both platforms. See", "../api/index.html", "Scala Docs".htmlPath, "and see",
    linkOut("../apiJs/index.html", "Scala Docs for JavaScript target", "."))

  def p2: PHtml = PHtml("""The Strategy games was the original motivation for the project, but the geometry and graphics library have far wider applicability.
  |The geometry and graphics are far more developed, while the tiling and strategy games are still in a far more experimental stage. This is in accordance with
  |the original vision, part of which was to explore the possibilities of an algebra of tiling.""".stripMargin)

  def p3: PHtml = PHtml("""I would welcome input from developers with greater experience and knowledge than my own. One of the goals of the project is to
  |explore, where it is best to compose with trait / class inheritance and where to use functions. When to use mutation and when to use immutability. When to
  |use smart, garbage collected heap based objects and when to use dumb data values. Balancing the competing priorities of elegance, succinctness, readability,
  |run-time performance, compile time performance and accessibility for inexperienced programmers. I feel Scala is, and in particular Scala 3 will be the ideal
  |language to explore these questions.""".stripMargin)
  
  def p4: PHtml = PHtml("Versions", UlHtml("Latest published Openstrat version 0.3.12, current 0.3.13snap",
    s"Scala: $scVer",
    "Jdk: 23+ required for JavaFx modules. 25 preferred.",
    "Scala.Js: 1.21.0",
    "Scala Native: 0.5.10",
    "JavaFx: 25.0.2",
    "Sbt: 1.12.9",
    "Mill: 1.1.5 with Mill's JDK set to 25."))

  def sbtCommands1: UlSection = UlSection(RArr("Run", CodeHtmlInline("sbt"), "in bash from project's root folder. From within the sbt console run:"),
    LiHtml("~DevFx/reStart".htmlSbt, "To launch a ScalaFx window. The most useful command for development."),
    LiHtml("~DicelessJs/fastOptJS".htmlSbt, "To rebuild a fast optimised JavaScript file. Use with", "Dev/DevPages/DicelessSbtFast.html".htmlPath),
    LiHtml("DicelessJs/fullOptJS".htmlSbt, "To build a full optimised JavaScript file. Use with", "Dev/DevPages/DicelessSbtFull.html".htmlPath),
    LiHtml("~Util/test".htmlSbt, "Rerun tests on Util module."),
    LiHtml("~Geom/test".htmlSbt, "Rerun tests on Geom module."),
    LiHtml("~Tiling/test".htmlSbt, "Rerun tests on Tiling module."),
    LiHtml("~Dev/test".htmlSbt, "Rerun tests on, Dev module."),
    LiHtml("~Util/test; Geom/test: Tiling/test; EGrid/test; Dev/test".htmlSbt, "Rerun tests on the 5 modules."),

    LiHtml("unidoc".htmlSbt, "Will produce docs for all the main code in all the modules for the Jvm platform. They can be found in",
    s"target/$scVer/unidoc/".htmlPath),

    LiHtml("JsAgg/unidoc".htmlSbt, "Will produce docs for all the main code in all the modules for the Javascript platform. They can be found in",
    s"Dev/JsAgg/target/$scVer/unidoc/".htmlPath),

    LiHtml("bothDoc".htmlSbt, "Will perform both the above tasks.")
  )

  def sbtCommands2: PHtml = PHtml("The tilde", "~".htmlSbt, """ tells sbt to rerun the command every time you modify and save a source file. The first command
  |will build and launch a ScalaFx window. It will rebuild and relaunch so you can immediately see the effects of your changes. Copy the""".stripMargin,
  "Dev/Misc/DevSettings.rson".htmlPath, "file to the", "Dev/User".htmlPath, "folder. Creating the directory if not already existing. Change the",
  "appStr".htmlPath, "setting in", "Dev/User/DevSettings.rson".htmlPath, "to change the application. All the examples on the", AHtml("richstrat.com"),
  """website are available plus others.The second command will also rebuild on source changes in similar manner. However unlike with the reStart command, when
  |you make a source file edit and save it, you will have to manually refresh the browser window after the fastOptJS command has finished the rebuild.""".
  stripMargin)

  def sbt3D = PHtml("For JavaFx 3D ", SbtInline("""set DevFx/reStart/mainClass:= Some("ostrat.pFx.App3D")"""))
  
  def credits: UlSection = UlSection("Credits".h3,
    LiHtml(AHtml("https://lampwww.epfl.ch/~doeraene/thesis/", "Sébastien Doeraene, Ph.D. thesis"), "for Scala.js"),
    LiHtml(AHtml("https://www.patreon.com/lihaoyi", "Li Haoyi"), "for Mill and uTest.")
  )
}