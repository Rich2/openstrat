/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex centre origin and hex step. */
class HCenStep(val r1: Int, val c1: Int, val stepInt: Int)
{ /** The Starting hex centre. */
  def hc1: HCen = HCen(r1, c1)
  def step: HStep = HStep.fromInt(stepInt)
  /** The destination hex centre. */
  def endHC(implicit grider: HGrider): HCen = HCen(r1 + step.r, c1 + step.c)
}

object HCenStep
{ def apply(hCen: HCen, step: HStep): HCenStep = new HCenStep(hCen.r, hCen.c, step.intValue)
  def apply(r: Int, c: Int, step: HStep): HCenStep = new HCenStep(r, c, step.intValue)
}