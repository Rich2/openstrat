/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._, reflect.ClassTag

/** A system of hex grids for the earth containing multiple grids. */
trait EGridMulti extends EGridSys with HGridMulti
{ ThisMulti =>
  override type GridT = EGrid
  override type ManT = EGridMan
  override def hCoordLL(hc: HCoord): LatLong = unsafeGetManFunc(hc)(_.grid.hCoordLL(hc))

  final override def hCenExists(r: Int, c: Int): Boolean = getMan(r, c).fold(false)(_.grid.hCenExists(r, c))

  override def sideTile1(hSide: HSide): HCen = unsafeGetManFunc(hSide.r, hSide.c)(_.sideTile1(hSide))

  override def sideTile2(hSide: HSide): HCen = unsafeGetManFunc(hSide.r, hSide.c)(_.sideTile2(hSide))

  override def adjTilesOfTile(tile: HCen): HCenArr = unsafeGetManFunc(tile)(_.adjTilesOfTile(tile))
  override def layerArrayIndex(r: Int, c: Int): Int = {
    val ind = unsafeGetManFunc(r, c) { man => man.indexStart + man.grid.layerArrayIndex(r, c) }
    if (ind < 0) {
      deb(s"r = $r, c = $c gives an array index of $ind, substiuting 0.")
      0
    }
    else ind
  }

  def sideOptsFromPairsSpawn[A <: AnyRef](sidePairs: RArr[(HGrid, HSideOptLayer[A])])(implicit ct: ClassTag[A]): HSideOptLayer[A] = {
    val res = newSideOpts[A]
    gridMansForeach { m =>
      val pair = sidePairs(m.thisInd)
      val origGrid = pair._1
      val lay: HSideOptLayer[A] = pair._2
      m.sidesForeach { hs =>
        val value: A = lay.unsafeApply(hs)(origGrid)
        res.set(ThisMulti, hs, value)
      }
    }
    res
  }

  def sideBoolsFromPairsSpawn(sidePairs: RArr[(HGrid, HSideBoolLayer)]): HSideBoolLayer = {
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

  override def coordCen: HCoord = grids(numGrids / 2).coordCen

  override def flatHCoordToPt2(hCoord: HCoord): Pt2 = unsafeGetManFunc(hCoord){m => m.grid.flatHCoordToPt2(hCoord) + m.offset }

  override def rowsCombine[A <: AnyRef](layer: HCenLayer[A], indexingGSys: HGridSys): RArr[HCenRowPair[A]] = grids.flatMap(_.rowsCombine(layer, this))

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  override def findStepEnd(startHC: HCen, step: HStep): Option[HCen] = unsafeGetManFunc(startHC)(_.findStepEnd(startHC, step))

  override def defaultView(pxScale: Double = 30): HGView = grids(grids.length / 2).defaultView(pxScale)

  override final def sidesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.sidesForeach(f))
  override final def linksForeach(f: HSide => Unit): Unit = gridMans.foreach(_.linksForeach(f))
  override final def edgesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.outerSidesForeach(f))

  def sideBoolsFromGrids[A <: AnyRef](sideLayers: RArr[HSideBoolLayer]): HSideBoolLayer = {
    val res = newSideBools
    gridMansForeach { m =>
      m.sidesForeach { hs =>
        val dGrid: HSideBoolLayer = sideLayers(m.thisInd)
        val value: Boolean = dGrid.apply(hs)(m.grid)
        res.set(hs, value)(ThisMulti)
      }
    }
    res
  }

  /** Temporary implementation. */
  final override def sideLayerArrayIndex(r: Int, c: Int): Int = unsafeGetManFunc(r, c) { man => man.sideIndexStart + man.sideArrIndex(r, c) }

}

trait EGridMan extends HGridMan
{ override def grid: EGrid
  def innerRowInnerSidesForeach(r: Int)(f: HSide => Unit): Unit
  final def linksForeach(f: HSide => Unit): Unit = grid.innerSideRowsForeach(r => innerRowInnerSidesForeach(r)(f))
}