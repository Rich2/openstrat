/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
//import Colour.Black

/** A Geometrical object or shape that has not been scaled. That has its iconic scale. An object centred on x = , y = 0, all the object is between x =
 * +- 0.5 and y = +- 0.5 */
/*trait UnScaled extends Any with TranserAll
{ type ThisT <: TranserAll
  def apply(): ThisT
  def fTrans(f: Vec2 => Vec2): ThisT
}*/


/** This is a display object that has a fixed size and alignment. The object itself should not scale or rotate. Hence transformations are applied
 *  to its reference point. This is for objects on a map as opposed to objects that are part of the map. */
//trait UnScaledGraphicElem extends GraphicFullElem

/*/** This is a shape that has a fixed size and alignment. Hence transformations are applied to its reference point. */
case class UnScaledShape(referenceVec: Vec2, relShape: PolyCurve, pointerId: Any, elems: Arr[PaintFullElem]) extends
UnScaledGraphicElem with ShapeActive
{ override type ThisT = UnScaledShape
  def shape: PolyCurve = relShape.slate(referenceVec)
  def fTrans(f: Vec2 => Vec2): UnScaledShape = UnScaledShape(f(referenceVec), relShape, pointerId, elems)
  def addElems(newElems: Arr[PaintFullElem]): UnScaledShape = UnScaledShape(referenceVec, shape, pointerId, elems ++ newElems)
  def mutObj(newObj: AnyRef): UnScaledShape = UnScaledShape(referenceVec, shape, newObj, elems)
}*/

/*object UnScaledShape
{ def fillDraw(referenceVec: Vec2, segs: PolyCurve, evObj: Any, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black):
  UnScaledShape = UnScaledShape(referenceVec, segs, evObj, Arr(PolyCurveFillDraw(segs, fillColour, lineWidth, lineColour)))
}*/

/** This is not a Polygon but should fTrans to Polygon. */
trait UnScaledPolygon //extends  UnScaled
{ type ThisT = PolygonGen
  def apply: PolygonGen
  def fTrans(f: Vec2 => Vec2): ThisT = apply.fTrans(f)
  def dist(width: Dist, cen: Dist2 = Dist2Z): PolygonDist  = apply.distScale(width)
  def minX: Double = apply.minX
  def maxX: Double = apply.maxX
  def minY: Double = apply.minY
  def maxY: Double = apply.maxY
}

trait UnScaledPolygonYMirror extends UnScaledPolygon
{
  /* The right side of the Y Axis of this UnscaledPolygon, defined relative to a unit of 100 for convenience. So 0.35 is defined as 35. 0.222 is defined as 22.2  */
  def rtLine100: Vec2s
  final override def apply = rtLine100.yMirrorClose.slateY(-50).scaleOld(0.01)
}