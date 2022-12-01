/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

trait Dbl2PairElem[A1 <: Dbl2Elem, A2] extends DblNPairElem[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
}

trait Dbl2PairArr[A1 <: Dbl2Elem, ArrA1 <: Dbl2Arr[A1], A2, A <: Dbl2PairElem[A1, A2]] extends DblNPairArr[A1, ArrA1, A2, A]
{ type ThisT <: Dbl2PairArr[A1, ArrA1, A2, A]

  /** Constructs new pair element from 2 [[Double]]s and a third parameter of type A2. */
  def newPair(dbl1: Double, dbl2: Double, a2: A2): A

  final override def apply(index: Int): A = newPair(a1ArrayDbl(index * 2), a1ArrayDbl(index * 2 + 1), a2Array(index))

  final override def unsafeSetElem(i: Int, newElem: A): Unit = { a1ArrayDbl(i * 2) = newElem.a1Dbl1; a1ArrayDbl(i * 2 + 1) = newElem.a1Dbl2
    a2Array(i) = newElem.a2 }

  def newA1(dbl1: Double, dbl2: Double): A1
  final override def a1Index(index: Int): A1 = newA1(a1ArrayDbl(index * 3), a1ArrayDbl(index * 3 + 1))
  final override def a1NumDbl: Int = 2
  final override def unsafeSetA1(index: Int, value: A1): Unit = { a1ArrayDbl(index * 2) = value.dbl1; a1ArrayDbl(index * 2 + 1) = value.dbl2 }

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Double](a1ArrayLength + 2)
    a1ArrayDbl.copyToArray(newA1Array)
    newA1Array(a1ArrayLength) = a1.dbl1
    newA1Array(a1ArrayLength + 1) = a1.dbl2
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait Dbl2PairBuff[A1 <: Dbl2Elem, A2, A <: Dbl2PairElem[A1, A2]] extends DblNPairBuff[A1, A2, A]
{ /** Constructs new pair element from 2 [[Double]]s and a third parameter of type A2. */
  def newElem(dbl1: Double, dbl2: Double, a2: A2): A
  inline final override def apply(index: Int): A = newElem(b1DblBuffer (index * 2), b1DblBuffer(index * 2 + 1), b2Buffer(index))

  override final def grow(newElem: A): Unit =
  { b1DblBuffer.append(newElem.a1Dbl1)
    b1DblBuffer.append(newElem.a1Dbl2)
    b2Buffer.append(newElem.a2)
  }

  override final def unsafeSetElem(i: Int, newElem: A): Unit =
  { b1DblBuffer(i * 3) = newElem.a1Dbl1
    b1DblBuffer(i * 3 + 1) = newElem.a1Dbl2
    b2Buffer(i) = newElem.a2
  }
}

trait Dbl2PairArrMapBuilder[B1 <: Dbl2Elem, ArrB1 <: Dbl2Arr[B1], B2, B <: Dbl2PairElem[B1, B2], ArrB <: Dbl2PairArr[B1, ArrB1, B2, B]] extends
  DblNPairArrMapBuilder[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: Dbl2PairBuff[B1, B2, B]
  override type B1BuffT <: Dbl2Buff[B1]
  final override def a1DblNum: Int = 2

  final override def indexSet(seqLike: ArrB, index: Int, elem: B): Unit = {
    seqLike.a1ArrayDbl(index * 3) = elem.a1Dbl1
    seqLike.a1ArrayDbl(index * 3 + 1) = elem.a1Dbl2
    seqLike.a2Array(index) = elem.a2
  }
}

trait Dbl2PairArrCompanion[A1 <: Dbl2Elem, ArrA1 <: Dbl2Arr[A1]] //extends DblNPairArrCompanion[A1, ArrA1]
{
  def seqToArrays[A2](pairs: Seq[Dbl2PairElem[_, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
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