/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Opt[A] extends Any
{ def fold[B](fNull: => B, fSome: A => B): B
  def map[B](f: A => B)(implicit ev: OptBuild[B]): Opt[B]
  def flatMap[B](f: A => Opt[B])(implicit ev: OptBuild[B]): Opt[B]
  def empty: Boolean
  @inline final def nonEmpty: Boolean = ! empty
}

object Opt
{ def apply[A](value: A)(implicit ev: OptBuild[A]): Opt[A] = ev(value)
}

trait SomeT[A] extends Any with Opt[A]
{ @inline def value: A
  final def empty: Boolean = false
  final def flatMap[B](f: A => ostrat.Opt[B])(implicit ev: OptBuild[B]): Opt[B] = f(value)
  final def fold[B](fNone: => B, fSome: A => B): B = fSome(value)
  final def map[B](f: A => B)(implicit ev: OptBuild[B]): Opt[B] = ev.apply(f(value))
}

trait NoOpt[A] extends Any with Opt[A]
{ final def empty: Boolean = true
  final override def fold[B](fNull: => B, fSome: A => B): B = fNull
  final override def map[B](f: A => B)(implicit ev: OptBuild[B]): Opt[B] = ev.none
  final override def flatMap[B](f: A => Opt[B])(implicit ev: OptBuild[B]): Opt[B] = ev.none
}

object NoOpt
{ def apply[A]()(implicit ev: OptBuild[A]): Opt[A] = ev.none
  def unapply[A](inp: Opt[A]): Boolean = inp.empty
}

object OptRef
{ def apply[A >: Null <: AnyRef](value: A): OptRef[A] = new OptRef(value)
}

class OptRef[A >: Null <: AnyRef](val ref: A) extends AnyVal with Opt[A]
{ def fold[B](vNone: => B, fSome: A => B): B = ife(ref == null, vNone, fSome(ref))
  override def toString: String = fold("NoRef", v => "Some(" + v.toString + ")")
  def empty: Boolean = ref == null
  override def map[B](f: A => B)(implicit ev: OptBuild[B]): Opt[B] = ife(empty, ev.none, ev(f(ref)))
  override def flatMap[B](f: A => Opt[B])(implicit ev: OptBuild[B]): Opt[B] = ife(empty, ev.none, f(ref))
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

case class SomeInt(value: Int) extends OptInt with SomeT[Int]
case object NoInt extends OptInt with NoOpt[Int]
{ def unapply(inp: OptInt): Boolean = inp.empty
}

sealed trait OptDbl extends Opt[Double]

case class SomeDbl(value: Double) extends OptDbl with SomeT[Double]

case object NoDbl extends OptDbl with NoOpt[Double]
{ def unapply(inp: OptDbl): Boolean = inp.empty
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

  implicit val doubleImplicit: OptBuild[Double] = new OptBuild[Double]
  { override type OptT = OptDbl
    def apply(inp: Double): OptDbl = SomeDbl(inp)
    def none: OptDbl = NoDbl
  }

  implicit def refImplicit[B >: Null <: AnyRef] = new OptBuild[B]
  { override type OptT = OptRef[B]
    override def apply(b: B) : OptRef[B] = new OptRef(b)
    override def none: Opt[B] = new OptRef[B](null)
  }
}