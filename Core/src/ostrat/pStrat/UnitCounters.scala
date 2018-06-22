/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._

object UnitCounters
{
   def infantry(scale: Double, evObj: AnyRef, fillColour: Colour, backgroundColour: Colour): PolySubj =
   {
      val rect: Vec2s = Rect.scale(scale, 1.5)
      val line1 = Line2(rect(0), rect(2))
      val line2 = Line2(rect(1), rect(3))
      val linesColour = fillColour.contrast2(backgroundColour)
      val subj = rect.fillDrawSubj(evObj, fillColour, 1, linesColour)
      subj.addElems(Seq(PolyLineDraw(Seq(line1, line2), 1, linesColour)))
   }
  
}