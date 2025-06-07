/* Copyright 2018 = 23 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pStrat
import geom._, Colour.Black

trait UnitCounter
{ def heightRatio = 0.7
  def lineWidth = 2
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound
  def str: String
}

/** Infantry unit counter. */
object InfantryCounter extends UnitCounter
{
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW
    val subj = rect.fillActiveDraw(fillColour, evObj, linesColour, lineWidth)
    subj.addChildren(RArr(rect.diags.draw(1, linesColour)))
  }

  def level(scale: Double, evObj: AnyRef, fillColour: Colour, unitLevel: LuUniLevel = FieldArmy): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW
    val subj = rect.fillActiveDraw(fillColour, evObj, linesColour, lineWidth)
    val lg1: RArr[Drawable] = unitLevel.drawables.scale(0.08).slateY(0.55).scale(scale)
    val lg2 = lg1.map(_.fillOrDraw(1, Black))
    subj.addChildren(rect.diags.draw(1, linesColour) %: lg2)
  }

  override def str: String = "Infantry"
}

/** Cavalry unit counter. */
object CavalryCounter extends UnitCounter
{
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val linesColour = fillColour.contrastBW
    val subj: RectCompound = rect.fillActiveDraw(fillColour, evObj, linesColour, lineWidth)
    subj.addChildren(RArr(rect.diag1.draw(1, linesColour)))
  }

  override def str: String = "Cavalry"
}

/** Armour unit counter, currently using cavalry symbol. */
object ArmourCounter extends UnitCounter
{
  def apply(scale: Double, evObj: AnyRef, fillColour: Colour): PolygonCompound =
  { val rect: Rect = Rect(scale, scale * heightRatio)
    val stadium: ShapeGenOld = Stadium(scale * 0.65, scale * heightRatio * 0.6)
    val linesColour = fillColour.contrastBW
    val subj: RectCompound = rect.fillActiveDraw(fillColour, evObj, linesColour, lineWidth)
    subj.addChildren(RArr(stadium.draw(linesColour, 2)))
  }

  override def str: String = "Armour"
}