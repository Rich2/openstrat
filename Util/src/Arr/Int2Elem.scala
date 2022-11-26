/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Int]]s. These are used in [[Int2Arr]] Array[Int] based collections. */
trait Int2Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2) }
}

trait Int2SeqLike[A <: Int2Elem] extends Any with IntNSeqLike[A]
{ override def elemProdSize: Int = 2
  final override def unsafeSetElem(index: Int, elem: A): Unit = { unsafeArray(2 * index) = elem.int1; unsafeArray(2 * index + 1) = elem.int2 }
  def newElem(i1: Int, i2: Int): A
  override def intBufferAppend(buffer: ArrayBuffer[Int], elem: A) : Unit = { buffer.append(elem.int1); buffer.append(elem.int2) }
}

/** A specialised immutable, flat Array[Double] based trait defined by a data sequence of a type of [[Int2Elem]]s. */
trait Int2SeqSpec[A <: Int2Elem] extends Any with Int2SeqLike[A] with IntNSeqSpec[A]
{
  override def ssIndex(index: Int): A = ssElem(unsafeArray(2 * index), unsafeArray(2 * index + 1))

  /** Construct element of the specifying sequence from 2 [[Int]]s. */
  def ssElem(int1: Int, int2: Int): A

  override def ssElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2)
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int2Elem]]s. */
trait Int2Arr[A <: Int2Elem] extends Any with IntNArr[A] with Int2SeqLike[A]
{ def head1: Int = unsafeArray(0)
  def head2: Int = unsafeArray(1)
  final override def length: Int = unsafeArray.length / 2
  final override def apply(index: Int): A = newElem(unsafeArray(2 * index), unsafeArray(2 * index + 1))
  override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](unsafeLength + 2)
    unsafeArray.copyToArray(newArray)
    newArray(unsafeLength) = operand.int1
    newArray(unsafeLength + 1) = operand.int2
    fromArray(newArray)
  }
}

trait Int2SeqLikeCommonBuilder[BB <: SeqLike[_]] extends IntNSeqLikeCommonBuilder[BB]
{ type BuffT <: Int2Buff[_]
  final override def elemProdSize: Int = 2
}
trait Int2SeqLikeMapBuilder[B <: Int2Elem, BB <: Int2SeqLike[B]] extends Int2SeqLikeCommonBuilder[BB] with IntNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Int2Buff[B]
  final override def indexSet(seqLike: BB, index: Int, value: B): Unit =
  { seqLike.unsafeArray(index * 2) = value.int1;
    seqLike.unsafeArray(index * 2 + 1) = value.int2
  }

  final override def buffGrow(buff: BuffT, value: B): Unit = { buff.unsafeBuffer.append(value.int1); buff.unsafeBuffer.append(value.int2); () }
}

/** Trait for creating the ArrTBuilder type class instances for [[Int2Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int2Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait Int2ArrMapBuilder[B <: Int2Elem, ArrB <: Int2Arr[B]] extends Int2SeqLikeMapBuilder[B, ArrB] with IntNArrMapBuilder[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Int2Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of B. Instances for [[ArrFlatBuilder] should go in the companion
 *  object the ArrT final class. The first type parameter is called B a sub class of Int2Elem, because to corresponds to the B in the
 *  ```map(f: A => B): ArrB``` function. */
trait Int2ArrFlatBuilder[ArrB <: Int2Arr[_]] extends Int2SeqLikeCommonBuilder[ArrB] with IntNArrFlatBuilder[ArrB]

/** A specialised flat ArrayBuffer[Int] based trait for [[Int2Elem]]s collections. */
trait Int2Buff[A <: Int2Elem] extends Any with IntNBuff[A]
{ type ArrT <: Int2Arr[A]
  override def elemProdSize: Int = 2
  final override def length: Int = unsafeBuffer.length / 2
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.int1).append(newElem.int2); () }
  def intsToT(i1: Int, i2: Int): A
  override def apply(index: Int): A = intsToT(unsafeBuffer(index * 2), unsafeBuffer(index * 2 + 1))
  override def unsafeSetElem(i: Int, value: A): Unit = { unsafeBuffer(i * 2) = value.int1; unsafeBuffer(i * 2 + 1) = value.int2 }
}

/** Helper class for companion objects of final [[Int2SeqSpec]] classes. */
trait Int2SeqLikeCompanion[A <: Int2Elem, ArrA <: Int2SeqLike[A]] extends IntNSeqLikeCompanion[A, ArrA]
{
  override def elemNumInts: Int = 2

  /** Apply factory method */
  def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length * 2
    val res = uninitialised(elems.length)
    var count: Int = 0

    while (count < arrLen)
    { res.unsafeArray(count) = elems(count / 2).int1
      count += 1
      res.unsafeArray(count) = elems(count / 2).int2
      count += 1
    }
    res
  }
}

trait Int2BuffCompanion[A <: Int2Elem, AA <: Int2Buff[A]] extends IntNBuffCompanion[A, AA]
{
  override def apply(elems: A*): AA =
  { val buffer: ArrayBuffer[Int] =  new ArrayBuffer[Int](elems.length * 2 + 6)
    elems.foreach{ elem => buffer.append(elem.int1); buffer.append(elem.int2) }
    fromBuffer(buffer)
  }

  final override def elemNumInts: Int = 2
}