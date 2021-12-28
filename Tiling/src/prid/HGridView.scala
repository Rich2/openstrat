/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._

class HGridView(val r: Int, val c: Int, val pxScale: Double)
{
  def hcoord: HCoord = HCoord(r, c)
  def vec: Vec2 = hcoord.toVec
}

object HGridView
{
  def apply(r: Int, c: Int, pxScale: Double = 50): HGridView = new HGridView(r, c, pxScale)
  def apply(hCoord: HCoord, pxScale: Double): HGridView = new HGridView(hCoord.r, hCoord.c, pxScale)
}