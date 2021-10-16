/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** An irregular hex grid, where the rows have different lengths and irregular start row coordinates. This is backed by an Array[Int] The (0) value
 *  gives the number of tile centre [[HCen]] rows. The (1) value gives the rTileMin the bottom row coordinate. There are 2 more values for row. Each
 *  row from lowest to highest has two values length of the row in the number of tile centres [[HCen]]s and the rTileMin coordinate for the row. */
trait HGridIrr extends Any with HGrid
{
  def unsafeArray: Array[Int]
  override def numTileRows: Int = unsafeArray(0)
  override def bottomTileRow: Int = unsafeArray(1)
  override def topTileRow: Int = bottomTileRow + unsafeArray(0) * 2 - 2

  override def tileColMin: Int = foldRows(0)((acc, r) => acc.min(tileRowStart(r)))
  override def tileColMax: Int = foldRows(0)((acc, r) => acc.max(tileRowEnd(r)))

  override def numRow0s: Int = numTileRows.ifMod(bottomTileRow.div4Rem0, _.roundUpToEven) / 2
  override def numRow2s: Int = numTileRows.ifMod(bottomTileRow.div4Rem2, _.roundUpToEven) / 2

  @inline protected def unsafeRowArrayindex(row: Int): Int = row - bottomTileRow + 2

  /** The total number of Tiles in the tile Grid. */
  override def numTiles: Int = foldRows(0)((acc, r) => acc + rowNumTiles(r))

  override def arrIndex(r: Int, c: Int): Int =
  { val wholeRows = iUntilFoldInt(bottomTileRow, r, 2){ (acc, r) => acc + rowNumTiles(r) }
    wholeRows + (c - tileRowStart(r)) / 4
  }

  override def hCenExists(r: Int, c: Int): Boolean ={
    false
  }
}