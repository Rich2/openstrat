/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait PolygonLike[VertT] extends Any
{
  /** The number of vertices and also the number of sides in this Polygon. */
  def vertsNum: Int

  def foreachVert[U](f: VertT => U): Unit

  /** This method should be overridden in final classes. */
  def vertsMap[B, ArrB <: SeqImut[B]](f: VertT => B)(implicit builder: ArrTBuilder[B, ArrB]): ArrB =
  { val res = builder.newArr(vertsNum)
    var count = 0
    foreachVert{ v =>
      builder.arrSet(res, count, f(v))
      count += 1
    }
    res
  }

  //def foreachLineVert[U](f: VertT => U): Unit
}
