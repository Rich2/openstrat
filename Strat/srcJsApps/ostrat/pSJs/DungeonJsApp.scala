/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs

@scalajs.js.annotation.JSExportTopLevel("DungeonJsApp")
object DungeonJsApp
{
   def main(args: Array[String]): Unit = new pGames.pDung.DungeonGui(CanvasJs)
}

