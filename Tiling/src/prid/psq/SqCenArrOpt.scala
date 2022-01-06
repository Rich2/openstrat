/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq

/** An immutable Arr of Opt Tile data for a specific square tile grid [[SqGrid]]. This is specialised for OptRef[A]. The tileGrid can map the
 * [[SqCen]] coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[SqGrid]] square grid parameter. */
class SqCenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal with TileArrOpt[A]
{
  def clone: SqCenArrOpt[A] = new SqCenArrOpt[A](unsafeArr.clone)

  /** Sets the Some value of the square tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSome(y: Int, c: Int, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[SqCen]] coordinate. This is an imperative mutating operation. */
  //def setSome(sc: SqCen, value: A)(implicit grid: SqGrid): Unit = unsafeArr(grid.arrIndex(sc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSomes(triples: (Int, Int, A)*)(implicit grid: SqGrid): Unit = triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(sc: SqCen, value: A)(implicit grid: SqGrid): SqCenArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(sc)) = value
    new SqCenArrOpt[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: SqCen)(implicit grid: SqGrid): SqCenArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(hc)) = null.asInstanceOf[A]
    new SqCenArrOpt[A](newArr)
  }

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def tileNone(sc: SqCen)(implicit grid: SqGrid): Boolean = unsafeArr(grid.arrIndex(sc)) == null

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(sc: SqCen)(implicit grid: SqGrid): A = unsafeArr(grid.arrIndex(sc))

  /** [[SqCen]] with Some map. map the Some values of this HcenArrOpt, with the respective [[SqCen]] coordinate to type B, the first type parameter B.
   *  Returns an immutable Array based collection of type ArrT, the second type parameter. */
  def scSomesMap[B, ArrB <: SeqImut[B]](f: (SqCen, A) => B)(implicit grid: SqGrid, build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    grid.foreach { sc =>
      val a: A = unsafeArr(grid.arrIndex(sc))
      if(a != null)
      { val newVal = f(sc, a)
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

  /** coordinate-foreach-Some. Foreach Some element and its associated [[HCen]] coordinate applies the side effecting parameter function. It ignores
   *  the None values. */
  def scSomesForeach(f: (SqCen, A) => Unit)(implicit grid: SqGrid): Unit = grid.foreach { sc =>
    val a = unsafeArr(grid.arrIndex(sc))
    if (a != null) f(sc, a)
  }

  /** Maps the Somes of this [[HCenArrOpt]] and the Some values of a second HCenArrOpt. Returns an immutable Array based collection of type ArrC, the
   *  second type parameter. */
  def some2sMap[B <: AnyRef, C, ArrC <: SeqImut[C]](optArrB: SqCenArrOpt[B])(f: (A, B) => C)(implicit grid: SqGrid, build: ArrBuilder[C, ArrC]): ArrC =
  { val buff = build.newBuff()

    grid.foreach { hc =>
      val a: A = unsafeArr(grid.arrIndex(hc))
      val b: B = optArrB.unsafeArr(grid.arrIndex(hc))
      if(a != null & b != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  def keyMap(implicit grid: SqGrid): Map[A, SqCen] =
  { val build = Map.newBuilder[A, SqCen]
    scSomesForeach((p, hc) => build.addOne(hc, p))
    build.result
  }

  /** Moves the object in the array location given by the 1st [[SqCen]] to the 2nd [[SqCen]], by setting sc2 to the value of sc1 and setting sc1 to
   *  None. */
  def unsafeMove(sc1: SqCen, sc2: SqCen)(implicit grid: SqGrid): Unit =
  { unsafeArr(grid.arrIndex(sc2)) = unsafeArr(grid.arrIndex(sc1))
    unsafeArr(grid.arrIndex(sc1)) = null.asInstanceOf[A]
  }
}
