/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

class SqcenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length

  /** Sets the Some value of the square tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSome(y: Int, c: Int, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[Sqcen]] coordinate. This is an imperative mutating operation. */
  def setSome(sc: Sqcen, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(sc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSomes(triples: (Int, Int, A)*)(implicit grid: SqGrid): Unit = triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  /** Coordinate map Somes. map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def cMapSomes[B, ArrT <: ArrImut[B]](f: (A, Sqcen) => B)(implicit grid: SqGrid, build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { (sc: Sqcen) =>
      val a: A = unsafeArr(grid.arrIndex(sc))
      if(a != null)
      { val newVal = f(a, sc)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }

  /** Coordinate map Nones. Map the None values respective [[Sqcen]] coordinates of this [[SqcenArrOpt]] to type B, the first type parameter. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def cMapNones[B, ArrT <: ArrImut[B]](f: Sqcen => B)(implicit grid: SqGrid, build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a: A = unsafeArr(grid.arrIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }

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
