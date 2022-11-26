/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A hex grid step representing the starting [[HCen]] of the step as well as the [[HDirn]] singleton object itself. */
class HCenStep(val r1: Int, val c1: Int, val stepInt: Int) extends Int3Elem
{ /** The Starting hex centre. */
  def startHC: HCen = HCen(r1, c1)

  /** The [[HDirn]] singleton object. */
  def step: HDirn = HDirn.fromInt(stepInt)

  /** To be phased out as does not use the gridSys .The destination hex centre. */
  def endHCOld(implicit grider: HGridSys): HCen = HCen(r1 + step.tr, c1 + step.tc)

  /** Returns the destination [[HCen]] if one exists within the [[HGridSys]]. */
  def endHC(implicit grider: HGridSys): Option[HCen] = grider.findStepEnd(this)

  /** Returns the destination [[HCen]] if one exists within the [[HGridSys]]. */
  def lineSegHC(implicit grider: HGridSys): Option[LineSegHC] = grider.findStepEnd(this).map(LineSegHC(startHC, _))

  def projLineSeg(implicit proj: HSysProjection): Option[LineSeg] =
  { val lhc: Option[LineSegHC] = lineSegHC(proj.gChild)
    lhc.flatMap(proj.transOptLineSeg(_))
  }

  override def int1: Int = r1
  override def int2: Int = c1
  override def int3: Int = stepInt
}

object HCenStep
{ def apply(hCen: HCen, step: HDirn): HCenStep = new HCenStep(hCen.r, hCen.c, step.int1)
  def apply(r: Int, c: Int, step: HDirn): HCenStep = new HCenStep(r, c, step.int1)

  implicit val arrMapBuilderEv: Int3ArrMapBuilder[HCenStep, HCenStepArr] = new Int3ArrMapBuilder[HCenStep, HCenStepArr]{
    override type BuffT = HCenStepBuff
    override def fromIntArray(array: Array[Int]): HCenStepArr = new HCenStepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCenStepBuff = HCenStepBuff()
  }
}

class HCenStepArr(val unsafeArray: Array[Int]) extends Int3Arr[HCenStep]
{ override type ThisT = HCenStepArr
  override def typeStr: String = "HCenStepArr"
  override def newElem(int1: Int, int2: Int, int3: Int): HCenStep = new HCenStep(int1, int2, int3)
  override def fromArray(array: Array[Int]): HCenStepArr = new HCenStepArr(array)
  override def fElemStr: HCenStep => String = _.toString
}

object HCenStepArr extends Int3SeqLikeCompanion[HCenStep, HCenStepArr]
{ override def fromArray(array: Array[Int]): HCenStepArr = new HCenStepArr(array)

  implicit val flatBuildEv: Int3ArrFlatBuilder[HCenStepArr] = new Int3ArrFlatBuilder[HCenStepArr]{
    override type BuffT = HCenStepBuff
    override def fromIntArray(array: Array[Int]): HCenStepArr = new HCenStepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HCenStepBuff = new HCenStepBuff(buffer)
  }
}

class HCenStepBuff(val unsafeBuffer: ArrayBuffer[Int]) extends Int3Buff[HCenStep]
{ override type ThisT = HCenStepBuff
  override type ArrT = HCenStepArr
  override def typeStr: String = "HCenStepBuff"
  override def sdElem(i1: Int, i2: Int, i3: Int): HCenStep = new HCenStep(i1, i2, i3)
}

object HCenStepBuff
{ def apply(initLen: Int = 4) = new HCenStepBuff(new ArrayBuffer[Int](initLen * 3))
}

class HCenStepPair[A2](val a1Int1: Int, val a1Int2: Int, val a1Int3: Int, val a2: A2) extends Int3PairElem[HCenStep, A2]
{ inline def r1: Int = a1Int1
  inline def c1: Int = a1Int2
  inline def stepInt: Int = a1Int3
  override def a1: HCenStep = new HCenStep(a1Int1, a1Int2, a1Int3)
  def step: HDirn = HDirn.fromInt(a1Int3)
}

class HCenStepPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends Int3PairArr[HCenStep, HCenStepArr, A2, HCenStepPair[A2]]
{ override type ThisT = HCenStepPairArr[A2]
  override def typeStr: String = "HCenStepArr"
  override def newPair(int1: Int, int2: Int, int3: Int, a2: A2): HCenStepPair[A2] = new HCenStepPair[A2](int1, int2, int3, a2)
  override def newA1(int1: Int, int2: Int, int3: Int): HCenStep = new HCenStep(int1, int2, int3)
  override def newFromArrays(a1Array: Array[Int], a2Array: Array[A2]): HCenStepPairArr[A2] = new HCenStepPairArr[A2](a1Array, a2Array)
  override def a1Arr: HCenStepArr = new HCenStepArr(a1ArrayInt)
  override def fElemStr: HCenStepPair[A2] => String = _.toString
}

object HCenStepPairArr extends Int3PairArrCompanion[HCenStep]
{
  def pairs[A2](pairs: (HCenStep, A2)*)(implicit ct: ClassTag[A2]): HCenStepPairArr[A2] = {
    val arrays = tuplesToArrays[A2](pairs)
    new HCenStepPairArr(arrays._1, arrays._2)
  }

  def apply[A2](pairs: HCenStepPair[A2]*)(implicit ct: ClassTag[A2]): HCenStepPairArr[A2] = {
    val arrays = pairsToArrays[A2](pairs)
    new HCenStepPairArr(arrays._1, arrays._2)
  }
}

class HCenStepPairBuff[A2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[A2]) extends Int3PairBuff[HCenStep, A2, HCenStepPair[A2]]
{ override type ThisT = HCenStepPairBuff[A2]
  override def typeStr: String = "HCenStep"
  override def newElem(int1: Int, int2: Int, int3: Int, a2: A2): HCenStepPair[A2] = new HCenStepPair[A2](int1, int2, int3, a2)
}

class HCenStepPairArrMapBuilder[A2](implicit val b2ClassTag: ClassTag[A2]) extends Int3PairArrMapBuilder[HCenStep, HCenStepArr, A2, HCenStepPair[A2], HCenStepPairArr[A2]]
{ override type BuffT = HCenStepPairBuff[A2]
  override type B1BuffT = HCenStepBuff
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[A2]): HCenStepPairBuff[A2] = new HCenStepPairBuff[A2](a1Buffer, a2Buffer)
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[A2]): HCenStepPairArr[A2] = new HCenStepPairArr[A2](b1ArrayInt, b2Array)
  override def b1ArrBuilder: ArrMapBuilder[HCenStep, HCenStepArr] = HCenStep.arrMapBuilderEv
  override def newB1Buff(): HCenStepBuff = HCenStepBuff()
}