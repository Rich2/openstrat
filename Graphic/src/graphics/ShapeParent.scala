package ostrat
package geom

case class ShapeParent(cen: Vec2, shape: Shape, pointerId: Any, elems: Refs[PaintElem]) extends GraphicParent with ShapeActive
{
  def fTrans(f: Vec2 => Vec2): ShapeParent = ShapeParent(f(cen), shape.fTrans(f), pointerId, elems.trans(f))
  override def addElems(newElems: Refs[PaintElem]): ShapeParent = ShapeParent(cen, shape, pointerId, elems ++ newElems)
  override def mutObj(newObj: Any): ShapeParent = ShapeParent(cen, shape, newObj, elems)
}

object ShapeParent
{
  def fill(cen: Vec2, shape: Shape, evObj: Any, colour: Colour) = ShapeParent(cen, shape, evObj, Refs(ShapeFill(shape, colour)))

  def fillDraw(cen: Vec2, shape: Shape, evObj: Any, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
    ShapeParent(cen, shape, evObj, Refs(ShapeFillDraw(shape, fillColour, lineWidth, lineColour)))

  def draw(cen: Vec2, shape: Shape, evObj: Any, lineWidth: Double, lineColour: Colour = Colour.Black) =
    ShapeParent(cen, shape, evObj, Refs(ShapeDraw(shape, lineWidth, lineColour)))
}