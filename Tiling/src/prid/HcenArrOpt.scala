/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An immutable Arr of Opt Tile data for a specific hex tile grid [[HGrid]]. This is specialised for OptRef[A]. The tileGrid can map the [[Hcen]]
 * coordinate of the tile to the index of the Arr. Hence most methods take an implicit [[HGrid]] hex grid parameter. */
class HcenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
  def clone: HcenArrOpt[A] = new HcenArrOpt[A](unsafeArr.clone)

  /** Sets the Some value of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSome(y: Int, c: Int, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  /** Sets the Some value of the hex tile data at the specified [[Hcen]] coordinate. This is an imperative mutating operation. */
  def setSome(hc: Hcen, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = value

  /** Sets the Some values of the hex tile data at the specified row and column coordinate values. This is an imperative mutating operation. */
  def setSomes(triples: (Int, Int, A)*)(implicit grid: HGridReg): Unit = triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  def setNone(hc: Hcen)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = null.asInstanceOf[A]

  def setAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

  def setSomeNew(hc: Hcen, value: A)(implicit grid: HGrid): HcenArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(hc)) = value
    new HcenArrOpt[A](newArr)
  }



 /* def mutMove(r1: Roord, r2: Roord)(implicit grid: TileGridSimple): Unit =
  { unsafeArr(grid.arrIndex(r2)) = unsafeArr(grid.arrIndex(r1))
    unsafeArr(grid.arrIndex(r1)) = null.asInstanceOf[A]
  }*/

  /** coordinate-foreach-Some. Foreach Some element and its associated [[Hcen]] coordinate applies the side effecting parameter function. It ignores
   *  the None values. */
  def cForeachSome(f: (A, Hcen) => Unit)(implicit grid: HGrid): Unit = grid.foreach { hc => f(unsafeArr(grid.arrIndex(hc)), hc) }

  /** Coordinate-map. Maps the this Arr of Opt values, with their respective [[Hcen]] coordinates to an Arr of type B. */
  def cMap[B, ArrT <: ArrImut[B]](fNone: => Hcen => B)(fSome: (A, Hcen) => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { hc =>
      val a = unsafeArr(grid.arrIndex(hc))
      if (a != null) build.buffGrow(buff, fSome(a, hc))
      else { val newVal = fNone(hc); build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }


  /** Maps the this Arr of Opt values, without their respective Hcen coordinates to an Arr of type B. This method treats the [[HcenArrOpt]] class like
   *  a standard Arr or Array. It does not utilise the grid [[HGrid]] from which this [[HcenArr]] was created. */
  def map[B, ArrT <: ArrImut[B]](noneValue: => B)(f: A => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.arrIndex(r))
      if (a != null) build.buffGrow(buff, noneValue)
      else { val newVal = f(a); build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }


  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def apply(hc: Hcen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))

  /** Maps the Some values to type B by the parameter function. It ignores the None values. This method treats the [[HcenArr]] class like a standard
   *  Arr or Array. It does not utilise the grid [[HGrid]] from which this [[HcenArrOpt]] was created. */
  def mapSomes[B, ArrT <: ArrImut[B]](f: A => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.arrIndex(r))
      if(a != null)
      { val newVal = f(a)
        build.buffGrow(buff, newVal)
      }
   }
   build.buffToArr(buff)
 }

  /** Coordinate map Somes. map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns
   *  an immutable Array based collection of type ArrT, the second type parameter. */
  def cMapSomes[B, ArrT <: ArrImut[B]](f: (A, Hcen) => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a: A = unsafeArr(grid.arrIndex(r))
      if(a != null)
      { val newVal = f(a, r)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }

  /** Coordinate map Nones. Map the None values respective [[Hcen]] coordinates of this [[HcenArrOpt]] to type B, the first type parameter. Returns an
   * immutable Array based collection of type ArrT, the second type parameter. */
  def cMapNones[B, ArrT <: ArrImut[B]](f: Hcen => B)(implicit grid: HGrid, build: ArrTBuilder[B, ArrT]): ArrT =
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

  /** Coordinate flatMap Somes. Maps and flattens each Some element with its associated [[Hcen]] coordinate. It ignores the None values. */
  def cFlatMapSomes[ArrT <: ArrImut[_]](f: (A, Hcen) => ArrT)(implicit grid: HGrid, build: ArrTFlatBuilder[ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { hc =>
      val a = unsafeArr(grid.arrIndex(hc))
      if(a != null)
      { val newVal = f(a, hc)
        build.buffGrowArr(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }
}