/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import geom._, pglobe._, prid._, phex._, reflect.ClassTag

/** A system of hex grids for the earth containing multiple grids. */
trait EGridMulti extends EGridSys with HGridMulti
{ ThisMulti =>
  override type GridT = EGrid
  type ManT <: EGridMan

  def gridMans: RArr[ManT]

  def numGrids: Int = gridMans.length

  /** Finds the most appropriate [[HGridMan]] for the [[HCoord]] or returns [[None]]. */
  def manFind(hc: HCoord): Option[ManT] = manFind(hc.r, hc.c)

  /** Finds the most appropriate [[HGridMan]] for the [[HCoord]] or returns [[None]]. */
  def manFind(r: Int, c: Int): Option[ManT]

  /** Gets the appropriate [[HGridMan]] for the [[HCoord]]. ex abbreviation for [[Exception]] Throws
   *  if [[HCoord]] doesn't exist. */
  final def manGetex(hc: HCoord): ManT = manGetex(hc.r, hc.c)

  /** Gets the appropriate [[HGridMan]] for the [[HCoord]].ex abbreviation for [[Exception]]. Throws
   *  if [[HCoord]] doesn't exist. */
  def manGetex(r: Int, c: Int): ManT

  /** Gets the appropriate [[HGrid]] for the [[HCoord]]. ex abbreviation for [[Exception]] Throws if
   *  [[HCoord]] doesn't exist. */
  def gridGetex(r: Int, c: Int): GridT = manGetex(r, c).grid

  /** Gets the appropriate [[HGrid]] for the [[HCoord]]. ex abbreviation for [[Exception]] Throws if
   *  [[HCoord]] doesn't exist. */
  final def gridGetex(hc: HCoord): GridT = manGetex(hc.r, hc.c).grid

  /** Maps the appropriate [[EGridMan]] for the [[HCoord]]. Throws if [[HCoord]] doesn't exist. */
  def manMapex[A](hc: HCoord)(f: ManT => A): A = f(manGetex(hc))

  /** Maps the appropriate [[EGridMan]] for the [[HCoord]]. ex abbreviation for [[Exception]].Throws
   *  if [[HCoord]] doesn't exist. */
  def manMapex[A](r: Int, c: Int)(f: ManT => A): A = f(manGetex(r, c))

  /** OptMaps the appropriate [[EGridMan]] for the [[HCoord]] to [[Option]] of A. */
  def manOptMap[A](hc: HCoord)(f: ManT => Option[A]): Option[A] = manFind(hc).flatMap(f)

  /** OptMaps the appropriate [[EGridMan]] for the [[HCoord]] to [[Option]] of A. */
  def manOptMap[A](r: Int, c: Int)(f: ManT => Option[A]): Option[A] = manFind(r, c).flatMap(f)

  def gridNumForeach(f: Int => Unit): Unit = iUntilForeach(numGrids)(f)

  def gridMansForeach(f: ManT => Unit): Unit = gridMans.foreach(f)

  def gridMansMap[A, AA <: Arr[A]](f: ManT => A)(implicit build: BuilderArrMap[A, AA]): AA = gridMans.map(f)

  def gridMansFlatMap[AA <: Arr[_]](f: ManT => AA)(implicit build: BuilderArrFlat[AA]): AA = gridMans.flatMap(f)

  override def foreach(f: HCen => Unit): Unit = grids.foreach(_.foreach(f))

  override def iForeach(f: (Int, HCen) => Unit): Unit = iForeach(0)(f)

  override def iForeach(init: Int)(f: (Int, HCen) => Unit): Unit =
  { var count = init
    grids.foreach { gr => gr.iForeach(count)(f); count += gr.numTiles }
  }

  override def stepEndGet(startCen: HCen, step: HStep): HCen = HCen(startCen.r + step.tr, startCen.c + step.tc)

  def hCenSteps(hCen: HCen): HStepArr = manMapex(hCen)(_.hCenSteps(hCen))

  final override def stepFind(startHC: HCen, endHC: HCen): Option[HStep] = manOptMap(startHC)(_.findStep(startHC, endHC))

  override def hCoordLL(hc: HCoord): LatLong = manMapex(hc)(_.grid.hCoordLL(hc))

  final override def hCenExists(r: Int, c: Int): Boolean = manFind(r, c).fold(false)(_.hCenExists(r, c))

  override def sideTileLtUnsafe(hSide: HSide): HCen = manMapex(hSide.r, hSide.c)(_.sideTileLtUnsafe(hSide))

  override def sideTileRtUnsafe(hSide: HSide): HCen = manMapex(hSide.r, hSide.c)(_.sideTileRtUnsafe(hSide))

  def sideTileLtAndVertUnsafe(hSide: HSide): (HCen, Int) = manMapex(hSide)(_.sideTileLtAndVertUnsafe(hSide))

  final override def adjTilesOfTile(origR: Int, origC: Int): HCenArr = manMapex(origR, origC)(_.adjTilesOfTile(origR, origC))

  override def layerArrayIndex(r: Int, c: Int): Int =
  { val ind = manMapex(r, c) { man => man.indexStart + man.grid.layerArrayIndex(r, c) }
    if (ind < 0) excep(s"r = $r, c = $c gives an array index of $ind, substiuting 0.")
    else ind
  }

  /** Spawns a new [[HSideLayer]] for this [[EGridMulti]], from an [[Arr]] of HGrid-HSideLayer pairs. */
  def sidesFromPairsSpawn[A](sidePairs: RArr[(HGrid, HSideLayer[A])], defaultA: A)(implicit ct: ClassTag[A]): HSideLayer[A] =
  { val res = HSideLayer[A](this, defaultA)
    gridMansForeach { m =>
      val pair = sidePairs(m.thisInd)
      val origGrid = pair._1
      val lay: HSideLayer[A] = pair._2
      m.sidesForeach { hs =>
        val value: A = lay(hs)(origGrid)
        res.set(ThisMulti, hs, value)
      }
    }
    res
  }

  /** Spawns a new [[LayerHSOptSys]] for this [[EGridMulti]], from an [[Arr]] of HGrid-HSideOptLayer pairs. */
  def sidesOptFromPairsSpawn[A, SA <: HSideSome](sidePairs: RArr[(HGrid, LayerHSOptSys[A, SA])])(implicit ct: ClassTag[A], noneTC: DefaultValue[A]):
  LayerHSOptSys[A, SA] =
  { val res = LayerHSOptSys[A, SA](this, noneTC)
    gridMansForeach { m =>
      val pair = sidePairs(m.thisInd)
      val origGrid = pair._1
      val lay: LayerHSOptSys[A, SA] = pair._2
      m.sidesForeach { hs =>
        val value: A = lay(hs)(origGrid)
        res.set(ThisMulti, hs, value)
      }
    }
    res
  }

  /** Spawns a new [[HSideBoolLayer]] for this [[EGridMulti]], from an [[Arr]] of HGrid-HSideOptLayer pairs. */
  def sideBoolsFromPairsSpawn(sidePairs: RArr[(HGrid, HSideBoolLayer)]): HSideBoolLayer =
  { val res = HSideBoolLayer()(this)
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

  override def flatHCoordToPt2(hCoord: HCoord): Pt2 = manMapex(hCoord){m => m.grid.flatHCoordToPt2(hCoord) + m.offset }

  override def rowsCombine[A <: AnyRef](layer: LayerHcRefSys[A], indexingGSys: HGridSys): RArr[HCenRowPair[A]] = grids.flatMap(_.rowsCombine(layer, this))

  /** Finds step from Start [[HCen]] to target from [[HCen]]. */
  override def stepEndFind(startHC: HCen, step: HStep): Option[HCen] = manMapex(startHC)(_.findStepEnd(startHC, step))

  override def defaultView(pxScale: Double = 30): HGView = grids(grids.length / 2).defaultView(pxScale)

  override final def sidesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.sidesForeach(f))
  override final def linksForeach(f: HSide => Unit): Unit = gridMans.foreach(_.linksForeach(f))
  override final def edgesForeach(f: HSide => Unit): Unit = gridMans.foreach(_.outerSidesForeach(f))

  def sideBoolsFromGrids[A <: AnyRef](sideLayers: RArr[HSideBoolLayer]): HSideBoolLayer =
  { val res = HSideBoolLayer()(this)
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
  final override def sideLayerArrayIndex(r: Int, c: Int): Int = manMapex(r, c) { man => man.sideIndexStart + man.sideArrIndex(r, c) }

  /** Finds the [[HCoord]] if it exists, by taking the [[HVDirn]] from an [[HVert]]. */
  override def vertToCoordFind(hVert: HVert, dirn: HVDirn): Option[HCoord] = manOptMap(hVert)(_.vertToCoordFind(hVert, dirn))
}