package ostrat
package pGrid

class TilesOptRef[A <: AnyRef](val unsafeArr: Array[A]) extends AnyVal
{
  def length: Int = unsafeArr.length
  def clone: TilesOptRef[A] = new TilesOptRef[A](unsafeArr.clone)
  def mutSetSome(y: Int, c: Int, value: A)(implicit grid: TileGrid): Unit = unsafeArr(grid.index(y, c)) = value

  def mutSetSome(r: Roord, value: A)(implicit grid: TileGrid): Unit = unsafeArr(grid.index(r)) = value
  def mutSetNone(r: Roord)(implicit grid: TileGrid): Unit = unsafeArr(grid.index(r)) = null.asInstanceOf[A]

  def mutSetAll(value: A): Unit = iUntilForeach(0, length)(unsafeArr(_) = value)

  def mutMove(r1: Roord, r2: Roord)(implicit grid: TileGrid): Unit =
  { unsafeArr(grid.index(r2)) = unsafeArr(grid.index(r1))
    unsafeArr(grid.index(r1)) = null.asInstanceOf[A]
  }

  def setSome(r: Roord, value: A)(implicit grid: TileGrid): TilesOptRef[A] =
  { val newArr = unsafeArr.clone()
    newArr(grid.index(r)) = value
    new TilesOptRef[A](newArr)
  }

  def unsafeSetSomes(triples: (Int, Int, A)*)(implicit grid: TileGrid): Unit =
    triples.foreach(t => unsafeArr(grid.index(t._1, t._2)) = t._3)

  /** Accesses element from Refs Arr. Only use this method where you are certain it is not null, or the consumer can deal with the null. */
  def apply(roord: Roord)(implicit grid: TileGrid): A = unsafeArr(grid.index(roord))

  def foreachSome(f: (Roord, A) => Unit)(implicit grid: TileGrid): Unit = grid.foreach { r => f(r, unsafeArr(grid.index(r))) }

  def mapSomes[B, ArrT <: Arr[B]](f: (Roord, A) => B)(implicit grid: TileGrid, build: ArrBuild[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.index(r))
      if(a != null)
      { val newVal = f(r, a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }

  def mapSomeOnlys[B, ArrT <: Arr[B]](f: A => B)(implicit grid: TileGrid, build: ArrBuild[B, ArrT]): ArrT =
  {
    val buff = build.newBuff()
    grid.foreach { r =>
      val a = unsafeArr(grid.index(r))
      if(a != null)
      { val newVal = f(a)
        build.buffGrow(buff, newVal)
      }
    }
    build.buffToArr(buff)
  }
}