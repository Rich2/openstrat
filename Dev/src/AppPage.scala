/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** An HTML Page for running an application. We may want a separate page for the documentation */
class AppPage(val JsMainStemName: String, fileNameStemIn: String = "", linkTextIn: String = "") extends HtmlPage
{
  val fileNameStem: String = ife(fileNameStemIn == "", JsMainStemName.toLowerCase(), fileNameStemIn) // + ".js"

  def htmlFileName: String = fileNameStem + ".html"

  def jsFileName: String = fileNameStem + ".js"

  val linkText: String = ife(linkTextIn == "", JsMainStemName, linkTextIn)

  override def head: HtmlHead = HtmlHead.titleCss(linkText, "only")

  def topMenu: HtmlUl =
  { val pages: RArr[AppPage] = AppPage.allTops.filterNot(_.JsMainStemName == JsMainStemName)
    val pairs1: ArrPairStr[String] = pages.mapPair(_.linkText)(_.htmlFileName)
    val pairs2: ArrPairStr[String] = PairStrElem("Home", "index.html") %: pairs1
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
  def apply(appStemName: String, fileNameIn: String = "", linkTextIn: String = ""): AppPage = new AppPage(appStemName, fileNameIn, linkTextIn)

  val allTops: RArr[AppPage] = RArr(AppPage("UnitLoc", "unitlocapp", "Unit Locator"), AppPage("Diceless", "dicelessapp"),
    AppPage("Periculo", "periculoapp", "Periculo Fundato"), AppPage("WW2"), AppPage("BC305"), AppPage("Planets"), AppPage("Zug", "zug", "ZugFuhrer"), AppPage("Flags"), AppPage("Dungeon"),
    AppPage("CivRise", "civrise", "Civ Rise"))

  val others: RArr[AppPage] = RArr(AppPage("WW1"), AppPage("Sors"), AppPage("Y1783"), AppPage("Y1492"), AppPage("Chess"), AppPage("EG1300", "eg1300app", "1300km Hex Earth"))

  def all: RArr[AppPage] = allTops ++ others

  val allTopPairs: ArrPairStr[String] = allTops.mapPair(_.linkText)(_.htmlFileName)

  def topMenu(pairs: ArrPairStr[String]): HtmlUl = HtmlUl(pairs.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
}