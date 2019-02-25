/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs

@scalajs.js.annotation.JSExportTopLevel("PlanetsJsApp")
object PlanetsJsApp
{
   def main(args: Array[String]): Unit = pGames.pSpace.Planets(CanvasJs)  
}


