/* Copyright 2018 = 23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pStrat
import geom._, Colour.Black

trait UnitCounter
{ def heightRatio = 0.7
  def lineWidth = 2
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound
  def str: String
}

object InfantryCounter extends UnitCounter
{
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW //2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, lineWidth, linesColour)
    subj.addChildren(RArr(rect.diags.draw(1, linesColour)))
  }

  def level(scale: Double, evObj: AnyRef, fillColour: Colour, unitLevel: LunitLevel = FieldArmy): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW
    val subj = rect.fillDrawActive(fillColour, evObj, lineWidth, linesColour)
    val lg1: Drawable = unitLevel.drawable.scale(0.08).slateY(0.55).scale(scale)
    val lg2 = lg1.fillOrDraw(1, Black)
    subj.addChildren(RArr(rect.diags.draw(1, linesColour), lg2))

  }

  override def str: String = "Infantry"
}

object CavalryCounter extends UnitCounter
{
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound = {
    val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW //2(backgroundColour)
    val subj = rect.fillDrawActive(fillColour, evObj, lineWidth, linesColour)
    subj.addChildren(RArr(rect.diag1.draw(1, linesColour)))
  }

  override def str: String = "Cavalry"
}