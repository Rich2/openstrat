package ostrat
package pGrid
import scala.reflect.ClassTag

class TilesRef[A <: AnyRef](val unsafeArr: Array[A])
{
  def length: Int = unsafeArr.length

  def apply(roord: Roord)(implicit grid: TileGrid): A = unsafeArr(grid.index(roord))
  def apply(y: Int, c: Int)(implicit grid: TileGrid): A = unsafeArr(grid.index(y, c))
  /** Set tile row from the Roord. */
  final def setRow(roord: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord = setRow(roord.y, roord.c, tileValues: _*)(grid)

  /** Note set Row starts with the y (row) parameter. */
  final def setRow(yRow: Int, cStart: Int, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles: List[A] = tileValues.toSingles
    tiles.iForeach { (e, i) =>
      val c = cStart + i * grid.cStep
      unsafeArr(grid.index(yRow, c)) = e
    }
    Roord(yRow, cStart + (tiles.length - 1) * grid.cStep)
  }

  def foreach(f: (Roord, A) => Unit)(implicit grid: TileGrid): Unit = grid.foreach{ r => f(r, unsafeArr(grid.index(r))) }

  def mutSetAll(value: A): Unit = iUntilForeach(0, length){i => unsafeArr(i) = value }


  def sqSetAllOfRow(y: Int, tileMakers: Multiple[A]*)(implicit grid: SquareGrid): Unit =
  { val tiles = tileMakers.flatMap(_.singlesList)
    tiles.iForeach{(el , i) =>
      val index = grid.index(y, grid.cTileMin + i * 2)
      unsafeArr(index) =  el
    }
  }

  /** Note set RowBack starts with the y (row) parameter */
  final def setRowBack(yRow: Int, cStart: Int, tileMakers: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles = tileMakers.toSingles
    tiles.iForeach{(el, i) =>
      val c = cStart - i * grid.cStep
      val index = grid.index(yRow, c)
      unsafeArr(index) = el
    }
    Roord(yRow, cStart - (tiles.length - 1) * grid.cStep)
  }

  final def setRowBack(roord: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
  setRowBack(roord.y, roord.c, tileValues: _*)(grid)

  final def setColumn(c: Int, yStart: Int, tileMakers: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles = tileMakers.flatMap(_.singlesList)
    tiles.iForeach{(el, i) =>
      val y = yStart + i * 2
      val index = grid.index(y, c)
      unsafeArr(index) =  el
    }
    Roord(yStart + (tiles.length - 1) * 2, c)
  }

  final def setColumn(roordStart: Roord, multis: Multiple[A]*)(implicit grid: TileGrid): Roord =
  setColumn(roordStart.c, roordStart.y, multis: _*)(grid)

  final def setColumnDown(c: Int, yStart: Int, tileMakers: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles = tileMakers.flatMap(_.singlesList)

    tiles.iForeach{(el, i) =>
      val y = yStart - i * 2
      val index = grid.index(y, c)
      unsafeArr(index) = el
    }
    Roord(c, yStart - (tiles.length - 1) * 2)
  }

  def setColumnDown(roordStart: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
  setColumnDown(roordStart.c, roordStart.y, tileValues: _*)(grid)

  def setTerrPath(startRoord: Roord, value: A, dirns: Multiple[SquareGrid.PathDirn]*)(implicit grid: SquareGrid): Roord =
  {
    var curr = startRoord
    import SquareGrid._

    dirns.foreach
    { case Multiple(Rt, i) => curr = setRow(curr, value * i)
    case Multiple(Lt, i) => curr = setRowBack(curr, value * i)
    case Multiple(Up, i) => curr = setColumn(curr, value * i)
    case Multiple(Dn, i) => curr = setColumnDown(curr, value * i)
    }
    curr
  }

  /** Sets a rectangle of tiles to the same terrain type. */
  def sqGridSetRect(yFrom: Int, yTo: Int, cFrom: Int, cTo: Int, tileValue: A)(implicit grid: SquareGrid): Unit =
    ijToForeach(yFrom, yTo, 2)(cFrom, cTo, 2) { (y, c) => unsafeArr(grid.index(y, c)) =  tileValue }
}

object TilesRef
{ def apply[A <: AnyRef](length: Int)(implicit ct: ClassTag[A]): TilesRef[A] = new TilesRef[A](new Array[A](length))

  implicit class TilesListImplicit[A](thisRefs: TilesRef[List[A]])
  { def prependAt(y: Int, c: Int, value: A)(implicit grid: TileGrid): Unit = prependAt(Roord(y, c), value)
    def prependAt(roord: Roord, value: A)(implicit grid: TileGrid): Unit = thisRefs.unsafeArr(grid.index(roord)) ::= value
    def prependAts(value : A, roords: Roord*)(implicit grid: TileGrid): Unit = roords.foreach{ r =>  thisRefs.unsafeArr(grid.index(r)) ::= value }

    def gridHeadsMap[B, BB <: Arr[B]](f: (Roord, A) => B)(implicit grid: TileGrid, build: ArrBuild[B, BB]): BB =
    {
      val buff = build.newBuff()
      grid.foreach { r => thisRefs(r) match
        {
          case h :: _ => build.buffGrow(buff, f(r, h))
          case _ =>
        }
      }
      build.buffToArr(buff)
    }
  }
}

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