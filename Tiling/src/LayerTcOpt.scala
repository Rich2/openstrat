/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An efficient immutable array of optional values mapped to a [[TGridSys]] tile grid. */
trait LayerTcOpt[A <: AnyRef] extends Any
{ type ThisT <: LayerTcOpt[A]

  def typeStr: String
  /** The underlying mutable backing [[Array]]. it is designated unsafe because it uses [[null]]s for run time efficiency. End users should rarely need to access this directly.  */
  def arrayUnsafe: Array[A]

  /** The length of this tile grid mapped [[Array]] of optional values. */
  def flatLength: Int = arrayUnsafe.length

  def foreach[U](noneValue: => U)(f: A => U): Unit = arrayUnsafe.foreach { a => if (a == null) noneValue else f(a) }

  /** Maps the this Arr of Opt values, without their respective Hcen coordinates to an Arr of type B. This method treats the [[HCenArrOpt]] class like
   *  a standard Arr or Array. It does not utilise the grid [[TGrid]] from which this [[LayerTcOpt]] was created. */
  def mapArr[B, ArrT <: Arr[B]](noneValue: => B)(f: A => B)(implicit build: BuilderArrMap[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    arrayUnsafe.foreach{ a => build.buffGrow(buff, if (a == null) noneValue else f(a)) }
    build.buffToSeqLike(buff)
  }

  def mapPairArr[B1, ArrB1 <: Arr[B1], B2, B <: PairFinalA1Elem[B1, B2], ArrB <: ArrPairFinalA1[B1,ArrB1, B2, B]](noneB1: => B1, noneB2: => B2)(f1: A => B1)(
    f2: A => B2)(implicit build: BuilderArrPairMap[B1, ArrB1, B2, B, ArrB]): ArrB =
  { val b1Buff = build.newB1Buff()
    val b2Buff = build.newB2Buffer()
    arrayUnsafe.foreach { a =>
      b1Buff.grow(if (a == null) noneB1 else f1(a))
      b2Buff.append(if (a == null) noneB2 else f2(a))
    }
    build.arrFromBuffs(b1Buff, b2Buff)
  }

  /** Maps the Some values to type B by the parameter function. It ignores the None values. This method treats the [[HCenArr]] class like a standard
   *  Arr or Array. It does not utilise the grid [[TGrid]] from which this [[LayerTcOpt]] was created. */
  def somesMapArr[B, ArrT <: Arr[B]](f: A => B)(implicit build: BuilderArrMap[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    arrayUnsafe.foreach { a =>
      if(a != null)
      { val newVal = f(a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToSeqLike(buff)
  }

  /** Returns an ArrBase[A] of type ArrA filtered to the Some values. */
  def somesArr[ArrA <: Arr[A]](implicit build: BuilderArrMap[A, ArrA]): ArrA =
  { val buff = build.newBuff()
    arrayUnsafe.foreach { a => if (a != null) build.buffGrow(buff, a) }
    build.buffToSeqLike(buff)
  }

  def foldSomes[B](init: B)(f: (B, A) => B): B =
  { var acc = init
    arrayUnsafe.foreach { a => if(a != null) acc = f(acc, a) }
    acc
  }

  def numSomes: Int = foldSomes(0)((acc, s) => acc + 1)
}