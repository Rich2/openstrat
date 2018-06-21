/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pEarth

/** Not sure if this trait needs to exist anymore */
trait ESide

object ESide
{
   implicit object ESideIsType extends IsType[ESide]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[ESide]
      override def asType(obj: AnyRef): ESide = obj.asInstanceOf[ESide]      
   }
}

object SideNone extends ESide
object SideEdge extends ESide
object Straits extends ESide

object VertIn