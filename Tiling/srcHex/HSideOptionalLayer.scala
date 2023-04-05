/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer

/** Data layer for [[HSide]]s of an [[HGridSys]]. */
class HSideOptionalLayer[A, SA <: HSideSome](val unsafeArray: Array[A]) extends HSideLayerAny[A]
{ /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(hs: HSide)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(hs))

  /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(r, c))

  def somesPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (HSide, Polygon) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          _: HSideSome => {
          val poly = corners.sideVerts(hs).project(proj)
          Some(f(hs, poly))
        }
        case _ => None
      }
    }

  def somesPolyMapAlt(proj: HSysProjection, corners: HCornerLayer)(f: (HSide, Polygon, SA) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          a: HSideSome => {
          val poly = corners.sideVerts(hs).project(proj)
          Some(f(hs, poly, a.asInstanceOf[SA]))
        }
        case _ => None
      }
    }

  /*def midsPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (Polygon, A) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          _: HSideMid => {
          val poly =corners.sideVerts(hs).project(proj)
          Some(f(poly, apply(hs)))
        }
        case _ => None
      }
    }*/
}