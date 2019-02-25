/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs

@scalajs.js.annotation.JSExportTopLevel("Ww2JsApp")
object Ww2JsApp
{
   import pGames.pWW2._
   def main(args: Array[String]): Unit = WWIIGui(CanvasJs, WW1940)   
} 