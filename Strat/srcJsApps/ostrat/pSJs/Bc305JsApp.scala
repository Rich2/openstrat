/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs

@scalajs.js.annotation.JSExportTopLevel("BC305JsApp")
object Bc305JsApp
{
   import pGames.p305._
   def main(args: Array[String]): Unit = BC305Gui(CanvasJs, Bc1)
}