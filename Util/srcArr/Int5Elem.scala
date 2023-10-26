/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 5 [[Int]]s. These are used in [[Int5Arr]] Array[Int] based collections. */
trait Int5Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int
  def int5: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3); f(int4); f(int5) }

  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit = { buffer.append(int1); buffer.append(int2); buffer.append(int3)
    buffer.append(int4); buffer.append(int5) }
}

trait Int5SeqLike[A <: Int5Elem] extends Any with SeqLikeIntN[A]
{ final override def elemProdSize: Int = 5

  def newElem(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): A

  override def setElemUnsafe(index: Int, newElem: A): Unit =
    unsafeArray.setIndex5(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)
}

trait Int5SeqSpec[A <: Int5Elem] extends Any with Int5SeqLike[A] with SeqSpecIntN[A]
{
  final def ssElemEq(a1: A, a2: A): Boolean =
    (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4) & (a1.int5 == a2.int5)

  override def ssIndex(index: Int): A =
    newElem(unsafeArray(5 * index), unsafeArray(5 * index + 1), unsafeArray(5 * index + 2), unsafeArray(5 * index + 3), unsafeArray(5 * index + 4))
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int5Elem]]s. */
trait Int5Arr[A <: Int5Elem] extends Any with Int5SeqLike[A] with ArrIntN[A]
{ final override def length: Int = unsafeArray.length / 5

  override def apply(index: Int): A =
    newElem(unsafeArray(5 * index), unsafeArray(5 * index + 1), unsafeArray(5 * index + 2), unsafeArray(5 * index + 3), unsafeArray(5 * index + 4))

  def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4) & (a1.int5 == a2.int5)

  def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  def head3: Int = unsafeArray(2)
  def head4: Int = unsafeArray(3)
  def head5: Int = unsafeArray(4)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](unsafeLength + 5)
    unsafeArray.copyToArray(newArray)
    newArray.setIndex5(length, operand.int1, operand.int2, operand.int3, operand.int4, operand.int5)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int5Elem]]s collections. */
trait Int5Buff[A <: Int5Elem] extends Any with IntNBuff[A]
{ type ThisT <: Int5Buff[A]

  /** Constructs a new element of this [[Buff]] from 5 [[Int]]s. */
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): A

  override def elemProdSize: Int = 5
  final override def length: Int = unsafeBuffer.length / 5
  final override def grow(newElem: A): Unit = unsafeBuffer.append5(newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)

  final override def apply(index: Int): A =
    newElem(unsafeBuffer(index * 5), unsafeBuffer(index * 5 + 1), unsafeBuffer(index * 5 + 2), unsafeBuffer(index * 5 + 3), unsafeBuffer(index * 5 + 4))

  final override def setElemUnsafe(i: Int, newElem: A): Unit =
    unsafeBuffer.setIndex5(i, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)
}

trait Int5SeqLikeCommonBuilder[BB <: Int5SeqLike[_]] extends IntNSeqLikeCommonBuilder[BB]
{ type BuffT <: Int5Buff[_]
  final override def elemProdSize: Int = 5
}

trait Int5SeqLikeMapBuilder[B <: Int5Elem, BB <: Int5SeqLike[B]] extends Int5SeqLikeCommonBuilder[BB] with IntNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Int5Buff[B]

  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit =
    seqLike.unsafeArray.setIndex5(index, elem.int1, elem.int2, elem.int3, elem.int4, elem.int5)

  final override def buffGrow(buff: BuffT, newElem: B): Unit =
    buff.unsafeBuffer.append5(newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)
}

/** Trait for creating the ArrTBuilder type class instances for [[Int5Arr]] final classes. Instances for the [[MapBuilderArr]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of [[Int5Elem]],
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int5ArrMapBuilder[B <: Int5Elem, ArrB <: Int5Arr[B]] extends Int5SeqLikeMapBuilder[B, ArrB] with IntNArrMapBuilder[B, ArrB]

trait Int5ArrFlatBuilder[ArrB <: Int5Arr[_]] extends Int5SeqLikeCommonBuilder[ArrB] with IntNArrFlatBuilder[ArrB]

/** Class for the singleton companion objects of [[Int5Arr]] final classes to extend. */
abstract class Int5ArrCompanion[A <: Int5Elem, M <: Int5Arr[A]] extends IntNSeqLikeCompanion[A, M]
{ final override def elemNumInts: Int = 5

  def buff(initialSize: Int): Int5Buff[A]

  final def apply(elems: A*): M =
  { val res = uninitialised(elems.length)
    var i: Int = 0
    while (i < elems.length)
    { res.unsafeArray.setIndex5(i, elems(i).int1, elems(i).int2, elems(i).int3, elems(i).int4, elems(i / 5).int5)
      i += 1
    }
    res
  }
}