/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Opt[A] extends Any
{ def fold[B](fNull: => B, fSome: A => B): B
}

object Opt { def apply[A <: AnyRef](value: A): Opt[A] = new OptRef(value) }

class OptRef[A <: AnyRef](val ref: A) extends AnyVal with Opt[A]
{
  override def fold[B](fNull: => B, fSome: A => B): B = if (ref == null) fNull else fSome(ref)
  override def toString: String = fold("NoRef", v => "Some(" + v.toString + ")")
}

sealed trait OptInt extends Opt[Int]
{
  def + (operand: OptInt): OptInt = combine(operand, _ + _)
  def combine[B](operand: OptInt, f: (Int, Int) => Int) = this match
  { 
    case SomeInt(v1) => operand match
    {
      case SomeInt(v2) => SomeInt(f(v1, v2))
      case _ => NoInt
    }
    case _ => NoInt
  }
}

case class SomeInt(value: Int) extends OptInt
{
  override def fold[B](fNull: => B, fSome: Int => B): B = fSome(value) 
}

case object NoInt extends OptInt
{ override def fold[B](fNull: => B, fSome: Int => B): B = fNull
}