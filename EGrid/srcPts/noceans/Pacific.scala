/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package noceans
import geom._, pglobe._, egrid._, WTiles._

object Hawaii extends EarthArea("Hawaii", 20.85 ll -156.92, oceanic)
{ val sHawaii: LatLong = 18.91 ll -155.68
  val nwHawaii: LatLong = 21.57 ll -158.28
  val nHawaii: LatLong = 21.71 ll -157.97
  val hana: LatLong = 20.75 ll -155.98
  val eHawii: LatLong = 19.51 ll -154.80

  override val polygonLL: PolygonLL = PolygonLL( sHawaii, nwHawaii, nHawaii, hana, eHawii)
}