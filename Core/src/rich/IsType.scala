/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

trait IsType[A <: AnyRef]
{
   def isType(obj: AnyRef): Boolean
   def asType(obj: AnyRef): A 
   def optType(obj: AnyRef): Option[A] = ifSome(isType(obj), asType(obj))  
}

object IsType
{
   implicit object AnyRefIsType extends IsType[AnyRef]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[AnyRef]
      override def asType(obj: AnyRef): AnyRef = obj.asInstanceOf[AnyRef]   
   }   
}

