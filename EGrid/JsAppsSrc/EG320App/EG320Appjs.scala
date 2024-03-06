/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, eg320._, prid.phex._

@JSExportTopLevel("EG320JsApp")
object EG320Appjs
{ @JSExport def main(): Unit = { EGTerrOnlyGui(CanvasJs, Scen320All, HGView(138, 1532, 25), false); () }
} 