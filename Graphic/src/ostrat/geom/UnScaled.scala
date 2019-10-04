/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** This is a display object that has a fixed size and alignment. The object itself should not scale or rotate. Hence transformations are applied
 *  to its reference point. This is for objects on a map as opposed to objects that are part of the map. */
trait UnScaledGraphicElem extends GraphicElem

/** This is a shape that has a fixed size and alignment. Hence transformations are applied to its reference point. */
case class UnScaledShape(referenceVec: Vec2, relShape: Shape, evObj: AnyRef, elems: Arr[PaintElem], zOrder: Int = 0) extends
UnScaledGraphicElem with ShapeActiveTr
{ def shape: Shape = relShape.slate(referenceVec)
  def fTrans(f: Vec2 => Vec2): UnScaledShape = UnScaledShape(f(referenceVec), relShape, evObj, elems)
  def addElems(newElems: List[PaintElem]): UnScaledShape = UnScaledShape(referenceVec, shape, evObj, elems ++ newElems)
  def mutObj(newObj: AnyRef): UnScaledShape = UnScaledShape(referenceVec, shape, newObj, elems)
}

object UnScaledShape
{ def fillDraw(referenceVec: Vec2, segs: Shape, evObj: AnyRef, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black):
  UnScaledShape = UnScaledShape(referenceVec, segs, evObj, Arr(ShapeFillDraw(segs, fillColour, lineWidth, lineColour)))
}

/** This is not a Polygon but should fTrans to Polygon. */
trait UnScaledPolygon extends UnScaled
{ type ScaledT = Polygon
  //def polygon: Polygon
  def fTrans(f: Vec2 => Vec2): Polygon = scaled.fTrans(f)
  def dist(width: Dist, cen: Dist2 = Dist2Z): DPolygon  = scaled.distScale(width)
}
