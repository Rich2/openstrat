/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Int]]s. These are used in [[Int3SeqSpec]] based collections. */
trait Int3Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3) }
}

trait Int3SeqLike[A <: Int3Elem] extends Any with IntNSeqLike[A]
{
  def newElem(i1: Int, i2: Int, i3: Int): A

  override def elemProdSize: Int = 3

  final override def unsafeSetElem(index: Int, newElem: A): Unit = { unsafeArray(3 * index) = newElem.int1; unsafeArray(3 * index + 1) = newElem.int2
    unsafeArray(3 * index + 2) = newElem.int3 }

  override def intBufferAppend(buffer: ArrayBuffer[Int], elem: A) : Unit = { buffer.append(elem.int1); buffer.append(elem.int2)
    buffer.append(elem.int3) }
}

/** A specialised immutable, flat Array[Double] based trait defined by a data sequence of a type of [[Int3Elem]]s. */
trait Int3SeqSpec[A <: Int3Elem] extends Any with Int3SeqLike[A] with IntNSeqSpec[A]
{
  final override def ssIndex(index: Int): A = ssElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))

  /** Creates a sequence-defined element from 3 [[Int]]s. */
  def ssElem(int1: Int, int2: Int, int3: Int): A

  override def ssElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3)
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int3Elem]]s. */
trait Int3Arr[A <: Int3Elem] extends Any with IntNArr[A] with Int3SeqLike[A]
{ def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  def head3: Int = unsafeArray(2)
  final override def length: Int = unsafeArray.length / 3

  override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3)
  final override def apply(index: Int): A = newElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](unsafeLength + 3)
    unsafeArray.copyToArray(newArray)
    newArray(unsafeLength) = operand.int1
    newArray(unsafeLength + 1) = operand.int2
    newArray(unsafeLength + 2) = operand.int3
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int3Elem]]s collections. */
trait Int3Buff[A <: Int3Elem] extends Any with IntNBuff[A]
{ type ArrT <: Int3Arr[A]
  override def elemProdSize: Int = 3
  final override def length: Int = unsafeBuffer.length / 3
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1).append(newElem.int2).append(newElem.int3); () }

  /** Constructs a sequence-defined element from 3 [[Int]]s.  */
  def sdElem(i1: Int, i2: Int, i3: Int): A
  override def apply(index: Int): A = sdElem(unsafeBuffer(index * 3), unsafeBuffer(index * 3 + 1), unsafeBuffer(index * 3 + 2))
  override def unsafeSetElem(i: Int, newElem: A): Unit = { unsafeBuffer(i * 3) = newElem.int1; unsafeBuffer(i * 3 + 1) = newElem.int2; unsafeBuffer(i * 3 + 2) = newElem.int3 }
}

trait Int3SeqLikeCommonBuilder[BB <: Int3SeqLike[_]] extends IntNSeqLikeCommonBuilder[BB]
{ type BuffT <: Int3Buff[_]
  final override def elemProdSize: Int = 3
}

trait Int3SeqLikeMapBuilder[B <: Int3Elem, BB <: Int3SeqLike[B]] extends Int3SeqLikeCommonBuilder[BB] with IntNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Int3Buff[B]

  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit = { seqLike.unsafeArray(index * 3) = elem.int1
    seqLike.unsafeArray(index * 3 + 1) = elem.int2; seqLike.unsafeArray(index * 3 + 2) = elem.int3 }

  final override def buffGrow(buff: BuffT, newElem: B): Unit = { buff.unsafeBuffer.append(newElem.int1); buff.unsafeBuffer.append(newElem.int2)
    buff.unsafeBuffer.append(newElem.int3); () }
}

/** Trait for creating the ArrTBuilder type class instances for [[Int3Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int3Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int3ArrMapBuilder[B <: Int3Elem, ArrB <: Int3Arr[B]] extends Int3SeqLikeMapBuilder[B, ArrB] with IntNArrMapBuilder[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Int3Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B a sub class of Int3Elem, because to corresponds to the B in the
 *  ```map(f: A => B): ArrB``` function. */
trait Int3ArrFlatBuilder[ArrB <: Int3Arr[_]] extends Int3SeqLikeCommonBuilder[ArrB] with IntNArrFlatBuilder[ArrB]

/** Helper class for companion objects of final [[Int3SeqSpec]] classes. */
abstract class Int3SeqLikeCompanion[A <: Int3Elem, ArrA <: Int3SeqLike[A]] extends IntNSeqLikeCompanion[A, ArrA]
{ override def elemNumInts: Int = 3

  /** Apply factory method. */
  final def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length * 3
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.unsafeArray(count) = elems(count / 3).int1
      count += 1
      res.unsafeArray(count) = elems(count / 3).int2
      count += 1
      res.unsafeArray(count) = elems(count / 3).int3
      count += 1
    }
    res
  }
}