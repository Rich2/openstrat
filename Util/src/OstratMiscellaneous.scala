/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Not entirely sure what this type class is for */
trait IsType[A <: AnyRef]
{ def isType(obj: AnyRef): Boolean
  def asType(obj: AnyRef): A
  def optType(obj: AnyRef): Option[A] = ife(isType(obj), Some(asType(obj)), None)
}

object IsType
{
  implicit object AnyRefIsType extends IsType[AnyRef]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[AnyRef]
    override def asType(obj: AnyRef): AnyRef = obj.asInstanceOf[AnyRef]
  }
}

sealed trait JustOrName[+T]

case class Just[T](value: T) extends JustOrName[T]

case class JustName[T](name: String) extends JustOrName[T]

case object Unknown extends JustOrName[Nothing]

object JustNone extends JustOrName[Nothing]