/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import scalajs.js.annotation._

@JSExportTopLevel("Y1783JsApp")
object Y1783JsApp
{
   import pGames.p1783._
   @JSExport def main(args: Array[String]): Unit = Y1783Gui(CanvasJs, Nap1)   
} 