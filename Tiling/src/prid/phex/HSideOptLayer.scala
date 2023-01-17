/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

class HSideOptLayer[A <: AnyRef](val unsafeArray: Array[A])
{
  def apply(hs: HSide)(implicit gridSys: HGridSys): Option[A] = {
    val res1 = unsafeApply(hs)
    ife(res1 == null, None, Some(res1))
  }


  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): Option[A] = {
    val res1 = unsafeApply(r, c)
    ife(res1 == null, None, Some(res1))
  }

  /** Value may be null. */
  def unsafeApply(hs: HSide)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(hs))

  def unsafeApply(r: Int, c: Int)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(r, c))

  def set(hs: HSide, value: A)(implicit grid: HGridSys, ct: ClassTag[A]): Unit =
  { val i = grid.sideLayerArrayIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def set(grid: HGridSys, hs: HSide, value: A)(implicit ct: ClassTag[A]): Unit =
  { val i = grid.sideLayerArrayIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  /** Swts the [[HSide]]s specified by their Int parameters to rhe given value. */
  def setSomeInts(value: A, hSideInts: Int*)(implicit grid: HGridSys): Unit =
  { val len = hSideInts.length / 2
    iUntilForeach(0, len * 2, 2) { i =>
      val index = grid.sideLayerArrayIndex(hSideInts(i), hSideInts(i + 1))
      unsafeArray(index) = value
    }
  }

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projOptsLineSegMap[B, ArrB <: Arr[B]](f: LineSeg => B)(implicit proj: HSysProjection, build: ArrMapBuilder[B, ArrB]): ArrB =
    projOptsLineSegMap(proj)(f)(build)

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projOptsLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: LineSeg => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
    proj.gChild.sidesOptMap { hs =>
      if (unsafeApply(hs)(proj.parent) != null) proj.transOptLineSeg(hs.lineSegHC).map(f)
      else None
    }

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projOptsHsLineSegMap[B, ArrB <: Arr[B]](f: (A, LineSeg) => B)(implicit proj: HSysProjection, build: ArrMapBuilder[B, ArrB]): ArrB =
    projOptsHsLineSegMap(proj)(f)(build)

  /** Maps across all the trues in this Side Layer that exist in the projection. */
  def projOptsHsLineSegMap[B, ArrB <: Arr[B]](proj: HSysProjection)(f: (A, LineSeg) => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
    proj.gChild.sidesOptMap { hs =>
      val st = unsafeApply(hs)(proj.parent)
      if (st != null) proj.transOptLineSeg(hs.lineSegHC).map(ls => f(st, ls))
      else None
    }
}