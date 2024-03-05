/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, pww2._, prid.phex._

@JSExportTopLevel("WW2JsApp")
object WW2JsApp
{ @JSExport def main(): Unit = { WW2Gui(CanvasJs, WW2Scen1, HGView(125, 1528, 22)); () }
} 