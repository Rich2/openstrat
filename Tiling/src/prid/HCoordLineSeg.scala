/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

/** A 2d line upon a HexGrid defined by its start and end [[HGrid]] [[HCoord]]s. */
case class HCoordLineSeg(r1: Int, c1: Int, r2: Int, c2: Int) //extends ProdInt4
{
  def _1: Int = r1
  def _2: Int = c1
  def _3: Int = r2
  def _4: Int = c2

  def coord1: HCoord = HCoord(r1, c1)
  def coord2: HCoord = HCoord(r2, c2)
  def lineSeg: LineSeg = LineSeg(coord1.toPt2, coord2.toPt2)
}

object HCoordLineSeg
{ /** Factory apply method to create a hex coordinate line segment a [[HCoordLineSeg]] from the start and end hex coordinates [[HCoord]]s. */
  def apply(hCoord1: HCoord, hCoord2: HCoord): HCoordLineSeg = new HCoordLineSeg(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)
}