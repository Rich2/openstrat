/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Int]]s. These are used in [[Int4Arr]] Array[Int] based collections. */
trait ElemInt4 extends Any with ElemIntN
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int
}

trait Int4SeqLike[A <: ElemInt4] extends Any with IntNSeqLike[A]
{
  override def elemProdSize: Int = 4
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A

  override def unsafeSetElem(index: Int, elem: A): Unit = {
    unsafeArray(4 * index) = elem.int1;
    unsafeArray(4 * index + 1) = elem.int2
    unsafeArray(4 * index + 2) = elem.int3
    unsafeArray(4 * index + 3) = elem.int4
  }
}

trait Int4SeqDef[A <: ElemInt4] extends Any with Int4SeqLike[A] with IntNSeqDef[A]
{
  def sdElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3)

  override def sdIndex(index: Int): A =
    newElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[ElemInt4]]s. */
trait Int4Arr[A <: ElemInt4] extends Any with Int4SeqLike[A] with IntNArr[A]
{ final override def length: Int = unsafeArray.length / 4

  override def apply(index: Int): A =
    newElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))

  def sdElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3)

  def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  def head3: Int = unsafeArray(2)
  def head4: Int = unsafeArray(3)
}

/** Trait for creating the ArrTBuilder type class instances for [[Int4Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int4Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int4ArrBuilder[B <: ElemInt4, ArrB <: Int4Arr[B]] extends IntNArrBuilder[B, ArrB]
{ type BuffT <: Int4Buff[B, ArrB]

  final override def elemProdSize: Int = 4
  def newArray(length: Int): Array[Int] = new Array[Int](length * 4)

  final override def arrSet(arr: ArrB, index: Int, value: B): Unit =
  { arr.unsafeArray(index * 4) = value.int1; arr.unsafeArray(index * 4 + 1) = value.int2; arr.unsafeArray(index * 4 + 2) = value.int3
    arr.unsafeArray(index * 4 + 3) = value.int4 }

  override def buffGrow(buff: BuffT, value: B): Unit = { buff.unsafeBuffer.append(value.int1); buff.unsafeBuffer.append(value.int2)
    buff.unsafeBuffer.append(value.int3); buff.unsafeBuffer.append(value.int4); () }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[ElemInt4]]s collections. */
trait Int4Buff[A <: ElemInt4, M <: Int4Arr[A]] extends Any with IntNBuff[A]
{ override def elemProdSize: Int = 4
  final override def length: Int = unsafeBuffer.length / 4
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1).append(newElem.int2).append(newElem.int3).append(newElem.int4); ()}
  def intsToT(i1: Int, i2: Int, i3: Int, i4: Int): A
  override def apply(index: Int): A = intsToT(unsafeBuffer(index * 4), unsafeBuffer(index * 4 + 1), unsafeBuffer(index * 4 + 2), unsafeBuffer(index * 4 + 3))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuffer(i * 4) = value.int1; unsafeBuffer(i * 4 + 1) = value.int2; unsafeBuffer(i * 4 + 2) = value.int3; unsafeBuffer(i * 4 + 3) = value.int4
  }
}

/** Class for the singleton companion objects of [[Int4Arr]] final classes to extend. */
abstract class Int4ArrCompanion[A <: ElemInt4, M <: Int4Arr[A]] extends IntNSeqLikeCompanion[A, M]
{ override def elemNumInts: Int = 4

  def buff(initialSize: Int): Int4Buff[A, M]

  def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = uninitialised(elems.length)
    var count: Int = 0
    while (count < arrLen)
    {
      res.unsafeArray(count) = elems(count / 2).int1
      count += 1
      res.unsafeArray(count) = elems(count / 2).int2
      count += 1
      res.unsafeArray(count) = elems(count / 2).int3
      count += 1
      res.unsafeArray(count) = elems(count / 2).int4
      count += 1
    }
    res
  }
}

/**  Class to persist specialised flat Array[Int] based [[Int4Arr]] collection classes. */
abstract class Int4SeqLikePersist[B <: ElemInt4, ArrB <: Int4Arr[B]](val typeStr: String) extends IntNSeqLikePersist[B, ArrB]
{
  override def appendtoBuffer(buf: ArrayBuffer[Int], value: B): Unit =
  { buf += value.int1
    buf += value.int2
    buf += value.int3
    buf += value.int4
  }

  override def syntaxDepthT(obj: ArrB): Int = 3
}