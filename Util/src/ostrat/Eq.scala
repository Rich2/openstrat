package ostrat

trait Eq[A]
{ def eqv(a1: A, a2: A): Boolean
}

object Eq
{
  implicit val intImplicit: Eq[Int] = (a1, a2) => a1 == a2

  implicit val doubleImplicit: Eq[Double] = (d1, d2) =>
  { val precision = 1e12
  ((d1 - d2).abs/(d1.abs.max(d2.abs).max(1))) * precision  < 1
  }

  // implicit def functorImplicit[A, F[A]](implicit evF: Functor[F], evA: Eq[A]): Eq[F[A]] = (a1, a2) => f => evF.map(f)

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
}
