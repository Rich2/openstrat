/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, reflect.ClassTag

/** [[PairElem]] where the first component of the pair is a [[Dbl3Elem]]. */
trait PairDbl3Elem[A1 <: Dbl3Elem, A2] extends PairDblNElem[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
  def a1Dbl3: Double
}

/** Common trait for [[ArrPairDbl3]]s and [[BuffPairDbl3]]s. */
trait SeqLikePairDbl3[A1 <: Dbl3Elem, A2, A <: PairDbl3Elem[A1, A2]] extends SeqLikePairDblN[A1, A2, A]
{ /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def elemFromDbls(dbl1: Double, dbl2: Double, dbl3: Double, a2: A2): A

  final override def a1NumDbl: Int = 3
}

/** [[Arr]] of [[PairDbl3Elem]]s. */
trait ArrPairDbl3[A1 <: Dbl3Elem, ArrA1 <: ArrDbl3[A1], A2, A <: PairDbl3Elem[A1, A2]] extends ArrPairDblN[A1, ArrA1, A2, A], SeqLikePairDbl3[A1, A2, A]
{ type ThisT <: ArrPairDbl3[A1, ArrA1, A2, A]

  /** Constructs an object of type A1 type from 3 [[Double]]s.  */
  def a1FromDbls(dbl1: Double, dbl2: Double, dbl3: Double): A1
  
  final override def a1Index(index: Int): A1 = a1FromDbls(a1ArrayDbl(index * 3), a1ArrayDbl(index * 3 + 1), a1ArrayDbl(index * 3 + 2))
  final override def apply(index: Int): A = elemFromDbls(a1ArrayDbl(index * 3), a1ArrayDbl(index * 3 + 1), a1ArrayDbl(index * 3 + 2), a2Array(index))
  final override def elem(index: Int): A = elemFromDbls(a1ArrayDbl(index * 3), a1ArrayDbl(index * 3 + 1), a1ArrayDbl(index * 3 + 2), a2Array(index))
  final override def setA1Unsafe(index: Int, value: A1): Unit = a1ArrayDbl.setIndex3(index, value.dbl1, value.dbl2, value.dbl3)

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
  { a1ArrayDbl.setIndex3(index, newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3)
    a2Array(index) = newElem.a2
  }

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Double](a1ArrayLength + 3)
    a1ArrayDbl.copyToArray(newA1Array)
    newA1Array.setIndex3(length, a1.dbl1, a1.dbl2, a1.dbl3)
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    fromArrays(newA1Array, newA2Array)
  }
}

/** Efficient buffer for [[PairDbl3Elem]]s.  */
trait BuffPairDbl3[A1 <: Dbl3Elem, A2, A <: PairDbl3Elem[A1, A2]] extends BuffPairDblN[A1, A2, A], SeqLikePairDbl3[A1, A2, A]
{ final override def apply(index: Int): A = elemFromDbls(b1DblBuffer (index * 3), b1DblBuffer(index * 3 + 1), b1DblBuffer(index * 3 + 2), b2Buffer(index))
  final override def elem(index: Int): A = elemFromDbls(b1DblBuffer (index * 3), b1DblBuffer(index * 3 + 1), b1DblBuffer(index * 3 + 2), b2Buffer(index))

  override final def grow(newElem: A): Unit =
  { b1DblBuffer.append3(newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3)
    b2Buffer.append(newElem.a2)
  }

  override final def setElemUnsafe(index: Int, newElem: A): Unit =
  { b1DblBuffer.setIndex3(index, newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3)
    b2Buffer(index) = newElem.a2
  }
}

/** Constructs [[ArrPairDbl3]] objects via map method. */
trait BuilderArrPairDbl3[B1 <: Dbl3Elem, ArrB1 <: ArrDbl3[B1], B2, B <: PairDbl3Elem[B1, B2], ArrB <: ArrPairDbl3[B1, ArrB1, B2, B]] extends
  BuilderArrPairDblNMap[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffPairDbl3[B1, B2, B]
  override type B1BuffT <: Dbl3Buff[B1]
  final override def a1DblNum: Int = 3

  final override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
  { seqLike.a1ArrayDbl.setIndex3(index, newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3)
    seqLike.a2Array(index) = newElem.a2
  }
}

/** Companion object helper trait for constructing [[ArrPairDbl3]] objects. */
trait CompanionArrPairDbl3[A1 <: Dbl3Elem, ArrA1 <: ArrDbl3[A1]] extends CompanionArrPairDblN[A1, ArrA1]
{ override def elemNumDbls: Int = 3

  def seqToArrays[A2](pairs: Seq[PairDbl3Elem[?, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
  {  val dblsArray = new Array[Double](pairs.length * 3)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      dblsArray.setIndex3(i, p.a1Dbl1, p.a1Dbl2, p.a1Dbl3)
      a2Array(i) = p.a2
      i += 1
    }
    (dblsArray, a2Array)
  }
}