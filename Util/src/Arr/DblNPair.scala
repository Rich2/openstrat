/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

trait ElemDblNPair[A1 <: ElemDblN, A2] extends ElemPair[A1, A2]

trait DblNPairArr[A1 <: ElemDblN, ArrA1 <: DblNArr[A1], A2, A <: ElemDblNPair[A1, A2]] extends PairArr[A1, ArrA1, A2, A]
{ type ThisT <: DblNPairArr[A1, ArrA1, A2, A]

  /** The backing Array for the first elements of the pairs. */
  def a1ArrayDbl: Array[Double]

  def newFromArrays(a1Array: Array[Double], a2Array: Array[A2]): ThisT
}

trait DblNPairBuff[A1 <: ElemDblN, A2, A <: ElemDblNPair[A1, A2]] extends PairBuff[A1, A2, A]
{ def a1DblBuffer: ArrayBuffer[Double]
  override final def grows(newElems: Arr[A]): Unit = newElems.foreach(grow)
}

trait DblNPairArrBuilder[B1 <: ElemDblN, ArrB1 <: DblNArr[B1], B2, B <: ElemDblNPair[B1, B2], ArrB <: DblNPairArr[B1, ArrB1, B2, B]] extends
  PairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: DblNPairBuff[B1, B2, B]
  type B1BuffT <: DblNBuff[B1]
  implicit def b2ClassTag: ClassTag[B2]
  def a1DblNum: Int
  def buffFromBuffers(a1Buffer: ArrayBuffer[Double], a2Buffer: ArrayBuffer[B2]): BuffT
  def arrFromArrays(a1ArrayDbl: Array[Double], a2Array: Array[B2]): ArrB
  final override def arrUninitialised(length: Int): ArrB = arrFromArrays(new Array[Double](length * a1DblNum), new Array[B2](length))
  final override def newBuff(length: Int): BuffT = buffFromBuffers(new ArrayBuffer[Double](length * a1DblNum), new ArrayBuffer[B2](length))
  inline final override def buffGrow(buff: BuffT, value: B): Unit = buff.grow(value)

  override def fromBuffs(a1Buff: B1BuffT, b2s: ArrayBuffer[B2]): ArrB = arrFromArrays(a1Buff.toArray, b2s.toArray)
}

/** Helper trait for Companion objects of [[DblNPairArr]] classes. */
trait DblNPairArrCompanion[A1 <: ElemDblN, ArrA1 <: DblNArr[A1]]
{
  /** The number of [[Double]] values that are needed to construct an element of the defining-sequence. */
  def elemNumDbls: Int
}

trait ElemDbl2Pair[A1 <: ElemDbl2, A2] extends ElemDblNPair[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
}

trait Dbl2PairArr[A1 <: ElemDbl2, ArrA1 <: Dbl2Arr[A1], A2, A <: ElemDbl2Pair[A1, A2]] extends DblNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Dbl2PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 2 [[Double]]s and a third parameter of type A2. */
  def newPair(dbl1: Double, dbl2: Double, a2: A2): A

  override final def apply(index: Int): A = newPair(a1ArrayDbl(index * 2), a1ArrayDbl(index * 2 + 1), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1ArrayDbl(i * 2) = value.a1Dbl1;
    a1ArrayDbl(i * 2 + 1) = value.a1Dbl2
    a2Array(i) = value.a2
  }

  def newA1(dbl1: Double, dbl2: Double): A1
  override def a1Index(index: Int): A1 = newA1(a1ArrayDbl(index * 3), a1ArrayDbl(index * 3 + 1))
}

trait Dbl2PairBuff[A1 <: ElemDbl2, A2, A <: ElemDbl2Pair[A1, A2]] extends DblNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 2 [[Double]]s and a third parameter of type A2. */
  def newElem(dbl1: Double, dbl2: Double, a2: A2): A
  inline final override def apply(index: Int): A = newElem(a1DblBuffer (index * 2), a1DblBuffer(index * 2 + 1), a2Buffer(index))

  override final def grow(newElem: A): Unit =
  { a1DblBuffer.append(newElem.a1Dbl1)
    a1DblBuffer.append(newElem.a1Dbl2)
    a2Buffer.append(newElem.a2)
  }

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1DblBuffer(i * 3) = value.a1Dbl1
    a1DblBuffer(i * 3 + 1) = value.a1Dbl2
    a2Buffer(i) = value.a2
  }
}

trait Dbl2PairArrBuilder[B1 <: ElemDbl2, ArrB1 <: Dbl2Arr[B1], B2, B <: ElemDbl2Pair[B1, B2], ArrB <: Dbl2PairArr[B1, ArrB1, B2, B]] extends
  DblNPairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Dbl2PairBuff[B1, B2, B]
  override type B1BuffT <: Dbl2Buff[B1]
  final override def a1DblNum: Int = 2

  final override def arrSet(arr: ArrB, index: Int, value: B): Unit = {
    arr.a1ArrayDbl(index * 3) = value.a1Dbl1
    arr.a1ArrayDbl(index * 3 + 1) = value.a1Dbl2
    arr.a2Array(index) = value.a2
  }

  override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = { buff.unsafeBuffer.append(newElem.dbl1); buff.unsafeBuffer.append(newElem.dbl2) }
}

trait Dbl2PairArrCompanion[A1 <: ElemDbl2, ArrA1 <: Dbl2Arr[A1]] extends DblNPairArrCompanion[A1, ArrA1]
{
  override def elemNumDbls: Int = 2

  def seqToArrays[A2](pairs: Seq[ElemDbl2Pair[_, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
  {  val dblsArray = new Array[Double](pairs.length * 2)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      dblsArray(i * 2) = p.a1Dbl1
      dblsArray(i * 2 + 1) = p.a1Dbl2
      a2Array(i) = p.a2
      i += 1
    }
    (dblsArray, a2Array)
  }
}

trait ElemDbl3Pair[A1 <: ElemDbl3, A2] extends ElemDblNPair[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
  def a1Dbl3: Double
}

trait Dbl3PairArr[A1 <: ElemDbl3, ArrA1 <: Dbl3Arr[A1], A2, A <: ElemDbl3Pair[A1, A2]] extends DblNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Dbl3PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def newPair(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): A

  def newA1(dbl1: Double, dbl2: Double, dbl3: Double): A1
  override def a1Index(index: Int): A1 = newA1(a1ArrayDbl(index * 3), a1ArrayDbl(index * 3 + 1), a1ArrayDbl(index * 3 + 2))

  override final def apply(index: Int): A = newPair(a1ArrayDbl(index * 3), a1ArrayDbl(index * 3 + 1), a1ArrayDbl(index * 3 + 2), a2Array(index))

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1ArrayDbl(i * 3) = value.a1Dbl1;
    a1ArrayDbl(i * 3 + 1) = value.a1Dbl2
    a1ArrayDbl(i * 3 + 2) = value.a1Dbl3
    a2Array(i) = value.a2
  }
}

trait Dbl3PairBuff[A1 <: ElemDbl3, A2, A <: ElemDbl3Pair[A1, A2]] extends DblNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def newElem(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): A

  inline final override def apply(index: Int): A = newElem(a1DblBuffer (index * 3), a1DblBuffer(index * 3 + 1), a1DblBuffer(index * 3 + 2), a2Buffer(index))

  override final def grow(newElem: A): Unit =
  { a1DblBuffer.append(newElem.a1Dbl1)
    a1DblBuffer.append(newElem.a1Dbl2)
    a1DblBuffer.append(newElem.a1Dbl3)
    a2Buffer.append(newElem.a2)
  }

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { a1DblBuffer(i * 3) = value.a1Dbl1
    a1DblBuffer(i * 3 + 1) = value.a1Dbl2
    a1DblBuffer(i * 3 + 2) = value.a1Dbl3
    a2Buffer(i) = value.a2
  }
}

trait Dbl3PairArrBuilder[B1 <: ElemDbl3, ArrB1 <: Dbl3Arr[B1], B2, B <: ElemDbl3Pair[B1, B2], ArrB <: Dbl3PairArr[B1, ArrB1, B2, B]] extends
  DblNPairArrBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Dbl3PairBuff[B1, B2, B]
  override type B1BuffT <: Dbl3Buff[B1]
  final override def a1DblNum: Int = 3

  final override def arrSet(arr: ArrB, index: Int, value: B): Unit = {
    arr.a1ArrayDbl(index * 3) = value.a1Dbl1
    arr.a1ArrayDbl(index * 3 + 1) = value.a1Dbl2
    arr.a1ArrayDbl(index * 3 + 2) = value.a1Dbl3
    arr.a2Array(index) = value.a2
  }

  override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = { buff.unsafeBuffer.append(newElem.dbl1); buff.unsafeBuffer.append(newElem.dbl2)
    buff.unsafeBuffer.append(newElem.dbl3) }
}

trait Dbl3PairArrCompanion[A1 <: ElemDbl3, ArrA1 <: Dbl3Arr[A1]] extends DblNPairArrCompanion[A1, ArrA1]
{
  override def elemNumDbls: Int = 3

  def seqToArrays[A2](pairs: Seq[ElemDbl3Pair[_, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
  {  val dblsArray = new Array[Double](pairs.length * 3)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      dblsArray(i * 3) = p.a1Dbl1
      dblsArray(i * 3 + 1) = p.a1Dbl2
      dblsArray(i * 3 + 2) = p.a1Dbl3
      a2Array(i) = p.a2
      i += 1
    }
    (dblsArray, a2Array)
  }
}//