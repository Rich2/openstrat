/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex

import scala.collection.mutable.ArrayBuffer

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

  implicit val buildEv: Int3ArrBuilder[HStepCen, HStepCenArr] = new Int3ArrBuilder[HStepCen, HStepCenArr]{
    override type BuffT = HStepCenBuff
    override def fromIntArray(array: Array[Int]): HStepCenArr = new HStepCenArr(array)
    override def fromIntBuffer(buffer: Buff[Int]): HStepCenBuff = new HStepCenBuff(buffer)
  }
}

class HStepCenArr(val unsafeArray: Array[Int]) extends Int3Arr[HStepCen]
{ override type ThisT = HStepCenArr
  override def sdElem(i1: Int, i2: Int, i3: Int): HStepCen = new HStepCen(i1, i2, i3)
  override def unsafeFromArray(array: Array[Int]): HStepCenArr = new HStepCenArr(array)
  override def fElemStr: HStepCen => String = _.toString
  override def typeStr: String = "HStepCens"

  implicit val flatBuildEv: Int3ArrFlatBuilder[HStepCen, HStepCenArr] = new Int3ArrFlatBuilder[HStepCen, HStepCenArr]{
    override type BuffT = HStepCenBuff
    override def fromIntArray(array: Array[Int]): HStepCenArr = new HStepCenArr(array)
    override def fromIntBuffer(buffer: Buff[Int]): HStepCenBuff = new HStepCenBuff(buffer)
  }
}

class HStepCenBuff(val unsafeBuffer: ArrayBuffer[Int]) extends Int3Buff[HStepCen]
{ override type ThisT = HStepCenBuff
  override type ArrT = HStepCenArr
  override def typeStr: String = "HStepCenBuff"
  override def sdElem(i1: Int, i2: Int, i3: Int): HStepCen = new HStepCen(i1, i2, i3)
}