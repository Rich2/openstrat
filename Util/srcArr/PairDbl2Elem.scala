/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, reflect.ClassTag

/** [[PairElem]] where the first component of the pair is a [[Dbl2Elem]]. */
trait PairDbl2Elem[A1 <: Dbl2Elem, A2] extends PairDblNElem[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
}

/** Common trait for [[ArrPairDbl2]]s and [[BuffPairDbl2]]s. */
trait SeqLikePairDbl2[A1 <: Dbl2Elem, A2, A <: PairDbl2Elem[A1, A2]] extends SeqLikePairDblN[A1, A2, A]
{ /** Constructs new pair element from 2 [[Double]]s. */
  def elemFromDbls(dbl1: Double, dbl2: Double, a2: A2): A

  final override def a1NumDbl: Int = 2
}

/** An [[Arr]] of [[PairElem]]s where the first component of the pairs is a [[Dbl2Elem]]. */
trait ArrPairDbl2[A1 <: Dbl2Elem, ArrA1 <: ArrDbl2[A1], A2, A <: PairDbl2Elem[A1, A2]] extends ArrPairDblN[A1, ArrA1, A2, A], SeqLikePairDbl2[A1, A2, A]
{ type ThisT <: ArrPairDbl2[A1, ArrA1, A2, A]
  final override def apply(index: Int): A = elemFromDbls(a1ArrayDbl(index * 2), a1ArrayDbl(index * 2 + 1), a2Array(index))
  final override def elem(index: Int): A = elemFromDbls(a1ArrayDbl(index * 2), a1ArrayDbl(index * 2 + 1), a2Array(index))

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
  { a1ArrayDbl.setIndex2(index, newElem.a1Dbl1, newElem.a1Dbl2)
    a2Array(index) = newElem.a2
  }

  def newA1(dbl1: Double, dbl2: Double): A1
  final override def a1Index(index: Int): A1 = newA1(a1ArrayDbl(index * 2), a1ArrayDbl(index * 2 + 1))  
  final override def setA1Unsafe(index: Int, value: A1): Unit = a1ArrayDbl.setIndex2(index, value.dbl1, value.dbl2)

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Double](a1ArrayLength + 2)
    a1ArrayDbl.copyToArray(newA1Array)
    newA1Array.setIndex2(length, a1.dbl1, a1.dbl2)
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

/** Efficient buffer for [[PairDbl2Elem]]s. */
trait BuffPairDbl2[A1 <: Dbl2Elem, A2, A <: PairDbl2Elem[A1, A2]] extends BuffPairDblN[A1, A2, A], SeqLikePairDbl2[A1, A2, A]
{ final override def apply(index: Int): A = elemFromDbls(b1DblBuffer (index * 2), b1DblBuffer(index * 2 + 1), b2Buffer(index))
  final override def elem(index: Int): A = elemFromDbls(b1DblBuffer (index * 2), b1DblBuffer(index * 2 + 1), b2Buffer(index))

  final override def grow(newElem: A): Unit =
  { b1DblBuffer.append2(newElem.a1Dbl1, newElem.a1Dbl2)
    b2Buffer.append(newElem.a2)
  }

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
  { b1DblBuffer.setIndex2(index, newElem.a1Dbl1, newElem.a1Dbl2)
    b2Buffer(index) = newElem.a2
  }
}

/** Builder for [[ArrPairDbl2]]s via the map A => PairB method. */
trait BuilderArrPairDbl2Map[B1 <: Dbl2Elem, ArrB1 <: ArrDbl2[B1], B2, B <: PairDbl2Elem[B1, B2], ArrB <: ArrPairDbl2[B1, ArrB1, B2, B]] extends
  BuilderArrPairDblNMap[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffPairDbl2[B1, B2, B]
  override type B1BuffT <: BuffDbl2[B1]
  final override def a1DblNum: Int = 2

  final override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
  { seqLike.a1ArrayDbl.setIndex2(index, newElem.a1Dbl1, newElem.a1Dbl2)
    seqLike.a2Array(index) = newElem.a2
  }
}

/** Helper trait for Companion objects of [[ArrPairDbl2]] classes. */
trait CompanionArrPairDbl2[A1 <: Dbl2Elem, ArrA1 <: ArrDbl2[A1]]
{
  def seqToArrays[A2](pairs: Seq[PairDbl2Elem[?, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
  { val dblsArray = new Array[Double](pairs.length * 2)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      dblsArray.setIndex2(i, p.a1Dbl1, p.a1Dbl2)
      a2Array(i) = p.a2
      i += 1
    }
    (dblsArray, a2Array)
  }
}