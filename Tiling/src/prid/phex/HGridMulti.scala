/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._

case class HGridMan(grid: HGrid, arrIndex: Int)
{ def numTiles: Int = grid.numTiles
  final def outSteps(hCen: HCen): Arr[(HStep, HCen)] = outSteps(hCen.r, hCen.c)
  def outSteps(r: Int, c: Int): Arr[(HStep, HCen)] = Arr()
  def sides: HSides = grid.sides
  val numSides: Int = grid.sides.length
  def sideLines(implicit grider: HGriderFlat): LineSegs = sides.map(_.lineSeg)
}

trait HGridMulti extends HGrider
{
  def gridMans: Arr[HGridMan]
  def grids: Arr[HGrid] = gridMans.map(_.grid)
  def numGrids: Int = gridMans.length
  final def unsafeGetMan(hCen: HCen): HGridMan = unsafeGetMan(hCen.r, hCen.c)
  def unsafeGetMan(r: Int, c: Int): HGridMan
  def unsafeGetManFunc[A](hCen: HCen)(f: HGridMan => A): A = f(unsafeGetMan(hCen))
  def unsafeGetManFunc[A](r: Int, c: Int)(f: HGridMan => A): A = f(unsafeGetMan(r, c))
  def gridNumForeach(f: Int => Unit): Unit = iUntilForeach(0, numGrids)(f)
  def gridNumsMap[A, AA <: SeqImut[A]](f: Int => A)(implicit build: ArrBuilder[A, AA]): AA = iUntilMap(0, numGrids)(f)
  def gridNumsFlatMap[AA <: SeqImut[_]](f: Int => AA)(implicit build: ArrFlatBuilder[AA]): AA = iUntilFlatMap(0, numGrids)(f)

  def gridNumsFold[B](initValue: B)(f: (B, Int) => B): B = {
    var acc: B = initValue
    gridNumForeach{ el => acc = f(acc, el) }
    acc
  }

  inline def gridNumsFold[B](f: (B, Int) => B)(implicit ev: DefaultValue[B]): B = gridNumsFold(ev.default)(f)

  def gridNumsSum(f: HGrid => Int): Int = gridNumsFold(0)((acc, el) => acc + f(grids(el)))
  final override def hCenExists(r: Int, c: Int): Boolean = unsafeGetManFunc(r, c)(_.grid.hCenExists(r, c))

  override def numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  override def unsafeStep(startCen: HCen, step: HStep): HCen = HCen(startCen.r + step.r, startCen.c + step.c)

  final override def arrIndex(r: Int, c: Int): Int = unsafeGetManFunc(r, c){ man => man.arrIndex + man.grid.arrIndex(r, c) }

  def sides: HSides = gridMans.flatMap(_.sides)
  def sideLines(implicit grider: HGriderFlat): LineSegs = gridMans.flatMap(_.sideLines)
 // def gridNumSides(gridNum: Int): Int

  override def numSides: Int = gridNumsSum{g => g.numSides }

  override def defaultView(pxScale: Double = 50): HGridView = grids(0).defaultView(pxScale)
}
