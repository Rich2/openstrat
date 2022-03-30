/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex centre origin and hex step. */
class HCenStep(val r1: Int, val c1: Int, val stepInt: Int) extends ElemInt3
{ /** The Starting hex centre. */
  def hc1: HCen = HCen(r1, c1)
  def step: HStep = HStep.fromInt(stepInt)
  /** The destination hex centre. */
  def endHC(implicit grider: HGrider): HCen = HCen(r1 + step.r, c1 + step.c)

  override def int1: Int = r1
  override def int2: Int = c1
  override def int3: Int = stepInt
}

object HCenStep
{ def apply(hCen: HCen, step: HStep): HCenStep = new HCenStep(hCen.r, hCen.c, step.intValue)
  def apply(r: Int, c: Int, step: HStep): HCenStep = new HCenStep(r, c, step.intValue)
}

class HCenStepArr(val unsafeArray: Array[Int]) extends Int3Arr[HCenStep]
{ override type ThisT = HCenStepArr
  override def typeStr: String = "HCenStepArr"
  override def sdElem(i1: Int, i2: Int, i3: Int): HCenStep = new HCenStep(i1, i2, i3)
  override def unsafeFromArray(array: Array[Int]): HCenStepArr = new HCenStepArr(array)
  override def fElemStr: HCenStep => String = _.toString
}

object HCenStepArr extends Int3SeqDefCompanion[HCenStep, HCenStepArr]
{ override def fromArray(array: Array[Int]): HCenStepArr = new HCenStepArr(array)
}