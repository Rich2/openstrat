/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, Colour.Black

/** A TileGrid is a description of an abstract TileGrid. It contains no data for the elements of any particular TileGrid. The Data for TileGrids is
 *  stored in flat arrays. The TileGrid gives the dimensions of a tileGrid. It has methods to interpret the data in flat Arrays created for that
 *  TileGrid specification. It has methods to map the elements of an Array to the the 2 dimensional geometry of the Tile Grid. On its own a TileGrid
 *  can produce the outlines of the grid, coordinates vector positions and other pure mathematical data. Combined with a simple function it can for
 *  example produce a Chess board. Combined with a 64 length array it can produce a Chess board position. For anything but the most simple games, you
 *  will probably want multiple arrays to describe the game state. The terrain for example may remain invariant, so the terrain data does not need to
 *  be reproduced with every move.
 *
 *  A TileGrid is for use cases where the proportions of the Grid predetermine the proportions of the visual representation, as opposed to a use case
 *  where the proportions of the enclosing space are a factor in determining the proportions of the grid. For example the various grid layouts of the
 *  Stars on the American flag.
 *  @groupname SidesGroup Tile Sides Methods
 *  @groupdesc SidesGroup Methods that operate on the tile sides of the grid. Remember a TileGrid object contains no data about the sides or the
 *             boundaries of the tiles.
 *  @groupprio SidesGroup 1010 */
trait TGrid extends Any
{
  /** Number of rows of tile centres. This will be different to the number of rows of sides and and will be different to the number of rows of
   *  vertices for HexGrids. */
  def numTileRows: Int

  /** The bottom or lowest tile centre row, r coordinate. */
  def bottomCenRow: Int

  /** The top of highest tile centre row, r coordinate. */
  def topCenRow: Int

  /** The centre of the hex grid in terms of r row coordinates. */
  def rCen: Int = (bottomCenRow + topCenRow) / 2

  /** The centre of the hex grid in terms of c column coordinates. */
  def cCen: Int = (leftCenCol + rightCenCol) / 2

  /** The [[TileCenOrSide]] coordinate centre of this tile grid. */
  def coordCen: TileCenOrSide

  /** The bottom or lowest tile side row, r coordinate. */
  @inline final def sideRowBottom: Int = bottomCenRow - 1

  /** The top of highest tile side row, r coordinate. */
  @inline def sideRowTop: Int = topCenRow + 1

  /** The minimum or lowest tile centre column c coordinate in the whole tile grid. This is called c rather than x because in hex grids [[HGrid]]s
   *  there is not a 1 to 1 ratio from column coordinate to the x value in a [[Pt2]]. */
  def leftCenCol: Int

  /** the Maximum or highest tile centre column c coordinate in the whole tile grid. This is called c rather than x because in hex grids [[HGrid]]s
   *  there is not a 1 to 1 ratio from column coordinate to the x value in a [[Pt2]]. */
  def rightCenCol: Int

  /** Width of the tile Grid from furthest tile edge to furthest tile edge. */
  def width: Double

  /** Height of the tile grid from furthest tile edge or vertex to furthest tile edge or vertex. */
  def height: Double

  /** The total number of tile centres in this tile Grid. */
  def numTiles: Int

  /** the ratio of r => y, when translating from [[TCoord]] tile grid coordinates to [[Pt2]] and [[Vec2]]s. */
  def yRatio: Double

  /** The centre of this grid in the X axis. this will be equal to the cCen [[Int]] value. */
  @inline def xCen: Double = (leftCenCol + rightCenCol) / 2

  /** The centre of this grid in the y axis. For [[SqGrid]]s this will be equal to the cCen [[Int]] value, but this is not the case for [[HGrid]]s. */
  @inline def yCen: Double

  /** The centre point as a [[Vec2]]. Not sure why this id implemented here. */
  def cenVec: Vec2 = Vec2(xCen, yCen)

  /** Foreach grid Row y coordinate. */
  final def foreachRow(f: Int => Unit): Unit = iToForeach(bottomCenRow, topCenRow, 2)(f)

  /** maps over each row number. */
  final def mapRows[B, BB <: SeqImut[B]](f: Int => B)(implicit build: ArrBuilder[B, BB]): BB =
  { val res = build.newArr(numTileRows)
    var index = 0
    foreachRow{r => res.unsafeSetElem(index, f(r)); index += 1 }
    res
  }

  /** flatMaps over each row number. */
  final def flatMapRows[ArrT <: SeqImut[_]](f: Int => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreachRow{ r => build.buffGrowArr(buff, f(r)) }
    build.buffToBB(buff)
  }

  /** foldLefts over each row number. */
  final def foldRows[B](init: B)(f: (B, Int) => B): B =
  { var acc = init
    foreachRow{ r => acc = f(acc, r) }
    acc
  }

  /** Foreach tile centre coordinate. A less strongly typed method than the foreach's in the sub traits. */
  def foreachCenCoord(f: TileCoord => Unit): Unit

  def mapCenCoords[B, BB <: SeqImut[B]](f: TileCoord => B)(implicit build: ArrBuilder[B, BB]): BB =
  { val res = build.newArr(numTiles)
    var count = 0
    foreachCenCoord { tc => res.unsafeSetElem(count, f(tc))
      count += 1
    }
    res
  }

  def fullDisplayScale(dispWidth: Double, dispHeight: Double, padding: Double = 20): Double =
  {
    def adj(inp : Double): Double = inp match
    { case n if n > 1000 => inp - padding
      case n if n > 500 => inp - padding * inp / 1000.0
      case n if n > 10 => n
      case _ => 10
    }
    (adj(dispWidth) / adj(width).max(1)).min(adj(dispHeight) / height.max(1))
  }

  /** The number of Rows of vertices. */
  @inline final def numOfVertRows: Int = ife(numTileRows > 1, numTileRows + 1, 0)

  /** Gives the text graphics for the row and column of each tile centre. */
  def rcTexts = mapCenCoords(tc => tc.rcStr.toTextGraphic(16, tc.toPt2))

  /* SideGroup Methods that operate on tile sides. **********************************************************/

  /** The number of Rows of Sides.
   *  @group SidesGroup */
  @inline final def numOfSideRows: Int = ife(numTileRows > 1, numTileRows * 2 + 1, 0)

  /** The bottom, lowest or minimum Side Row of this TileGrid. The r value, the row number value.
   *  @group SidesGroup */
  @inline final def bottomSideRow: Int = bottomCenRow - 1

  /** The top, highest or maximum Side Row of this TileGrid. The r value, the row number.
   *  @group SidesGroup*/
  @inline final def topSideRow: Int = topCenRow + 1

  /** Foreachs over each Row of Sides. Users will not normally need to use this method directly.
   *  @group SidesGroup */
  def sideRowForeach(f: Int => Unit) : Unit = iToForeach(bottomCenRow - 1, topCenRow + 1)(f)

  /** The line segments [[LineSeg]]s for the sides of the tiles.
   *  @group SidesGroup */
  def sideLines: LineSegs

  /** This gives the all tile grid lines in a single colour and line width.
   *  @group SidesGroup  */
  final def sidesDraw(colour: Colour = Black, lineWidth: Double = 2.0): LinesDraw = sideLines.draw(lineWidth, colour)
}