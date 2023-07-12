/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

/** An HTML Page for running an application. We may want a separate page for the documentation */
class AppPage(val appStemName: String, fileNameStemIn: String = "", linkTextIn: String = "") extends HtmlPage
{
  val fileNameStem: String = ife(fileNameStemIn == "", appStemName.toLowerCase(), fileNameStemIn) // + ".js"

  def htmlFileName: String = fileNameStem + ".html"

  def jsFileName: String = fileNameStem + ".js"

  val linkText: String = ife(linkTextIn == "", appStemName, linkTextIn)

  override def head: HtmlHead = HtmlHead.titleCss(linkText, "only")

  def topMenu: HtmlUl =
  { val pages: RArr[AppPage] = AppPage.all.filterNot(_.appStemName == appStemName)
    val pairs1: StrPairArr[String] = pages.mapPair(_.linkText)(_.htmlFileName)
    val pairs2: StrPairArr[String] = StrPair("Home", "index.html") %: pairs1
    AppPage.topMenu(pairs2)
  }

  override def body: HtmlBody = HtmlBody(topMenu, HtmlCanvas.id("scanv"), HtmlScript.jsSrc(jsFileName), HtmlScript.main(appStemName + "JsApp"))
}

object AppPage {
  def apply(appStemName: String, fileNameIn: String = "", linkTextIn: String = ""): AppPage = new AppPage(appStemName, fileNameIn, linkTextIn)

  val all = RArr(AppPage("UnitLoc", "unitlocapp", "Unit Locator"), AppPage("Diceless", "dicelessapp"), AppPage("WW2"), AppPage("BC305"), AppPage("Planets"),
    AppPage("Zug", "zug", "ZugFuhrer"), AppPage("Y1783"), AppPage("Flags"), AppPage("Dungeon"), AppPage("CivRise", "civrise", "Civ Rise"))

  val allPairs: StrPairArr[String] = all.mapPair(_.linkText)(_.htmlFileName)

  def topMenu(pairs: StrPairArr[String]): HtmlUl = HtmlUl(pairs.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
}
