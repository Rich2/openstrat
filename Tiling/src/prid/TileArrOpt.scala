/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An efficient immutable array of optional values mapped to a [[TGrid]] tile grid. */
trait TileArrOpt[A <: AnyRef] extends Any
{ /** The underlying mutable backing [[Array]]. it is designated unsafe because it uses [[null]]s for run time efficiency. End users should rarely need to access this directly.  */
  def unsafeArr: Array[A]

  /** The length of this tile grid mapped [[Array]] of optional values. */
  def length: Int = unsafeArr.length

  /** Maps the this Arr of Opt values, without their respective Hcen coordinates to an Arr of type B. This method treats the [[HCenArrOpt]] class like
   *  a standard Arr or Array. It does not utilise the grid [[TGrid]] from which this [[TileArrOpt]] was created. */
  def map[B, ArrT <: SeqImut[B]](noneValue: => B)(f: A => B)(implicit /*grid: HGrid,*/ build: ArrBuilder[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    unsafeArr.foreach{ a => build.buffGrow(buff, if (a == null) noneValue else f(a)) }
    build.buffToBB(buff)
  }

  /** Maps the Some values to type B by the parameter function. It ignores the None values. This method treats the [[HCenArr]] class like a standard
   *  Arr or Array. It does not utilise the grid [[TGrid]] from which this [[TileArrOpt]] was created. */
  def mapSomes[B, ArrT <: SeqImut[B]](f: A => B)(implicit build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    unsafeArr.foreach { a =>
      if(a != null)
      { val newVal = f(a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Returns an ArrBase[A] of type ArrA filtered to the Some values. */
  def somesArr[ArrA <: SeqImut[A]](implicit build: ArrBuilder[A, ArrA]): ArrA =
  { val buff = build.newBuff()
    unsafeArr.foreach { a => if (a != null) build.buffGrow(buff, a) }
    build.buffToBB(buff)
  }

  def foldSomes[B](init: B)(f: (B, A) => B): B =
  { var acc = init
    unsafeArr.foreach { a => if(a != null) acc = f(acc, a) }
    acc
  }

  def numSomes: Int = foldSomes(0)((acc, s) => acc + 1)
}
