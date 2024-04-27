/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import math._, collection.mutable.ArrayBuffer, reflect.ClassTag

trait PtLength3 extends PointDbl3
{ type ThisT <: PtLength3

  /** Number of metres in the X component of this point. */
  def xMetresNum: Double

  /** Number of metres in the Y component of this point. */
  def yMetresNum: Double

  /** Number of metres in the Z component of this point. */
  def zMetresNum: Double

  /** Number of metres in the X component of this point. */
  def xKilometresNum: Double

  /** Number of metres in the Y component of this point. */
  def yKilometresNum: Double

  /** Number of metres in the Z component of this point. */
  def zKilometresNum: Double

  def / (operator: Length): Pt3

  def lineSegTo(endPt: PtLength3): LineSegLength3[? <: PtLength3]
  def lineSegFrom(startPt: PtLength3): LineSegLength3[? <: PtLength3]
}