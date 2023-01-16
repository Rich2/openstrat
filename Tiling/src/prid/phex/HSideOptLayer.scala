/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, reflect.ClassTag

class HSideOptLayer[A <: AnyRef](val unsafeArray: Array[A])
{
  /** Value may be null. */
  def unsafeApply(hs: HSide)(implicit gridSys: HGridSys): A = unsafeArray(gridSys.sideArrIndex(hs))

  def set(hs: HSide, value: A)(implicit grid: HGridSys, ct: ClassTag[A]): Unit =
  { val i = grid.sideArrIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def set(grid: HGridSys, hs: HSide, value: A)(implicit ct: ClassTag[A]): Unit =
  { val i = grid.sideArrIndex(hs)
    if (i >= unsafeArray.length) deb(s"$hs")
    unsafeArray(i) = value
  }

  def setTruesInts(value: A, hSideInts: Int*)(implicit grid: HGridSys): Unit =
  {
    val len = hSideInts.length / 2
    iUntilForeach(0, len * 2, 2) { i =>
      val index = grid.sideLayerArrayIndex(hSideInts(i), hSideInts(i + 1))
      unsafeArray(index) = value
    }
  }
}