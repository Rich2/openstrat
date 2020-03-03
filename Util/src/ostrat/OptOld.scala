/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

@deprecated trait OptOld[A] extends Any
{ def fold[B](fNull: => B, fSome: A => B): B
  def map[B](f: A => B)(implicit ev: OptBuild[B]): OptOld[B]
  def flatMap[B](f: A => OptOld[B])(implicit ev: OptBuild[B]): OptOld[B]
  def empty: Boolean
  @inline final def nonEmpty: Boolean = ! empty
}

object OptOld
{ def apply[A](value: A)(implicit ev: OptBuild[A]): OptOld[A] = ev(value)
}

trait SomeT[A] extends Any with OptOld[A]
{ @inline def value: A
  final def empty: Boolean = false
  final def flatMap[B](f: A => ostrat.OptOld[B])(implicit ev: OptBuild[B]): OptOld[B] = f(value)
  final def fold[B](fNone: => B, fSome: A => B): B = fSome(value)
  final def map[B](f: A => B)(implicit ev: OptBuild[B]): OptOld[B] = ev.apply(f(value))
}

trait NoOptOld[A] extends Any with OptOld[A]
{ final def empty: Boolean = true
  final override def fold[B](fNull: => B, fSome: A => B): B = fNull
  final override def map[B](f: A => B)(implicit ev: OptBuild[B]): OptOld[B] = ev.none
  final override def flatMap[B](f: A => OptOld[B])(implicit ev: OptBuild[B]): OptOld[B] = ev.none
}

object NoOptOld
{ def apply[A]()(implicit ev: OptBuild[A]): OptOld[A] = ev.none
  def unapply[A](inp: OptOld[A]): Boolean = inp.empty
}

object OptOldRef
{ def apply[A >: Null <: AnyRef](value: A): OptOldRef[A] = new OptOldRef(value)
}

class OptOldRef[A >: Null <: AnyRef](val ref: A) extends AnyVal with OptOld[A]
{ def fold[B](vNone: => B, fSome: A => B): B = ife(ref == null, vNone, fSome(ref))
  override def toString: String = fold("NoRef", v => "Some(" + v.toString + ")")
  def empty: Boolean = ref == null
  override def map[B](f: A => B)(implicit ev: OptBuild[B]): OptOld[B] = ife(empty, ev.none, ev(f(ref)))
  override def flatMap[B](f: A => OptOld[B])(implicit ev: OptBuild[B]): OptOld[B] = ife(empty, ev.none, f(ref))
}

sealed trait OptOldInt extends OptOld[Int]
{
  def fold[B](fNull: => B, fSome: Int => B): B
  def + (operand: OptOldInt): OptOldInt = combine(operand, _ + _)

  def combine[B](operand: OptOldInt, f: (Int, Int) => Int) = this match
  {
    case SomeInt(v1) => operand match
    { case SomeInt(v2) => SomeInt(f(v1, v2))
      case _ => NoIntOld
    }
    case _ => NoIntOld
  }
}

case class SomeInt(value: Int) extends OptOldInt with SomeT[Int]

case object NoIntOld extends OptOldInt with NoOptOld[Int]
{ def unapply(inp: OptOldInt): Boolean = inp.empty
}

trait OptBuild[B]
{ type OptT <: OptOld[B]
  def apply(b: B): OptOld[B]
  def none: OptOld[B]
}