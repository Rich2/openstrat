/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** An efficient array[Int] based collection for [[HSep]]s hex grid centre coordinates. */
class HSepArr(val unsafeArray: Array[Int]) extends AnyVal with ArrInt2[HSep]
{ type ThisT = HSepArr
  override def newElem(int1: Int, int2: Int): HSep = HSep(int1, int2)

  override def fromArray(array: Array[Int]): HSepArr = new HSepArr(array)

  override def typeStr: String = "HSides"

  override def fElemStr: HSep => String = _ => "Not implemented"
}

/** Companion object for [[HSepArr]] trait efficient array[Int] based collection for [[HSep]]s hex grid centre coordinates, contains factory apply and
 *  uninitialised methods. */
object HSepArr extends CompanionSeqLikeInt2[HSep, HSepArr]
{ override def fromArray(array: Array[Int]): HSepArr = new HSepArr(array)

  /** Implicit flatMap builder instance / evidence for [[HSepArr]]. */
  implicit val flatBuilderEv: BuilderArrFlat[HSepArr] = new BuilderArrInt2Flat[HSepArr]
  { type BuffT = HSepBuff
    override def fromIntArray(array: Array[Int]): HSepArr = new HSepArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HSepBuff = new HSepBuff(buffer)
  }

  /** Implicit [[Show]] type class instance / evidence for [[HSepArr]]. */
  implicit lazy val showEv: ShowSequ[HSep, HSepArr] = ShowSequ[HSep, HSepArr]()

  /** Implicit [[Unshow]] type class instance / evidence for [[HSepArr]]. */
  implicit lazy val unshowEv: UnshowSeq[HSep, HSepArr] = UnshowSeq[HSep, HSepArr]()
}

/** Efficient buffer class for [[HSep]]s. */
class HSepBuff(val unsafeBuffer: ArrayBuffer[Int] = BufferInt()) extends AnyVal with BuffInt2[HSep]
{ type ArrT = HSepArr
  override def typeStr: String = "HSideBuff"
  override def newElem(i1: Int, i2: Int): HSep = HSep(i1, i2)
}

object HSepBuff
{ /** Factory apply method for creating empty [[HSepBuff]] instances. Efficent buffer class for [[HSep]]s. */
  def apply(length: Int = 4): HSepBuff = new HSepBuff(new ArrayBuffer[Int](length * 2))
}

/** [[ArrPair]] class for [[HSep]]s. */
class HSepArrPair[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends ArrPairInt2[HSep, HSepArr, A2, HSepPair[A2]]
{ override type ThisT = HSepArrPair[A2]
  override def typeStr: String = "HSidePairArr"
  override def newFromArrays(newA1Array: Array[Int], newA2Array: Array[A2]): HSepArrPair[A2] = new HSepArrPair[A2](newA1Array, newA2Array)
  override def newPair(int1: Int, int2: Int, a2: A2): HSepPair[A2] = new HSepPair[A2](int1, int2, a2)
  override def newA1(int1: Int, int2: Int): HSep = HSep(int1, int2)
  override def a1Arr: HSepArr = new HSepArr(a1ArrayInt)
  override def fElemStr: HSepPair[A2] => String = _.toString
  def hSideArr: HSepArr = new HSepArr(a1ArrayInt)
  def headHSide: HSep = HSep(a1ArrayInt(0), a1ArrayInt(1))
}

object HSepPairArr1
{
  def unapply[A](inp: HSepArrPair[A]): Option[(HSep, A)] = inp match{
    case ha if ha.length == 1 => Some((ha.a1Index(0), ha.a2Index(0)))
    case _ => None
  }
}


class HSidePairBuff[B2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[B2]) extends BuffPairInt2[HSep, B2, HSepPair[B2]]
{ override type ThisT = HSidePairBuff[B2]
  override def typeStr: String = "HSidePairBuff"
  override def newElem(int1: Int, int2: Int, a2: B2): HSepPair[B2] = new HSepPair[B2](int1, int2, a2)
}

class HSidePairArrMapBuilder[B2](implicit ct: ClassTag[B2]) extends BuilderArrPairInt2Map[HSep, HSepArr, B2, HSepPair[B2], HSepArrPair[B2]]
{ override type BuffT = HSidePairBuff[B2]
  override type B1BuffT = HSepBuff
  override implicit val b2ClassTag: ClassTag[B2] = ct
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): HSidePairBuff[B2] = new HSidePairBuff[B2](a1Buffer, a2Buffer)
  override def b1ArrBuilder: BuilderArrMap[HSep, HSepArr] = HSep.arrMapBuilderEv
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): HSepArrPair[B2] = new HSepArrPair[B2](b1ArrayInt, b2Array)
  override def newB1Buff(): HSepBuff = HSepBuff()
}