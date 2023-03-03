/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** Type class for 3D [[Length]] geometric rotation transformations of objects of type T. */
trait RotateM3T[T]
{ /** Rotate around the X axis, viewed from positive X. A positive angle is anti clockwise. */
  def rotateXT(obj: T, angle: AngleVec): T

  /** Rotates this vector around the Y axis, viewed form positive Y through the given angle around the origin. */
  def rotateYT(obj: T, angle: AngleVec): T

  /** Rotate around the Z axis, viewed from positive Z. A positive angle is anti clockwise. */
  def rotateZT(obj: T, angle: AngleVec): T

  /** Rotate 180 degrees around the Z axis. */
  def rotateZ180T(obj: T): T
}

/** Companion object for the Rotate[T] type class, contains implicit instances for collections and other container classes. */
object RotateM3T
{
  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: ArrMapBuilder[A, AA], ev: RotateM3T[A]): RotateM3T[AA] = new RotateM3T[AA]
  { override def rotateXT(obj: AA, angle: AngleVec): AA = obj.map(ev.rotateXT(_, angle))
    override def rotateYT(obj: AA, angle: AngleVec): AA = obj.map(ev.rotateYT(_, angle))
    override def rotateZT(obj: AA, angle: AngleVec): AA = obj.map(ev.rotateZT(_, angle))
    override def rotateZ180T(obj: AA): AA = obj.map(ev.rotateZ180T(_))
  }

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: RotateM3T[A]): RotateM3T[F[A]] = new RotateM3T[F[A]]
  { override def rotateXT(obj: F[A], angle: AngleVec): F[A] = evF.mapT(obj, evA.rotateXT(_, angle))
    override def rotateYT(obj: F[A], angle: AngleVec): F[A] = evF.mapT(obj, evA.rotateYT(_, angle))
    override def rotateZT(obj: F[A], angle: AngleVec): F[A] = evF.mapT(obj, evA.rotateZT(_, angle))
    override def rotateZ180T(obj: F[A]): F[A] = evF.mapT(obj, evA.rotateZ180T(_))
  }
  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: RotateM3T[A]): RotateM3T[Array[A]] = new RotateM3T[Array[A]]
  { override def rotateXT(obj: Array[A], angle: AngleVec): Array[A] = obj.map(ev.rotateXT(_, angle))
    override def rotateYT(obj: Array[A], angle: AngleVec): Array[A] = obj.map(ev.rotateYT(_, angle))
    override def rotateZT(obj: Array[A], angle: AngleVec): Array[A] = obj.map(ev.rotateYT(_, angle))
    override def rotateZ180T(obj: Array[A]): Array[A] = obj.map(ev.rotateZ180T(_))
  }
}

/** Extension class for instances of the RotateM3 type class. */
class RotateM3Extensions[T](value: T, ev: RotateM3T[T])
{
  /** Rotate around the X axis, 3D geometric transformation of the object by the [[AngleVec]] parameter. */
  def rotateX(angle: AngleVec): T = ev.rotateXT(value, angle)

  /** Rotate around the Y axis, 3D geometric transformation of the object by the [[AngleVec]] parameter. */
  def rotateY(angle: AngleVec): T = ev.rotateYT(value, angle)

  /** Rotate around the Z axis, 3D geometric transformation of the object by the [[AngleVec]] parameter. */
  def rotateZ(angle: AngleVec): T = ev.rotateZT(value, angle)

  /** Rotate around the Z axis, 3D geometric transformation of the object by 180 degrees. */
  def rotateZ180: T = ev.rotateZ180T(value)
}

/** Implementations for Type class for 3D [[Length]] geometric rotation transformations of objects of type T. */
trait RotateM3TPtPt[T] extends RotateM3T[T]
{ def fptp(obj: T, f: PtM3 => PtM3): T
  override def rotateXT(obj: T, angle: AngleVec): T = fptp(obj, _.rotateX(angle))
  override def rotateYT(obj: T, angle: AngleVec): T = fptp(obj, _.rotateY(angle))
  override def rotateZT(obj: T, angle: AngleVec): T = fptp(obj, _.rotateZ(angle))
  override def rotateZ180T(obj: T): T = fptp(obj, _.rotateZ180)
}