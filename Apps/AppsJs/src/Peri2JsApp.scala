/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pSJs
import scalajs.js._, annotation._, peri._, prid.phex._, org.scalajs.dom._

object Peri2JsApp
{
  def main(args: Array[String]): Unit =
  { val oFile: Promise[Response] = fetch("http ://37.221.93.158/index.html")
    Peri2Gui(CanvasJs, PeriScen.init(PeriScen1), HGView(102, 1536, 24)); ()
  }
} 