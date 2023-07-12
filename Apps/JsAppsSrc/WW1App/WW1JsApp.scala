/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, pww1._, prid.phex._

@JSExportTopLevel("WW1JsApp")
object WW1JsApp
{
  @JSExport def main(): Unit = { WW1Gui(CanvasJs, WW1Scen1, HGView(310, 520, 24)); () }
} 