/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A hex grid step representing the starting [[SqCen]] of the step as well as the [[SqStep]] singleton object itself. */
class SqCenStep(val r1: Int, val c1: Int, val stepInt: Int) extends Int3Elem
{ /** The Starting square centre coordinate. */
  def startSC: SqCen = SqCen(r1, c1)

  /** The [[SqStep]] singleton object. */
  def step: SqStep = SqStep.fromInt(stepInt)

  /** Returns the destination [[SqCen]] if one exists within the [[SqGridSys]]. */
  def endSqC(implicit gridSys: SqGridSys): Option[SqCen] = gridSys.stepEndFind(this)

  /** Returns the destination [[SqCen]] if one exists within the [[SqGridSys]]. */
  def lineSegSqC(implicit gridSys: SqGridSys): Option[LineSegSC] = gridSys.stepEndFind(this).map(LineSegSC(startSC, _))

  def projLineSeg(implicit proj: SqSysProjection): Option[LineSeg] =
  { val lhc: Option[LineSegSC] = lineSegSqC(proj.gChild)
    lhc.flatMap(proj.transOptLineSeg(_))
  }

  override def int1: Int = r1
  override def int2: Int = c1
  override def int3: Int = stepInt
}

object SqCenStep
{ def apply(hCen: SqCen, step: SqStep): SqCenStep = new SqCenStep(hCen.r, hCen.c, step.int1)
  def apply(r: Int, c: Int, step: SqStep): SqCenStep = new SqCenStep(r, c, step.int1)

  implicit val arrMapBuilderEv: BuilderArrInt3Map[SqCenStep, SqCenStepArr] = new BuilderArrInt3Map[SqCenStep, SqCenStepArr]{
    override type BuffT = SqCenStepBuff
    override def fromIntArray(array: Array[Int]): SqCenStepArr = new SqCenStepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqCenStepBuff = SqCenStepBuff()
  }
}

class SqCenStepArr(val unsafeArray: Array[Int]) extends ArrInt3[SqCenStep]
{ override type ThisT = SqCenStepArr
  override def typeStr: String = "SqCenStepArr"
  override def newElem(int1: Int, int2: Int, int3: Int): SqCenStep = new SqCenStep(int1, int2, int3)
  override def fromArray(array: Array[Int]): SqCenStepArr = new SqCenStepArr(array)
  override def fElemStr: SqCenStep => String = _.toString
}

object SqCenStepArr extends CompanionSeqLikeInt3[SqCenStep, SqCenStepArr]
{ override def fromArray(array: Array[Int]): SqCenStepArr = new SqCenStepArr(array)

  implicit val flatBuildEv: BuilderArrInt3Flat[SqCenStepArr] = new BuilderArrInt3Flat[SqCenStepArr]{
    override type BuffT = SqCenStepBuff
    override def fromIntArray(array: Array[Int]): SqCenStepArr = new SqCenStepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): SqCenStepBuff = new SqCenStepBuff(buffer)
  }
}

class SqCenStepBuff(val unsafeBuffer: ArrayBuffer[Int]) extends BuffInt3[SqCenStep]
{ override type ThisT = SqCenStepBuff
  override type ArrT = SqCenStepArr
  override def typeStr: String = "SqCenStepBuff"
  override def newElem(i1: Int, i2: Int, i3: Int): SqCenStep = new SqCenStep(i1, i2, i3)
}

object SqCenStepBuff
{ def apply(initLen: Int = 4) = new SqCenStepBuff(new ArrayBuffer[Int](initLen * 3))
}

class SqCenStepPair[A2](val a1Int1: Int, val a1Int2: Int, val a1Int3: Int, val a2: A2) extends Int3PairElem[SqCenStep, A2]
{ inline def r1: Int = a1Int1
  inline def c1: Int = a1Int2
  inline def stepInt: Int = a1Int3
  override def a1: SqCenStep = new SqCenStep(a1Int1, a1Int2, a1Int3)
  def step: SqStep = SqStep.fromInt(a1Int3)
}

class SqCenStepPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends Int3PairArr[SqCenStep, SqCenStepArr, A2, SqCenStepPair[A2]]
{ override type ThisT = SqCenStepPairArr[A2]
  override def typeStr: String = "SqCenStepArr"
  override def newPair(int1: Int, int2: Int, int3: Int, a2: A2): SqCenStepPair[A2] = new SqCenStepPair[A2](int1, int2, int3, a2)
  override def newA1(int1: Int, int2: Int, int3: Int): SqCenStep = new SqCenStep(int1, int2, int3)
  override def newFromArrays(newA1Array: Array[Int], newA2Array: Array[A2]): SqCenStepPairArr[A2] = new SqCenStepPairArr[A2](newA1Array, newA2Array)
  override def a1Arr: SqCenStepArr = new SqCenStepArr(a1ArrayInt)
  override def fElemStr: SqCenStepPair[A2] => String = _.toString
}

object SqCenStepPairArr extends Int3PairArrCompanion[SqCenStep]
{
  def pairs[A2](pairs: (SqCenStep, A2)*)(implicit ct: ClassTag[A2]): SqCenStepPairArr[A2] = {
    val arrays = tuplesToArrays[A2](pairs)
    new SqCenStepPairArr(arrays._1, arrays._2)
  }

  def apply[A2](pairs: SqCenStepPair[A2]*)(implicit ct: ClassTag[A2]): SqCenStepPairArr[A2] = {
    val arrays = pairsToArrays[A2](pairs)
    new SqCenStepPairArr(arrays._1, arrays._2)
  }
}

class SqCenStepPairBuff[A2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[A2]) extends Int3PairBuff[SqCenStep, A2, SqCenStepPair[A2]]
{ override type ThisT = SqCenStepPairBuff[A2]
  override def typeStr: String = "SqCenStep"
  override def newElem(int1: Int, int2: Int, int3: Int, a2: A2): SqCenStepPair[A2] = new SqCenStepPair[A2](int1, int2, int3, a2)
}

class SqCenStepPairArrMapBuilder[A2](implicit val b2ClassTag: ClassTag[A2]) extends Int3PairArrMapBuilder[SqCenStep, SqCenStepArr, A2, SqCenStepPair[A2], SqCenStepPairArr[A2]]
{ override type BuffT = SqCenStepPairBuff[A2]
  override type B1BuffT = SqCenStepBuff
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[A2]): SqCenStepPairBuff[A2] = new SqCenStepPairBuff[A2](a1Buffer, a2Buffer)
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[A2]): SqCenStepPairArr[A2] = new SqCenStepPairArr[A2](b1ArrayInt, b2Array)
  override def b1ArrBuilder: BuilderArrMap[SqCenStep, SqCenStepArr] = SqCenStep.arrMapBuilderEv
  override def newB1Buff(): SqCenStepBuff = SqCenStepBuff()
}