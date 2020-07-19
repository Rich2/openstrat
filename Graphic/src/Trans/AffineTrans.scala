/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import reflect.ClassTag

/** The typeclass trait for transforming an object in 2d geometry. */
trait AffineTrans[T] extends TransSim[T]
{ def trans(obj: T, f: Vec2 => Vec2):  T
  override def slate(obj: T, offset: Vec2): T = trans(obj, _ + offset)
  override def scale(obj: T, operand: Double): T = trans(obj, _ * operand)
  def shear(obj: T, xScale: Double, yScale: Double): T = trans(obj, v => Vec2(v.x * yScale, v.y * xScale))
  override def rotateRadians(obj: T, radians: Double): T = trans(obj, _.rotateRadians(radians))
  def mirrorYOffset(obj: T, xOffset: Double): T = trans(obj, _.mirrorYOffset(xOffset))
  def mirrorXOffset(obj: T, yOffset: Double): T = trans(obj, _.mirrorXOffset(yOffset))
  override def reflectSegT(obj: T, line: Sline): T = trans(obj, _.reflect(line))
  override def reflectT(obj: T, line: Line): T = trans(obj, _.reflect(line))
}

/** The companion object for the Trans[T] typeclass, containing instances for common classes. */
object AffineTrans
{
  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: AffineTrans[A]): AffineTrans[AA] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def fromTranserAllImplicit[T <: AffinePreserve]: AffineTrans[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: AffineTrans[A]): AffineTrans[F[A]] =
    (obj, f) => evF.map(obj, el => evA.trans(el, f))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: AffineTrans[A]): AffineTrans[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}