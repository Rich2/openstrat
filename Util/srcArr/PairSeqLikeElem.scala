/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, reflect.ClassTag

/** A [[PairElem]] whose first component is a [[SeqLike]]. */
trait PairSeqLikeElem[A1E, A1 <: SeqLike[A1E], A2] extends PairFinalA1Elem[A1, A2] with SpecialT
{ def a1: A1
  def a2: A2
}

/** An [[Arr]] of [[PairElem]]s where the first component of each [[PairElem]] is a [[SeqLike]]. Stored in 2 [[Array]]s for efficiency. */
trait ArrPairSeqLike[A1E, A1 <: SeqLike[A1E], A1Arr <: Arr[A1], A2, A <: PairSeqLikeElem[A1E, A1, A2]] extends ArrPairFinalA1[A1, A1Arr, A2, A]

/** A [[Buff]] of [[PairElem]]s where the first component of each [[PairElem]] is a [[SeqLike]], stored in 2 [[ArrayBuffer]]s for efficiency. */
trait BuffPairSeqLike[A1E, A1 <: SeqLike[A1E], A2, A <: PairSeqLikeElem[A1E, A1, A2]] extends BuffPair[A1, A2, A]
{ def b2Buffer: ArrayBuffer[A2]  
}

/** An [[Builder]] */
trait BuilderArrMapPairSeqLike[B1E, B1 <: SeqLike[B1E], ArrB1 <: Arr[B1], B2, B <: PairSeqLikeElem[B1E, B1, B2], ArrB <: ArrPairFinalA1[B1, ArrB1, B2, B]] extends
  BuilderArrPairMap[B1, ArrB1, B2, B, ArrB]
{ /** Builder for the first element of the pair of type B1. This method will need to be overwritten to a narrow type. */
  def b1Builder: BuilderSeqLikeMap[B1E, B1]
}

trait SeqLikeDblNPairElem[A1E <: DblNElem, A1 <: SeqLikeDblNImut[A1E], A2] extends PairSeqLikeElem[A1E, A1, A2]
{ /** The backing Array of Doubles for the A1 [[SeqLikeDblNImut]]. */
  def a1ArrayDbl: Array[Double]
}

trait SeqLikeDblNPairArr[A1E <: DblNElem, A1 <: SeqLikeDblNImut[A1E], A1Arr <: Arr[A1], A2, A <: PairSeqLikeElem[A1E, A1, A2]] extends
  ArrPairSeqLike[A1E, A1, A1Arr, A2, A]
{ type ThisT <: SeqLikeDblNPairArr[A1E, A1, A1Arr, A2, A]

  /** Backing [[Array]] for the A1 components of the pairs. In this case the elements of that array are themselves [[Array]]s of [[Double]]s. */
  def a1ArrayDbls: Array[Array[Double]]

  /** Constructs a value of type A1 from an [[Array]][Double] */
  def a1FromArrayDbl(array: Array[Double]): A1

  /** Constructs a new instance of the class's final type from an Array of Array[Double]s and an Array of type A2. */
  def newFromArrays(array1: Array[Array[Double]], array2: Array[A2]): ThisT

  override def a1Index(index: Int): A1 = a1FromArrayDbl(a1ArrayDbls(index))
  override def uninitialised(length: Int)(implicit classTag: ClassTag[A2]): ThisT = newFromArrays(new Array[Array[Double]](length), new Array[A2](length))
  final override def setA1Unsafe(index: Int, value: A1): Unit = { a1ArrayDbls(index) = value.arrayUnsafe }

  override def replaceA1byA2(key: A2, newValue: A1): ThisT =
  { val newA1s = new Array[Array[Double]](length)
    a1ArrayDbls.copyToArray(newA1s)
    val res = newFromArrays(newA1s, a2Array)
    var i = 0
    while (i < length) { if (key == a2Index(i)) res.setA1Unsafe(i, newValue); i += 1 }
    res
  }

  @targetName("append") final override def +%(operand: A)(implicit ct: ClassTag[A2]): ThisT = appendPair(operand.a1, operand.a2)

  final override def appendPair(a1: A1, a2: A2)(implicit ct: ClassTag[A2]): ThisT =
  { val newA1Array = new Array[Array[Double]](length + 1)
    a1ArrayDbls.copyToArray(newA1Array)
    newA1Array(length) = a1.arrayUnsafe
    val newA2Array = new Array[A2](length + 1)
    a2Array.copyToArray(newA2Array)
    newA2Array(length) = a2
    newFromArrays(newA1Array, newA2Array)
  }
}

trait SeqLikeDblNPairBuff[B1E <: DblNElem, B1 <: SeqLikeDblNImut[B1E], B2, B <: SeqLikeDblNPairElem[B1E, B1, B2]] extends BuffPairSeqLike[B1E, B1, B2, B]
{ /** Backing [[ArrayBuffer]] for the B1 components. */
  def b1Buffer: ArrayBuffer[Array[Double]]

  final override def grow(newElem: B): Unit = { b1Buffer.append(newElem.a1ArrayDbl); b2Buffer.append(newElem.a2) }

  final def growArr(newElems: SeqLikeDblNPairArr[B1E, B1, ?, B2, B]): Unit = { newElems.a1ArrayDbls.foreach(b1Buffer.append(_))
    newElems.a2Array.foreach(b2Buffer.append(_)) }

  final override def pairGrow(b1: B1, b2: B2): Unit = { b1Buffer.append(b1.arrayUnsafe); b2Buffer.append(b2) }
}

trait SeqLikeDblNPairArrBuilder[B1E <: DblNElem, B1 <: SeqLikeDblNImut[B1E], ArrB1 <: Arr[B1], B2, B <: SeqLikeDblNPairElem[B1E, B1, B2], ArrB <: ArrPairFinalA1[B1, ArrB1, B2, B]] extends
  BuilderArrMapPairSeqLike[B1E, B1, ArrB1, B2, B, ArrB]
{ type BuffT <: SeqLikeDblNPairBuff[B1E, B1, B2, B]
  type B1BuffT <: BuffArrayDbl[B1]

  /** Construct the final target [[Arr]] type from an Array of Arrays of Doubles and an Array of B2. */
  def fromArrays(arrayArrayDbl: Array[Array[Double]], a2Array: Array[B2]): ArrB

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = buff.bufferUnsafe.append(newElem.arrayUnsafe)
  final override def arrFromBuffs(b1Buff: B1BuffT, b2Buffer: ArrayBuffer[B2]): ArrB = fromArrays(b1Buff.arrayArrayDbl, b2Buffer.toArray)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = { buff.b1Buffer.append(newElem.a1ArrayDbl); buff.b2Buffer.append(newElem.a2) }
}

trait SeqLikeIntNPairElem[A1E <: IntNElem, A1 <: SeqLikeIntNImut[A1E], A2] extends PairSeqLikeElem[A1E, A1, A2] with ArrayIntBackedPair[A1, A2]

trait SeqLikeIntNPairArr[A1E <: IntNElem, A1 <: SeqLikeIntNImut[A1E], ArrA1 <: Arr[A1], A2, A <: SeqLikeIntNPairElem[A1E, A1, A2]] extends
  ArrPairSeqLike[A1E, A1, ArrA1, A2, A] with ArrayIntBackedPairArr[A1, ArrA1, A2, A]
{ type ThisT <: SeqLikeIntNPairArr[A1E, A1, ArrA1, A2, A]
}

trait SeqLikeIntNPairBuff[B1E <: IntNElem, B1 <: SeqLikeIntNImut[B1E], B2, B <: SeqLikeIntNPairElem[B1E, B1, B2]] extends BuffPairSeqLike[B1E, B1, B2, B]
{ def b1Buffer: ArrayBuffer[Array[Int]]
  final override def grow(newElem: B): Unit = { b1Buffer.append(newElem.a1ArrayInt); b2Buffer.append(newElem.a2) }

  final def growArr(newPairArr: SeqLikeIntNPairArr[B1E, B1, ?, B2, B]): Unit = { newPairArr.a1ArrayArrayInts.foreach(b1Buffer.append(_))
    newPairArr.a2Array.foreach(b2Buffer.append(_)) }

  final override def pairGrow(b1: B1, b2: B2): Unit = { b1Buffer.append(b1.arrayUnsafe); b2Buffer.append(b2) }
}

trait SeqLikeIntNPairArrBuilder[B1E <: IntNElem, B1 <: SeqLikeIntNImut[B1E], ArrB1 <: Arr[B1], B2, B <: SeqLikeIntNPairElem[B1E, B1, B2], ArrB <: ArrPairFinalA1[B1, ArrB1, B2, B]] extends
  BuilderArrMapPairSeqLike[B1E, B1, ArrB1, B2, B, ArrB]
{ type BuffT <: SeqLikeIntNPairBuff[B1E, B1, B2, B]
  type B1BuffT <: ArrayIntBuff[B1]

  /** Construct the final target [[Arr]] type from an Array of Arrays of [[Int]]s and an Array of B2. */
  def fromArrays(arrayArrayInt: Array[Array[Int]], a2Array: Array[B2]): ArrB

  final override def b1BuffGrow(buff: B1BuffT, newElem: B1): Unit = buff.unsafeBuffer.append(newElem.arrayUnsafe)
  final override def arrFromBuffs(b1Buff: B1BuffT, b2Buffer: ArrayBuffer[B2]): ArrB = fromArrays(b1Buff.arrayArrayInt, b2Buffer.toArray)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = { buff.b1Buffer.append(newElem.a1ArrayInt); buff.b2Buffer.append(newElem.a2) }
}