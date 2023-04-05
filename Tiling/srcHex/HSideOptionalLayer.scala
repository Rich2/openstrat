/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** Data layer for [[HSide]]s of an [[HGridSys]]. */
class HSideOptionalLayer[A, SA <: HSideSome](val unsafeArray: Array[A]) extends HSideLayerAny[A]
{ /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(hs: HSide)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(hs))

  /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(r, c))

  /** Maps over the respective [[HSide]] and [[Polygon]]s of the Some values, but does not use the value's themselves. */
  def somesHsPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (HSide, Polygon) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
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

  /** Maps over the Some values with their respective [[Polygon]]s. */
  def somePolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (SA, Polygon) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          sa: HSideSome => {
          val poly = corners.sideVerts(hs).project(proj)
          Some(f(sa.asInstanceOf[SA], poly))
        }
        case _ => None
      }
    }

  /** Maps over the Some values with their respective [[HSide]] and [[Polygon]]s. */
  def someSCPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (SA, HSide, Polygon) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          sa: HSideSome =>
        { val poly = corners.sideVerts(hs).project(proj)
          Some(f(sa.asInstanceOf[SA], hs, poly))
        }
        case _ => None
      }
    }
}