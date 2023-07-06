/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._, puloc._, geom._, pglobe._

@JSExportTopLevel("UnitLocJsApp")
object DicelessJsApp
{   
  @JSExport def main(): Unit = ULocGui(CanvasJs, MTime(1939, 9, 15), EarthView(52, 18, 1.6))
} 
