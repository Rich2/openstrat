/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Polygon extends Vec2sLike with Shape with ProlignPreserve
{ type ThisT <: Polygon
  def length: Int
  
  def x0: Double
  def y0: Double
  def elem1sArray: Array[Double]
  def elem2sArray: Array[Double]

  /** Creates a bounding rectangle for a collection of 2d points */
  def boundingRect: BoundingRect =
  { var minX, maxX = x0
    var minY, maxY = y0
    foreachTail{v =>
      minX = minX.min(v.x)
      maxX = maxX.max(v.x)
      minY = minY.min(v.y)
      maxY = maxY.max(v.y)
    }
    BoundingRect(minX, maxX, minY, maxY)
  }


}