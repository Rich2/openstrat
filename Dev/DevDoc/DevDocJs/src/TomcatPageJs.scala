/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package docjs
import geom.*, pSJs.*, scalajs.js.annotation.*, pDoc.TomcatPage//, org.scalajs.dom.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    val num = TomcatPage.inpAcc.length
    deb(s"Found $num in TomcatPage")
    TomcatPage.inpAcc.foreach(TextContentUpdater(_))
    //HtmlClassTextModder("uName", "nset", uName1)
    //HtmlClassTextModder("cName", "cset", cName1)
  }
} 