/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A system of tile grids. Can be a single tile grid or a system of multiple tile grids. */
trait TGridSys extends Any
{
  /** The total number of tile centres in this tile Grid system. */
  lazy val numTiles: Int

  /** the ratio of r => y, when translating from [[TCoord]] tile grid coordinates to [[Pt2]] and [[Vec2]]s. */
  def yRatio: Double

  /** Number of rows of tile centres. This will be different to the number of rows of sides and and will be different to the number of rows of
   *  vertices for HexGrids. */
  def numTileRows: Int

  /** For each row of tiles performs side effecting function on the r coordinate of the row. */
  def foreachRow(f: Int => Unit): Unit

  /** maps over each row number. */
  final def mapRows[B, BB <: Arr[B]](f: Int => B)(implicit build: ArrMapBuilder[B, BB]): BB =
  { val res = build.uninitialised(numTileRows)
    var index = 0
    foreachRow{r => res.unsafeSetElem(index, f(r)); index += 1 }
    res
  }

  /** flatMaps over each row number. */
  final def flatMapRows[ArrT <: Arr[_]](f: Int => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT =
  { val buff = build.newBuff(numTiles)
    foreachRow{ r => build.buffGrowArr(buff, f(r)) }
    build.buffToSeqLike(buff)
  }

  /** The top most point in the grid where the value of y is maximum. */
  def top: Double

  /** The bottom most point in the grid where the value of y is minimum. */
  def bottom: Double

  /** The left most point in the grid where x is minimum. */
  def left: Double

  /** The right most point in the grid where the value of x is maximum. */
  def right: Double

  /** Height of the tile grid system from furthest tile edge or vertex to furthest tile edge or vertex. */
  final def height: Double = top - bottom

  /** Width of the tile grid system from furthest tile edge or vertex to furthest tile edge or vertex. */
  final def width: Double = right - left

  /** The centre of this grid in the X axis. this will be equal to the cCen [[Int]] value. */
  def xCen: Double = left aver right

  /** The centre of this grid in the y axis. For [[SqGrid]]s this will be equal to the cCen [[Int]] value, but this is not the case for [[HGrid]]s. */
  @inline def yCen: Double = bottom aver top

  /** The centre point as a [[Vec2]]. Not sure why this id implemented here. */
  def cenVec: Vec2 = Vec2(xCen, yCen)

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
}