/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** The type class trait for transforming an object in 2d geometry. Note overrides necessary to preserve type. */
trait AffineTrans[T] extends TransSim[T]
{ def trans(obj: T, f: Pt2 => Pt2):  T
  override def slate(obj: T, offset: Vec2Like): T = trans(obj, _.slate(offset))
  override def scale(obj: T, operand: Double): T = trans(obj, _.scale(operand))
  def shear(obj: T, xScale: Double, yScale: Double): T = trans(obj, v => Pt2(v.x * yScale, v.y * xScale))
  override def rotate(obj: T, angle: AngleVec): T = trans(obj, _.rotate(angle))
  override def reflectT(obj: T, line: LineLike): T = trans(obj, _.reflect(line))
}

/** The companion object for the Trans[T] type class, containing instances for common classes. */
object AffineTrans
{
  implicit def arrImplicit[A, AA <: Arr[A]](implicit build: BuilderArrMap[A, AA], ev: AffineTrans[A]): AffineTrans[AA] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def fromTranserAllImplicit[T <: AffinePreserve]: AffineTrans[T] =
    (obj, f) => obj.ptsTrans(f).asInstanceOf[T]

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: AffineTrans[A]): AffineTrans[F[A]] =
    (obj, f) => evF.mapT(obj, el => evA.trans(el, f))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: AffineTrans[A]): AffineTrans[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}