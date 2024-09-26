/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation.*, pww2.*, prid.phex.*

@JSExportTopLevel("WW2AppJs")
object WW2AppJs
{ @JSExport def main(args: Array[String]): Unit = { WW2Gui(CanvasJs, WW2Scen1, HGView(125, 1528, 22)); () }
} 