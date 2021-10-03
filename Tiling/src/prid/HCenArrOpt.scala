/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An immutable Arr of Opt Tile data for a specific hex tile grid [[HGrid]]. This is specialised for OptRef[A]. The tileGrid can map the [[HCen]]
 * coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[HGrid]] hex grid parameter. */
class HCenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
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
  def foreachHCenSome(f: (HCen, A) => Unit)(implicit grid: HGrid): Unit = grid.foreach { hc =>
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

  /** Maps the this Arr of Opt values, without their respective Hcen coordinates to an Arr of type B. This method treats the [[HCenArrOpt]] class like
   *  a standard Arr or Array. It does not utilise the grid [[HGrid]] from which this [[HCenArr]] was created. */
  def map[B, ArrT <: ArrBase[B]](noneValue: => B)(f: A => B)(implicit grid: HGrid, build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.arrIndex(r))
      build.buffGrow(buff, if (a == null) noneValue else f(a))
    }
    build.buffToBB(buff)
  }

  def apply(hc: HCen)(implicit grid: HGrid): Option[A] = {
    val r = unsafeArr(grid.arrIndex(hc))
    if (r == null) None else Some(r)
  }

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def unSafeApply(hc: HCen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))

  /** The tile is a None at the given hex grid centre coordinate [[HCen]]. */
  def tileNone(hc: HCen)(implicit grid: HGrid): Boolean = unsafeArr(grid.arrIndex(hc)) == null

  /** Maps the Some values to type B by the parameter function. It ignores the None values. This method treats the [[HCenArr]] class like a standard
   *  Arr or Array. It does not utilise the grid [[HGrid]] from which this [[HCenArrOpt]] was created. */
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

  /** Returns an Arr filtered to the Some values. */
  def somesArr[ArrT <: ArrBase[A]](implicit build: ArrBuilder[A, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    unsafeArr.foreach { a => if (a != null) build.buffGrow(buff, a) }
    build.buffToBB(buff)
  }

  /** Coordinate map Somes. map the Some values of this HcenArrOpt, with the respective [[HCen]] coordinate to type B, the first type parameter B. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def mapHCenSomes[B, ArrT <: ArrBase[B]](f: (HCen, A) => B)(implicit grid: HGrid, build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { hc =>
      val a: A = unsafeArr(grid.arrIndex(hc))
      if(a != null)
      { val newVal = f(hc, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Coordinate map Somes. map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def mapSomes2[B <: AnyRef, C, ArrT <: ArrBase[C]](optArrB: HCenArrOpt[B])(f: (A, B) => C)(implicit grid: HGrid, build: ArrBuilder[C, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { hc =>
      val a: A = unsafeArr(grid.arrIndex(hc))
      val b: B = optArrB.unsafeArr(grid.arrIndex(hc))
      if(a != null)
      { val newVal = f(a, b)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToBB(buff)
  }

  /** Coordinate map Somes. map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def mapHCenSomes2[B <: AnyRef, C, ArrT <: ArrBase[C]](optArrB: HCenArrOpt[B])(f: (HCen, A, B) => C)(implicit grid: HGrid, build: ArrBuilder[C, ArrT]): ArrT =
  {
    val buff = build.newBuff()
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

  /** Coordinate map Nones. Map the None values respective [[HCen]] coordinates of this [[HCenArrOpt]] to type B, the first type parameter. Returns an
   * immutable Array based collection of type ArrT, the second type parameter. */
  def mapHCenNones[B, ArrT <: ArrBase[B]](f: HCen => B)(implicit grid: HGrid, build: ArrBuilder[B, ArrT]): ArrT =
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

  /** Coordinate flatMap Somes. Maps and flattens each Some element with its associated [[HCen]] coordinate. It ignores the None values. */
  def flatMapHCenSomes[ArrT <: ArrBase[_]](f: (HCen, A) => ArrT)(implicit grid: HGrid, build: ArrFlatBuilder[ArrT]): ArrT =
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
    foreachHCenSome((p, hc) => build.addOne(hc, p))
    build.result
  }
}