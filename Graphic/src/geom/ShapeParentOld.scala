/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

case class ShapeParentOld(cen: Vec2, shape: Shape, evObj: AnyRef, elems: ArrOld[PaintElem]) extends GraphicParentOld with ShapeActiveTr
{
   def fTrans(f: Vec2 => Vec2): ShapeParentOld = ShapeParentOld(f(cen), shape.fTrans(f), evObj, elems.trans(f))
   override def addElems(newElems: ArrOld[PaintElem]): ShapeParentOld = ShapeParentOld(cen, shape, evObj, elems ++ newElems)
   override def mutObj(newObj: AnyRef): ShapeParentOld = ShapeParentOld(cen, shape, newObj, elems)
}

object ShapeParentOld
{
   def fill(cen: Vec2, shape: Shape, evObj: AnyRef, colour: Colour) = ShapeParentOld(cen, shape, evObj, ArrOld(ShapeFill(shape, colour)))

   def fillDraw(cen: Vec2, shape: Shape, evObj: AnyRef, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
      ShapeParentOld(cen, shape, evObj, ArrOld(ShapeFillDraw(shape, fillColour, lineWidth, lineColour)))

   def draw(cen: Vec2, shape: Shape, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Colour.Black) =
      ShapeParentOld(cen, shape, evObj, ArrOld(ShapeDraw(shape, lineWidth, lineColour)))
}
