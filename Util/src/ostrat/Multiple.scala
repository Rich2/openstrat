/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** The Multiple type class allow you to represent multiple values of type A. Implicit conversion in package object. */
case class Multiple[+A](value: A, num: Int)
{ /** multiply the [[Multiple]] number with the operand */
  def * (operand: Int): Multiple[A] = Multiple(value, num * operand)

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
  { val res: Multiple[B] = f(value)
    Multiple[B](res.value, res.num * num)
  }

  def toArr[ArrA <: SeqImut[A]@uncheckedVariance](implicit build: ArrBuilder[A, ArrA]@uncheckedVariance): ArrA =
  { val res: ArrA = build.newArr(num)
    iUntilForeach(num){i => res.unsafeSetElem(i, value)}
    res
  }
}

/** Companion object for the Multiple[+A] type class. */
object Multiple
{
  implicit def eqImplicit[A](implicit ev: EqT[A]): EqT[Multiple[A]] = (a1, a2) => (a1.num == a2.num) & ev.eqT(a1.value, a2.value)

  implicit def toMultipleImplicit[A](value: A): Multiple[A] = Multiple(value, 1)

  implicit class RefsImplicit[A](thisRefs: RArr[Multiple[A]])
  { def numSingles: Int = thisRefs.sumBy(_.num)
  }

  implicit class MultipleSeqImplicit[A](thisSeq: Seq[Multiple[A]])
  { def numSingles: Int = thisSeq.sumBy(_.num)

    def toSinglesList: List[A] = thisSeq.toList.flatMap(_.singlesList)

    def singles[ArrA <: SeqImut[A]](implicit build: ArrBuilder[A, ArrA]): ArrA ={
      val len: Int = thisSeq.sumBy(_.num)
      val res = build.newArr(len)
      var i = 0
      thisSeq.foreach(m => iUntilForeach(m.num){j =>
        res.unsafeSetElem(i, m.value)
        i += 1
      })
      res
    }
    def iForeachSingle(f: (Int, A) => Unit): Unit = toSinglesList.iForeach(f)
  }
}