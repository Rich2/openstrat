/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** An HTML Page for running an application. We may want a separate page for the documentation */
class AppPage(val jsMainStem: String, val dirRel: DirsRel, htmlTitleIn: String = "", htmlFileStemIn: String = "") extends HtmlPage
{ /** The [[String]] for the HTML title element. */
  val pageTitleStr: String = htmlTitleIn.emptyMap(jsMainStem)

  /** HTML and javaScript file name stem to which the ".html" or the ".js" extension will be added. */
  val filesStem: String = htmlFileStemIn.emptyMap(jsMainStem.toLowerCase)

  /** HTML file name including the ".html" extension. */
  def htmlFileName: String = filesStem + ".html"

  /** The directory location with the website as a [[String]]. */
  def htmlPathName: DirsFileRel = dirRel /> htmlFileName

  /** JavaScript file name including the ".js" extension. */
  def jsFileName: String = filesStem + ".js"

  /** The HTML path and full file name as a [[String]]. */
  def htmlPathNameStr: String = dirRel /% htmlFileName

  override def head: HtmlHead = HtmlHead.titleCss(pageTitleStr, "../only")

  def topMenu: HtmlUl =
  { val pages: RArr[AppPage] = AppPage.allTops.filterNot(_.jsMainStem == jsMainStem)
    val pairs1: ArrPairStr[DirsFileRel] = pages.mapPair(_.filesStem){ linkPage => linkPage.htmlPathName }
    val pairs2: ArrPairStr[DirsFileRel] = PairStrElem("Home", DirsFileRel("index.html")) %: pairs1
    AppPage.topMenu(pairs2, dirRel)
  }

  override def body: HtmlBody = HtmlBody(topMenu, HtmlCanvas.id("scanv"), HtmlScript.jsSrc(jsFileName), HtmlScript.main(jsMainStem + "Js"))
}

/** Companion object for [[AppPage]] class. Contains factory apply methods directory paths and list of app links. Longer term may need reorganisation, */
object AppPage
{ /** Factory apply method for [[AppPage]] class. The first parameter is the stem of the name of the main function in the JavaScript file, to which "JsApp"
   * [[String]] is appended. The second parameter is the file name's stems to which the [[String]]s ".html" and ",js" will be added. The default is the lower
   * case of the first parameter. The third parameter is the title, which unlike the first two parameters can contain spaces which defaults to the first
   * parameter. */
  def apply(jsMainStem: String, dirPath: DirsRel, htmlTitleIn: String = "", htmlFileNameStem: String = ""): AppPage =
    new AppPage(jsMainStem, dirPath, htmlTitleIn, htmlFileNameStem)

  val egameDir: DirsRel = DirsRel("earthgames")
  val mapDir: DirsRel = DirsRel("egrids")
  val otDir: DirsRel = DirsRel("otherapps")

  val dicelessApp: AppPage = AppPage("DicelessApp", egameDir, "DiceLess")
  val ww2App: AppPage = AppPage("WW2App", egameDir)  
  val periculoApp: AppPage = AppPage("PericuloApp", egameDir, "Periculo Fundato")
  val bcApp: AppPage = AppPage("BC305App", egameDir)
  val ww1App: AppPage = AppPage("WW1App", egameDir)
  val sorsApp: AppPage = AppPage("SorsApp", egameDir, "Sors Imperiorum")
  val indRevApp: AppPage = AppPage("IndRevApp", egameDir)
  val discovApp: AppPage = AppPage("DiscovApp", egameDir, "Age of Discovery")
  
  val eGameApps = RArr(dicelessApp, ww2App, periculoApp, bcApp, ww1App, sorsApp, indRevApp, discovApp)

  val uLocApp: AppPage = AppPage("UnitLocApp", otDir, "Unit Locator")
  val planetsApp: AppPage = AppPage("PlanetsApp", otDir)
  val zugApp: AppPage = AppPage("ZugApp", otDir, "ZugFuhrer")
  val flagsApp: AppPage = AppPage("FlagsApp", otDir)
  val dungeonApp: AppPage = AppPage("DungeonApp", otDir, "Dungeon game")
  val civRiseApp: AppPage = AppPage("CivRiseApp", otDir, "Civ Rise")
  val chessApp: AppPage = AppPage("ChessApp", otDir)
  
  val otherApps = RArr(uLocApp, planetsApp, zugApp, flagsApp, dungeonApp, civRiseApp, chessApp)
  
  /** list of app links to go in the page headers. */
  val allTops: RArr[AppPage] = RArr(dicelessApp, ww2App, uLocApp, periculoApp, bcApp, planetsApp, zugApp, flagsApp, dungeonApp, civRiseApp)

  val eGrids: RArr[AppPage] = RArr(AppPage("EG1300", mapDir, "1300km Hex Earth"), AppPage("EG1000", mapDir, "1000km Hex Earth"),
    AppPage("EG640", mapDir, "640km Hex Earth"), AppPage("EG460", mapDir, "460km Hex Earth"), AppPage("EG320", mapDir, "320km Hex Earth"),
    AppPage("EG220Europe", mapDir), AppPage("EG220EuropeWide", mapDir), AppPage("EG220NAmerica", mapDir, "220km Hex North America"),
    AppPage("EG160Europe", mapDir), AppPage("EG120Europe", mapDir), AppPage("EG80Europe", mapDir), AppPage("EarthApp", mapDir))

  val others: RArr[AppPage] = RArr(ww1App, indRevApp, sorsApp, discovApp, chessApp)

  def allApps: RArr[AppPage] = allTops ++ others
  def all: RArr[AppPage] = allApps ++ eGrids

  object AllHtmlExtractor
  {
    def unapply(inp: String): Option[AppPage] = all.find(_.htmlPathNameStr == inp.drop(1))
  }

  val defaultTopPairs: ArrPairStr[DirsFileRel] = allTops.mapPair(_.filesStem)(_.htmlPathName)

  def topMenu(pairs: ArrPairStr[DirsFileRel], origin: DirsRel = DirsRel()): HtmlUl =
    HtmlUl(pairs.pairMap { (s1, s2) => HtmlLi.a(origin </>% s2, s1) }, RArr(IdAtt("topmenu")))
}