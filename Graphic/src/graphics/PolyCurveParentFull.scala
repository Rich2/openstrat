package ostrat
package geom

case class PolyCurveParentFull(cen: Vec2, shape: PolyCurve, pointerId: Any, children: Arr[GraphicAffElem]) extends DisplayParentFull with ShapeActive
{ override type ThisT = PolyCurveParentFull
  def fTrans(f: Vec2 => Vec2): PolyCurveParentFull = PolyCurveParentFull(f(cen), shape.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[GraphicAffElem]): PolyCurveParentFull = PolyCurveParentFull(cen, shape, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolyCurveParentFull = PolyCurveParentFull(cen, shape, newObj, children)
}

object PolyCurveParentFull
{
  def fill(cen: Vec2, shape: PolyCurve, evObj: Any, colour: Colour) = PolyCurveParentFull(cen, shape, evObj, Arr(PolyCurveFill(shape, colour)))

  def fillDraw(cen: Vec2, shape: PolyCurve, evObj: Any, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
    PolyCurveParentFull(cen, shape, evObj, Arr(PolyCurveFillDraw(shape, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, shape: PolyCurve, evObj: Any, lineWidth: Double, lineColour: Colour = Colour.Black) =
    PolyCurveParentFull(cen, shape, evObj, Arr(PolyCurveDraw(shape, lineWidth, lineColour)))
}