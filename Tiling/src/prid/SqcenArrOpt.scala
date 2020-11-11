/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

class SqcenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
  def setSome(y: Int, c: Int, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  /** map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns an immutable Array
   * based collection of type ArrT, the second type parameter. */
  /*def mapSqcenSomes[B, ArrT <: ArrBase[B]](f: (Sqcen, A) => B)(implicit grid: SqGrid, build: ArrBuild[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a: A = unsafeArr(grid.arrIndex(r))
      if(a != null)
      { val newVal = f(r, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }*/
}
