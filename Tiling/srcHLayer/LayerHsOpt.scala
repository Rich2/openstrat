/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

trait LayerHsOpt
{
  type KeyT <: HCenStruct
}

/** Data layer for [[HSide]]s of an [[HGridSys]] where there is are [[HSideSome]] and [[HSideNone]] types. */
class LayerHSOptSys[A, SA <: HSideSome](val unsafeArray: Array[A]) extends HSideLayerAny[A]
{ type KeyT = HGridSys

  /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(hs: HSide)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(hs))

  /** apply index method returns the data from this layer for the given [[HSide]]. */
  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideLayerArrayIndex(r, c))

  /** Maps over the respective [[HSide]] and [[Polygon]]s of the Some values, but does not use the value's themselves. */
  def someOnlyHSPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (HSide, Polygon) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match
      { case _: HSideSome =>
        { val poly = corners.sidePoly(hs).project(proj)
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
          val poly = corners.sidePoly(hs).project(proj)
          Some(f(sa.asInstanceOf[SA], poly))
        }
        case _ => None
      }
    }

  /** Maps over the Some values with their respective [[HSide]] and [[Polygon]]s. */
  def someHSPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (SA, HSide, Polygon) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          sa: HSideSome =>
        { val poly = corners.sidePoly(hs).project(proj)
          Some(f(sa.asInstanceOf[SA], hs, poly))
        }
        case _ => None
      }
    }

  /** Spawns a new [[HSideOptlLayer]] data layer for the child [[HGridSys]] from this [[LayerHSOptSys]]. */
  def spawn(parentGridSys: HGridSys, childGridSys: HGridSys)(implicit ct: ClassTag[A]): LayerHSOptSys[A, SA] =
  { val array: Array[A] = new Array[A](childGridSys.numSides)
    childGridSys.sidesForeach { sc => array(childGridSys.sideLayerArrayIndex(sc)) = apply(sc)(parentGridSys) }
    new LayerHSOptSys[A, SA](array)
  }
}

object LayerHSOptSys
{
  def apply[A, SA <: HSideSome]()(implicit ct: ClassTag[A], defaultValue: DefaultValue[A], gridSys: HGridSys): LayerHSOptSys[A, SA] =
    apply[A, SA](gridSys, defaultValue)(ct)

  def apply[A, SA <: HSideSome](gridSys: HGridSys, defaultValue: DefaultValue[A])(implicit ct: ClassTag[A]): LayerHSOptSys[A, SA] =
  { val newArray = new Array[A](gridSys.numSides)
    iUntilForeach(gridSys.numSides)(newArray(_) = defaultValue.default)
    new LayerHSOptSys[A, SA](newArray)
  }
}