/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

class HSideOptLayer[A <: AnyRef](val unsafeArray: Array[A])
{
  def setTruesInts(value: A, hSideInts: Int*)(implicit grid: HGridSys): Unit =
  {
    val len = hSideInts.length / 2
    iUntilForeach(0, len * 2, 2) { i =>
      val index = grid.sideLayerArrayIndex(hSideInts(i), hSideInts(i + 1))
      unsafeArray(index) = value
    }
  }
}