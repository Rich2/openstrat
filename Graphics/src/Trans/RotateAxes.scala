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
  implicit def transSimerImplicit[T <: SimilarPreserve]: RotateAxes[T] = new RotateAxes[T]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: T): T = obj.rotate90.asInstanceOf[T]

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: T): T = obj.rotate180.asInstanceOf[T]

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: T): T = obj.rotate270.asInstanceOf[T]
  }  
  
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: RotateAxes[A]): RotateAxes[F[A]] = new RotateAxes[F[A]]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: F[A]): F[A] = evF.mapT(obj, evA.rotateT90(_))

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: F[A]): F[A] = evF.mapT(obj, evA.rotateT180(_))

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: F[A]): F[A] = evF.mapT(obj, evA.rotateT270(_))
  }

  implicit def arrBaseImplicit[A, ArrT <: ArrBase[A]](implicit build: ArrBuild[A, ArrT], evA: RotateAxes[A]): RotateAxes[ArrT] = new RotateAxes[ArrT]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: ArrT): ArrT = obj.map(evA.rotateT90(_))

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: ArrT): ArrT = obj.map(evA.rotateT180(_))

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: ArrT): ArrT = obj.map(evA.rotateT270(_))
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
{ /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: T = ev.rotateT90(value)

  /** Rotates 180 degrees or Pi radians. */
  def rotate180: T = ev.rotateT180(value)

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: T = ev.rotateT270(value)

  /** Rotates 90 degrees or Pi / 2 radians clockwise. */
  def clk90: T = ev.rotateT270(value)

  /** Produces a regular cross of a sequence of four of the elements rotated */
  def rCross: Seq[T] = List(value, ev.rotateT270(value), ev.rotateT180(value), ev.rotateT90(value))

  def rCrossArr[TT <: ArrBase[T]](implicit build: ArrBuild[T, TT]): TT =  rCross.toImut// ??? // iToMap(1, 4)(i => rotate(deg90 * i))
}
