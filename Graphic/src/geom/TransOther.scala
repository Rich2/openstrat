/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Affine Transformation */
trait TransAffine[T] extends TransSim[T]
{
  def shear(obj: T, xScale: Double, yScale: Double): T
}

/** A Similar Transformations type class */
trait TransSim[T] extends TransRigid[T]
{
  def scale(obj: T, operand: Double): T
}

/** A Rigid or Euclidean transformations type class. */
trait TransRigid[T]
{
  def slate(obj: T, offset: Vec2): T
  //def rotate
  //def reflect
}
