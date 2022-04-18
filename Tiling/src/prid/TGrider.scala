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

  def foreachRow(f: Int => Unit): Unit

  /** maps over each row number. */
  /*final def mapRows[B, BB <: SeqImut[B]](f: Int => B)(implicit build: ArrBuilder[B, BB]): BB =
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
  }*/
}

trait TGridMulti extends TGrider
{ type GridT <: TGrid
  def grids: Arr[GridT]
  def foreachRow(f: Int => Unit): Unit = grids.foreach(_.foreachRow(f))
}