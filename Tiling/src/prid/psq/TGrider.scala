/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A system of tile grids. Can be a single tile grid or a system of multiple tile grids. */
trait TGrider extends Any
{
  /** The total number of tile centres in this tile Grid system. */
  def numTiles: Int

  /** the ratio of r => y, when translating from [[TCoord]] tile grid coordinates to [[Pt2]] and [[Vec2]]s. */
  def yRatio: Double
}

/** A system of tile grids using a flat 2D geometry. This includes all single tile grids which are considered flat and some systems of multiple tile grids. */
trait TGriderFlat extends Any with TGrider
{ /** The top most point in the grid where the value of y is maximum. */
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
}