/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*

/** Versionless. Creates POM files and copies Mill, JAR, artifacts for Apps JVM module. */
object AppsPommer extends OsModuleJvmVerless(DirsRel("Apps"), "apps", RArr(EGridPommer, TilingPommer, GeomPommer, UtilPommer), RArr())

/** The top level HTML documentation page for the apps. */
object AppsPage extends OSDocumentationPage
{ override def titleStr: String = "Applications Module"
  override val fileNameStemStr: String = "apps"
  override def body: BodyHtml = BodyHtml(H1Html("Apps Module"), main)
  def main: DivHtml = DivHtml.classAtt("main", stratList, otherTiled, otherApps)
  def egameDir: String = (dirsRel </ AppPage.egameDir).asStr
  def otDir: String = ".." / AppPage.otDir.asStr

  def stratList: OlSection = OlSection(H2Html("Strategy Games using tiled world maps."),
    LiHtml(AHtml(egameDir / "dicelessapp.html", "DiceLess"), "A simple simultaneous turn multi player game set in Europe in 1900. As the name suggests"
      -- "no random element."),
    LiHtml(AHtml(egameDir / "periculoapp.html", "Periculo Fundatuso"), "A simple consecutive turn, world map game that has some grounding in earth" --
      "geography."),
    LiHtml(AHtml(egameDir / "ww2app.html", "World War II Game"), "Using 460km scale."),

    LiHtml(AHtml(egameDir / "bc305app.html", "BC305"), """A grand strategy game with a start point of 305BC, using 220km scale. This is the game that most
    | interests me.""".stripMargin),

    LiHtml(AHtml(egameDir / "ww1app.html", "World War I Game"), "A 120km hex game."),
    LiHtml(AHtml(egameDir / "sorsapp.html", "Sors Imperiorum"), "A game where empires appear at set times according to history. Uses 220km hex scale"),

    LiHtml(AHtml(egameDir / "discovapp.html", "Age of Discovery"),
      "A grand strategy game with a start point of 1492. It also uses an 160km scale world map."),

    LiHtml(AHtml(egameDir / "indrevapp.html", "IndRev"), "A grand strategy game with a start point of 1783. It also uses a 220km scale worldmap. This" --
      "is the second game that most interests me."),
  )

  def otherTiled: OlSection = OlSection(H2Html("Other Tiled Map Applications."),
    LiHtml(AHtml(otDir / "unitlocapp.html", "Unit Locator"), "Locates military units and gives information for a given date and time."),
    LiHtml(AHtml(otDir / "zugapp.html", "Zug Fuhrer"), "A Tactical strategy game with a 20 metre hex scale."),
    LiHtml(AHtml(otDir / "dungeonapp.html", "Dungeon Game"), "A Tactical strategy game on square tiles with a 0.5 metre tile scale."),
    LiHtml(AHtml(otDir / "civriseapp.html", "Civ Rise"), "A 4X strategy game using hexs. Its main use so far has been to develop a generalised" --
      "side terrain. The terrain can be designed for the game, so can be neater than terrain to model prexsisting terrain, whether from the real world or a" --
      "pre exsting fantasy world.")
  )

  def otherApps: OlSection = OlSection(H2Html("Other Apps"),
    LiHtml("Geometry and Graphics Tutorials"),

    LiHtml(AHtml(otDir / "planetsapp.html", "Planets"),
      "Mostly knocked togethor quickly some time back. I've included it next just because its different."),

    LiHtml("Simultaneous turn, tile based tutorial games."),

    LiHtml(AHtml(otDir / "flagsapp.html", "Flags"),
      "Just some flags using the graphics module. Thanks to Rod and Stephen who did most of the work on this."),

    LiHtml(AHtml(otDir / "chessapp.html", "Chess"), "Not completed."))
}