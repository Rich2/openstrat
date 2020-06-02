/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** Type class for the rotation of objects of type T */
trait Rotate[T]
{ def rotateRadiansT(obj: T, radians: Double): T
}

object Rotate
{
  implicit def transSimerImplicit[T <: TransSimer]: Rotate[T] = (obj, radians) => obj.rotateRadians(radians).asInstanceOf[T]
  
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: Rotate[A]): Rotate[AA] =
    (obj, radians) => obj.map(ev.rotateRadiansT(_, radians))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Rotate[A]): Rotate[F[A]] =
    (obj, radians) => evF.map(obj, evA.rotateRadiansT(_, radians))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Rotate[A]): Rotate[Array[A]] = (obj, radians) => obj.map(ev.rotateRadiansT(_, radians))
}

/** Extension class for instances of the Rotate type class. */
class RotateExtensions[T](value: T, ev: Rotate[T]) extends RotateGenExtensions [T]
{
  override def rotateRadians(radians: Double): T = ev.rotateRadiansT(value, radians)
  def rotate(angle: Angle): T = ev.rotateRadiansT(value, angle.radians)
}

trait RotateGenExtensions[T]
{
  def rotateRadians(radians: Double): T
  def rotate(angle: Angle): T
}
