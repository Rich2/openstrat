/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import scalajs.js.annotation._, pGames.pZug._

@JSExportTopLevel("ZugJsApp")
object ZugJsApp
{
   @JSExport def main(args: Array[String]): Unit = new ZugGui(CanvasJs, ZGame1, PlayGermanyBritain)
}
