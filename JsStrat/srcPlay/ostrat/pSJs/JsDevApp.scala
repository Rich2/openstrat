/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import scala.scalajs.js.annotation._

@JSExportTopLevel("JsDevApp")
object JsDevApp
{
   @JSExport
   def main(appNum: Int = 1): Unit =
   {      
      pDev.Apps.curr._1(CanvasJs)      
   }
}