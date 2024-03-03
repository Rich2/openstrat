/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, pind._, prid.phex._

@JSExportTopLevel("IndRevJsApp")
object IndRevJsApp
{   
  @JSExport def main(): Unit = IndRevGui(CanvasJs, IndRevScen1, HGView(160, 512, 22))
} 