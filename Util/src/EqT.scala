/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Equals type class trait has one method eqT that tests Equality on 2 values of type T. */
trait EqT[A]
{
  /** Tests Equality on 2 values of type T. */
  def eqT(a1: A, a2: A): Boolean
}

/** The companion object for the EqT type class, containing instances for common types. This does not currently use a functor instance for a number of
 * reasons. */
object EqT
{
  implicit val intImplicit: EqT[Int] = (a1, a2) => a1 == a2
  implicit val doubleImplicit: EqT[Double] =  (a1, a2) => a1 == a2
  implicit val booleanImplicit: EqT[Boolean] = (a1, a2) => a1 == a2
  implicit val stringImplicit: EqT[String] = (a1, a2) => a1 == a2
  implicit val charImplicit: EqT[Char] = (a1, a2) => a1 == a2
  implicit val noneImplicit: EqT[None.type] = (_, _) => true
  implicit def someImplicit[A](implicit ev: EqT[A]): EqT[Some[A]]= (s1, s2) => ev.eqT(s1.value, s2.value)

  implicit def optionImplicit[A](implicit ev: EqT[A]): EqT[Option[A]] = (a1, a2) => (a1, a2) match
  { case (None, None) => true
    case (Some(v1), Some(v2)) => ev.eqT(v1, v2)
    case _ => false
  }

  implicit def listImplicit[A](implicit ev: EqT[A]): EqT[List[A]] = (l1, l2) =>
  { def loop(rem1: List[A], rem2: List[A]): Boolean = (rem1, rem2) match
    { case (Nil, Nil) => true
      case (::(h1, t1) , ::(h2, t2)) if ev.eqT(h1, h2) => loop(t1, t2)
      case _ => false
    }
    loop(l1, l2)
  }

  implicit def arrayImplicit[A](implicit ev: EqT[A]): EqT[Array[A]] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var count = 0
      var acc = true
      var continue = true

      while (count < a1.length & continue)
      { if (ev.eqT(a1(count), a2(count))) count += 1
        else {acc = false; continue = false}
      }
      acc
    }

  implicit def seqImplicit[A](implicit ev: EqT[A]): EqT[Seq[A]] = (s1, s2) => (s1.length == s2.length) & s1.iForall{ (i, el) => ev.eqT(el, s2(i)) }

  implicit def vectorImplicit[A](implicit ev: EqT[A]): EqT[Vector[A]] = (s1, s2) => (s1.length == s2.length) & s1.iForall{ (i, el) => ev.eqT(el, s2(i)) }

  implicit def tuple2Implicit[A1, A2](implicit eq1: EqT[A1], eq2: EqT[A2]): EqT[(A1, A2)] = (p1, p2) => eq1.eqT(p1._1, p2._1) & eq2.eqT(p1._2, p2._2)
}

class Eq1T[A1, A](val fArg1: A => A1)(implicit eq1: EqT[A1]) extends EqT[A]
{ override def eqT(r1: A, r2: A): Boolean = eq1.eqT(fArg1(r1), fArg1(r2))
}

/** Equality type class trait for Product 2. */
trait Eq2T[A1, A2, A] extends EqT[A]
{ def fArg1: A => A1
  def fArg2: A => A2
  implicit def eq1: EqT[A1]
  implicit def eq2: EqT[A2]
  override def eqT(r1: A, r2: A): Boolean = eq1.eqT(fArg1(r1), fArg1(r2)) & eq2.eqT(fArg2(r1), fArg2(r2))
}

object Eq2T
{
  def apply[A1, A2, A](fArg1In: A => A1, fArg2In: A => A2)(implicit eq1In: EqT[A1], eq2In: EqT[A2]): Eq2T[A1, A2, A] = new Eq2T[A1, A2, A]
  { override def fArg1: A => A1 = fArg1In
    override def fArg2: A => A2 = fArg2In
    override implicit def eq1: EqT[A1] = eq1In
    override implicit def eq2: EqT[A2] = eq2In
  }
}

case class Eq2DblsT[A](fArg1: A => Double, fArg2: A => Double) extends Eq2T[Double, Double, A]
{ override implicit def eq1: EqT[Double] = EqT.doubleImplicit
  override implicit def eq2: EqT[Double] = EqT.doubleImplicit
}

/** Equality type class trait for Product 3. */
class Eq3T[A1, A2, A3, A](val fArg1: A => A1, val fArg2: A => A2, val fArg3: A => A3)(implicit eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3]) extends
  EqT[A]
{ override def eqT(a1: A, a2: A): Boolean = eq1.eqT(fArg1(a1), fArg1(a2)) & eq2.eqT(fArg2(a1), fArg2(a2)) & eq3.eqT(fArg3(a1), fArg3(a2))
}

object Eq3T
{
  def apply[A1, A2, A3, A](fArg1: A => A1, fArg2: A => A2, fArg3: A => A3)(implicit eq1: EqT[A1], eq2: EqT[A2], eq3: EqT[A3]): Eq3T[A1, A2, A3, A] =
    new Eq3T(fArg1, fArg2, fArg3)
}

/** Equality type class trait for Product 4. */
class Eq4T[A1, A2, A3, A4, A](val fArg1: A => A1, val fArg2: A => A2, val fArg3: A => A3, val fArg4: A => A4)(implicit eq1: EqT[A1], eq2: EqT[A2],
                                                                                                              eq3: EqT[A3], eq4: EqT[A4]) extends EqT[A]
{
  override def eqT(a1: A, a2: A): Boolean = eq1.eqT(fArg1(a1), fArg1(a2)) & eq2.eqT(fArg2(a1), fArg2(a2)) & eq3.eqT(fArg3(a1), fArg3(a2)) &
    eq4.eqT(fArg4(a1), fArg4(a2))
}