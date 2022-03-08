/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** This is not a [[Polygon]] but can apply or transformed to a Polygon. Not sure how useful this trait is. not sure if it is the correct approach to
 * preserving information. */
trait PolygonMemIcon
{ def apply(): PolygonGen
  def memScale: Polygon = ???
  def memIconScale: Double
}

trait PolygonYMirror extends Polygon
{
  def rtLine: LinePath

  /** Performs the side effecting function on the [[Pt2]] value of each vertex. */
  override def vertsForeach[U](f: Pt2 => U): Unit = {
    rtLine.dataForeach(f(_))
  }

  /** Performs the side effecting function on the [[Pt2]] value of each vertex, excluding vertex v1. */
  override def vertsTailForeach[U](f: Pt2 => U): Unit = ???

  /** Returns the vertex of the given index. Throws if the index is out of range, if it less than 1 or greater than the number of vertices. */
  override def unsafeVert(index: Int): Pt2 = ???

  /** The number of vertices and also the number of sides in this Polygon. */
  override def vertsNum: Int = ???
}

/** An unscaled polygon that is symmetrical about the Y axis. */
trait UnScaledPolygonYMirror extends PolygonMemIcon
{
  /* The right side of the Y Axis of this UnscaledPolygon, defined relative to a unit of 100 for convenience. So 0.35 is defined as 35. 0.222 is defined as 22.2  */
  def rtLine100: LinePath
  final override def apply(): PolygonGen = rtLine100.yMirrorClose.slateY(-50).scale(0.01)
  def memIconScale: Double = 100
}
