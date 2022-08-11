/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** Hex grid system projection. */
trait HSysProjection
{ type GridT <: HGridSys
  def gridSys: GridT
  def sides: LineSegArr
}