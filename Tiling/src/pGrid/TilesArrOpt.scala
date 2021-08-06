/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pGrid

/** An immutable Arr of Opt Tile data for a specific TileGrid. This is specialised for OptRef[A]. The tileGrid can map the Roord of the Tile to the
 *  index of the Arr. Hence most methods take an implicit TileGrid parameter. */
class TilesArrOpt[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
  def clone: TilesArrOpt[A] = new TilesArrOpt[A](unsafeArr.clone)
  def mutSetSome(y: Int, c: Int, value: A)(implicit grid: TileGridOld): Unit = unsafeArr(grid.arrIndex(y, c)) = value

  def mutSetSome(r: Roord, value: A)(implicit grid: TileGridOld): Unit = unsafeArr(grid.arrIndex(r)) = value
  def mutSetNone(r: Roord)(implicit grid: TileGridOld): Unit = unsafeArr(grid.arrIndex(r)) = null.asInstanceOf[A]

  def mutSetAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

  def mutMove(r1: Roord, r2: Roord)(implicit grid: TileGridOld): Unit =
  { unsafeArr(grid.arrIndex(r2)) = unsafeArr(grid.arrIndex(r1))
    unsafeArr(grid.arrIndex(r1)) = null.asInstanceOf[A]
  }

  def setSome(r: Roord, value: A)(implicit grid: TileGridOld): TilesArrOpt[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.arrIndex(r)) = value
    new TilesArrOpt[A](newArr)
  }

  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit grid: TileGridOld): Unit =
    triples.foreach(t => unsafeArr(grid.arrIndex(t._1, t._2)) = t._3)

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def apply(roord: Roord)(implicit grid: TileGridOld): A = unsafeArr(grid.arrIndex(roord))

  def foreachSome(f: (Roord, A) => Unit)(implicit grid: TileGridOld): Unit = grid.foreach { r => f(r, unsafeArr(grid.arrIndex(r))) }

  def mapSomeWithRoords[B, ArrT <: ArrBase[B]](f: (Roord, A) => B)(implicit grid: TileGridOld, build: ArrBuilder[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.arrIndex(r))
      if(a != null)
      { val newVal = f(r, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }

  def flatMapSomes[ArrT <: ArrBase[_]](f: (Roord, A) => ArrT)(implicit grid: TileGridOld, build: SeqFlatBuilder[ArrT]): ArrT =
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
  }

  def mapSomes[B, ArrT <: ArrBase[B]](f: A => B)(implicit grid: TileGridOld, build: ArrBuilder[B, ArrT]): ArrT =
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
}