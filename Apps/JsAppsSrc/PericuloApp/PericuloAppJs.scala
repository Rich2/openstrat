/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, peri._, prid.phex._

@JSExportTopLevel("PericuloAppJs")
object PericuloAppJs
{ @JSExport def main(): Unit = { PeriGui(CanvasJs, PeriScen.init(PeriScen1), HGView(102, 1536, 24)); () }
} 