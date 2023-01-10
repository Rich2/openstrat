/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 6 [[Int]]s. These are used in [[Int6Arr]] [[Array]][Int] based collections. */
trait Int6Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int
  def int5: Int
  def int6: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3); f(int4); f(int5); f(int6) }
}

trait Int6SeqLike[A <: Int6Elem] extends Any with IntNSeqLike[A]
{ final override def elemProdSize: Int = 6

  def newElem(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): A

  override def unsafeSetElem(index: Int, newElem: A): Unit = { unsafeArray(6 * index) = newElem.int1; unsafeArray(6 * index + 1) = newElem.int2
    unsafeArray(6 * index + 2) = newElem.int3; unsafeArray(6 * index + 3) = newElem.int4;
    unsafeArray(6 * index + 5) = newElem.int6  }

  override def intBufferAppend(buffer: ArrayBuffer[Int], elem: A): Unit = { buffer.append(elem.int1); buffer.append(elem.int2)
    buffer.append(elem.int3); buffer.append(elem.int4); buffer.append(elem.int5); buffer.append(elem.int6) }
}

trait Int6SeqSpec[A <: Int6Elem] extends Any with Int6SeqLike[A] with IntNSeqSpec[A]
{
  final def ssElemEq(a1: A, a2: A): Boolean =
    (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4) & (a1.int5 == a2.int5) & (a1.int6 == a2.int6)

  override def ssIndex(index: Int): A = newElem(unsafeArray(6 * index), unsafeArray(6 * index + 1), unsafeArray(6 * index + 2),
    unsafeArray(6 * index + 3), unsafeArray(6 * index + 4), unsafeArray(6 * index + 5))
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int5Elem]]s. */
trait Int6Arr[A <: Int6Elem] extends Any with Int6SeqLike[A] with IntNArr[A]
{ final override def length: Int = unsafeArray.length / 6

  override def apply(index: Int): A = newElem(unsafeArray(6 * index), unsafeArray(6 * index + 1), unsafeArray(6 * index + 2),
    unsafeArray(6 * index + 3), unsafeArray(6 * index + 4), unsafeArray(6 * index + 5))

  def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4) &
    (a1.int5 == a2.int5) & (a1.int6 == a2.int6)

  def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  def head3: Int = unsafeArray(2)
  def head4: Int = unsafeArray(3)
  def head5: Int = unsafeArray(4)
  def head6: Int = unsafeArray(5)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](unsafeLength + 6)
    unsafeArray.copyToArray(newArray)
    newArray(unsafeLength) = operand.int1
    newArray(unsafeLength + 1) = operand.int2
    newArray(unsafeLength + 2) = operand.int3
    newArray(unsafeLength + 3) = operand.int4
    newArray(unsafeLength + 4) = operand.int5
    newArray(unsafeLength + 5) = operand.int6
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int5Elem]]s collections. */
trait Int6Buff[A <: Int6Elem] extends Any with IntNBuff[A]
{ override def elemProdSize: Int = 6
  final override def length: Int = unsafeBuffer.length / 6

  final override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1).append(newElem.int2).append(newElem.int3).append(newElem.int4).
    append(newElem.int5).append(newElem.int6); () }

  def intsToElem(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): A

  final override def apply(index: Int): A = intsToElem(unsafeBuffer(index * 6), unsafeBuffer(index * 6 + 1), unsafeBuffer(index * 6 + 2),
    unsafeBuffer(index * 6 + 3), unsafeBuffer(index * 6 + 4), unsafeBuffer(index * 6 + 5))

  final override def unsafeSetElem(i: Int, newElem: A): Unit = { unsafeBuffer(i * 6) = newElem.int1; unsafeBuffer(i * 6 + 1) = newElem.int2
    unsafeBuffer(i * 6 + 2) = newElem.int3; unsafeBuffer(i * 6 + 3) = newElem.int4; unsafeBuffer(i * 6 + 4) = newElem.int5
    unsafeBuffer(i * 6 + 5) = newElem.int6 }
}

trait Int6SeqLikeCommonBuilder[BB <: Int6SeqLike[_]] extends IntNSeqLikeCommonBuilder[BB]
{ type BuffT <: Int6Buff[_]
  final override def elemProdSize: Int = 6
}

trait Int6SeqLikeMapBuilder[B <: Int6Elem, BB <: Int6SeqLike[B]] extends Int6SeqLikeCommonBuilder[BB] with IntNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Int6Buff[B]

  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit = { seqLike.unsafeArray(index * 6) = elem.int1
    seqLike.unsafeArray(index * 6 + 1) = elem.int2; seqLike.unsafeArray(index * 6 + 2) = elem.int3; seqLike.unsafeArray(index * 6 + 3) = elem.int4
    seqLike.unsafeArray(index * 6 + 4) = elem.int5; seqLike.unsafeArray(index * 6 + 5) = elem.int6
  }

  final override def buffGrow(buff: BuffT, newElem: B): Unit = { buff.unsafeBuffer.append(newElem.int1); buff.unsafeBuffer.append(newElem.int2)
    buff.unsafeBuffer.append(newElem.int3); buff.unsafeBuffer.append(newElem.int4); buff.unsafeBuffer.append(newElem.int5)
    buff.unsafeBuffer.append(newElem.int6); () }
}

/** Trait for creating the ArrTBuilder type class instances for [[Int5Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of [[Int5Elem]],
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int6ArrMapBuilder[B <: Int6Elem, ArrB <: Int6Arr[B]] extends Int6SeqLikeMapBuilder[B, ArrB] with IntNArrMapBuilder[B, ArrB]

trait Int6ArrFlatBuilder[ArrB <: Int6Arr[_]] extends Int6SeqLikeCommonBuilder[ArrB] with IntNArrFlatBuilder[ArrB]

/** Class for the singleton companion objects of [[Int6Arr]] final classes to extend. */
abstract class Int6ArrCompanion[A <: Int6Elem, M <: Int6Arr[A]] extends IntNSeqLikeCompanion[A, M]
{ final override def elemNumInts: Int = 6

  def buff(initialSize: Int): Int6Buff[A]

  final def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 6
    val res = uninitialised(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.unsafeArray(count) = elems(count / 6).int1
      count += 1
      res.unsafeArray(count) = elems(count / 6).int2
      count += 1
      res.unsafeArray(count) = elems(count / 6).int3
      count += 1
      res.unsafeArray(count) = elems(count / 6).int4
      count += 1
      res.unsafeArray(count) = elems(count / 6).int5
      count += 1
      res.unsafeArray(count) = elems(count / 6).int6
      count += 1
    }
    res
  }
}