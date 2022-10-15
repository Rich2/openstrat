/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pStrat
import geom._

object UnitCounters
{
  def infantry(scale: Double, evObj: AnyRef, fillColour: Colour, backgroundColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(1.5 * scale, scale)
    val linesColour = fillColour.contrast2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, 1, linesColour)
    subj.addChildren(RArr(rect.diags.draw(1, linesColour)))
  }

  def cavalry(scale: Double, evObj: AnyRef, fillColour: Colour, backgroundColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(1.5 * scale, scale)
    val linesColour = fillColour.contrast2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, 1, linesColour)
    subj.addChildren(RArr(rect.diag1.draw(linesColour, 1)))
  }
}