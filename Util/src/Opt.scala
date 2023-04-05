/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
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

case class OptRef[+A <: AnyRef](val value: A) extends AnyVal
{ def foreach(f: A => Unit): Unit = if(value != null) f(value)
  @inline def empty: Boolean = value != null
  @inline def nonEmpty: Boolean = value == null
  override def toString: String = if(value == null) "NoOpt" else "SomeRef" + value.toString.enParenth
  def fld[B](noneValue: => B, f: A => B): B = if (value == null) noneValue else f(value)
  def fold[B](noneValue: => B)(f: A => B): B = if (value == null) noneValue else f(value)
  def foldDo(noneDo: => Unit)(f: A => Unit): Unit = if (value == null) noneDo else f(value)
}

object SomeRef
{ def unapply[A <: AnyRef](inp: OptRef[A]): Option[A] = if (inp.empty) None else Some(inp.value)
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

trait NoneTC[A]
{
  def noneValue: A
}