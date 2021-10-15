/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

trait TileArrOpt[A <: AnyRef] extends Any
{
  def unsafeArr: Array[A]
  def length: Int = unsafeArr.length

  /** Maps the this Arr of Opt values, without their respective Hcen coordinates to an Arr of type B. This method treats the [[HCenArrOpt]] class like
   *  a standard Arr or Array. It does not utilise the grid [[TGrid]] from which this [[TileArrOpt]] was created. */
  def map[B, ArrT <: ArrBase[B]](noneValue: => B)(f: A => B)(implicit /*grid: HGrid,*/ build: ArrBuilder[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    unsafeArr.foreach{ a => build.buffGrow(buff, if (a == null) noneValue else f(a)) }
    build.buffToBB(buff)
  }

  /** Maps the Some values to type B by the parameter function. It ignores the None values. This method treats the [[HCenArr]] class like a standard
   *  Arr or Array. It does not utilise the grid [[TGrid]] from which this [[TileArrOpt]] was created. */
  def mapSomes[B, ArrT <: ArrBase[B]](f: A => B)(implicit build: ArrBuilder[B, ArrT]): ArrT =
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

  def foldSomes[B](init: B)(f: (B, A) => B): B =
  { var acc = init
    unsafeArr.foreach { a => if(a != null) acc = f(acc, a) }
    acc
  }

  def numSomes: Int = foldSomes(0)((acc, s) => acc + 1)
}
