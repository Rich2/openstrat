/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._

case class HGridMan(grid: HGrid){
  def sides: HSides = grid.sides
  def sideLines: LineSegs = sides.map(_.lineSeg)
}

trait HGridMulti extends HGrider
{
  def gridMans: Arr[HGridMan]
  def grids: Arr[HGrid] = gridMans.map(_.grid)
  def numGrids: Int = gridMans.length
  def gridNumForeach(f: Int => Unit): Unit = iUntilForeach(0, numGrids)(f)
  def gridNumsMap[A, AA <: SeqImut[A]](f: Int => A)(implicit build: ArrBuilder[A, AA]): AA = iUntilMap(0, numGrids)(f)
  def gridNumsFlatMap[AA <: SeqImut[_]](f: Int => AA)(implicit build: ArrFlatBuilder[AA]): AA = iUntilFlatMap(0, numGrids)(f)

  def gridNumsFold[B](initValue: B)(f: (B, Int) => B): B = {
    var acc: B = initValue
    gridNumForeach{ el => acc = f(acc, el) }
    acc
  }

  inline def gridNumsFold[B](f: (B, Int) => B)(implicit ev: DefaultValue[B]): B = gridNumsFold(ev.default)(f)

  override def numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  def sides: HSides = gridMans.flatMap(_.sides)
  final def sideLines = gridMans.flatMap(_.sideLines)
  def gridNumSides(gridNum: Int): Int

  //override def sides: HSides = gridNumsFlatMap{ n => gridSides(n) }

  override def numSides: Int = gridNumsFold{(acc, i) => acc + gridNumSides(i) }

  override def defaultView(pxScale: Double = 50): HGridView = grids(0).defaultView(pxScale)
}
