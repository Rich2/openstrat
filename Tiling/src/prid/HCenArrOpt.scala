/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An immutable Arr of Opt Tile data for a specific hex tile grid [[HGrid]]. This is specialised for OptRef[A]. The tileGrid can map the [[HCen]]
 * coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[HGrid]] hex grid parameter. */
class HCenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal with TileArrOpt[A]
{
  def clone: HCenArrOpt[A] = new HCenArrOpt[A](unsafeArr.clone)

  /** Sets the Some value of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSome(r: Int, c: Int, value: A)(implicit grid: HGrid): Unit = unsafeArr(grid.arrIndex(r, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[HCen]] coordinate. This is an imperative mutating operation. */
  def unsafeSetSome(hc: HCen, value: A)(implicit grid: HGrid): Unit = unsafeArr(grid.arrIndex(hc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit grid: HGrid): Unit = triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  /** Mutates the value ot the specified location to None. */
  def unsafeSetNone(hc: HCen)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = null.asInstanceOf[A]

  def unsafeSetAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

  /** Creates a new ArrOpt with the specified location set to the specified value. */
  def setSome(hc: HCen, value: A)(implicit grid: HGrid): HCenArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(hc)) = value
    new HCenArrOpt[A](newArr)
  }

  /** Creates a new ArrOpt with the specified location set to NoRef. */
  def setNone(hc: HCen)(implicit grid: HGrid): HCenArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(hc)) = null.asInstanceOf[A]
    new HCenArrOpt[A](newArr)
  }

  /** Moves the object in the array location given by the 1st [[HCen]] to the 2nd [[HCen]], by setting hc2 to the value of hc1 and setting hc1 to
   *  None. */
  def unsafeMove(hc1: HCen, hc2: HCen)(implicit grid: HGrid): Unit =
  { unsafeArr(grid.arrIndex(hc2)) = unsafeArr(grid.arrIndex(hc1))
    unsafeArr(grid.arrIndex(hc1)) = null.asInstanceOf[A]
  }

  /** coordinate-foreach-Some. Foreach Some element and its associated [[HCen]] coordinate applies the side effecting parameter function. It ignores
   *  the None values. */
  def hcSomesForeach(f: (HCen, A) => Unit)(implicit grid: HGrid): Unit = grid.foreach { hc =>
    val a = unsafeArr(grid.arrIndex(hc))
    if (a != null) f(hc, a)
  }

  /** Coordinate-map. Maps the this Arr of Opt values, with their respective [[HCen]] coordinates to an Arr of type B. */
  def mapHCen[B, ArrT <: ArrBase[B]](fNone: => HCen => B)(fSome: (HCen, A) => B)(implicit grid: HGrid, build: ArrBuilder[B, ArrT]): ArrT =
  { val buff = build.newBuff()
    grid.foreach { hc =>
      val a = unsafeArr(grid.arrIndex(hc))
      if (a != null) build.buffGrow(buff, fSome(hc, a))
      else { val newVal = fNone(hc); build.buffGrow(buff, newVal) }
    }
    build.buffToBB(buff)
  }

  def apply(hc: HCen)(implicit grid: HGrid): Option[A] =
  { if (!grid.hCenExists(hc)) None else
      { val elem = unsafeArr(grid.arrIndex(hc))
        if (elem == null) None else Some(elem)
      }
  }

  def apply(r: Int, c: Int)(implicit grid: HGrid): Option[A] = {
    if (!grid.hCenExists(r, c)) None else {
      val elem = unsafeArr(grid.arrIndex(r, c))
      if (elem == null) None else Some(elem)
    }
  }

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(hc: HCen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def tileNone(hc: HCen)(implicit grid: HGrid): Boolean = unsafeArr(grid.arrIndex(hc)) == null


  /** Returns an Arr filtered to the Some values. */
  def somesArr[ArrT <: ArrBase[A]](implicit build: ArrBuilder[A, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    unsafeArr.foreach { a => if (a != null) build.buffGrow(buff, a) }
    build.buffToBB(buff)
  }

  /** [[HCen]] with Some map. map the Some values of this HcenArrOpt, with the respective [[HCen]] coordinate to type B, the first type parameter B.
   *  Returns an immutable Array based collection of type ArrT, the second type parameter. */
  def hcSomesMap[B, ArrB <: ArrBase[B]](f: (HCen, A) => B)(implicit grid: HGrid, build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    grid.foreach { hc =>
      val a: A = unsafeArr(grid.arrIndex(hc))
      if(a != null)
      { val newVal = f(hc, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Maps the Somes of this [[HCenArrOpt]] and the Some values of a second HCenArrOpt. Returns an immutable Array based collection of type ArrC, the
   *  second type parameter. */
  def some2sMap[B <: AnyRef, C, ArrC <: ArrBase[C]](optArrB: HCenArrOpt[B])(f: (A, B) => C)(implicit grid: HGrid, build: ArrBuilder[C, ArrC]): ArrC =
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

  /** [[HCen]] with Some values from 2 [[HCenArrOpts]] map to type C. This only maps the values where both collections have Some values. Returns an
   *  immutable Array based collection of type ArrC, the second type parameter. */
  def hcSome2sMap[B <: AnyRef, C, ArrC <: ArrBase[C]](optArrB: HCenArrOpt[B])(f: (HCen, A, B) => C)(implicit grid: HGrid, build: ArrBuilder[C, ArrC]):
    ArrC =
  { val buff = build.newBuff()

    grid.foreach { hc =>
      val a: A = unsafeArr(grid.arrIndex(hc))
      val b: B = optArrB.unsafeArr(grid.arrIndex(hc))
      if(a != null)
      { val newVal = f(hc, a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** maps the [[HCen]] Coordinate values to type B, but only where the element is a None value. Returns an immutable Array based collection of type
   *  ArrB, the second type parameter. */
  def hcNonesMap[B, ArrB <: ArrBase[B]](f: HCen => B)(implicit grid: HGrid, build: ArrBuilder[B, ArrB]): ArrB =
  { val buff = build.newBuff()

    grid.foreach { r =>
      val a: A = unsafeArr(grid.arrIndex(r))
      if(a == null)
      { val newVal = f(r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** [[HCen]] with element flatMap, but only coordinates where there some value. Maps and flattens each [[HCen]] coordinate with its associated
   * element of type A. It ignores the None values. Note the function signature follows the foreach based convention of putting the collection element
   * 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. */
  def hcSomesFlatMap[ArrT <: ArrBase[_]](f: (HCen, A) => ArrT)(implicit grid: HGrid, build: ArrFlatBuilder[ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { hc =>
      val a = unsafeArr(grid.arrIndex(hc))
      if(a != null)
      { val newVal = f(hc, a)
        build.buffGrowArr(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  def keyMap(implicit grid: HGrid): Map[A, HCen] =
  { val build = Map.newBuilder[A, HCen]
    hcSomesForeach((p, hc) => build.addOne(hc, p))
    build.result
  }

  def find(value: A)(implicit grid: HGrid): Option[HCen] =
  { var res: Option[HCen] = None
    hcSomesForeach{ (hc, a) => if (value == a) res = Some(hc)}
    res
  }
}