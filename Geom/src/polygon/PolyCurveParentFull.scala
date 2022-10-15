/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui._

case class PolyCurveParentFull(cen: Pt2, shape: ShapeGenOld, pointerId: AnyRef, children: RArr[GraphicAffineElem]) extends GraphicParentFull with
  PolyCurveActive
{ override type ThisT = PolyCurveParentFull
  def ptsTrans(f: Pt2 => Pt2): PolyCurveParentFull = PolyCurveParentFull(f(cen), shape.ptsTrans(f), pointerId,
    children.map[GraphicAffineElem, RArr[GraphicAffineElem]](el => el.ptsTrans(f)))
  override def addElems(newElems: RArr[GraphicAffineElem]): PolyCurveParentFull = PolyCurveParentFull(cen, shape, pointerId, children ++ newElems)
  override def mutObj(newObj: AnyRef): PolyCurveParentFull = PolyCurveParentFull(cen, shape, newObj, children)

  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}
}

object PolyCurveParentFull
{
  def fill(cen: Pt2, shape: ShapeGenOld, evObj: AnyRef, colour: Colour) = PolyCurveParentFull(cen, shape, evObj, RArr(PolyCurveFill(shape, colour)))

  def fillDraw(cen: Pt2, shape: ShapeGenOld, evObj: AnyRef, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
    PolyCurveParentFull(cen, shape, evObj, RArr(PolyCurveFillDraw(shape, fillColour, lineColour, lineWidth)))

  def draw(cen: Pt2, shape: ShapeGenOld, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Colour.Black) =
    PolyCurveParentFull(cen, shape, evObj, RArr(PolyCurveDraw(shape, lineColour, lineWidth)))
}