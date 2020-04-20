/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransAffer extends TransSimer
{ type ThisT <: TransSimer
  def shear(xScale: Double, yScale: Double): ThisT
}

/** Affine Transformation */
trait TransAff[T] extends TransSim[T]
{ def shear(obj: T, xScale: Double, yScale: Double): T
}

trait TransSimer extends TransRigider
{ type ThisT <: TransSimer
  def scale(operand: Double): ThisT
}

/** A Similar Transformations type class */
trait TransSim[T] extends TransRigid[T]
{ def scale(obj: T, operand: Double): T
}

trait TransRigider
{ type ThisT <: TransRigider
  def slate(offset: Vec2): ThisT
  def rotateRadians(radians: Double): ThisT
  //def rotate(angle: Angle): T
}

/** A Rigid or Euclidean transformations type class. */
trait TransRigid[T]
{
  def slate(obj: T, offset: Vec2): T
  def rotateRadians(obj: T, radians: Double): T
  //def rotate(obj: T, angle: Angle): T
  //def reflect
}
