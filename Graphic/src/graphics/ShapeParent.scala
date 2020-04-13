package ostrat
package geom

case class ShapeParent(cen: Vec2, shape: Shape, pointerId: Any, children: Arr[PaintElem]) extends GraphicParent with ShapeActive
{
  def fTrans(f: Vec2 => Vec2): ShapeParent = ShapeParent(f(cen), shape.fTrans(f), pointerId, children.trans(f))
  override def addElems(newElems: Arr[PaintElem]): ShapeParent = ShapeParent(cen, shape, pointerId, children ++ newElems)
  override def mutObj(newObj: Any): ShapeParent = ShapeParent(cen, shape, newObj, children)
}

object ShapeParent
{
  def fill(cen: Vec2, shape: Shape, evObj: Any, colour: Colour) = ShapeParent(cen, shape, evObj, Arr(ShapeFill(shape, colour)))

  def fillDraw(cen: Vec2, shape: Shape, evObj: Any, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
    ShapeParent(cen, shape, evObj, Arr(ShapeFillDraw(shape, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, shape: Shape, evObj: Any, lineWidth: Double, lineColour: Colour = Colour.Black) =
    ShapeParent(cen, shape, evObj, Arr(ShapeDraw(shape, lineWidth, lineColour)))
}