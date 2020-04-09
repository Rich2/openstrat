package ostrat
package pGrid

class TilesOptRef[A <: AnyRef](unsafeArr: Array[A])
{
  def unsafeSetSome(y: Int, c: Int, value: A)(implicit grid: TileGrid): Unit = unsafeArr(grid.index(y, c)) = value

  def unsafeSetSome(r: Roord, value: A)(implicit grid: TileGrid): Unit = unsafeArr(grid.index(r)) = value

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
}
