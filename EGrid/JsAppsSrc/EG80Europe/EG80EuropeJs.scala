/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg80._, prid.phex._

@JSExportTopLevel("EG80EuropeJs")
object EG80EuropeJs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen80Europe, HGView(460, 512, 25), false); () }
} 