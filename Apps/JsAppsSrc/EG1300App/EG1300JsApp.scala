/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg13._, prid.phex._

@JSExportTopLevel("EG1300JsApp")
object EG1300JsApp
{
  @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen13All, HGView(102, 512, 27), false); () }
} 