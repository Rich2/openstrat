/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

class RefsGridExtensions[A <: AnyRef](thisRefs: Refs[A])
{
  def gridElem(y: Int, c: Int)(implicit grid: TileGrid): A = thisRefs(grid.index(y, c))
  def gridElem(roordStart: Roord)(implicit grid: TileGrid): A = thisRefs(grid.index(roordStart))

  /** Set tile row from the Cood. */
  final def setRow(startRoord: Roord, tileValues: Multiple[A]*)(implicit rid: TileGrid): Roord =
    setRow(startRoord.y, startRoord.c, tileValues: _*)

  /** Note set Row starts with the y (row) parameter. */
  final def setRow(yRow: Int, cStart: Int, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles: List[A] = tileValues.toSingles
    tiles.iForeach{(el, i) =>
      val c = cStart + i * grid.cStep
      val index = grid.index(yRow, c)
      thisRefs.unsafeSetElem(index, el)
    }
    Roord(yRow, cStart + (tiles.length - 1) * grid.cStep)
  }

  def sqSetAllOfRow(y: Int, tileMakers: Multiple[A]*)(implicit grid: SquareGrid): Unit =
  { val tiles = tileMakers.flatMap(_.singlesList)
    tiles.iForeach{(el , i) =>
      val index = grid.index(y, grid.cTileMin + i * 2)
      thisRefs.unsafeSetElem(index, el)
    }
  }

  /** Note set RowBack starts with the y (row) parameter */
  final def setRowBack(yRow: Int, cStart: Int, tileMakers: Multiple[A]*)(implicit grid: TileGrid): Roord =
  {
    val tiles = tileMakers.toSingles
    tiles.iForeach{(el, i) =>
      val c = cStart - i * grid.cStep
      val index = grid.index(yRow, c)
      thisRefs.unsafeSetElem(index, el)
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
      thisRefs.unsafeSetElem(index, el)
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
      thisRefs.unsafeSetElem(index, el)
    }
    Roord(c, yStart - (tiles.length - 1) * 2)
  }

  final def setColumnDown(roordStart: Roord, tileValues: Multiple[A]*)(implicit grid: TileGrid): Roord =
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
    ijToForeach(yFrom, yTo, 2)(cFrom, cTo, 2) { (y, c) => thisRefs.unsafeSetElem(grid.index(y, c), tileValue) }
}
