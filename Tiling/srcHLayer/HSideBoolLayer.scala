/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** Boolean data corresponding to the sides of a [[HGridSys]] hex grid system , stored using an underlying Array[Boolean]. Thhese classes should be
 *  created, initalised and used using an [HGrid]] class. For convenience the [[HGrid]] is passed as an implicit parameter. */
final class HSideBoolLayer(val unsafeArray: Array[Boolean]) extends AnyVal with BoolSeqSpec
{ override type ThisT = HSideBoolLayer
  override def typeStr: String = "HSideBoolDGrid"
  override def fromArray(array: Array[Boolean]): HSideBoolLayer = new HSideBoolLayer(array)

  def apply(hs: HSide)(implicit gridSys: HGridSys): Boolean = unsafeArray(gridSys.sideLayerArrayIndex(hs))

  /** Foreach true value applies the side effecting function to the corresponding [[HSide]]
   *  value.  */
  def truesHsForeach(f: HSide => Unit)(implicit gridSys: HGridSys): Unit =
  { var i = 0
    gridSys.sidesForeach{hs =>
      if (unsafeArray(i) == true) f(hs)
      i += 1
    }
  }

  /** Maps the true values to a [[Arr]][B]. */
  def truesHsMap[B, ArrB <: Arr[B]](f: HSide => B)(implicit gridSys: HGridSys, build: MapBuilderArr[B, ArrB]): ArrB = truesHsMap(gridSys)(f)(build)

  /** Maps the true values to a [[Arr]][B]. */
  def truesHsMap[B, ArrB <: Arr[B]](gridSys: HGridSys)(f: HSide => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
  { var i = 0
    val buff = build.newBuff()
    gridSys.sidesForeach{hs =>
      if (unsafeArray(i))
        build.buffGrow(buff, f(hs))
      i += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Returns the [[HSide]]s that have a corresponding true value. */
  def trueHSides(implicit gridSys: HGridSys): HSideArr = truesHsMap(hs => hs)

  def projTruesMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: HSide => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
  { var i = 0
    val buff = build.newBuff()
    proj.gChild.sidesForeach { hs =>
      if (unsafeArray(i))
        build.buffGrow(buff, f(hs))
      i += 1
    }
    build.buffToSeqLike(buff)
  }

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projTruesLineSegMap[B, ArrB <: Arr[B]](f: LineSeg => B)(implicit proj: HSysProjection, build: MapBuilderArr[B, ArrB]): ArrB =
    projTruesLineSegMap(proj)(f)(build)

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projTruesLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LineSeg => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
    proj.gChild.sidesOptMap { hs => if (apply(hs)(proj.parent)) proj.transOptLineSeg(hs.lineSegHC).map(f)
      else None
    }

  /** Maps across all the link trues in this Side Layer that exist in the projection. */
  def projLinkTruesLineSegMap[B, ArrB <: Arr[B]](f: LineSeg => B)(implicit proj: HSysProjection, build: MapBuilderArr[B, ArrB]): ArrB =
    projLinkTruesLineSegMap(proj)(f)(build)

  /** Maps across all the link trues in this Side Layer that exist in the projection. */
  def projLinkTruesLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LineSeg => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
    proj.gChild.linksOptMap { hs =>
      if (apply(hs)(proj.parent)) proj.transOptLineSeg(hs.lineSegHC).map(f)
      else None
    }

  /** Maps across all the falses in this Side Layer that exist in the projection. */
  def projFalsesLineSegMap[B, ArrB <: Arr[B]](f: LineSeg => B)(implicit proj: HSysProjection, build: MapBuilderArr[B, ArrB]): ArrB =
    projFalsesLineSegMap(proj)(f)

  /** Maps across all the falses in this Side Layer that exist in the projection. */
  def projFalsesLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LineSeg => B)(implicit build: MapBuilderArr[B, ArrB]): ArrB =
    proj.gChild.sidesOptMap { hs =>
      if (!apply(hs)(proj.parent)) proj.transOptLineSeg(hs.lineSegHC).map(f)
      else None
    }

  def projFalsesHsLineSegOptMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HSide, LineSeg) => Option[B])(implicit build: MapBuilderArr[B, ArrB]): ArrB =
  { val buff = build.newBuff()
    proj.gChild.sidesForeach { hs =>
      if (!apply(hs)(proj.parent))
        proj.transOptLineSeg(hs.lineSegHC).foreach{ls => f(hs, ls).foreach(build.buffGrow(buff, _) )}
    }
    build.buffToSeqLike(buff)
  }

  /** Projection OptMaps over the [[HSide]] and [[LineSeg]] of eahc false [[HSide]] link. */
  def projFalseLinksHsLineSegOptMap[B, ArrB <: Arr[B]](f: (HSide, LineSeg) => Option[B])(
    implicit proj: HSysProjection, build: MapBuilderArr[B, ArrB]): ArrB = projFalseLinksHsLineSegOptMap(proj)(f)(build)

  /** Projection OptMaps over the [[HSide]] and [[LineSeg]] of eahc false [[HSide]] link. */
  def projFalseLinksHsLineSegOptMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (HSide, LineSeg) => Option[B])(implicit build: MapBuilderArr[B, ArrB]): ArrB = {
    val buff = build.newBuff()
    proj.gChild.linksForeach { hs =>
      if (!apply(hs)(proj.parent))
        proj.transOptLineSeg(hs.lineSegHC).foreach { ls => f(hs, ls).foreach(build.buffGrow(buff, _)) }
    }
    build.buffToSeqLike(buff)
  }

  def set(hs: HSide, value: Boolean)(implicit grid: HGridSys): Unit = {
    val i = grid.sideLayerArrayIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def set(r: Int, c: Int, value: Boolean)(implicit grid: HGridSys): Unit = { unsafeArray(grid.sideLayerArrayIndex(r, c)) = value }
  def setTrues(hSides: HSideArr)(implicit grid: HGridSys): Unit = hSides.foreach(r => unsafeArray(grid.sideLayerArrayIndex(r)) = true)
  def setTrues(hSides: HSide*)(implicit grid: HGridSys): Unit = hSides.foreach(r => unsafeArray(grid.sideLayerArrayIndex(r)) = true)
  def setTruesPairs(hSidePairs: (Int, Int)*)(implicit grid: HGridSys): Unit = hSidePairs.foreach(p => unsafeArray(grid.sideLayerArrayIndex(p._1, p._2)) = true)

  def setTruesInts(hSideInts: Int*)(implicit grid: HGridSys): Unit ={
    val len = hSideInts.length / 2
    iUntilForeach(0, len * 2, 2){ i =>
      val index = grid.sideLayerArrayIndex(hSideInts(i), hSideInts(i + 1))
      unsafeArray(index) = true
    }
  }

  /** Spawns a new [[HSideBoolLayer]] data layer for the child [[HGridSys]]. */
  def spawn(parentGridSys: HGridSys, childGridSys: HGridSys): HSideBoolLayer =
  { val array: Array[Boolean] = new Array[Boolean](childGridSys.numSides)
    childGridSys.sidesForeach { sc => array(childGridSys.sideLayerArrayIndex(sc)) = apply(sc)(parentGridSys) }
    new HSideBoolLayer(array)
  }
}

object HSideBoolLayer
{ /** Factory apply method for [[HSideBoolean]] takes an [[HGridSys]] as an implicit parameter. */
  def apply()(implicit gridSys: HGridSys): HSideBoolLayer = new HSideBoolLayer(new Array[Boolean](gridSys.numSides))
}