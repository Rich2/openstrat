/* Copyright 2018 = 23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pStrat
import geom._

object UnitCounters
{
  def heightRatio = 0.7
  def lineWidth = 2
  def infantry(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW//2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, lineWidth, linesColour)
    subj.addChildren(RArr(rect.diags.draw(1, linesColour)))
  }

  def cavalry(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW//2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, lineWidth, linesColour)
    subj.addChildren(RArr(rect.diag1.draw(linesColour, 1)))
  }
}