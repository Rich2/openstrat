/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom.*, reflect.ClassTag

/** Data layer for [[HSep]]s of an [[HGridSys]]. */
class HSepLayer[A](val unsafeArray: Array[A]) extends HSepLayerAny[A]
{ /** apply index method returns the data from this layer for the given [[HSep]]. */
  def apply(hs: HSep)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sepLayerArrayIndex(hs))

  /** apply index method returns the data from this layer for the given [[HSep]]. */
  def apply(gridSys: HGridSys, hs:HSep): A = unsafeArray(gridSys.sepLayerArrayIndex(hs))

  /** apply index method returns the data from this layer for the given [[HSep]]. */
  def apply(r: Int, c: Int)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sepLayerArrayIndex(r, c))

  def somesPolyMap(proj: HSysProjection, corners: HCornerLayer)(f: (HSep, Polygon) => Graphic2Elem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          _: HSepSome => {
          val poly = corners.sepPoly(hs).project(proj)
          Some(f(hs, poly))
        }
        case _ => None
      }
    }

  def somesPolyMapAlt(proj: HSysProjection, corners: HCornerLayer)(f: (HSep, Polygon, A) => Graphic2Elem)(implicit gridSys: HGridSys): GraphicElems =
    proj.sidesOptMap { hs =>
      apply(hs) match {
        case
          a: HSepSome => {
          val poly = corners.sepPoly(hs).project(proj)
          Some(f(hs, poly, a))
        }
        case _ => None
      }
    }

  /** Spawns a new [[HSepLayer]] data layer for the child [[HGridSys]] from this [[HSepLayer]]. */
  def spawn(parentGridSys: HGridSys, childGridSys: HGridSys)(implicit ct: ClassTag[A]): HSepLayer[A] =
  { val array: Array[A] = new Array[A](childGridSys.numSeps)
    childGridSys.sepsForeach { sc => array(childGridSys.sepLayerArrayIndex(sc)) = apply(parentGridSys, sc) }
    new HSepLayer[A](array)
  }
}

object HSepLayer
{
  def apply[A](initial: A)(using ctA: ClassTag[A], gridSys: HGridSys): HSepLayer[A] = apply[A](gridSys, initial)

  def apply[A](gridSys: HGridSys, initial: A)(implicit ct: ClassTag[A]): HSepLayer[A] =
  { val newArray = new Array[A](gridSys.numSeps)
    iUntilForeach(gridSys.numSeps)(newArray(_) = initial)
    new HSepLayer[A](newArray)
  }
}