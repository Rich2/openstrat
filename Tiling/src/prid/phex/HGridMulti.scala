/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A system of multiple [[HGrid]]s. */
trait HGridMulti extends HGridSys with TGridMulti
{ ThisMulti =>
  type GridT <: HGrid
  type ManT <: HGridMan
  def gridMans: RArr[ManT]
  def numGrids: Int = gridMans.length

  override def flatHCoordToPt2(hCoord: HCoord): Pt2 = unsafeGetManFunc(hCoord){m => m.grid.flatHCoordToPt2(hCoord) + m.offset }

  override def coordCen: HCoord = grids(numGrids / 2).coordCen

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

  def gridMansFold[B](initValue: B)(f: (B, ManT) => B): B = gridMans.foldLeft(initValue)(f)
  inline def gridMansFold[B](f: (B, ManT) => B)(implicit ev: DefaultValue[B]): B = gridMansFold(ev.default)(f)
  def gridMansSum(f: ManT => Int): Int = gridMansFold(0)((acc, el) => acc + f(el))

  def gridNumsFold[B](initValue: B)(f: (B, Int) => B): B =
  { var acc: B = initValue
    gridNumForeach{ el => acc = f(acc, el) }
    acc
  }

  override def rowsCombine[A <: AnyRef](layer: HCenLayer[A], indexingGSys: HGridSys): RArr[HCenRowPair[A]] = grids.flatMap(_.rowsCombine(layer, this))

  final override def hCenExists(r: Int, c: Int): Boolean = getMan(r, c).fold(false)(_.grid.hCenExists(r, c))

  override def sideTile1(hSide: HSide): HCen = unsafeGetManFunc(hSide.r, hSide.c)(_.sideTile1(hSide))

  override def sideTile2(hSide: HSide): HCen = unsafeGetManFunc(hSide.r, hSide.c)(_.sideTile2(hSide))

  override def adjTilesOfTile(tile: HCen): HCenArr = unsafeGetManFunc(tile)(_.adjTilesOfTile(tile))
  //override def numTiles: Int = grids.sumBy(_.numTiles)
  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))
  override def iForeach(f: (HCen, Int) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (HCen, Int) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  override def unsafeStepEnd(startCen: HCen, step: HStep): HCen = HCen(startCen.r + step.tr, startCen.c + step.tc)
  def hCenSteps(hCen: HCen): HStepArr = unsafeGetManFunc(hCen)(_.hCenSteps(hCen))
  final override def findStep(startHC: HCen, endHC: HCen): Option[HStep] = unsafeGetManFunc(startHC)(_.findStep(startHC, endHC))

  /*lazy val gridIndexArray: Array[Int] =
  { val res = new Array[Int](numGrids)
    var acc = 0
    grids.iForeach{(i, g) => res(i) = acc; acc += g.numTiles }
    res
  }*/

  override def layerArrayIndex(r: Int, c: Int): Int ={
    val ind = unsafeGetManFunc(r, c){ man => man.indexStart + man.grid.layerArrayIndex(r, c) }
    if (ind < 0) {
      deb(s"r = $r, c = $c gives an array index of $ind, substiuting 0.")
      0
    }
    else ind
  }

  /** Temporary implementation. */
  final override def sideLayerArrayIndex(r: Int, c: Int): Int = unsafeGetManFunc(r, c){ man => man.sideIndexStart + man.sideArrIndex(r, c) }

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  override def findStepEnd(startHC: HCen, step: HStep): Option[HCen] = unsafeGetManFunc(startHC)(_.findStepEnd(startHC, step))

  override def defaultView(pxScale: Double = 50): HGView = grids(0).defaultView(pxScale)
  override final def sidesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.sidesForeach(f))
  override final def linksForeach(f: HSide => Unit): Unit = gridMans.foreach(_.linksForeach(f))
  override final def edgesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.outerSidesForeach(f))

  def sideBoolsFromGrids[A <: AnyRef](sideLayers: RArr[HSideBoolLayer]): HSideBoolLayer =
  { val res = newSideBools
    gridMansForeach{ m =>
      m.sidesForeach{ hs =>
        val dGrid: HSideBoolLayer = sideLayers(m.thisInd)
        val value: Boolean = dGrid.apply(hs)(m.grid)
        res.set(hs, value)(ThisMulti)
      }
    }
    res
  }

  def sideBoolsFromPairsSpawn[A <: AnyRef](sidePairs: RArr[(HGrid, HSideBoolLayer)]): HSideBoolLayer = {
    val res = newSideBools
    gridMansForeach { m =>
      val pair = sidePairs(m.thisInd)
      val origGrid = pair._1
      val lay: HSideBoolLayer = pair._2
      m.sidesForeach { hs =>
        val value: Boolean = lay(hs)(origGrid)
        res.set(hs, value)(ThisMulti)
      }
    }
    res
  }
}