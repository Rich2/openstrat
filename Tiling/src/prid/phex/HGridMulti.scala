/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** A system of multiple [[HGrid]]s. */
trait HGridMulti extends HGridSys with TGridMulti
{ ThisMulti =>
  type GridT <: HGrid
  type ManT <: HGridMan
  def gridMans: RArr[ManT]
  def numGrids: Int = gridMans.length

  /** Finds the most appropriate [[HGridMan]] for the [[HCoord]] or returns [[None]]. */
  def getMan(hc: HCoord): Option[ManT] = getMan(hc.r, hc.c)

  /** Finds the most appropriate [[HGridMan]] for the [[HCoord]] or returns [[None]]. */
  def getMan(r: Int, c: Int): Option[ManT]

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
  def gridNumForeach(f: Int => Unit): Unit = iUntilForeach(numGrids)(f)
  def gridMansForeach(f: ManT => Unit) = gridMans.foreach(f)
  def gridMansMap[A, AA <: Arr[A]](f: ManT => A)(implicit build: ArrMapBuilder[A, AA]): AA = gridMans.map(f)
  def gridMansFlatMap[AA <: Arr[_]](f: ManT => AA)(implicit build: ArrFlatBuilder[AA]): AA = gridMans.flatMap(f)


  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  override def unsafeStepEnd(startCen: HCen, step: HStep): HCen = HCen(startCen.r + step.tr, startCen.c + step.tc)
  def hCenSteps(hCen: HCen): HStepArr = unsafeGetManFunc(hCen)(_.hCenSteps(hCen))
  final override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = unsafeGetManFunc(startHC)(_.findStep(startHC, endHC))
}