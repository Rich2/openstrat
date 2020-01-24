/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import reflect.ClassTag
/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must
 *  implement the single method fTrans(f: Vec2 => Vec2): T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T.  */
trait Transer extends Any
{ def fTrans(f: Vec2 => Vec2): Transer
}

/** A Geometrical object or shape that has not been scaled. That has its iconic scale. An object centred on x = , y = 0, all the object is between x =
 * +- 0.5 and y = +- 0.5 */
trait UnScaled extends Any with Transer
{ type TranserT <: Transer
  def scaled: TranserT
  def fTrans(f: Vec2 => Vec2): TranserT
}

/** The typeclass trait for transforming an object in 2d geometry. */
trait Trans[T]
{ def trans(obj: T, f: Vec2 => Vec2):  T
}

/** The companion object for the Trans[T] typeclass, containing instances for common classes. */
object Trans
{
  implicit def ArrImutImplicit[A, AA <: ArrImut[A]](implicit build: ArrBuild[A, AA], ev: Trans[A]): Trans[AA] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def fromScaledImplicit[T <: Transer]: Trans[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]

  implicit def fromUnScaledImplicit[T <: UnScaled]: Trans[T#TranserT] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T#TranserT]

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Trans[A]): Trans[F[A]] =
    (obj, f) => evF.map(obj, el => evA.trans(el, f))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Trans[A]): Trans[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}