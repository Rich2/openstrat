/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Opt[A] extends Any
{ def fold[B](fNull: => B, fSome: A => B): B
  implicit def map[B](f: A => B)(implicit ev: OptBuild[B]): Opt[B] = ???
}

object Opt
{ def apply[A](value: A)(implicit ev: OptBuild[A]): Opt[A] = ev(value)
}

object NoOpt
{ def apply[A]()(implicit ev: OptBuild[A]): Opt[A] = ev.none
}

object OptRef
{
  def apply[A >: Null <: AnyRef](value: A): OptRef[A] = new OptRef(value)
}

class OptRef[A >: Null <: AnyRef](val ref: A) extends AnyVal with Opt[A]
{ def fold[B](fNull: => B, fSome: A => B): B = ife(ref == null, fNull, fSome(ref))
  override def toString: String = fold("NoRef", v => "Some(" + v.toString + ")")
  def empty: Boolean = ref == null
  def nonEmpty: Boolean = ref != null
}

sealed trait OptInt extends Opt[Int]
{
  def fold[B](fNull: => B, fSome: Int => B): B
  def + (operand: OptInt): OptInt = combine(operand, _ + _)
  def combine[B](operand: OptInt, f: (Int, Int) => Int) = this match
  { 
    case SomeInt(v1) => operand match
    { case SomeInt(v2) => SomeInt(f(v1, v2))
      case _ => NoInt
    }
    case _ => NoInt
  }
}

case class SomeInt(value: Int) extends OptInt
{ override def fold[B](fNull: => B, fSome: Int => B): B = fSome(value)
}

case object NoInt extends OptInt
{ def fold[B](fNull: => B, fSome: Int => B): B = fNull
}

trait OptBuild[B]
{ type OptT <: Opt[B]
  def apply(b: B): Opt[B]
  def none: Opt[B]
}

object OptBuild
{
  implicit val intImplicit: OptBuild[Int] = new OptBuild[Int]
  { override type OptT = OptInt
    def apply(i: Int): OptInt = SomeInt(i)
    def none: OptInt = NoInt
  }

  implicit def refImplicit[B >: Null <: AnyRef] = new OptBuild[B]
  { override type OptT = OptRef[B]
    override def apply(b: B) : OptRef[B] = new OptRef(b)
    override def none: Opt[B] = new OptRef[B](null)
  }
}