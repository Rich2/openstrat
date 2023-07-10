/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._

object DevHtmlApp extends App
{
  class SubPage(val appStemName: String, fileNameStemIn: String = "", linkTextIn: String = "")
  {
    val fileNameStem: String = ife(fileNameStemIn == "", appStemName.toLowerCase(), fileNameStemIn)// + ".js"

    def htmlFileName: String = fileNameStem + ".html"

    def jsFileName: String = fileNameStem + ".js"
    val linkText: String = ife(linkTextIn == "", appStemName, linkTextIn)
  }

  object SubPage
  { def apply(appStemName: String, fileNameIn: String = "", linkTextIn: String = ""): SubPage = new SubPage(appStemName, fileNameIn, linkTextIn)
  }

  val sett = findDevSettingT[DirPathAbs]("projPath")
  val subPages = RArr(SubPage("UnitLoc", "unitlocapp", "Unit Locator"), SubPage("Diceless", "dicelessapp"), SubPage("WW2"), SubPage("BC305"), SubPage("Planets"),
    SubPage("Zug","zug", "ZugFuhrer"), SubPage("Y1783"), SubPage("Flags"), SubPage("Dungeon"), SubPage("CivRise"))

  def make(path: DirPathAbs, page: SubPage): Unit =
  { val head = HtmlHead.titleCss(page.linkText, "only")

    val pages: RArr[SubPage] = subPages.filterNot(_.appStemName == page.appStemName)
    val pairs1 = pages.mapPair(_.linkText)(_.htmlFileName)
    val pairs2 = StrPair("Home", "index.html") %: pairs1
    val list = HtmlUl(pairs2.pairMap { (s1, s2) => HtmlLi.a(s2, s1) }, RArr(IdAtt("topmenu")))
    val body = HtmlBody.elems(list, HtmlCanvas.id("scanv"), HtmlScript.jsSrc(page.jsFileName), HtmlScript.main(page.appStemName + "JsApp"))
    val content = HtmlPage(head, body)

    val res = fileWrite(path.str -/- "Dev/SbtDir", page.htmlFileName, content.out)
    debvar(res)
  }

  sett.forGoodForBad { path => subPages.foreach(page => make(path, page)) }{
    errs => deb("")
    errs.foreach(println)
  }
}