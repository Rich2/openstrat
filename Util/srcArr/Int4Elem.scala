/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Int]]s. These are used in [[Int4Arr]] Array[Int] based collections. */
trait Int4Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3); f(int4) }

  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit =
  { buffer.append(int1); buffer.append(int2); buffer.append(int3); buffer.append(int4) }
}

trait Int4SeqLike[A <: Int4Elem] extends Any with SeqLikeIntN[A]
{ final override def elemProdSize: Int = 4

  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A

  override def setElemUnsafe(index: Int, newElem: A): Unit = unsafeArray.setIndex4(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

trait Int4SeqSpec[A <: Int4Elem] extends Any with Int4SeqLike[A] with SeqSpecIntN[A]
{
  final def ssElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4)

  override def ssIndex(index: Int): A =
    newElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int4Elem]]s. */
trait Int4Arr[A <: Int4Elem] extends Any with Int4SeqLike[A] with ArrIntN[A]
{ final override def length: Int = unsafeArray.length / 4

  override def apply(index: Int): A =
    newElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))

  def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4)

  def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  def head3: Int = unsafeArray(2)
  def head4: Int = unsafeArray(3)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](unsafeLength + 4)
    unsafeArray.copyToArray(newArray)
    newArray.setIndex4(length, operand.int1, operand.int2, operand.int3, operand.int4)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int4Elem]]s collections. */
trait Int4Buff[A <: Int4Elem] extends Any with IntNBuff[A]
{ type ThisT <: Int4Buff[A]

  /** Constructs a new element of this [[Buff]] form 4 [[Int]]s. */
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A

  override def elemProdSize: Int = 4
  final override def length: Int = unsafeBuffer.length / 4
  final override def grow(newElem: A): Unit = unsafeBuffer.append4(newElem.int1, newElem.int2, newElem.int3, newElem.int4)

  final override def apply(index: Int): A = newElem(unsafeBuffer(index * 4), unsafeBuffer(index * 4 + 1), unsafeBuffer(index * 4 + 2),
    unsafeBuffer(index * 4 + 3))

  final override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer.setIndex4(i, newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

trait Int4SeqLikeCommonBuilder[BB <: Int4SeqLike[_]] extends BuilderAllSeqLikeIntN[BB]
{ type BuffT <: Int4Buff[_]
  final override def elemProdSize: Int = 4
}

trait Int4SeqLikeMapBuilder[B <: Int4Elem, BB <: Int4SeqLike[B]] extends Int4SeqLikeCommonBuilder[BB] with IntNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Int4Buff[B]

  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit =
    seqLike.unsafeArray.setIndex4(index, elem.int1, elem.int2, elem.int3, elem.int4)

  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.unsafeBuffer.append4(newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

/** Trait for creating the ArrTBuilder type class instances for [[Int4Arr]] final classes. Instances for the [[BuilderMapArr]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int4Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int4ArrMapBuilder[B <: Int4Elem, ArrB <: Int4Arr[B]] extends Int4SeqLikeMapBuilder[B, ArrB] with IntNArrMapBuilder[B, ArrB]

trait Int4ArrFlatBuilder[ArrB <: Int4Arr[_]] extends Int4SeqLikeCommonBuilder[ArrB] with IntNArrFlatBuilder[ArrB]

/** Class for the singleton companion objects of [[Int4Arr]] final classes to extend. */
abstract class Int4ArrCompanion[A <: Int4Elem, M <: Int4Arr[A]] extends CompanionSeqLikeIntN[A, M]
{ final override def elemNumInts: Int = 4

  def buff(initialSize: Int): Int4Buff[A]

  final def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = uninitialised(elems.length)
    var i: Int = 0
    while (i < elems.length)
    { res.unsafeArray.setIndex4(i, elems(i).int1, elems(i).int2, elems(i).int3, elems(i).int4)
      i += 1
    }
    res
  }
}