/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg220._, prid.phex._

@JSExportTopLevel("EG220NAmericaJs")
object EG220NAmericaJs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen220NorthAmerica, HGView(154, 9742, 25), false); () }
} 