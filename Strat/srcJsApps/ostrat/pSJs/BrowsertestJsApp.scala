/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs

@scalajs.js.annotation.JSExportTopLevel("BrowsertestJsApp")
object BrowsertestJsApp
{   
   def main(args: Array[String]): Unit = pStrat.FlagsGui(CanvasJs)
}
  
