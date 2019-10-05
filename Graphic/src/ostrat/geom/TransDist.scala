/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

/** Trait for objects that can be transformed in 2 dimensional distance space. */
trait TransDister extends Any
{ def fTrans(f: Dist2 => Dist2): Transer
}

/** The companion object for Transer. */
object TransDister
{
  implicit def TransDistFromTransDisterImplicit[T <: TransDister]: TransDist[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: TransDist[A]): TransDist[F[A]] =
    (obj, f) => evF.map(obj, el => evA.trans(el, f))
}

/** The typeclass trait for transforming an object in 2d geometry. */
trait TransDist[T]
{
  def trans(obj: T, f: Dist2 => Dist2):  T  
}

/** The companion object for the TransDist typeclass, containing instances for common classes. */
object TransDist
{
  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Trans[A]): Trans[F[A]] =
    (obj, f) => evF.map(obj, el => evA.trans(el, f))

  implicit def ArrayTransDist[A](implicit ct: reflect.ClassTag[A], ev: TransDist[A]): TransDist[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}

/** The object can be transformed in 2 dimensional Distance space. Leaf classes must implement the single method fTrans(f: Dist2 => Dist2):  T The related
 *   trait TranExtension  does the same for trans(f: Vec2 => Vec2): T. */
trait TransDistExtension[T] extends Any with TransGenExtension[T]
{
  def trans(f: Dist2 => Dist2):  T  
  def slate(offset: Dist2): T = trans(_ + offset)
  def slate(xOffset: Dist, yOffset: Dist): T = trans(_.addXY(xOffset, yOffset))
  def slateX(xOffset: Dist): T = trans(_.addX(xOffset))
  def slateY(yOffset: Dist): T = trans(_.addY(yOffset))
  def scale(factor: Double): T = trans(_ * factor)

  override def rotate(angle: Angle): T = trans(_.rotate(angle))
  override def rotateRadians(r: Double): T = trans(_.rotateRadians(r))
  
  /** this.asInstanceOf[T] */  
  def identity: T = this.asInstanceOf[T]   
  def inverseY: T = trans(v => Dist2(v.x, -v.y))  
}