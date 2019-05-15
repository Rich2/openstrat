/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat.geom

/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must implement the single method fTrans(f: Vec2 => Vec2):
 *  T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T. */
trait Trans[T]
{
  def trans(obj: T, f: Vec2 => Vec2):  T  
}

object Trans
{
  implicit def ListTrans[A](implicit ev: Trans[A]): Trans[List[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
  
  implicit def OptionTrans[A](implicit ev: Trans[A]): Trans[Option[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
    
  implicit def SomeTrans[A](implicit ev: Trans[A]): Trans[Some[A]] =
    (obj, f) => Some(ev.trans(obj.value, f))  
  
//  import scala.reflect.ClassTag  
//  
//  implicit def toTransArray[TT <: Transable[_ ]](arr: Array[TT])(ev: ClassTag[TT]) = new ImplicitTransableArray[TT](arr, ev)  
//  class ImplicitTransableArray[TT <: Transable[_ ]](val arr: Array[TT], implicit val ev: ClassTag[TT]) extends Transable[Array[TT]]
//  {
//    def fTrans(f: Vec2 => Vec2): Array[TT] = arr.map(_.fTrans(f).asInstanceOf[TT])         
//  }
//  
//  implicit def toTransArr[A <: Transable[_]](arr: Arr[A])(ev: ClassTag[A], ev2: Array[A] => Arr[A]) = new ImplicitTransableArr[A](arr, ev, ev2)
//  class ImplicitTransableArr[A <: Transable[_]](val arr: Arr[A], implicit val ev: ClassTag[A], implicit val ev2: Array[A] => Arr[A]) extends
//  Transable[Arr[A]]
//  {
//    def fTrans(f: Vec2 => Vec2): Arr[A] = arr.map[A](_.fTrans(f).asInstanceOf[A])         
//  }  
}

