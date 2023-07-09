/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._

object DevHtmlApp extends App
{
  class SubPage(val appStemName: String, fileNameIn: String = "", linkTextIn: String = "")
  {
    val fileName: String = ife(fileNameIn == "", appStemName.toLowerCase(), fileNameIn)
    val linkText: String = ife(linkTextIn == "", appStemName, linkTextIn)
  }

  object SubPage
  { def apply(appStemName: String, fileNameIn: String = "", linkTextIn: String = ""): SubPage = new SubPage(appStemName, fileNameIn, linkTextIn)
  }

  val sett = findDevSettingT[DirPathAbs]("projPath")
  val subPages = RArr(SubPage("UnitLoc", "unitloc", "Unit Locator"), SubPage("Diceless"), SubPage("BC305"), SubPage("Planets"),
    SubPage("Zug","zug", "ZugFuhrer"))

  def make(page: SubPage): Unit = sett.forGoodForBad { path =>
    val head = HtmlHead.titleCss(page.linkText, "only")

    val pairs: StrPairArr[String] = StrStrPairArr("index", "Home", "diceless", "Diceless", "bc305", "BC305", "planets", "Planets", "zug", "Zugfuhrer")
    val pairs2 = pairs.filterNotOnA2(_ == page.appStemName)

    val list = HtmlUl(pairs2.pairMap{ (s1, s2) => HtmlLi.a(s1 + ".html", s2) })
    val body = HtmlBody.elems(list, HtmlCanvas.id("scanv"))
    val content = HtmlPage(head, body)

    val res = fileWrite(path.str -/- "Dev/SbtDir", page.fileName + "app.html", content.out)
    debvar(res)
  }{ errs =>
     deb("")
     errs.foreach(println)
  }
  subPages.foreach(make)
}