/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import reflect.ClassTag
/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: Vec2 => Vec2):
 *  T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T.  */
trait Transer extends Any
{ def fTrans(f: Vec2 => Vec2): Transer
}

/** The typeclass trait for transforming an object in 2d geometry. */
trait Trans[T]
{ def trans(obj: T, f: Vec2 => Vec2):  T
}

/** The companion object for the Trans[T] typeclass, containing instances for common classes. */
object Trans
{
  implicit def TransFromTranserImplicit[T <: Transer]: Trans[T] =
    (obj, f) => obj.fTrans(f).asInstanceOf[T]

  implicit def ArrTrans[A](implicit ct: ClassTag[A], ev: Trans[A]): Trans[Arr[A]] = (obj, f) => obj.map(el => ev.trans(el, f))
  
  implicit def ListTrans[A](implicit ev: Trans[A]): Trans[List[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Trans[A]): Trans[F[A]] =
    (obj, f) => evF.map(obj)(el => evA.trans(el, f))// obj.map(el => ev.trans(el, f))
  
 // implicit def OptionTrans[A](implicit ev: Trans[A]): Trans[Option[A]] =
   // (obj, f) => obj.map(el => ev.trans(el, f))
    
  implicit def SomeTrans[A](implicit ev: Trans[A]): Trans[Some[A]] =
    (obj, f) => Some(ev.trans(obj.value, f))  
  
  implicit def ArrayTrans[A](implicit ct: ClassTag[A], ev: Trans[A]): Trans[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))

  implicit def EitherTrans[A, B](implicit ev: Trans[B]): Trans[Either[A, B]] =
    (obj, f) => obj.map(el => ev.trans(el, f))  
}

