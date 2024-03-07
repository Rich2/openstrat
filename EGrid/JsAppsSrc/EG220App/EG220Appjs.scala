/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg220._, prid.phex._

@JSExportTopLevel("EG220JsApp")
object EG220Appjs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen220Europe, HGView(162, 1522, 25), false); () }
} 