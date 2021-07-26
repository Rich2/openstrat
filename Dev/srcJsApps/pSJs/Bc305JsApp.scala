/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js.annotation._

@JSExportTopLevel("BC305JsApp")
object Bc305JsApp
{
   import pGames.p305._
   @JSExport def main(args: Array[String]): Unit = BC305Gui(CanvasJs, Bc1)
}