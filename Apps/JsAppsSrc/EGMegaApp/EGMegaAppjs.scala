/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, egmega._, prid.phex._

@JSExportTopLevel("EGMegaJsApp")
object EGMegaAppjs
{
  @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, ScenMegaAll, HGView(102, 512, 27), false); () }
} 