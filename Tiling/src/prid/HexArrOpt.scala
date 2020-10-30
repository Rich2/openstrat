/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An immutable Arr of Opt Tile data for a specific TileGrid. This is specialised for OptRef[A]. The tileGrid can map the Roord of the Tile to the
 *  index of the Arr. Hence most methods take an implicit TileGrid parameter. */
class HexArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
  def clone: HexArrOpt[A] = new HexArrOpt[A](unsafeArr.clone)
  def mutSetSome(y: Int, c: Int, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  def mutSetSome(hc: HCen, value: A)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = value
  def mutSetNone(hc: HCen)(implicit grid: HGridReg): Unit = unsafeArr(grid.arrIndex(hc)) = null.asInstanceOf[A]

  def mutSetAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

 /* def mutMove(r1: Roord, r2: Roord)(implicit grid: TileGridSimple): Unit =
  { unsafeArr(grid.arrIndex(r2)) = unsafeArr(grid.arrIndex(r1))
    unsafeArr(grid.arrIndex(r1)) = null.asInstanceOf[A]
  }

  def setSome(r: Roord, value: A)(implicit grid: TileGridSimple): HexArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(r)) = value
    new HexArrOpt[A](newArr)
  }*/

  def mutSetSomes(triples: (Int, Int, A)*)(implicit grid: HGridReg): Unit =
    triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def apply(hc: HCen)(implicit grid: HGrid): A = unsafeArr(grid.arrIndex(hc))

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

  def mapSomeWithHCens[B, ArrT <: ArrBase[B]](f: (HCen, A) => B)(implicit grid: HGrid, build: ArrBuild[B, ArrT]): ArrT =
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