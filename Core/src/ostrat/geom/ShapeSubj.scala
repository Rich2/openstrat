/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

case class ShapeSubj(cen: Vec2, shape: Shape, evObj: AnyRef, elems: List[PaintElem[_]], layer: Int = 0) extends GraphicSubject[ShapeSubj] with
   ClickShapeTr
{  
   def fTrans(f: Vec2 => Vec2): ShapeSubj = ShapeSubj(f(cen), shape.fTrans(f), evObj, elems.fTrans(f))   
   override def addElems(newElems: List[PaintElem[_]]): ShapeSubj = ShapeSubj(cen, shape, evObj, elems ++ newElems)
   override def mutObj(newObj: AnyRef): ShapeSubj = ShapeSubj(cen, shape, newObj, elems)
}

object ShapeSubj
{
   def fill(cen: Vec2, shape: Shape, evObj: AnyRef, colour: Colour) = ShapeSubj(cen, shape, evObj, List(ShapeFill(shape, colour)))
   
   def fillDraw(cen: Vec2, shape: Shape, evObj: AnyRef, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
      ShapeSubj(cen, shape, evObj, List(ShapeFillDraw(shape, fillColour, lineWidth, lineColour)))
   
   def draw(cen: Vec2, shape: Shape, evObj: AnyRef, lineWidth: Double, lineColour: Colour = Colour.Black) =
      ShapeSubj(cen, shape, evObj, List(ShapeDraw(shape, lineWidth, lineColour)))     
}