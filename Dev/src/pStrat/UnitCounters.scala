/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._

object UnitCounters
{
  def infantry(scale: Double, evObj: AnyRef, fillColour: Colour, backgroundColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(1.5 * scale, scale)
    val line1 = LineSeg(rect(0), rect(2))
    val line2 = LineSeg(rect(1), rect(3))
    val linesColour = fillColour.contrast2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, 1, linesColour)
    subj.addChildren(Arr(LineSegs(line1, line2).draw(1, linesColour)))
  }  
}