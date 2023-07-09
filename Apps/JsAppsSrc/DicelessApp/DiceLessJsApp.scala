/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, dless._, prid.phex._

@JSExportTopLevel("DicelessJsApp")
object DicelessJsApp
{   
  @JSExport def main(): Unit = DLessGui(CanvasJs, DLessScen1, HGView(141, 524, 20))
} 