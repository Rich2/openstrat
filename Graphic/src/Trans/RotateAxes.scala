/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

import scala.reflect.ClassTag

/** Type class for 2d geometric transformations that rotate the axes. */
trait RotateAxes[T]
{ /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
  def rotateT90(obj: T): T

  /** Rotates object of type T, 180 degrees or Pi radians. */
  def rotateT180(obj: T): T

  /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
  def rotateT270(obj: T): T
}

/** Companion object for the RotateAxes type class. */
object RotateAxes
{
  implicit def transSimerImplicit[T <: TransSimer]: RotateAxes[T] = new RotateAxes[T] {
    /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: T): T = ???

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: T): T = ???

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: T): T = ???
  }  
  
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: RotateAxes[A]): RotateAxes[F[A]] = new RotateAxes[F[A]]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: F[A]): F[A] = evF.map(obj, evA.rotateT90(_))

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: F[A]): F[A] = evF.map(obj, evA.rotateT180(_))

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: F[A]): F[A] = evF.map(obj, evA.rotateT270(_))
  }

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], evA: RotateAxes[A]): RotateAxes[Array[A]] = new RotateAxes[Array[A]]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: Array[A]): Array[A] = obj.map(evA.rotateT90(_))

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: Array[A]): Array[A] = obj.map(evA.rotateT180(_))

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: Array[A]): Array[A] = obj.map(evA.rotateT270(_))
  }
}

/** Extension class for instances of the RotateAxes type class. */
class RotateAxesExtensions[T](value: T, ev: RotateAxes[T])
