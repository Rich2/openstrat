/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Data layer for [[HSide]]s of an [[HGridSys]]. */
class HSideLayer[A <: HSideGeom](val unsafeArray: Array[A]) extends HSideLayerAny[A]
{ /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(hs: HSide)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(hs))

  /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(r, c))

  def projPolyLineMap[B, ArrB <: Arr[B]](proj: HSysProjection, corners: HCornerLayer)(f: LineSeg => B)(implicit build: ArrMapBuilder[B, ArrB]): ArrB =
  {
    ???
  }
}
