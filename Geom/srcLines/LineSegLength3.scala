/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import collection.mutable.ArrayBuffer

trait LineSegLength3[VT <: PtLength3] extends LineSegLike[VT] with Dbl6Elem
{
  def xStartMetresNum: Double
  def yStartMetresNum: Double
  def zStartMetresNum: Double
  def xEndMetresNum: Double
  def yEndMetresNum: Double
  def zEndMetresNum: Double
}