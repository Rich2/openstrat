/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package docjs
import geom.*, pSJs.*, scalajs.js.annotation.*, org.scalajs.dom.*, pDoc.TomcatPage.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    deb("Starting TomcatPageJs")
    HtmlClassTextModder("uName", "nset", uName1)
    HtmlClassTextModder("cName", "cset", cName1)
  }
} 