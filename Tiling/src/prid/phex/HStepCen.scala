/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

/** Hex centre origin and hex step. */
class HStepCen(val stepInt: Int, val r2: Int, val c2: Int) extends ElemInt3
{ def step: HStep = HStep.fromInt(stepInt)

  /** The Starting hex centre. */
  def endHC: HCen = HCen(r2, c2)

  override def int1: Int = stepInt
  override def int2: Int = r2
  override def int3: Int = c2
}

object HStepCen
{ def apply(step: HStep, hCen: HCen): HStepCen = new HStepCen(step.intValue, hCen.r, hCen.c)
  def apply(step: HStep, r: Int, c: Int): HStepCen = new HStepCen(step.intValue, r, c)
}

class HStepCens(val unsafeArray: Array[Int]) extends ArrInt3s[HStepCen]
{  override type ThisT = HStepCens
  override def dataElem(i1: Int, i2: Int, i3: Int): HStepCen = new HStepCen(i1, i2, i3)
  override def unsafeFromArray(array: Array[Int]): HStepCens = new HStepCens(array)
  override def fElemStr: HStepCen => String = _.toString
  override def typeStr: String = "HStepCens"
}