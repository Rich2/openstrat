/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg460._, prid.phex._

@JSExportTopLevel("EG460JsApp")
object EG460Appjs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen460All, HGView(125, 1536, 25), false); () }
} 