/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._
import pDisp._
import Colour._
case class TestCanv(canv: CanvasLike) extends pDisp.CanvasSimple
{
   val r1 = Rect(600, 400).fillDrawSubj("This is a Rect", Red, 1)
   val stuff = List(r1)
   mouseUp = (v, b, s) => {}//deb("clickList:" -- s.toString)}
   repaint(stuff)
}