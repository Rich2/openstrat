/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransAffer extends Any with TransSimer
{ type ThisT <: TransSimer
  def shear(xScale: Double, yScale: Double): ThisT
}

/** Affine Transformation */
trait TransAff[T] extends TransSim[T]
{ def shear(obj: T, xScale: Double, yScale: Double): T
}