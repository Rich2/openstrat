/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

trait Opt[A] extends Any
{ def foreach(f: A => Unit): Unit
  @inline def empty: Boolean
  @inline def nonEmpty: Boolean
}

trait SomeT[A] extends Any with Opt[A]
{ @inline override def empty: Boolean = false
  @inline override def nonEmpty: Boolean =true
}

trait NoOpt[A] extends Any with Opt[A]
{ @inline override def empty: Boolean = true
  @inline override def nonEmpty: Boolean = false
  override def foreach(f: A => Unit): Unit = {}
}

trait OptInt extends Opt[Int]
{ def map(f: Int => Int): OptInt
  def |+| (operand: OptInt): OptInt
}

case class SomeInt(value: Int) extends OptInt with SomeT[Int]
{ override def foreach(f: Int => Unit): Unit = f(value)
  override def map(f: Int => Int): OptInt = SomeInt(f(value))
  override def |+| (operand: OptInt): OptInt = operand.map(value + _)
}

case object NoInt extends OptInt with NoOpt[Int]
{ override def map(f: Int => Int): OptInt = NoInt
  override def |+| (operand: OptInt): OptInt = NoInt
}

/*
trait DefaultValue[A]
{
  def default: A
}*/
