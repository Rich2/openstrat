/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** The Multiple type class allow you to represent multiple values of type A. Implicit conversion in package object. */
case class Multiple[+A](value: A, num: Int)
{ def * (operand: Int): Multiple[A] = Multiple(value, num * operand)
  override def toString = "Multiple" + (value.toString + "; " + num.toString).enParenth

  def foreach(f: A => Unit): Unit = num.doTimes(() => f(value))

  def singlesList: List[A] =
  {
    var acc: List[A] = Nil
    var count = 0
    while (count < num)
    { acc ::= value
      count += 1
    }
    acc
  }
  def map[B](f: A => B): Multiple[B] = Multiple[B](f(value), num)

  def flatMap[B](f: A => Multiple[B]) =
  { val res = f(value)
    Multiple[B](res.value, res.num * num)
  }
}

/** Companion object for the Multiple[+A] type class. */
object Multiple
{
  implicit def eqImplicit[A](implicit ev: EqT[A]): EqT[Multiple[A]] = (a1, a2) => (a1.num == a2.num) & ev.eqv(a1.value, a2.value)

  implicit def toMultipleImplicit[A](value: A): Multiple[A] = Multiple(value, 1)

  implicit class RefsImplicit[A](thisRefs: Arr[Multiple[A]])
  { def singlesLen: Int = thisRefs.sumBy(_.num)
  }

  implicit class MultipleSeqImplicit[A](thisSeq: Seq[Multiple[A]])
  { def toSingles: List[A] = thisSeq.toList.flatMap(_.singlesList)
    def iForeachSingle(f: (Int, A) => Unit) = toSingles.iForeach(f)
  }
}