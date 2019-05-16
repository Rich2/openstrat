/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.geom

/** The typeclass trait for transforming an object in 2d geometry. */
trait Trans[T]
{
  def trans(obj: T, f: Vec2 => Vec2):  T  
}

/** The companion object for the Trans[T] typeclass, containing instances for common classes. */
object Trans
{
  implicit def ListTrans[A](implicit ev: Trans[A]): Trans[List[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
  
  implicit def OptionTrans[A](implicit ev: Trans[A]): Trans[Option[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
    
  implicit def SomeTrans[A](implicit ev: Trans[A]): Trans[Some[A]] =
    (obj, f) => Some(ev.trans(obj.value, f))  
  
  implicit def ArrayTrans[A](implicit ct: reflect.ClassTag[A], ev: Trans[A]): Trans[Array[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
}

