/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A system of [[HGrid]]s mapped to a 2D flat plane. */
trait HGridMultiFlat extends HGridMulti with HGridSysFlat
{
  override def polygons: Arr[Polygon] = gridMans.flatMap(m => m.grid.polygons.slate(m.offset))
  override def activeTiles: Arr[PolygonActive] = gridMans.flatMap{m => m.grid.map{ hc => hc.polygonReg.slate(m.offset).active(hc)}}
}