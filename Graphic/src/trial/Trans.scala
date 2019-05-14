/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package trial

trait Trans[T]{ def trans(obj: T, f: Int => Int): T }

object Trans
{
  implicit def ListTranser[A <: Transer]: Trans[List[A]] = 
    (obj, f) => obj.map(el => el.fTrans(f)).asInstanceOf[List[A]]  
  
  implicit def ListTrans[A](implicit ev: Trans[A]): Trans[List[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))  
  
  implicit def OptionTranser[A <: Transer]: Trans[Option[A]] = 
    (obj, f) => obj.map(el => el.fTrans(f)).asInstanceOf[Option[A]]
    
  implicit def OptionTrans[A](implicit ev: Trans[A]): Trans[Option[A]] =
    (obj, f) => obj.map(el => ev.trans(el, f))
    
  implicit def SomeTrans[A](implicit ev: Trans[A]): Trans[Some[A]] =
    (obj, f) => Some(ev.trans(obj.value, f))  
}