/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package docjs
import geom.*, pSJs.*, scalajs.js.annotation.*, pDoc.TomcatPage//, org.scalajs.dom.*

@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  @JSExport def main(args: Array[String]): Unit =
  {
    val num = TomcatPage.inpTextAcc.length
    deb(s"Found $num in TomcatPage")
    TomcatPage.inpTextAcc.foreach(ContentUpdaterText(_))
    TomcatPage.inpNumAcc.foreach(ContentUpdaterNum(_))
  }
} 