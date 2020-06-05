/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait Polygon extends TransElem
{ def length: Int
  def apply(index: Int): Vec2
  def foreach[U](f: Vec2 => U): Unit
  def foreachTail[U](f: Vec2 => U): Unit
  
  def foldLeft[B](initial: B)(f: (B, Vec2) => B): B =
  { var acc: B = initial
    foreach{ v => acc = f(acc, v) }
    acc
  }
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
