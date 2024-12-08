/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, egrid._, egmega._, prid.phex._

object EG1000Js
{ @JSExport def main(args: Array[String]): Unit = { EGTerrOnlyGui(CanvasJs, ScenMegaAll, HGView(105, 512, 27), false, false); () }
} 