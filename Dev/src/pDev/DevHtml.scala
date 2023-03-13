/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._

object DevHtmlApp extends App
{
  val sett = findDevSettingT[DirPathAbs]("projPath")

  def make(title: String): Unit = sett.forGood{ path =>
    val head = HtmlHead.titleCss(title, "only")
    val pairs = StrStrPairArr("index", "Home", "diceless", "Diceless", "bc305", "BC305", "planets", "Planets", "zug", "Zugfuhrer")
    val list = HtmlUl(pairs.pairMap{ (s1, s2) => HtmlLi.a(s1 + ".html", s2) })
    val body = HtmlBody.elems(list, HtmlCanvas.id("scanv"))
    val content = HtmlPage(head, body)

    val a320 = geom.HexReg.areaFromShortRadius(160)
    debvar(a320)
    val a220 = geom.HexReg.areaFromShortRadius(110)
    debvar(a220)

    val res = fileWrite(path.str -/- "Dev/SbtDir", title.toLowerCase() + "app.html", content.out)
    debvar(res)
  }
  make("Diceless")
}