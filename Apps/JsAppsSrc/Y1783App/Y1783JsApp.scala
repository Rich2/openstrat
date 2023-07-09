/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, pnap._, prid.phex._

@JSExportTopLevel("Y1783JsApp")
object Y1783JsApp
{   
  @JSExport def main(): Unit = NapGui(CanvasJs, NapScen1, HGView(462, 512, 22))
} 