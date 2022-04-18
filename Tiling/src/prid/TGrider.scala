/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

/** A system of tile grids. Can be a single tile grid or a system of multiple tile grids. */
trait TGrider extends Any
{
  /** The total number of tile centres in this tile Grid system. */
  def numTiles: Int

  /** the ratio of r => y, when translating from [[TCoord]] tile grid coordinates to [[Pt2]] and [[Vec2]]s. */
  def yRatio: Double

  def flatMapRows[ArrT <: SeqImut[_]](f: Int => ArrT)(implicit build: ArrFlatBuilder[ArrT]): ArrT
}

trait TGridMulti extends TGrider
{
  type GridT <: TGrid
}