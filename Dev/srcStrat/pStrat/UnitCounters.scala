/* Copyright 2018 = 23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pStrat
import geom._

trait UnitCounter
{ def heightRatio = 0.7
  def lineWidth = 2
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound
  def str: String
}

object InfantryCounter extends UnitCounter
{
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound = {
    val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW //2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, lineWidth, linesColour)
    subj.addChildren(RArr(rect.diags.draw(1, linesColour)))
  }

  override def str: String = "Infantry"
}

object CavalryCounter extends UnitCounter
{
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound = {
    val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW //2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, lineWidth, linesColour)
    subj.addChildren(RArr(rect.diag1.draw(linesColour, 1)))
  }

  override def str: String = "Cavalry"
}