/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import collection.mutable.ArrayBuffer, reflect.ClassTag

class LatLongPair[A2](val latMilliSecs: Double, val longMilliSecs: Double, val a2: A2) extends PointDbl2Pair[LatLong, A2]
{
  override def a1: LatLong = LatLong.milliSecs(latMilliSecs, longMilliSecs)
}

