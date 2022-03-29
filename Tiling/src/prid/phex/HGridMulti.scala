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

  /** Default implementation may need removal. */
  def adjTilesOfTile(tile: HCen): HCens = grid.adjTilesOfTile(tile)

  //def hCenSteps(hCen: HCen)
}

trait HGridMulti extends HGrider
{
  def gridMans: Arr[HGridMan]
  def grids: Arr[HGrid] = gridMans.map(_.grid)
  def numGrids: Int = gridMans.length

  /** Gets the appropriate [[HGridMan]] for the [[HCen]]. Throws if HCen doesn't exist. */
  final def unsafeGetMan(hCen: HCen): HGridMan = unsafeGetMan(hCen.r, hCen.c)

  /** Gets the appropriate [[HGridMan]] for the [[HCen]]. Throws if HCen doesn't exist. */
  def unsafeGetMan(r: Int, c: Int): HGridMan

  def unsafeGetManFunc[A](hCen: HCen)(f: HGridMan => A): A = f(unsafeGetMan(hCen))
  def unsafeGetManFunc[A](r: Int, c: Int)(f: HGridMan => A): A = f(unsafeGetMan(r, c))
  def gridNumForeach(f: Int => Unit): Unit = iUntilForeach(0, numGrids)(f)
  def gridMansForeach(f: HGridMan => Unit) = gridMans.foreach(f)
  def gridMansMap[A, AA <: SeqImut[A]](f: HGridMan => A)(implicit build: ArrBuilder[A, AA]): AA = gridMans.map(f)
  def gridMansFlatMap[AA <: SeqImut[_]](f: HGridMan => AA)(implicit build: ArrFlatBuilder[AA]): AA = gridMans.flatMap(f)

  def gridMansFold[B](initValue: B)(f: (B, HGridMan) => B): B = gridMans.foldLeft(initValue)(f)
  inline def gridMansFold[B](f: (B, HGridMan) => B)(implicit ev: DefaultValue[B]): B = gridMansFold(ev.default)(f)
  def gridMansSum(f: HGridMan => Int): Int = gridMansFold(0)((acc, el) => acc + f(el))

  def gridNumsFold[B](initValue: B)(f: (B, Int) => B): B = {
    var acc: B = initValue
    gridNumForeach{ el => acc = f(acc, el) }
    acc
  }

  final override def hCenExists(r: Int, c: Int): Boolean = unsafeGetManFunc(r, c)(_.grid.hCenExists(r, c))
  override def adjTilesOfTile(tile: HCen): HCens = unsafeGetManFunc(tile)(_.adjTilesOfTile(tile))
  override def numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  override def unsafeStep(startCen: HCen, step: HStep): HCen = HCen(startCen.r + step.r, startCen.c + step.c)
  def hCenSteps(hCen: HCen): HSteps = ???
  override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = ???

  final override def arrIndex(r: Int, c: Int): Int = unsafeGetManFunc(r, c){ man => man.arrIndex + man.grid.arrIndex(r, c) }

  def sides: HSides = gridMans.flatMap(_.sides)
  def sideLines(implicit grider: HGriderFlat): LineSegs = gridMans.flatMap(_.sideLines)
 // def gridNumSides(gridNum: Int): Int

  override def numSides: Int = gridMansSum{g => g.numSides }

  override def defaultView(pxScale: Double = 50): HGridView = grids(0).defaultView(pxScale)
}
