/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg120._, prid.phex._

@JSExportTopLevel("EG120EuropeJs")
object EG120EuropeJs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, EGrid120.scen0, HGView(315, 512, 25), false); () }
} 