/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import scalajs.js.annotation._

@JSExportTopLevel("Ww2JsApp")
object Ww2JsApp
{
   import pGames.pWW2._
   @JSExport def main(args: Array[String]): Unit = WWIIGui(CanvasJs, WW1940)   
} 