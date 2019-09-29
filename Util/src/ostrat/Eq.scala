package ostrat

trait Eq[A]
{ def eqv(a1: A, a2: A): Boolean
}

/** The campanion object for the Eq typeclass, containing instances for common types. This does not currently use a functor instance for a number of
 * reasons. */
object Eq
{
  implicit val intImplicit: Eq[Int] = (a1, a2) => a1 == a2

  implicit val doubleImplicit: Eq[Double] = (d1, d2) => d1 == d2
  /*{ val precision = 1e12
  ((d1 - d2).abs/(d1.abs.max(d2.abs).max(1))) * precision  < 1
  }*/

  implicit val booleanImplicit: Eq[Boolean] = (a1, a2) => a1 == a2
  implicit val stringImplicit: Eq[String] = (a1, a2) => a1 == a2
  implicit val charImplicit: Eq[Char] = (a1, a2) => a1 == a2

  implicit def optionImplicit[A](implicit ev: Eq[A]): Eq[Option[A]] = (a1, a2) => (a1, a2) match
  { case (None, None) => true
    case (Some(v1), Some(v2)) => ev.eqv(v1, v2)
    case _ => false
  }

  implicit def listImplicit[A](implicit ev: Eq[A]): Eq[List[A]] = (l1, l2) =>
  { def loop(rem1: List[A], rem2: List[A]): Boolean = (rem1, rem2) match
    { case (Nil, Nil) => true
      case (h1 :: t1, h2 :: t2) if ev.eqv(h1, h2) => loop(t1, t2)
      case _ => false
    }
    loop(l1, l2)
  }

  implicit def arrayImplicit[A](implicit ev: Eq[A]): Eq[Array[A]] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var count = 0
      var acc = true
      var continue = true

      while (count < a1.length & continue)
      { if (ev.eqv(a1(count), a2(count))) count += 1
        else {acc = false; continue = false}
      }
      acc
    }

  implicit def arrImplicit[A](implicit ev: Eq[A]): Eq[Arr[A]] = (a1, a2) =>
    if(a1.length != a2.length) false
    else
    { var count = 0
      var acc = true
      var continue = true

      while (count < a1.length & continue)
      { if (ev.eqv(a1(count), a2(count))) count += 1
        else {acc = false; continue = false}
      }
      acc
    }

  implicit def seqImplicit[A](implicit ev: Eq[A]): Eq[Seq[A]] = (s1, s2) => (s1.length == s2.length) & s1.iForall((el, i) => ev.eqv(el, s2(i)))
  
  implicit def vectorImplicit[A](implicit ev: Eq[A]): Eq[Vector[A]] = (s1, s2) => (s1.length == s2.length) & s1.iForall((el, i) => ev.eqv(el, s2(i)))

  implicit def tuple2Implicit[A1, A2](implicit eq1: Eq[A1], eq2: Eq[A2]): Eq[(A1, A2)] = (p1, p2) => eq1.eqv(p1._1, p2._1) & eq2.eqv(p1._2, p2._2)
}

class EqCase1[A1, R](val fArg1: R => A1)(implicit eq1: Eq[A1]) extends Eq[R]
{ override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2))
}

class EqCase2[A1, A2, R](val fArg1: R => A1, val fArg2: R => A2)(implicit eq1: Eq[A1], eq2: Eq[A2]) extends Eq[R]
{ override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2))
}

object EqCase2
{ def apply[A1, A2, R](fArg1: R => A1, fArg2: R => A2)(implicit eq1: Eq[A1], eq2: Eq[A2]): EqCase2[A1, A2, R] = new EqCase2(fArg1, fArg2)
}

class EqCase3[A1, A2, A3, R](val fArg1: R => A1, val fArg2: R => A2, val fArg3: R => A3)(implicit eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]) extends
  Eq[R]
{ override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2)) & eq3.eqv(fArg3(a1), fArg3(a2))
}

object EqCase3
{
  def apply[A1, A2, A3, R](fArg1: R => A1, fArg2: R => A2, fArg3: R => A3)(implicit eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3]): EqCase3[A1, A2, A3, R] =
    new EqCase3(fArg1, fArg2, fArg3)
}

class EqCase4[A1, A2, A3, A4, R](val fArg1: R => A1, val fArg2: R => A2, val fArg3: R => A3, val fArg4: R => A4)(implicit eq1: Eq[A1], eq2: Eq[A2],
  eq3: Eq[A3], eq4: Eq[A4]) extends Eq[R]
{
  override def eqv(a1: R, a2: R): Boolean = eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2)) & eq3.eqv(fArg3(a1), fArg3(a2)) &
    eq4.eqv(fArg4(a1), fArg4(a2))
}

class EqCase5[A1, A2, A3, A4, A5, R](val fArg1: R => A1, val fArg2: R => A2, val fArg3: R => A3, val fArg4: R => A4, val fArg5: R => A5)(implicit
  eq1: Eq[A1], eq2: Eq[A2], eq3: Eq[A3], eq4: Eq[A4], eq5: Eq[A5]) extends Eq[R]
{ override def eqv(a1: R, a2: R): Boolean =
    eq1.eqv(fArg1(a1), fArg1(a2)) & eq2.eqv(fArg2(a1), fArg2(a2)) & eq3.eqv(fArg3(a1), fArg3(a2)) & eq4.eqv(fArg4(a1), fArg4(a2)) &
    eq5.eqv(fArg5(a1), fArg5(a2))
}
