/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** An HTML Page for running an application. We may want a separate page for the documentation */
class AppPage(val JsMainStem: String, val dirStr: String = "/", htmlTitleIn: String = "", htmlFileStemIn: String = "", jsFileStemIn: String = "") extends HtmlPage
{
  val htmlTitle: String = htmlTitleIn.emptyMap(JsMainStem)

  val htmlFileStem: String = htmlFileStemIn.emptyMap(JsMainStem.toLowerCase)

  def htmlFileName: String = htmlFileStem + ".html"

  def htmlLoc: String = dirStr + htmlFileName

  def jsFileName: String = jsFileStem + ".js"

  val jsFileStem: String = jsFileStemIn.emptyMap(JsMainStem.toLowerCase)

  override def head: HtmlHead = HtmlHead.titleCss(htmlTitle, "/only")

  def topMenu: HtmlUl =
  { val pages: RArr[AppPage] = AppPage.allTops.filterNot(_.JsMainStem == JsMainStem)
    val pairs1: ArrPairStr[String] = pages.mapPair(_.jsFileStem)(_.htmlLoc)
    val pairs2: ArrPairStr[String] = PairStrElem("Home", "/index.html") %: pairs1
    AppPage.topMenu(pairs2)
  }

  override def body: HtmlBody = HtmlBody(topMenu, HtmlCanvas.id("scanv"), HtmlScript.jsSrc(jsFileName), HtmlScript.main(JsMainStem + "Js"))
}

object AppPage
{
  /** Factory apply method for [[AppPage]] class. The first parameter is the stem of the name of the main function function in the JavaScript file, to
   * which "JsApp" [[String]] is appended. The second parameter is the file name's stems to which the [[String]]s ".html" and ",js" will be added. The
   * default is the lower case of the first parameter. The third parameter is the title, which unlike the first two parameters can contain spaces
   * which defaults to the first parameter. */
  def apply(jsMainStem: String, dirStr: String = "/", htmlTitleIn: String = "", htmlFileNameStem: String = "", jsFileStem: String = ""): AppPage =
    new AppPage(jsMainStem, dirStr, htmlTitleIn, htmlFileNameStem, jsFileStem)

  val egameDir: String = "/earthgames/"
  val egrDir: String = "/egrids/"
  val otDir: String = "/otherapps/"

  val allTops: RArr[AppPage] = RArr(AppPage("UnitLocApp", otDir, "Unit Locator"), AppPage("DicelessApp", egameDir, "DiceLess"),
    AppPage("PericuloApp", egameDir, "Periculo Fundato"), AppPage("WW2App", egameDir), AppPage("BC305App", egameDir), AppPage("Planets", otDir),
    AppPage("ZugApp", otDir, "ZugFuhrer"), AppPage("Flags", otDir), AppPage("DungeonApp", otDir, "Dungeon game"),  AppPage("CivRiseApp", otDir, "Civ Rise"))

  val eGrids: RArr[AppPage] = RArr(AppPage("EG1300App", egameDir, "1300km Hex Earth"), AppPage("EG1000App", egrDir, "1000km Hex Earth"),
    AppPage("EG640App", egrDir, "640km Hex Earth"), AppPage("EG460App", egrDir, "460km Hex Earth"), AppPage("EG320App", egrDir, "320km Hex Earth"),
    AppPage("EG220Europe", egrDir), AppPage("EG220NAmerica", egrDir, "220km Hex North America"), AppPage("EG160Europe", egrDir), AppPage("EG120Europe", egrDir))

  val others: RArr[AppPage] = RArr(AppPage("WW1App", egameDir), AppPage("SorsApp", egameDir, "Sors Imperiorum"), AppPage("IndRevApp", egameDir),
    AppPage("DiscovApp", egameDir, "Age of Discovery"), AppPage("ChessApp", otDir))

  def all: RArr[AppPage] = allTops ++ eGrids ++ others

  val allTopPairs: ArrPairStr[String] = allTops.mapPair(_.jsFileStem)(_.htmlLoc)

  def topMenu(pairs: ArrPairStr[String]): HtmlUl = HtmlUl(pairs.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
}