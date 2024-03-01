/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, reflect.ClassTag

/** [[PairElem]] where the first component of the pair is a [[Dbl4Elem]]. */
trait PairDbl4Elem[A1 <: Dbl4Elem, A2] extends PairDblNElem[A1, A2]
{ def a1Dbl1: Double
  def a1Dbl2: Double
  def a1Dbl3: Double
  def a1Dbl4: Double
}

/** [[Arr]] of [[PairDbl4]] elements. */
trait ArrPairDbl4[A1 <: Dbl4Elem, ArrA1 <: Dbl4Arr[A1], A2, A <: PairDbl4Elem[A1, A2]] extends ArrPairDblN[A1, ArrA1, A2, A]
{ type ThisT <: ArrPairDbl4[A1, ArrA1, A2, A]

  /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def newPair(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, a2: A2): A

  def newA1(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double): A1
  final override def a1Index(index: Int): A1 = newA1(a1ArrayDbl(index * 4), a1ArrayDbl(index * 4 + 1), a1ArrayDbl(index * 4 + 2), a1ArrayDbl(index * 4 + 3))

  override final def apply(index: Int): A =
    newPair(a1ArrayDbl(index * 4), a1ArrayDbl(index * 4 + 1), a1ArrayDbl(index * 4 + 2), a1ArrayDbl(index * 4 + 3), a2Array(index))

  final override def setA1Unsafe(index: Int, value: A1): Unit = a1ArrayDbl.setIndex4(index, value.dbl1, value.dbl2, value.dbl3, value.dbl4)

  override final def setElemUnsafe(i: Int, newElem: A): Unit =
  { a1ArrayDbl.setIndex4(i, newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3, newElem.a1Dbl4)
    a2Array(i) = newElem.a2
  }

  override def a1NumDbl: Int = 4

  @targetName("append") final def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Double](a1ArrayLength + 4)
    a1ArrayDbl.copyToArray(newA1Array)
    newA1Array.setIndex4(length, a1.dbl1, a1.dbl2, a1.dbl3, a1.dbl4)
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

/** Efficient buffer class for [[PairDbl4]] elements. */
trait BuffPairDbl4[B1 <: Dbl4Elem, B2, B <: PairDbl4Elem[B1, B2]] extends BuffPairDblN[B1, B2, B]
{ /** Constructs new pair element from 3 [[Double]]s and a third parameter of type A2. */
  def newElem(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, a2: B2): B

  inline final override def apply(index: Int): B =
    newElem(b1DblBuffer (index * 4), b1DblBuffer(index * 4 + 1), b1DblBuffer(index * 4 + 2), b1DblBuffer(index * 4 + 3), b2Buffer(index))

  override final def grow(newElem: B): Unit =
  { b1DblBuffer.append4(newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3, newElem.a1Dbl4)
    b2Buffer.append(newElem.a2)
  }

  override final def setElemUnsafe(i: Int, newElem: B): Unit =
  { b1DblBuffer.setIndex4(i, newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3, newElem.a1Dbl4)
    b2Buffer(i) = newElem.a2
  }
}

/** Common trait for builders of [[ArrPairDbl4]] objects via the map and flatMap methods. */
trait BuilderArrPairDbl4[B1 <: Dbl4Elem, ArrB1 <: Dbl4Arr[B1], B2, ArrB <: ArrPairDbl4[B1, ArrB1, B2, _]] extends
BuilderArrPairDblN[B1, ArrB1, B2, ArrB]
{ type BuffT <: BuffPairDbl4[B1, B2, ?]
}

/** Builder for [[ArrPairDbl4]] objects via the map f: A => PairB method. */
trait BuilderArrPairDbl4Map[B1 <: Dbl4Elem, ArrB1 <: Dbl4Arr[B1], B2, B <: PairDbl4Elem[B1, B2], ArrB <: ArrPairDbl4[B1, ArrB1, B2, B]] extends
BuilderArrPairDbl4[B1, ArrB1, B2, ArrB] with  BuilderArrPairDblNMap[B1, ArrB1, B2, B, ArrB]
{ type BuffT <: BuffPairDbl4[B1, B2, B]
  override type B1BuffT <: BuffDbl4[B1]
  final override def a1DblNum: Int = 4

  final override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
  { seqLike.a1ArrayDbl.setIndex4(index, newElem.a1Dbl1, newElem.a1Dbl2, newElem.a1Dbl3, newElem.a1Dbl4)
    seqLike.a2Array(index) = newElem.a2
  }
}

/** Builder for [[ArrPairDbl4]] objects via the flatMap f: A => ArrPairB method. */
trait BuilderArrPairDbl4Flat[B1 <: Dbl4Elem, ArrB1 <: Dbl4Arr[B1], B2, ArrB <: ArrPairDbl4[B1, ArrB1, B2, ?]] extends
  BuilderArrPairDbl4[B1, ArrB1, B2, ArrB] with BuilderArrPairDblNFlat[B1, ArrB1, B2, ArrB]

trait Dbl4PairArrCompanion[A1 <: Dbl4Elem, ArrA1 <: Dbl4Arr[A1]] extends CompanionArrPairDblN[A1, ArrA1]
{
  override def elemNumDbls: Int = 4

  def seqToArrays[A2](pairs: Seq[PairDbl4Elem[_, A2]])(implicit ct: ClassTag[A2]): (Array[Double], Array[A2]) =
  {  val dblsArray = new Array[Double](pairs.length * 4)
    val a2Array = new Array[A2](pairs.length)
    var i = 0
    pairs.foreach{p =>
      dblsArray.setIndex4(i, p.a1Dbl1, p.a1Dbl2, p.a1Dbl3, p.a1Dbl4)
      a2Array(i) = p.a2
      i += 1
    }
    (dblsArray, a2Array)
  }
}