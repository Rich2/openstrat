/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An immutable Arr of Opt Tile data for a specific square tile grid [[SqGrid]]. This is specialised for OptRef[A]. The tileGrid can map the
 * [[SqCen]] coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[SqGrid]] square grid parameter. */
class SqCenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal with TileArrOpt[A]
{
  def clone: SqCenArrOpt[A] = new SqCenArrOpt[A](unsafeArr.clone)

  /** Sets the Some value of the square tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSome(y: Int, c: Int, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[SqCen]] coordinate. This is an imperative mutating operation. */
  def setSome(sc: SqCen, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(sc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSomes(triples: (Int, Int, A)*)(implicit grid: SqGrid): Unit = triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  /** Coordinate map Somes. map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def cMapSomes[B, ArrT <: SeqImut[B]](f: (A, SqCen) => B)(implicit grid: SqGrid, build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { (sc: SqCen) =>
      val a: A = unsafeArr(grid.arrIndex(sc))
      if(a != null)
      { val newVal = f(a, sc)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Coordinate map Nones. Map the None values respective [[SqCen]] coordinates of this [[SqCenArrOpt]] to type B, the first type parameter. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def cMapNones[B, ArrT <: SeqImut[B]](f: SqCen => B)(implicit grid: SqGrid, build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a: A = unsafeArr(grid.arrIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Moves the object in the array location given by HCen1 to HCen2, by setting H2 to the value of h1 and setting H1 to null. */
  def mutMove(s1: SqCen, s2: SqCen)(implicit grid: SqGrid): Unit =
  { unsafeArr(grid.arrIndex(s2)) = unsafeArr(grid.arrIndex(s1))
    unsafeArr(grid.arrIndex(s1)) = null.asInstanceOf[A]
  }
}
