/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

trait HGridMulti extends HGrider
{
  def grids: Arr[HGrid]
  def numGrids: Int = grids.length
  def gridNumForeach(f: Int => Unit): Unit = iUntilForeach(0, numGrids)(f)
  def gridNumsMap[A, AA <: SeqImut[A]](f: Int => A)(implicit build: ArrBuilder[A, AA]): AA = iUntilMap(0, numGrids)(f)
  def gridNumsFlatMap[AA <: SeqImut[_]](f: Int => AA)(implicit build: ArrFlatBuilder[AA]): AA = iUntilFlatMap(0, numGrids)(f)

  def gridNumsFold[B](initValue: B)(f: (B, Int) => B): B = {
    var acc: B = initValue
    gridNumForeach{ el => acc = f(acc, el) }
    acc
  }

  inline def gridNumsFold[B](f: (B, Int) => B)(implicit ev: DefaultValue[B]): B = gridNumsFold(ev.default)(f)

  override val numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  def gridSides(gridNum: Int): HSides
  def gridNumSides(gridNum: Int): Int

  override def sides: HSides = gridNumsFlatMap{ n => gridSides(n) }

  override def numSides: Int = gridNumsFold{(acc, i) => acc + gridNumSides(i) }

  override def defaultView(pxScale: Double = 50): HGridView = grids(0).defaultView(pxScale)
}

trait HGridMultiFlat extends HGridMulti with HGriderFlat
{
  def gridsOffsets: Vec2s

  def gridOffsetsForeach(f: (HGrid, Vec2) => Unit): Unit = gridNumForeach{ i => f(grids(i), gridsOffsets(i)) }
  def gridOffsetsMap[A, AA <: SeqImut[A]](f: (HGrid, Vec2) => A)(implicit build: ArrBuilder[A, AA]): AA = ???

  def gridOffsetsFlatMap[AA <: SeqImut[_]](f: (HGrid, Vec2) => AA)(implicit build: ArrFlatBuilder[AA]): AA =
  { val buff = build.newBuff()
    gridOffsetsForeach{ (g, v) => build.buffGrowArr(buff, f(g, v)) }
    build.buffToBB(buff)
  }

  override def polygons: Arr[Polygon] = gridOffsetsFlatMap((g, offset) => g.polygons.slate(offset))

  override def activeTiles: Arr[PolygonActive] = gridOffsetsFlatMap{(grid, offset) => grid.map{ hc => hc.polygonReg.slate(offset).active(hc)} }

  override def sideLines: LineSegs = ???
}