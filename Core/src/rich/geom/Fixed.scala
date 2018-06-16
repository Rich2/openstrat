/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
import Colour.Black

/** I think this might be better called Aligned rather than Fixed */
trait FixedCanvObj[T <: FixedCanvObj[T]] extends CanvObj[T]

/** Not sure what this is. Its not just a Shape though */
case class FixedShape(posn: Vec2, shape: Seq[ShapeSeg], evObj: AnyRef, elems: Seq[CanvEl[_]]) extends FixedCanvObj[FixedShape]
{  
   def fTrans(f: Vec2 => Vec2): FixedShape = FixedShape(f(posn), shape, evObj, elems)
   def boundingRect: BoundingRect = shape.boundingRect
   //override def ptIn: Vec2 => Boolean = shape.ptInShape
   def addElems(newElems: Seq[CanvEl[_]]): FixedShape = FixedShape(posn, shape, evObj, elems ++ newElems)
   def mutObj(newObj: AnyRef): FixedShape = FixedShape(posn, shape, newObj, elems)
}

object FixedShape
{
   def fillDraw(posn: Vec2, segs: Seq[ShapeSeg], evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black): FixedShape =
      FixedShape(posn, segs, evObj, Seq(FillDrawShape(segs, fillColour, lineWidth, lineColour)))
}