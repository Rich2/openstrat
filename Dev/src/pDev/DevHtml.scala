/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pjvm._, pWeb._

object DevHtmlApp extends App
{
  val sett = findDevSettingT[DirPathAbs]("projPath")

  def make(title: String): Unit = sett.forGood{ path =>
    val head = HtmlHead.titleCss(title, "only.css")
    val item = HtmlLi.a("index.html", "Home")
    val body = HtmlBody.elems(item, HtmlCanvas.id("scanv"))
    val content = HtmlPage(head, body)

    case class Cities(contents: RArr[XCon]) extends XmlNoAtts
    { override def tag: String = "Cities"
    }

    println(Cities(RArr()).out())
    val res = fileWrite(path.str -/- "Dev/SbtDir", title.toLowerCase() + "app.html", content.out)
    debvar(res)
  }
  make("Diceless")
}