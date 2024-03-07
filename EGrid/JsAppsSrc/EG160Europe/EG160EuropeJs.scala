/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg160._, prid.phex._

@JSExportTopLevel("EG160EuropeJs")
object EG160EuropeJs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen160Europe, HGView(282, 534, 25), false); () }
} 