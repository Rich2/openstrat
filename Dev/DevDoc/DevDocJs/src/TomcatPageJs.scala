/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package docjs
import pSJs.*, pDoc.TomcatPage//, scalajs.js.annotation.*, org.scalajs.dom.*

//@JSExportTopLevel("TomcatPageJs")
object TomcatPageJs
{
  def main(args: Array[String]): Unit =
  { val num = TomcatPage.inpAcc.length
    deb(s"Found $num in TomcatPage")
    TomcatPage.inpAcc.foreach(inputUpdater => ContentUpdater(inputUpdater))
  }
} 