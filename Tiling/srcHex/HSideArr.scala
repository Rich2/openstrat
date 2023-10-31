/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** An efficient array[Int] based collection for [[HSide]]s hex grid centre coordinates. */
class HSideArr(val unsafeArray: Array[Int]) extends AnyVal with ArrInt2[HSide]
{ type ThisT = HSideArr
  override def newElem(int1: Int, int2: Int): HSide = HSide(int1, int2)

  override def fromArray(array: Array[Int]): HSideArr = new HSideArr(array)

  override def typeStr: String = "HSides"

  override def fElemStr: HSide => String = _ => "Not implemented"
}

/** Companion object for [[HSideArr]] trait efficient array[Int] based collection for [[HSide]]s hex grid centre coordinates, contains factory apply and
 *  uninitialised methods. */
object HSideArr extends CompanionSeqLikeInt2[HSide, HSideArr]
{ override def fromArray(array: Array[Int]): HSideArr = new HSideArr(array)

  /** Implicit [[Show]] type class instance / evidence for [[HSideArr]]. */
  implicit val showEv: ShowSequ[HSide, HSideArr] = ShowSequ[HSide, HSideArr]()

  /** Implicit [[Unshow]] type class instance / evidence for [[HSideArr]]. */
  implicit val unshowEv: UnshowArrIntN[HSide, HSideArr] = UnshowArrIntN[HSide, HSideArr](fromArray)

  /** Implicit flatMap builder instance / evidence for [[HSideArr]]. */
  implicit val flatBuilderEv: BuilderArrFlat[HSideArr] = new Int2ArrFlatBuilder[HSideArr]
  { type BuffT = HSideBuff
    override def fromIntArray(array: Array[Int]): HSideArr = new HSideArr(array)
    override def fromIntBuffer(buffer: ArrayBuffer[Int]): HSideBuff = new HSideBuff(buffer)
  }
}

class HSideBuff(val unsafeBuffer: ArrayBuffer[Int] = BufferInt()) extends AnyVal with BuffInt2[HSide]
{ type ArrT = HSideArr
  override def typeStr: String = "HSideBuff"
  override def newElem(i1: Int, i2: Int): HSide = HSide(i1, i2)
}

object HSideBuff
{ def apply(length: Int = 4): HSideBuff = new HSideBuff(new ArrayBuffer[Int](length * 2))
}

class HSidePairArr[A2](val a1ArrayInt: Array[Int], val a2Array: Array[A2]) extends ArrPairInt2[HSide, HSideArr, A2, HSidePair[A2]]
{ override type ThisT = HSidePairArr[A2]
  override def typeStr: String = "HSidePairArr"
  override def newFromArrays(newA1Array: Array[Int], newA2Array: Array[A2]): HSidePairArr[A2] = new HSidePairArr[A2](newA1Array, newA2Array)
  override def newPair(int1: Int, int2: Int, a2: A2): HSidePair[A2] = new HSidePair[A2](int1, int2, a2)
  override def newA1(int1: Int, int2: Int): HSide = HSide(int1, int2)
  override def a1Arr: HSideArr = new HSideArr(a1ArrayInt)
  override def fElemStr: HSidePair[A2] => String = _.toString
  def hSideArr: HSideArr = new HSideArr(a1ArrayInt)
  def headHSide: HSide = HSide(a1ArrayInt(0), a1ArrayInt(1))
}

object HSidePairArr1
{
  def unapply[A](inp: HSidePairArr[A]): Option[(HSide, A)] = inp match{
    case ha if ha.length == 1 => Some((ha.a1Index(0), ha.a2Index(0)))
    case _ => None
  }
}


class HSidePairBuff[B2](val b1IntBuffer: ArrayBuffer[Int], val b2Buffer: ArrayBuffer[B2]) extends BuffPairInt2[HSide, B2, HSidePair[B2]]
{ override type ThisT = HSidePairBuff[B2]
  override def typeStr: String = "HSidePairBuff"
  override def newElem(int1: Int, int2: Int, a2: B2): HSidePair[B2] = new HSidePair[B2](int1, int2, a2)
}

class HSidePairArrMapBuilder[B2](implicit ct: ClassTag[B2]) extends Int2PairArrMapBuilder[HSide, HSideArr, B2, HSidePair[B2], HSidePairArr[B2]]
{ override type BuffT = HSidePairBuff[B2]
  override type B1BuffT = HSideBuff
  override implicit val b2ClassTag: ClassTag[B2] = ct
  override def buffFromBuffers(a1Buffer: ArrayBuffer[Int], a2Buffer: ArrayBuffer[B2]): HSidePairBuff[B2] = new HSidePairBuff[B2](a1Buffer, a2Buffer)
  override def b1ArrBuilder: BuilderArrMap[HSide, HSideArr] = HSide.arrMapBuilderEv
  override def arrFromArrays(b1ArrayInt: Array[Int], b2Array: Array[B2]): HSidePairArr[B2] = new HSidePairArr[B2](b1ArrayInt, b2Array)
  override def newB1Buff(): HSideBuff = HSideBuff()
}