/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid

trait HGridBased extends Any {
  /** Boolean. True if the [[HCen]] hex centre exists in this hex grid. */
  final def hCenExists(hc: HCen): Boolean = hCenExists(hc.r, hc.c)

  /** Boolean. True if the specified hex centre exists in this hex grid. */
  def hCenExists(r: Int, c:Int): Boolean
}
