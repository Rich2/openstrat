/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

trait HGridMulti extends HGrider
{
  def grids: Arr[HGrid]
  override val numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }
}

trait HGridMultiFlat extends HGridMulti with HGriderFlat
{
  def gridsOffsets: Vec2s

  override def polygons: Arr[Polygon] = ???

  /** The active tiles without any PaintElems. */
  override def activeTiles: Arr[PolygonActive] = ???
}