/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._

class SubPage(val appStemName: String, fileNameStemIn: String = "", linkTextIn: String = "") {
  val fileNameStem: String = ife(fileNameStemIn == "", appStemName.toLowerCase(), fileNameStemIn) // + ".js"

  def htmlFileName: String = fileNameStem + ".html"

  def jsFileName: String = fileNameStem + ".js"

  val linkText: String = ife(linkTextIn == "", appStemName, linkTextIn)

  def out: String = {
    val pages: RArr[SubPage] = SubPage.all.filterNot(_.appStemName == appStemName)
    val pairs1: StrPairArr[String] = pages.mapPair(_.linkText)(_.htmlFileName)
    val pairs2: StrPairArr[String] = StrPair("Home", "index.html") %: pairs1
    val list: HtmlUl = SubPage.topMenu(pairs2)// HtmlUl(pairs2.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
    val head = HtmlHead.titleCss(linkText, "only")
    val body = HtmlBody.elems(list, HtmlCanvas.id("scanv"), HtmlScript.jsSrc(jsFileName), HtmlScript.main(appStemName + "JsApp"))
    HtmlPage(head, body).out
  }
}

object SubPage {
  def apply(appStemName: String, fileNameIn: String = "", linkTextIn: String = ""): SubPage = new SubPage(appStemName, fileNameIn, linkTextIn)

  val all = RArr(SubPage("UnitLoc", "unitlocapp", "Unit Locator"), SubPage("Diceless", "dicelessapp"), SubPage("WW2"), SubPage("BC305"), SubPage("Planets"),
    SubPage("Zug", "zug", "ZugFuhrer"), SubPage("Y1783"), SubPage("Flags"), SubPage("Dungeon"), SubPage("CivRise"))

  val allPairs: StrPairArr[String] = all.mapPair(_.linkText)(_.htmlFileName)

  def topMenu(pairs: StrPairArr[String]): HtmlUl = HtmlUl(pairs.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
}
