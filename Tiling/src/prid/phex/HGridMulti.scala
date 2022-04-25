/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._

/** A system of multiple [[HGrid]]s. */
trait HGridMulti extends HGridSys with TGridMulti
{ final type GridT = HGrid
  def gridMans: Arr[HGridMan]
  def grids: Arr[HGrid] = gridMans.map(_.grid)
  def numGrids: Int = gridMans.length

  /** Gets the appropriate [[HGridMan]] for the [[HCen]]. Throws if HCen doesn't exist. */
  final def unsafeGetMan(hc: HCoord): HGridMan = unsafeGetMan(hc.r, hc.c)

  /** Gets the appropriate [[HGridMan]] for the [[HCen]]. Throws if HCen doesn't exist. */
  def unsafeGetMan(r: Int, c: Int): HGridMan

  def unsafeGetManFunc[A](hc: HCoord)(f: HGridMan => A): A = f(unsafeGetMan(hc))
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

  /** Combine adjacent elements of data in a row. */
  def rowCombine[A <: AnyRef](data: HCenDGrid[A], indexingGrider: HGridSys): Arr[HCenRowValue[A]] = grids.flatMap(_.rowCombine(data, this))

  final override def hCenExists(r: Int, c: Int): Boolean = unsafeGetManFunc(r, c)(_.grid.hCenExists(r, c))
  override def adjTilesOfTile(tile: HCen): HCenArr = unsafeGetManFunc(tile)(_.adjTilesOfTile(tile))
  override def numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  override def unsafeStepEnd(startCen: HCen, step: HDirn): HCen = HCen(startCen.r + step.tr, startCen.c + step.tc)
  def hCenSteps(hCen: HCen): HDirnArr = unsafeGetManFunc(hCen)(_.hCenSteps(hCen))
  final override def findStep(startHC: HCen, endHC: HCen): Option[HDirn] = unsafeGetManFunc(startHC)(_.findStep(startHC, endHC))

  final override def arrIndex(r: Int, c: Int): Int = unsafeGetManFunc(r, c){ man => man.arrIndex + man.grid.arrIndex(r, c) }

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  override def findStepEnd(startHC: HCen, step: HDirn): Option[HCen] = unsafeGetManFunc(startHC)(_.findStepEnd(startHC, step))

  def sides: HSideArr = gridMans.flatMap(_.sides)
  def sideLines(implicit grider: HGridSysFlat): LineSegs = gridMans.flatMap(_.sideLines)
 // def gridNumSides(gridNum: Int): Int

  override def numSides: Int = gridMansSum{g => g.numSides }

  override def defaultView(pxScale: Double = 50): HGridView = grids(0).defaultView(pxScale)
}
