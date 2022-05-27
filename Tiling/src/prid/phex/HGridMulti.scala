/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import ostrat.geom._

/** A system of multiple [[HGrid]]s. */
trait HGridMulti extends HGridSys with TGridMulti
{ type GridT <: HGrid
  type ManT <: HGridMan
  def gridMans: Arr[ManT]
  def numGrids: Int = gridMans.length

  override def hCoordToPt2(hCoord: HCoord): Pt2 = unsafeGetManFunc(hCoord)(m => m.grid.hCoordToPt2(hCoord) + m.offset)

  override def coordCen: HCoord = grids(numGrids / 2).coordCen

  /** Gets the appropriate [[HGridMan]] for the [[HCoord]]. Throws if [[HCoord]] doesn't exist. */
  final def unsafeGetMan(hc: HCoord): ManT = unsafeGetMan(hc.r, hc.c)

  /** Gets the appropriate [[HGridMan]] for the [[HCoord]]. Throws if [[HCoord]] doesn't exist. */
  def unsafeGetMan(r: Int, c: Int): ManT

  /** Gets the appropriate [[HGrid]] for the [[HCoord]]. Throws if [[HCoord]] doesn't exist. */
  def unsafeGetGrid(r: Int, c: Int): GridT = unsafeGetMan(r, c).grid.asInstanceOf[GridT]

  /** Gets the appropriate [[HGrid]] for the [[HCoord]]. Throws if [[HCoord]] doesn't exist. */
  final def unsafeGetGrid(hc: HCoord): GridT = unsafeGetMan(hc.r, hc.c).grid.asInstanceOf[GridT]

  def unsafeGetManFunc[A](hc: HCoord)(f: ManT => A): A = f(unsafeGetMan(hc))
  def unsafeGetManFunc[A](r: Int, c: Int)(f: ManT => A): A = f(unsafeGetMan(r, c))
  def gridNumForeach(f: Int => Unit): Unit = iUntilForeach(0, numGrids)(f)
  def gridMansForeach(f: ManT => Unit) = gridMans.foreach(f)
  def gridMansMap[A, AA <: SeqImut[A]](f: ManT => A)(implicit build: ArrBuilder[A, AA]): AA = gridMans.map(f)
  def gridMansFlatMap[AA <: SeqImut[_]](f: ManT => AA)(implicit build: ArrFlatBuilder[AA]): AA = gridMans.flatMap(f)

  def gridMansFold[B](initValue: B)(f: (B, ManT) => B): B = gridMans.foldLeft(initValue)(f)
  inline def gridMansFold[B](f: (B, ManT) => B)(implicit ev: DefaultValue[B]): B = gridMansFold(ev.default)(f)
  def gridMansSum(f: ManT => Int): Int = gridMansFold(0)((acc, el) => acc + f(el))

  def gridNumsFold[B](initValue: B)(f: (B, Int) => B): B = {
    var acc: B = initValue
    gridNumForeach{ el => acc = f(acc, el) }
    acc
  }
  override def polygons: Arr[Polygon] = gridMans.flatMap(m => m.grid.polygons.slate(m.offset))
  override def activeTiles: Arr[PolygonActive] = gridMans.flatMap{m => m.grid.map{ hc => hc.polygonReg.slate(m.offset).active(hc)}}
  /** Combine adjacent elements of data in a row. */
  def rowCombine[A <: AnyRef](data: HCenDGrid[A], indexingGrider: HGridSys): Arr[HCenRowValue[A]] = grids.flatMap(_.rowCombine(data, this))

  final override def hCenExists(r: Int, c: Int): Boolean = unsafeGetManFunc(r, c)(_.grid.hCenExists(r, c))
  override def adjTilesOfTile(tile: HCen): HCenArr = unsafeGetManFunc(tile)(_.adjTilesOfTile(tile))
  //override def numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  override def unsafeStepEnd(startCen: HCen, step: HDirn): HCen = HCen(startCen.r + step.tr, startCen.c + step.tc)
  def hCenSteps(hCen: HCen): HDirnArr = unsafeGetManFunc(hCen)(_.hCenSteps(hCen))
  final override def findStep(startHC: HCen, endHC: HCen): Option[HDirn] = unsafeGetManFunc(startHC)(_.findStep(startHC, endHC))

  override def arrIndex(r: Int, c: Int): Int = unsafeGetManFunc(r, c){ man => man.arrIndex + man.grid.arrIndex(r, c) }

  /** Temporary implementation. */
  override def sideArrIndex(r: Int, c: Int): Int = 0

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  override def findStepEnd(startHC: HCen, step: HDirn): Option[HCen] = unsafeGetManFunc(startHC)(_.findStepEnd(startHC, step))

  override def defaultView(pxScale: Double = 50): HGView = grids(0).defaultView(pxScale)
  override final def sidesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.sidesForeach(f))
  override final def innerSidesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.innerSidesForeach(f))
  override final def outerSidesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.outerSidesForeach(f))
  def sideBoolsFromGrids[A <: AnyRef](sideDataGrids: HSideArr): Unit ={
    val res = newSideBools
    gridMansForeach{m => m.sidesForeach(???) }
  }
}
