/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import pDisp._
import Colour._
case class TestCanv(canv: CanvasPlatform) extends pDisp.CanvasSimple
{
   val r1 = Circle.fillSubj(500, "This is a red Circle", Red)
   val r2 = Circle.fillSubj(500, "This is pink circle", Pink, -500, 0)
   val stuff = List(r1, r2)
   mouseUp = (v, b, s) => deb("clickList:" -- s.toString)
   repaint(stuff)
}