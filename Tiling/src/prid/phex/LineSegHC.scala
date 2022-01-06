/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._

/** A 2d line upon a HexGrid defined by its start and end [[HGrid]] [[HCoord]]s. */
case class LineSegHC(r1: Int, c1: Int, r2: Int, c2: Int) extends LineSegLike[HCoord] //extends ProdInt4
{
  def _1: Int = r1
  def _2: Int = c1
  def _3: Int = r2
  def _4: Int = c2

  /** The start [[HCoord]] point. */
  def startPt: HCoord = HCoord(r1, c1)

  /** The end [[HCoord]] point. */
  def endPt: HCoord = HCoord(r2, c2)

  def lineSeg: LineSeg = LineSeg(startPt.toPt2, endPt.toPt2)
}

object LineSegHC
{ /** Factory apply method to create a hex coordinate line segment a [[LineSegHC]] from the start and end hex coordinates [[HCoord]]s. */
  def apply(hCoord1: HCoord, hCoord2: HCoord): LineSegHC = new LineSegHC(hCoord1.r, hCoord1.c, hCoord2.r, hCoord2.c)
}