/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package prid

/** An irregular hex grid. This grid is irregular in the length of the hex rows. */
trait HGridIrr extends HGrid
{

}

object HGridIrr
{
  /** An irregular hex grid. This grid is irregular in the length of the hex rows. The (0) value gives yTileMin. There are 2 more values for row. Each
   *  row from lowest to highest has two values the xMin for the row and the index into a data array for the first tile of the grid row.
   * @constructor creates a new HexGridIrr with a defined grid.
   * @param yTileMin         The y value for the bottom tile row of the TileGrid
   * @param tileRowsStartEnd the Array contains 2 values per Tile Row, the cStart Tile and the cEnd Tile */
  case class HGridIrrImp(unsafeArray: Array[Int]) extends HGrid
  {
    override def rTileMin: Int = ???

    override def rTileMax: Int = ???

    /** Minimum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
    override def cTileMin: Int = ???

    /** Maximum c or column value. This is not called x because in some grids there is not a 1 to 1 ratio from column coordinate to x. */
    override def cTileMax: Int = ???

    override def numOfRow2s: Int = ???

    override def numOfRow0s: Int = ???

    /** The total number of Tiles in the tile Grid. */
    override def numOfTiles: Int = ???

    def rowForeach(r: Int)(f: HCen => Unit): Unit = ???

    override def rowIForeach(r: Int, count: Int)(f: (HCen, Int) => Unit): Int = ???

    override def width: Double = ???

    override def height: Double = ???

    override def rowForeachSide(r: Int)(f: HSide => Unit): Unit = ???

    override def arrIndex(r: Int, c: Int): Int = ???

    override def tileRowLen(row: Int): Int = ???
  }
}