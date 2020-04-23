/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._

object UnitCounters
{
  def infantry(scale: Double, evObj: AnyRef, fillColour: Colour, backgroundColour: Colour): PolygonParentFull =
  { val rect: Polygon = Rectangle(1.5 * scale, scale)
    val line1 = Line2(rect(0), rect(2))
    val line2 = Line2(rect(1), rect(3))
    val linesColour = fillColour.contrast2(backgroundColour)
    val subj = rect.parentFillDraw(evObj, fillColour, 1, linesColour)
    subj.addElems(Arr(Line2s(line1, line2).draw(1, linesColour)))
  }  
}