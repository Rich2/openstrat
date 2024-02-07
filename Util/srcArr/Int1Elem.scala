/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from a single [[Int]]. These are used in [[ArrInt1]] Array[Int] based collections. */
trait Int1Elem extends Any with IntNElem
{ /* The single [[int]] value from which the final class can be constructed. */
  def int1: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]) : Unit = { buffer.append(int1) }
}

trait SeqLikeInt1[A <: Int1Elem] extends Any with SeqLikeIntN[A]
{ final override def elemProdSize: Int = 1
  final override def setElemUnsafe(index: Int, newElem: A): Unit = { arrayUnsafe(index) = newElem.int1 }
}

/** A specialised immutable, flat Array[Int] based trait defined by a data sequence of a type of [[Int1Elem]]s. */
trait SeqSpecInt1[A <: Int1Elem] extends Any with SeqLikeInt1[A] with SeqSpecIntN[A]
{ final override def ssIndex(index: Int): A = ssElem(arrayUnsafe(index))

  /** Constructs an element of the specifing sequence from an [[Int]] value. */
  def ssElem(intValue: Int): A
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int1Elem]]s. */
trait ArrInt1[A <: Int1Elem] extends Any with ArrIntN[A] with SeqLikeInt1[A]
{ final override def length: Int = arrayUnsafe.length

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](length + 1)
    arrayUnsafe.copyToArray(newArray)
    newArray(length) = operand.int1
    fromArray(newArray)
  }

  def newElem(intValue: Int): A
  final override def apply(index: Int): A = newElem(arrayUnsafe(index))
  final override def elemEq(a1: A, a2: A): Boolean = a1.int1 == a2.int1
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int1Elem]]s collections. */
trait BuffInt1[A <: Int1Elem] extends Any with BuffIntN[A]
{ type ThisT <: BuffInt1[A]

  /** Constructs a new element of this [[BuffSequ]] from a single [[Int]]. */
  def newElem(value: Int): A

  final override def length: Int = unsafeBuffer.length
  def apply(i1: Int): A = newElem(unsafeBuffer(i1))
  override def elemProdSize: Int = 1
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1); () }
  override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer(i) = newElem.int1
}

/** Base trait for constructing [[Arr]]s with [[Int1Elem]] elements via both map and flatMap methods. */
trait BuilderArrInt1[ArrB <: ArrInt1[_]] extends BuilderSeqLikeIntN[ArrB]
{ type BuffT <: BuffInt1[_]
  final override def elemProdSize: Int = 1
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt1]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to
 *  the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt1Map[A <: Int1Elem, ArrT <: ArrInt1[A]] extends BuilderArrInt1[ArrT] with BuilderArrIntNMap[A, ArrT]
{ type BuffT <: BuffInt1[A]
  final override def indexSet(seqLike: ArrT, index: Int, newElem: A): Unit =  seqLike.arrayUnsafe(index) = newElem.int1
  final override def buffGrow(buff: BuffT, newElem: A): Unit = { buff.unsafeBuffer.append(newElem.int1); () }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrInt1]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[BuilderArrFlat] should go in the companion
 *  object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrIn1Flat[ArrT <: ArrInt1[_]] extends BuilderArrInt1[ArrT] with BuilderArrIntNFlat[ArrT]

/** Helper class for companion objects of final [[SeqLikeInt1]] classes. */
trait CompanionSeqLikeInt1[A <: Int1Elem, ArrA <: SeqLikeInt1[A]] extends CompanionSeqLikeIntN[A, ArrA]
{
  final override def elemNumInts: Int = 1

  /** Apply factory method */
  final def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.arrayUnsafe(count) = elems(count).int1
      count += 1
    }
    res
  }
}