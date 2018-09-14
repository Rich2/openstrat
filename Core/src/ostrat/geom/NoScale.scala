/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** This is a display object that has a fixed size and alignment. The object itself should not scale or rotate. Hence transformations are applied
 *  to its reference point. This is for objects on a map as opposed to objects that are part of the map. */
trait NoScaleGraphicElem[T <: NoScaleGraphicElem[T]] extends GraphicElem[T]

/** This is a shape that has a fixed size and alignment. Hence transformations are applied to its reference point. */
case class NoScaleShape(referenceVec: Vec2, relShape: Shape, evObj: AnyRef, elems: List[PaintElem[_]]) extends NoScaleGraphicElem[NoScaleShape]
with ClickShapeTr
{
   def shape: Shape = relShape.slate(referenceVec)
   def fTrans(f: Vec2 => Vec2): NoScaleShape = NoScaleShape(f(referenceVec), relShape, evObj, elems)   
   def addElems(newElems: List[PaintElem[_]]): NoScaleShape = NoScaleShape(referenceVec, shape, evObj, elems ++ newElems)
   def mutObj(newObj: AnyRef): NoScaleShape = NoScaleShape(referenceVec, shape, newObj, elems)
}

object NoScaleShape
{
   def fillDraw(referenceVec: Vec2, segs: Shape, evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black):
   NoScaleShape = NoScaleShape(referenceVec, segs, evObj, List(ShapeFillDraw(segs, fillColour, lineWidth, lineColour)))
}

//case class NoScalePoly