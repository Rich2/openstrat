/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An immutable Arr of Opt Tile data for a specific hex tile grid [[HGrid]]. This is specialised for OptRef[A]. The tileGrid can map the Roord of the
 *  Tile to the index of the Arr. Hence most methods take an implicit TileGrid parameter. */
class HcenArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
  def clone: HcenArrOpt[A] = new HcenArrOpt[A](unsafeArr.clone)
  def setSome(y: Int, c: Int, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(y, c)) = value
  def setSome(hc: Hcen, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = value

  def setNone(hc: Hcen)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = null.asInstanceOf[A]

  def setAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

 /* def mutMove(r1: Roord, r2: Roord)(implicit grid: TileGridSimple): Unit =
  { unsafeArr(grid.arrIndex(r2)) = unsafeArr(grid.arrIndex(r1))
    unsafeArr(grid.arrIndex(r1)) = null.asInstanceOf[A]
  }*/

  /** Maps the this Arr of Opt values, with their respective Hcen coordinates to an Arr of type B. */
  def map[B, ArrT <: ArrBase[B]](fNone: => Hcen => B)(fSome: (A, Hcen) => B)(implicit grid: HGrid, build: ArrBuild[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { hc =>
      val a = unsafeArr(grid.arrIndex(hc))
      if (a != null) build.buffGrow(buff, fNone(hc))
      else { val newVal = fSome(a, hc); build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }


  /** Maps the this Arr of Opt values, without their respective Hcen coordinates to an Arr of type B. */
  def mapOnly[B, ArrT <: ArrBase[B]](noneValue: => B)(f: A => B)(implicit grid: HGrid, build: ArrBuild[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.arrIndex(r))
      if (a != null) build.buffGrow(buff, noneValue)
      else { val newVal = f(a); build.buffGrow(buff, newVal) }
    }
    build.buffToArr(buff)
  }

  def setSome(hc: Hcen, value: A)(implicit grid: HGrid): HcenArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(hc)) = value
    new HcenArrOpt[A](newArr)
  }

  def mutSetSomes(triples: (Int, Int, A)*)(implicit grid: HGridReg): Unit =
    triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def apply(hc: Hcen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))

//  def foreachSome(f: (Roord, A) => Unit)(implicit grid: TileGridSimple): Unit = grid.foreach { r => f(r, unsafeArr(grid.arrIndex(r))) }

  def mapSomes[B, ArrT <: ArrBase[B]](f: A => B)(implicit grid: HGrid, build: ArrBuild[B, ArrT]): ArrT =
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

  /** map the some values of this HcenArrOpt, with the respective Hcen coordinate to type B, the first type parameter B. Returns an immutable Array
   * based collection of type ArrT, the second type parameter. */
  def mapSomeHcens[B, ArrT <: ArrBase[B]](f: (A, Hcen) => B)(implicit grid: HGrid, build: ArrBuild[B, ArrT]): ArrT =
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

 /* def flatMapSomes[ArrT <: ArrBase[_]](f: (Roord, A) => ArrT)(implicit grid: TileGridSimple, build: ArrFlatBuild[ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.arrIndex(r))
      if(a != null)
      { val newVal = f(r, a)
        build.buffGrowArr(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }*/
}