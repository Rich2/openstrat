/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom

case class ShapeSubj(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, elems: Seq[CanvEl[_]]) extends CanvSubj[ShapeSubj] with
   ClickShapeTr
{  
   def fTrans(f: Vec2 => Vec2): ShapeSubj = ShapeSubj(f(cen), shape.fTrans(f), evObj, elems.fTrans(f))   
   override def addElems(newElems: Seq[CanvEl[_]]): ShapeSubj = ShapeSubj(cen, shape, evObj, elems ++ newElems)
   override def mutObj(newObj: AnyRef): ShapeSubj = ShapeSubj(cen, shape, newObj, elems)
}

object ShapeSubj
{
   def fill(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, colour: Colour) = ShapeSubj(cen, shape, evObj, Seq(FillShape(shape, colour)))
   
   def fillDraw(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, fillColour: Colour, lineWidth: Int, lineColour: Colour) =
      ShapeSubj(cen, shape, evObj, Seq(FillDrawShape(shape, fillColour, lineWidth, lineColour)))
   
   def draw(cen: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, lineWidth: Double, lineColour: Colour = Colour.Black) =
      ShapeSubj(cen, shape, evObj, Seq(DrawShape(shape, lineWidth, lineColour)))     
}