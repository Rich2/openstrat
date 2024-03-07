/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** An HTML Page for running an application. We may want a separate page for the documentation */
class AppPage(val JsMainStemName: String, fileNameStemIn: String = "", linkTextIn: String = "", val dirStr: String = "/") extends HtmlPage
{
  val fileNameStem: String = ife(fileNameStemIn == "", JsMainStemName.toLowerCase(), fileNameStemIn)

  def htmlFileName: String = fileNameStem + ".html"

  def htmlLoc: String = dirStr + htmlFileName

  def jsFileName: String = fileNameStem + ".js"

  val linkText: String = ife(linkTextIn == "", JsMainStemName, linkTextIn)

  override def head: HtmlHead = HtmlHead.titleCss(linkText, "/only")

  def topMenu: HtmlUl =
  { val pages: RArr[AppPage] = AppPage.allTops.filterNot(_.JsMainStemName == JsMainStemName)
    val pairs1: ArrPairStr[String] = pages.mapPair(_.linkText)(_.htmlLoc)
    val pairs2: ArrPairStr[String] = PairStrElem("Home", "/index.html") %: pairs1
    AppPage.topMenu(pairs2)
  }

  override def body: HtmlBody = HtmlBody(topMenu, HtmlCanvas.id("scanv"), HtmlScript.jsSrc(jsFileName), HtmlScript.main(JsMainStemName + "JsApp"))
}

object AppPage
{
  /** Factory apply method for [[AppPage]] class. The first parameter is the stem of the name of the main function function in the JavaScript file, to
   * which "JsApp" [[String]] is appended. The second parameter is the file name's stems to which the [[String]]s ".html" and ",js" will be added. The
   * default is the lower case of the first parameter. The third parameter is the title, which unlike the first two parameters can contain spaces
   * which defaults to the first parameter. */
  def apply(appStemName: String, fileNameIn: String = "", linkTextIn: String = "", locStr: String = "/"): AppPage =
    new AppPage(appStemName, fileNameIn, linkTextIn, locStr)

  val egameDir: String = "/earthgames/"
  val egrDir: String = "/egrids/"
  val otDir: String = "/otherapps/"

  val allTops: RArr[AppPage] = RArr(AppPage("UnitLoc", "unitlocapp", "Unit Locator", otDir), AppPage("Diceless", "dicelessapp", "DiceLess", egameDir),
    AppPage("Periculo", "periculoapp", "Periculo Fundato", egameDir), AppPage("WW2", "ww2app", "WW2", egameDir),
    AppPage("BC305", "bc305app", "BC305", egameDir), AppPage("Planets", "planets", "Planets", otDir), AppPage("Zug", "zugapp", "ZugFuhrer", otDir),
    AppPage("Flags", "flags", "Flags", otDir), AppPage("Dungeon", "dungeonapp", "Dungeon game", otDir),
    AppPage("CivRise", "civriseapp", "Civ Rise", otDir))

  val eGrids: RArr[AppPage] = RArr(AppPage("EG1300", "eg1300app", "1300km Hex Earth", egrDir), AppPage("EG1000", "eg1000app", "1000km Hex Earth", egrDir),
    AppPage("EG640", "eg640app", "640km Hex Earth", egrDir), AppPage("EG460", "eg460app", "460km Hex Earth", egrDir),
    AppPage("EG320", "eg320app", "320km Hex Earth", egrDir), AppPage("EG220", "eg220app", "220km Hex Europe", egrDir))

  val others: RArr[AppPage] = RArr(AppPage("WW1", "ww1app", "WW1", egameDir), AppPage("Sors", "sorsapp", "Sors Imperiorum", egameDir),
    AppPage("IndRev", "indrevapp", "IndRev", egameDir), AppPage("Discov", "discovapp", "Age of Discovery", egameDir), AppPage("Chess", "chessapp", "Chess", otDir))

  def all: RArr[AppPage] = allTops ++ eGrids ++ others

  val allTopPairs: ArrPairStr[String] = allTops.mapPair(_.linkText)(_.htmlLoc)

  def topMenu(pairs: ArrPairStr[String]): HtmlUl = HtmlUl(pairs.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
}