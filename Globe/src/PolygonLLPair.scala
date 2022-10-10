/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import geom._

class PolygonLLPair[A2](val unsafeArray: Array[Double], val a2: A2) extends PolygonDblsPair[LatLong, PolygonLL, A2]{
  override def polygon: PolygonLL = new PolygonLL(unsafeArray)
}