/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._

@JSExportTopLevel("PlanetsAppJs")
object PlanetsAppJs
{ @JSExport def main(): Unit = pspace.PlanetsGui(CanvasJs)
}