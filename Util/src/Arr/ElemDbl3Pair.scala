/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

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

trait Dbl3PairBuff[B1 <: ElemDbl3, B2, B <: ElemDbl3Pair[B1, B2]] extends DblNPairBuff[B1, B2, B]
{ /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def newElem(dbl1: Double, dbl2: Double, dbl3: Double, a2: B2): B

  inline final override def apply(index: Int): B = newElem(b1DblBuffer (index * 3), b1DblBuffer(index * 3 + 1), b1DblBuffer(index * 3 + 2), b2Buffer(index))

  override final def grow(newElem: B): Unit =
  { b1DblBuffer.append(newElem.a1Dbl1)
    b1DblBuffer.append(newElem.a1Dbl2)
    b1DblBuffer.append(newElem.a1Dbl3)
    b2Buffer.append(newElem.a2)
  }

  override final def unsafeSetElem(i: Int, value: B): Unit =
  { b1DblBuffer(i * 3) = value.a1Dbl1
    b1DblBuffer(i * 3 + 1) = value.a1Dbl2
    b1DblBuffer(i * 3 + 2) = value.a1Dbl3
    b2Buffer(i) = value.a2
  }
}

trait Dbl3PairArrMapBuilder[B1 <: ElemDbl3, ArrB1 <: Dbl3Arr[B1], B2, B <: ElemDbl3Pair[B1, B2], ArrB <: Dbl3PairArr[B1, ArrB1, B2, B]] extends
  DblNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Dbl3PairBuff[B1, B2, B]
  override type B1BuffT <: Dbl3Buff[B1]
  final override def a1DblNum: Int = 3

  final override def indexSet(seqLike: ArrB, index: Int, value: B): Unit = {
    seqLike.a1ArrayDbl(index * 3) = value.a1Dbl1
    seqLike.a1ArrayDbl(index * 3 + 1) = value.a1Dbl2
    seqLike.a1ArrayDbl(index * 3 + 2) = value.a1Dbl3
    seqLike.a2Array(index) = value.a2
  }
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
}