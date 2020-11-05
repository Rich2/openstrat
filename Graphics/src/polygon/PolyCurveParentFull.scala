package ostrat
package geom

case class PolyCurveParentFull(cen: Pt2, shape: PolyCurve, pointerId: Any, children: Arr[GraphicAffineElem]) extends GraphicParentFull with
  PolyCurveActive
{ override type ThisT = PolyCurveParentFull
  def fTrans(f: Pt2 => Pt2): PolyCurveParentFull = PolyCurveParentFull(f(cen), shape.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[GraphicAffineElem]): PolyCurveParentFull = PolyCurveParentFull(cen, shape, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolyCurveParentFull = PolyCurveParentFull(cen, shape, newObj, children)
  override def xCen: Double = ???
  override def yCen: Double = ???
  override def slateTo(newCen: Pt2): PolyCurveParentFull = ???
}

object PolyCurveParentFull
{
  def fill(cen: Pt2, shape: PolyCurve, evObj: Any, colour: Colour) = PolyCurveParentFull(cen, shape, evObj, Arr(PolyCurveFill(shape, colour)))

  def fillDraw(cen: Pt2, shape: PolyCurve, evObj: Any, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
    PolyCurveParentFull(cen, shape, evObj, Arr(PolyCurveFillDraw(shape, fillColour, lineWidth, lineColour)))

  def draw(cen: Pt2, shape: PolyCurve, evObj: Any, lineWidth: Double, lineColour: Colour = Colour.Black) =
    PolyCurveParentFull(cen, shape, evObj, Arr(PolyCurveDraw(shape, lineWidth, lineColour)))
}