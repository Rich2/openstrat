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
}

trait Int4SeqLike[A <: Int4Elem] extends Any with IntNSeqLike[A]
{ final override def elemProdSize: Int = 4

  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A

  override def setElemUnsafe(index: Int, newElem: A): Unit = { unsafeArray(4 * index) = newElem.int1; unsafeArray(4 * index + 1) = newElem.int2
    unsafeArray(4 * index + 2) = newElem.int3; unsafeArray(4 * index + 3) = newElem.int4 }

  override def intBufferAppend(buffer: ArrayBuffer[Int], elem: A): Unit = { buffer.append(elem.int1); buffer.append(elem.int2)
    buffer.append(elem.int3); buffer.append(elem.int4) }
}

trait Int4SeqSpec[A <: Int4Elem] extends Any with Int4SeqLike[A] with IntNSeqSpec[A]
{
  final def ssElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4)

  override def ssIndex(index: Int): A =
    newElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int4Elem]]s. */
trait Int4Arr[A <: Int4Elem] extends Any with Int4SeqLike[A] with IntNArr[A]
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
    newArray(unsafeLength) = operand.int1
    newArray(unsafeLength + 1) = operand.int2
    newArray(unsafeLength + 2) = operand.int3
    newArray(unsafeLength + 3) = operand.int4
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
  final override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1).append(newElem.int2).append(newElem.int3).append(newElem.int4); ()}

  final override def apply(index: Int): A = newElem(unsafeBuffer(index * 4), unsafeBuffer(index * 4 + 1), unsafeBuffer(index * 4 + 2),
    unsafeBuffer(index * 4 + 3))

  final override def setElemUnsafe(i: Int, newElem: A): Unit = { unsafeBuffer(i * 4) = newElem.int1; unsafeBuffer(i * 4 + 1) = newElem.int2
    unsafeBuffer(i * 4 + 2) = newElem.int3; unsafeBuffer(i * 4 + 3) = newElem.int4 }
}

trait Int4SeqLikeCommonBuilder[BB <: Int4SeqLike[_]] extends IntNSeqLikeCommonBuilder[BB]
{ type BuffT <: Int4Buff[_]
  final override def elemProdSize: Int = 4
}

trait Int4SeqLikeMapBuilder[B <: Int4Elem, BB <: Int4SeqLike[B]] extends Int4SeqLikeCommonBuilder[BB] with IntNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Int4Buff[B]

  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit = { seqLike.unsafeArray(index * 4) = elem.int1
    seqLike.unsafeArray(index * 4 + 1) = elem.int2; seqLike.unsafeArray(index * 4 + 2) = elem.int3; seqLike.unsafeArray(index * 4 + 3) = elem.int4
  }

  final override def buffGrow(buff: BuffT, newElem: B): Unit = { buff.unsafeBuffer.append(newElem.int1); buff.unsafeBuffer.append(newElem.int2)
    buff.unsafeBuffer.append(newElem.int3); buff.unsafeBuffer.append(newElem.int4); () }
}

/** Trait for creating the ArrTBuilder type class instances for [[Int4Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int4Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int4ArrMapBuilder[B <: Int4Elem, ArrB <: Int4Arr[B]] extends Int4SeqLikeMapBuilder[B, ArrB] with IntNArrMapBuilder[B, ArrB]

trait Int4ArrFlatBuilder[ArrB <: Int4Arr[_]] extends Int4SeqLikeCommonBuilder[ArrB] with IntNArrFlatBuilder[ArrB]

/** Class for the singleton companion objects of [[Int4Arr]] final classes to extend. */
abstract class Int4ArrCompanion[A <: Int4Elem, M <: Int4Arr[A]] extends IntNSeqLikeCompanion[A, M]
{ final override def elemNumInts: Int = 4

  def buff(initialSize: Int): Int4Buff[A]

  final def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = uninitialised(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.unsafeArray(count) = elems(count / 4).int1
      count += 1
      res.unsafeArray(count) = elems(count / 4).int2
      count += 1
      res.unsafeArray(count) = elems(count / 4).int3
      count += 1
      res.unsafeArray(count) = elems(count / 4).int4
      count += 1
    }
    res
  }
}

/**  Class to persist specialised flat Array[Int] based [[Int4Arr]] collection classes. */
abstract class Int4SeqLikePersist[B <: Int4Elem, ArrB <: Int4Arr[B]](val typeStr: String) extends IntNSeqLikePersist[B, ArrB]
{ override def syntaxDepthT(obj: ArrB): Int = 3
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: B): Unit = { buf += value.int1; buf += value.int2; buf += value.int3; buf += value.int4 }
}