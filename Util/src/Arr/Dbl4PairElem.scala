/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

trait Dbl4PairElem[A1 <: Dbl4Elem, A2] extends DblNPairElem[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
  def a1Dbl3: Double
  def a1Dbl4: Double
}

trait Dbl4PairArr[A1 <: Dbl4Elem, ArrA1 <: Dbl4Arr[A1], A2, A <: Dbl4PairElem[A1, A2]] extends DblNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Dbl4PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def newPair(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, a2: A2): A

  def newA1(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double): A1
  final override def a1Index(index: Int): A1 = newA1(a1ArrayDbl(index * 4), a1ArrayDbl(index * 4 + 1), a1ArrayDbl(index * 4 + 2), a1ArrayDbl(index * 4 + 3))

  override final def apply(index: Int): A =
    newPair(a1ArrayDbl(index * 4), a1ArrayDbl(index * 4 + 1), a1ArrayDbl(index * 4 + 2), a1ArrayDbl(index * 4 + 3), a2Array(index))

  final override def unsafeSetA1(index: Int, value: A1): Unit = { a1ArrayDbl(index * 4) = value.dbl1; a1ArrayDbl(index * 4 + 1) = value.dbl2
    a1ArrayDbl(index * 4 + 2) = value.dbl3; a1ArrayDbl(index * 4 + 3) = value.dbl4 }

  override final def unsafeSetElem(i: Int, newValue: A): Unit =
  { a1ArrayDbl(i * 4) = newValue.a1Dbl1;
    a1ArrayDbl(i * 4 + 1) = newValue.a1Dbl2
    a1ArrayDbl(i * 4 + 2) = newValue.a1Dbl3
    a1ArrayDbl(i * 4 + 3) = newValue.a1Dbl4
    a2Array(i) = newValue.a2
  }

  override def a1NumDbl: Int = 4

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Double](a1ArrayLength + 4)
    a1ArrayDbl.copyToArray(newA1Array)
    newA1Array(a1ArrayLength) = a1.dbl1
    newA1Array(a1ArrayLength + 1) = a1.dbl2
    newA1Array(a1ArrayLength + 2) = a1.dbl3
    newA1Array(a1ArrayLength + 3) = a1.dbl4
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait Dbl4PairBuff[B1 <: Dbl4Elem, B2, B <: Dbl4PairElem[B1, B2]] extends DblNPairBuff[B1, B2, B]
{ /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def newElem(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, a2: B2): B

  inline final override def apply(index: Int): B =
    newElem(b1DblBuffer (index * 4), b1DblBuffer(index * 4 + 1), b1DblBuffer(index * 4 + 2), b1DblBuffer(index * 4 + 3), b2Buffer(index))

  override final def grow(newElem: B): Unit =
  { b1DblBuffer.append(newElem.a1Dbl1)
    b1DblBuffer.append(newElem.a1Dbl2)
    b1DblBuffer.append(newElem.a1Dbl3)
    b1DblBuffer.append(newElem.a1Dbl4)
    b2Buffer.append(newElem.a2)
  }

  override final def unsafeSetElem(i: Int, newValue: B): Unit =
  { b1DblBuffer(i * 4) = newValue.a1Dbl1
    b1DblBuffer(i * 4 + 1) = newValue.a1Dbl2
    b1DblBuffer(i * 4 + 2) = newValue.a1Dbl3
    b1DblBuffer(i * 4 + 3) = newValue.a1Dbl4
    b2Buffer(i) = newValue.a2
  }
}

trait Dbl4PairArrCommonBuilder[B1 <: Dbl4Elem, ArrB1 <: Dbl4Arr[B1], B2, ArrB <: Dbl4PairArr[B1, ArrB1, B2, _]] extends
DblNPAirArrCommonBuilder[B1, ArrB1, B2, ArrB]
{ type BuffT <: Dbl4PairBuff[B1, B2, _]

}

trait Dbl4PairArrMapBuilder[B1 <: Dbl4Elem, ArrB1 <: Dbl4Arr[B1], B2, B <: Dbl4PairElem[B1, B2], ArrB <: Dbl4PairArr[B1, ArrB1, B2, B]] extends
Dbl4PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with  DblNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Dbl4PairBuff[B1, B2, B]
  override type B1BuffT <: Dbl4Buff[B1]
  final override def a1DblNum: Int = 4

  final override def indexSet(seqLike: ArrB, index: Int, value: B): Unit = {
    seqLike.a1ArrayDbl(index * 4) = value.a1Dbl1
    seqLike.a1ArrayDbl(index * 4 + 1) = value.a1Dbl2
    seqLike.a1ArrayDbl(index * 4 + 2) = value.a1Dbl3
    seqLike.a1ArrayDbl(index * 4 + 3) = value.a1Dbl4
    seqLike.a2Array(index) = value.a2
  }
}

trait Dbl4PairArrFlatBuilder[B1 <: Dbl4Elem, ArrB1 <: Dbl4Arr[B1], B2, ArrB <: Dbl4PairArr[B1, ArrB1, B2, _]] extends
  Dbl4PairArrCommonBuilder[B1, ArrB1, B2, ArrB] with DblNPairArrFlatBuilder[B1, ArrB1, B2, ArrB]

trait Dbl4PairArrCompanion[A1 <: Dbl4Elem, ArrA1 <: Dbl4Arr[A1]] extends DblNPairArrCompanion[A1, ArrA1]
{
  override def elemNumDbls: Int = 4

  def seqToArrays[A2](pairs: Seq[Dbl4PairElem[_, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
  {  val dblsArray = new Array[Double](pairs.length * 4)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      dblsArray(i * 4) = p.a1Dbl1
      dblsArray(i * 4 + 1) = p.a1Dbl2
      dblsArray(i * 4 + 2) = p.a1Dbl3
      dblsArray(i * 4 + 3) = p.a1Dbl4
      a2Array(i) = p.a2
      i += 1
    }
    (dblsArray, a2Array)
  }
}