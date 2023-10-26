/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Int]]s. These are used in [[SeqSpecInt3]] based collections. */
trait Int3Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit = { buffer.append(int1); buffer.append(int2); buffer.append(int3) }
}

/** Sequence like class that has [[Int3Elem]]s as its elements or its specifying sequence. */
trait SeqLikeInt3[A <: Int3Elem] extends Any with SeqLikeIntN[A]
{
  def newElem(i1: Int, i2: Int, i3: Int): A

  override def elemProdSize: Int = 3
  final override def setElemUnsafe(index: Int, newElem: A): Unit = unsafeArray.setIndex3(index, newElem.int1, newElem.int2, newElem.int3)
}

/** A specialised immutable, flat Array[Double] based trait defined by a data sequence of a type of [[Int3Elem]]s. */
trait SeqSpecInt3[A <: Int3Elem] extends Any with SeqLikeInt3[A] with SeqSpecIntN[A]
{ final override def ssIndex(index: Int): A = newElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))
  final override def ssElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3)
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int3Elem]]s. */
trait ArrInt3[A <: Int3Elem] extends Any with ArrIntN[A] with SeqLikeInt3[A]
{ def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  def head3: Int = unsafeArray(2)
  final override def length: Int = unsafeArray.length / 3

  override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3)
  final override def apply(index: Int): A = newElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))

  @targetName("append") final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](unsafeLength + 3)
    unsafeArray.copyToArray(newArray)
    newArray.setIndex3(length, operand.int1, operand.int2, operand.int3)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int3Elem]]s collections. */
trait Int3Buff[A <: Int3Elem] extends Any with BuffIntN[A]
{ type ThisT <: Int3Buff[A]

  /** Constructs a new element of this [[Buff]] from 3 [[Int]]s. */
  def newElem(i1: Int, i2: Int, i3: Int): A

  override def elemProdSize: Int = 3
  final override def length: Int = unsafeBuffer.length / 3
  override def grow(newElem: A): Unit = unsafeBuffer.append3(newElem.int1, newElem.int2, newElem.int3)
  override def apply(index: Int): A = newElem(unsafeBuffer(index * 3), unsafeBuffer(index * 3 + 1), unsafeBuffer(index * 3 + 2))
  override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer.setIndex3(i, newElem.int1, newElem.int2, newElem.int3)
}

trait Int3SeqLikeCommonBuilder[BB <: SeqLikeInt3[_]] extends BuilderSeqLikeIntN[BB]
{ type BuffT <: Int3Buff[_]
  final override def elemProdSize: Int = 3
}

trait Int3SeqLikeMapBuilder[B <: Int3Elem, BB <: SeqLikeInt3[B]] extends Int3SeqLikeCommonBuilder[BB] with BuilderSeqLikeIntNMap[B, BB]
{ type BuffT <: Int3Buff[B]
  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit = seqLike.unsafeArray.setIndex3(index, elem.int1, elem.int2, elem.int3)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.unsafeBuffer.append3(newElem.int1, newElem.int2, newElem.int3)
}

trait Int3SeqLikeFlatBuilder[BB <: SeqLikeInt3[_]] extends Int3SeqLikeCommonBuilder[BB] with BuilderSeqLikeIntNFlat[BB]

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt3]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int3Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int3ArrMapBuilder[B <: Int3Elem, ArrB <: ArrInt3[B]] extends Int3SeqLikeMapBuilder[B, ArrB] with BuilderArrIntNMap[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrInt3]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[BuilderArrFlat] should go in the companion
 *  object the ArrT final class. The first type parameter is called B a sub class of Int3Elem, because to corresponds to the B in the
 *  ```map(f: A => B): ArrB``` function. */
trait Int3ArrFlatBuilder[ArrB <: ArrInt3[_]] extends Int3SeqLikeCommonBuilder[ArrB] with BuilderArrIntNFlat[ArrB]

/** Helper class for companion objects of final [[SeqSpecInt3]] classes. */
abstract class Int3SeqLikeCompanion[A <: Int3Elem, ArrA <: SeqLikeInt3[A]] extends CompanionSeqLikeIntN[A, ArrA]
{ override def elemNumInts: Int = 3

  /** Apply factory method for constructing [[SeqLike]] objects from [[Int3Elem]]s. */
  final def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length * 3
    val res = uninitialised(elems.length)
    var i: Int = 0

    while (i < elems.length)
    { res.unsafeArray.setIndex3(i, elems(i).int1, elems(i).int2, elems(i).int3)
      i += 1
    }
    res
  }
}