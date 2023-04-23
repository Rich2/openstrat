/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Data layer for [[HSide]]s of an [[HGridSys]]. */
class HSideLayer[A](val unsafeArray: Array[A]) extends HSideLayerAny[A]
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

  def somesPolyMapAlt(proj: HSysProjection, corners: HCornerLayer)(f: (HSide, Polygon, A) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          a: HSideSome => {
          val poly = corners.sideVerts(hs).project(proj)
          Some(f(hs, poly, a))
        }
        case _ => None
      }
    }
}

object HSideLayer
{
  def apply[A](initial: A)(implicit ct: ClassTag[A], gridSys: HGridSys): HSideLayer[A] = apply[A](gridSys, initial)(ct)

  def apply[A](gridSys: HGridSys, initial: A)(implicit ct: ClassTag[A]): HSideLayer[A] =
  { val newArray = new Array[A](gridSys.numSides)
    iUntilForeach(gridSys.numSides)(newArray(_) = initial)
    new HSideLayer[A](newArray)
  }
}