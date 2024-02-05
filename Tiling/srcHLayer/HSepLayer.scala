/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

/** Data layer for [[HSep]]s of an [[HGridSys]]. */
class HSepLayer[A](val unsafeArray: Array[A]) extends HSepLayerAny[A]
{ /** apply index method returns the data from this layer for the given [[HSep]]. */
  def apply(hs: HSep)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sepLayerArrayIndex(hs))

  /** apply index method returns the data from this layer for the given [[HSep]]. */
  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sepLayerArrayIndex(r, c))

  def somesPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (HSep, Polygon) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          _: HSideSome => {
          val poly = corners.sidePoly(hs).project(proj)
          Some(f(hs, poly))
        }
        case _ => None
      }
    }

  def somesPolyMapAlt(proj: HSysProjection, corners: HCornerLayer)(f: (HSep, Polygon, A) => GraphicElem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          a: HSideSome => {
          val poly = corners.sidePoly(hs).project(proj)
          Some(f(hs, poly, a))
        }
        case _ => None
      }
    }

  /** Spawns a new [[HSepLayer]] data layer for the child [[HGridSys]] from this [[HSepLayer]]. */
  def spawn(parentGridSys: HGridSys, childGridSys: HGridSys)(implicit ct: ClassTag[A]): HSepLayer[A] =
  { val array: Array[A] = new Array[A](childGridSys.numSides)
    childGridSys.sepsForeach { sc => array(childGridSys.sepLayerArrayIndex(sc)) = apply(sc)(parentGridSys) }
    new HSepLayer[A](array)
  }
}

object HSepLayer
{
  def apply[A](initial: A)(implicit ct: ClassTag[A], gridSys: HGridSys): HSepLayer[A] = apply[A](gridSys, initial)(ct)

  def apply[A](gridSys: HGridSys, initial: A)(implicit ct: ClassTag[A]): HSepLayer[A] =
  { val newArray = new Array[A](gridSys.numSides)
    iUntilForeach(gridSys.numSides)(newArray(_) = initial)
    new HSepLayer[A](newArray)
  }
}