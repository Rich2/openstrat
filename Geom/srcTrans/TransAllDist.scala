/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** Trait for objects that can be transformed in 2 dimensional distance space. */
trait TransDister extends Any
{ def fTrans(f: PtM2 => PtM2): AffinePreserve
}

/** The companion object for Transer. */
object TransDister
{
  implicit def TransDistFromTransDisterImplicit[T <: TransDister]: TransAllDist[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAllDist[A]): TransAllDist[F[A]] =
    (obj, f) => evF.mapT(obj, el => evA.trans(el, f))
}

/** The typeclass trait for transforming an object in 2d geometry. */
trait TransAllDist[T]
{
  def trans(obj: T, f: PtM2 => PtM2):  T
}

/** The companion object for the TransDist typeclass, containing instances for common classes. */
object TransAllDist
{
  implicit def arrImutImplicit[A, AA <: Arr[A]](implicit build: BuilderMapArr[A, AA], ev: TransAllDist[A]): TransAllDist[AA] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransAllDist[A]): TransAllDist[F[A]] =
    (obj, f) => evF.mapT(obj, el => evA.trans(el, f))

  implicit def arrayImplicit[A](implicit ct: reflect.ClassTag[A], ev: TransAllDist[A]): TransAllDist[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}

/** The object can be transformed in 2 dimensional Distance space. Leaf classes must implement the single method fTrans(f: Dist2 => Dist2):  T The related
 *   trait TranExtension  does the same for trans(f: Vec2 => Vec2): T. */
trait TransAllDistExtension[T] extends Any
{
  def trans(f: PtM2 => PtM2):  T

  def scale(factor: Double): T = trans(_ * factor)
  
  /** this.asInstanceOf[T] */  
  def identity: T = this.asInstanceOf[T]   
  def inverseY: T = trans(v => PtM2(v.x, -v.y))
}