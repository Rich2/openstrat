package ostrat
package geom

case class PolyCurveParent(cen: Vec2, shape: PolyCurve, pointerId: Any, children: Arr[PaintFullElem]) extends GraphicParent with ShapeActive
{ override type ThisT = PolyCurveParent
  def fTrans(f: Vec2 => Vec2): PolyCurveParent = PolyCurveParent(f(cen), shape.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[PaintFullElem]): PolyCurveParent = PolyCurveParent(cen, shape, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): PolyCurveParent = PolyCurveParent(cen, shape, newObj, children)
}

object PolyCurveParent
{
  def fill(cen: Vec2, shape: PolyCurve, evObj: Any, colour: Colour) = PolyCurveParent(cen, shape, evObj, Arr(PolyCurveFill(shape, colour)))

  def fillDraw(cen: Vec2, shape: PolyCurve, evObj: Any, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
    PolyCurveParent(cen, shape, evObj, Arr(PolyCurveFillDraw(shape, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, shape: PolyCurve, evObj: Any, lineWidth: Double, lineColour: Colour = Colour.Black) =
    PolyCurveParent(cen, shape, evObj, Arr(PolyCurveDraw(shape, lineWidth, lineColour)))
}