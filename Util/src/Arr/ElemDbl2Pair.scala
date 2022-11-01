/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

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
  inline final override def apply(index: Int): A = newElem(b1DblBuffer (index * 2), b1DblBuffer(index * 2 + 1), b2Buffer(index))

  override final def grow(newElem: A): Unit =
  { b1DblBuffer.append(newElem.a1Dbl1)
    b1DblBuffer.append(newElem.a1Dbl2)
    b2Buffer.append(newElem.a2)
  }

  override final def unsafeSetElem(i: Int, value: A): Unit =
  { b1DblBuffer(i * 3) = value.a1Dbl1
    b1DblBuffer(i * 3 + 1) = value.a1Dbl2
    b2Buffer(i) = value.a2
  }
}

trait Dbl2PairArrMapBuilder[B1 <: ElemDbl2, ArrB1 <: Dbl2Arr[B1], B2, B <: ElemDbl2Pair[B1, B2], ArrB <: Dbl2PairArr[B1, ArrB1, B2, B]] extends
  DblNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
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