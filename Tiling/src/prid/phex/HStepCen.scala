/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer

/** Hex centre origin and hex step. */
class HStepCen(val stepInt: Int, val r2: Int, val c2: Int) extends ElemInt3
{ def step: HDirn = HDirn.fromInt(stepInt)

  /** The Starting hex centre. */
  def endHC: HCen = HCen(r2, c2)

  override def int1: Int = stepInt
  override def int2: Int = r2
  override def int3: Int = c2

  override def equals(obj: Any): Boolean = obj match {
    case hsc: HStepCen => stepInt == hsc.stepInt & r2 == hsc.r2 & c2 == hsc.c2
    case _ => false
  }
}

object HStepCen
{ def apply(step: HDirn, hCen: HCen): HStepCen = new HStepCen(step.intValue, hCen.r, hCen.c)
  def apply(step: HDirn, r: Int, c: Int): HStepCen = new HStepCen(step.intValue, r, c)

  implicit val buildEv: Int3ArrBuilder[HStepCen, HStepCenArr] = new Int3ArrBuilder[HStepCen, HStepCenArr]{
    override type BuffT = HStepCenBuff
    override def fromIntArray(array: Array[Int]): HStepCenArr = new HStepCenArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HStepCenBuff = new HStepCenBuff(buffer)
  }
}

class HStepCenArr(val unsafeArray: Array[Int]) extends Int3Arr[HStepCen]
{ override type ThisT = HStepCenArr
  override def sdElem(int1: Int, int2: Int, int3: Int): HStepCen = new HStepCen(int1, int2, int3)
  override def fromArray(array: Array[Int]): HStepCenArr = new HStepCenArr(array)
  override def fElemStr: HStepCen => String = _.toString
  override def typeStr: String = "HStepCens"
}

object HStepCenArr extends  Int3SeqLikeCompanion[HStepCen, HStepCenArr]
{ override def fromArray(array: Array[Int]): HStepCenArr = new HStepCenArr(array)

  /** Apply factory method */
  def apply(elems: (HDirn, Int, Int)*): HStepCenArr =
  { val arrLen: Int = elems.length * 3
    val array: Array[Int] = new Array[Int](arrLen)
    var count: Int = 0

    while (count < arrLen)
    { array(count) = elems(count / 3)._1.intValue
      count += 1
      array(count) = elems(count / 3)._2
      count += 1
      array(count) = elems(count / 3)._3
      count += 1
    }
    new HStepCenArr(array)
  }

  implicit val flatBuildEv: Int3ArrFlatBuilder[HStepCen, HStepCenArr] = new Int3ArrFlatBuilder[HStepCen, HStepCenArr]{
    override type BuffT = HStepCenBuff
    override def fromIntArray(array: Array[Int]): HStepCenArr = new HStepCenArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HStepCenBuff = new HStepCenBuff(buffer)
  }
}

class HStepCenBuff(val unsafeBuffer: ArrayBuffer[Int]) extends Int3Buff[HStepCen]
{ override type ThisT = HStepCenBuff
  override type ArrT = HStepCenArr
  override def typeStr: String = "HStepCenBuff"
  override def sdElem(i1: Int, i2: Int, i3: Int): HStepCen = new HStepCen(i1, i2, i3)
}