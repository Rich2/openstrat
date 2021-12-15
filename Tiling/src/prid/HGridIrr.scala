/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int]. There are 2
 * values for each row. Each row from lowest to highest has two values. The rTileMin coordinate for the row followed by the length of the row in the
 * number of tile centres [[HCen]]s. */
abstract class HGridIrr() extends HGrid
{
  /* in this [[Array]][Int] there are 2 values for each row. Each row from lowest to highest has two values. The rTileMin coordinate for the row
  followed by the length of the row in the number of tile centres [[HCen]]s. */
  def unsafeRowsArray: Array[Int]
  override def topTileRow: Int = bottomTileRow + numTileRows * 2 - 2
  override def tileColMin: Int = foldRows(Int.MaxValue - 1)((acc, r) => acc.min(tileRowStart(r)))
  override def tileColMax: Int = foldRows(Int.MinValue )((acc, r) => acc.max(tileRowEnd(r)))

  override def numRow0s: Int = numTileRows.ifMod(bottomTileRow.div4Rem0, _.roundUpToEven) / 2
  override def numRow2s: Int = numTileRows.ifMod(bottomTileRow.div4Rem2, _.roundUpToEven) / 2

  @inline protected def unsafeRowArrayindex(row: Int): Int = row - bottomTileRow //+ 2

  /** The total number of Tiles in the tile Grid. */
  override def numTiles: Int = foldRows(0)((acc, r) => acc + rowNumTiles(r))

  override def arrIndex(r: Int, c: Int): Int =
  { val wholeRows = iUntilFoldInt(bottomTileRow, r, 2){ (acc, r) => acc + rowNumTiles(r) }
    wholeRows + (c - tileRowStart(r)) / 4
  }

  override def rowNumTiles(row: Int): Int = unsafeRowsArray(row - bottomTileRow + 1)

  /** Foreachs over each tile centre of the specified row applying the side effecting function to the [[HCen]]. */
  def rowForeach(r: Int)(f: HCen => Unit): Unit = iToForeach(tileRowStart(r), tileRowEnd(r), 4){ c => f(HCen(r, c))}

  override def rowIForeach(r: Int, initCount: Int = 0)(f: (HCen, Int) => Unit): Int = {
    var count = initCount
    iToForeach(tileRowStart(r), tileRowEnd(r), 4){ c => f(HCen(r, c), count); count += 1 }
    count
  }

  /** The start (or by default left column) of the tile centre of the given row. Will throw on illegal values. */
  override def tileRowStart(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowStart method.")
    case r if r > topTileRow => excep(s"$r Row number greater than top tile row in tileRowStart method.")
    case r if r < bottomTileRow => excep(s"$r Row number less than bottom tile row in tileRowStart method.")
    case _ => unsafeRowsArray(row - bottomTileRow)// + 1)
  }

  /** The end (or by default right) column number of the tile centre of the given row. Will throw on illegal values. */
  override def tileRowEnd(row: Int): Int = row match
  { case r if r.isOdd => excep(s"$r is odd number which is illegal for a tile row in tileRowEnd method.")
    case r if r > topTileRow => excep(s"$r Row number greater than top tile row in tileRowEnd method.")
    case r if r < bottomTileRow => excep(s"$r Row number less than bottom tile row value in tileRowEnd method.")
    case _ => tileRowStart(row) + (rowNumTiles(row) - 1) * 4
  }

  override def hCenExists(r: Int, c: Int): Boolean = r match
  { case r if r > topTileRow => false
    case r if r < bottomTileRow => false
    case r => c >= tileRowStart(r) & c <= tileRowEnd(r)
  }
  override def width: Double = (tileColMax - tileColMin + 4) / Sqrt3
  override def height: Double = topTileRow - bottomTileRow + 3
}