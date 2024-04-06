/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg13._, prid.phex._

@JSExportTopLevel("EG1300AppJs")
object EG1300AppJs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen13All, HGView(103, 512, 27), false, false); () }
} 