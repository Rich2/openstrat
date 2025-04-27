/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** A [[PairElem]] where A1 is an [[HStep]]. */
class HStepPair[A2](val a1Int1: Int, val a2: A2) extends PairInt1Elem[HStep, A2]
{ override def a1: HStep = HStep.fromInt(a1Int1)
}

/** A specialised [[Arr]] class for [[HStepPair]]s. */
class HStepPairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends ArrPairInt1[HStep, HStepArr, A2, HStepPair[A2]]
{ override type ThisT = HStepPairArr[A2]
  override def typeStr: String = "HDirnPairArr"
  override def elemFromInt(int1: Int, a2: A2): HStepPair[A2] = new HStepPair[A2](int1, a2)
  override def a1FromInt(int1: Int): HStep = HStep.fromInt(int1)
  override def newFromArrays(newA1Array: Array[Int], newA2Array: Array[A2]): HStepPairArr[A2] = new HStepPairArr[A2](a1ArrayInt, newA2Array)
  override def a1Arr: HStepArr = new HStepArr(a1ArrayInt)
  override def fElemStr: HStepPair[A2] => String = _.toString
}

object HStepPairArr1{
  def unapply[A](inp: Any): Option[(HStep, A)] = inp match{
    case ha: HStepPairArr[_] if ha.length == 1 => Some((ha.a1Index(0), ha.a2Index(0).asInstanceOf[A]))
    case _ => None
  }
}

/** A [[Buff]] class for [[HStepPair]]s.  */
class HStepPairBuff[A2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[A2]) extends BuffPairInt1[HStep, A2, HStepPair[A2]]
{ override type ThisT = HStepPairBuff[A2]
  override def typeStr: String = "HDirnPairBuff"
  override def elemFromInt(int1: Int, a2: A2): HStepPair[A2] = new HStepPair[A2](int1, a2)
}

/** An [[BuilderArrMap]] for [[HStepPair]]s. */
class HStepPairArrMapBuilder[B2](implicit val b2ClassTag: ClassTag[B2]) extends BuilderArrPairIn1Map[HStep, HStepArr, B2, HStepPair[B2], HStepPairArr[B2]]
{ override type BuffT = HStepPairBuff[B2]
  override type B1BuffT = HStepBuff
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): HStepPairBuff[B2] = new HStepPairBuff[B2](a1Buffer, a2Buffer)
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): HStepPairArr[B2] = new HStepPairArr[B2](b1ArrayInt, b2Array)
  override def b1ArrBuilder: BuilderArrMap[HStep, HStepArr] = HStep.arrMapBuildEv
  override def newB1Buff(): HStepBuff = HStepBuff()
}