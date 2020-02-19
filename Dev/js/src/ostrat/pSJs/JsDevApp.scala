/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import scala.scalajs.js.annotation._

@JSExportTopLevel("JsDevApp")
object JsDevApp
{ @JSExport def main(appStr: String = "1"): Unit =
  { AppsForJs.curr(appStr)._1(CanvasJs)
    ()
  }
}
