/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** An HTML Page for running an application. We may want a separate page for the documentation */
class appPage(val appStemName: String, fileNameStemIn: String = "", linkTextIn: String = "") extends HtmlPage
{
  val fileNameStem: String = ife(fileNameStemIn == "", appStemName.toLowerCase(), fileNameStemIn) // + ".js"

  def htmlFileName: String = fileNameStem + ".html"

  def jsFileName: String = fileNameStem + ".js"

  val linkText: String = ife(linkTextIn == "", appStemName, linkTextIn)

  override def head: HtmlHead = HtmlHead.titleCss(linkText, "only")

  override def body: HtmlBody =
  { val pages: RArr[appPage] = appPage.all.filterNot(_.appStemName == appStemName)
    val pairs1: StrPairArr[String] = pages.mapPair(_.linkText)(_.htmlFileName)
    val pairs2: StrPairArr[String] = StrPair("Home", "index.html") %: pairs1
    val list: HtmlUl = appPage.topMenu(pairs2)
    HtmlBody(list, HtmlCanvas.id("scanv"), HtmlScript.jsSrc(jsFileName), HtmlScript.main(appStemName + "JsApp"))
  }
}

object appPage {
  def apply(appStemName: String, fileNameIn: String = "", linkTextIn: String = ""): appPage = new appPage(appStemName, fileNameIn, linkTextIn)

  val all = RArr(appPage("UnitLoc", "unitlocapp", "Unit Locator"), appPage("Diceless", "dicelessapp"), appPage("WW2"), appPage("BC305"), appPage("Planets"),
    appPage("Zug", "zug", "ZugFuhrer"), appPage("Y1783"), appPage("Flags"), appPage("Dungeon"), appPage("CivRise", "civrise", "Civ Rise"))

  val allPairs: StrPairArr[String] = all.mapPair(_.linkText)(_.htmlFileName)

  def topMenu(pairs: StrPairArr[String]): HtmlUl = HtmlUl(pairs.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
}
