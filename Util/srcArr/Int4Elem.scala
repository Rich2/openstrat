/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Int]]s. These are used in [[ArrInt4]] Array[Int] based collections. */
trait Int4Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3); f(int4) }

  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit =
  { buffer.append(int1); buffer.append(int2); buffer.append(int3); buffer.append(int4) }
}

/** [[SeqLike]] with [[Int4Elem]]s. */
trait SeqLikeInt4[A <: Int4Elem] extends Any with SeqLikeIntN[A]
{ final override def elemProdSize: Int = 4

  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A

  override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex4(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

/** A compound object defined / specified by a sequence of [[Int4Elem]]s. */
trait SeqSpecInt4[A <: Int4Elem] extends Any with SeqLikeInt4[A] with SeqSpecIntN[A]
{
  final def ssElemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4)

  override def ssIndex(index: Int): A =
    newElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int4Elem]]s. */
trait ArrInt4[A <: Int4Elem] extends Any with SeqLikeInt4[A] with ArrIntN[A]
{ final override def length: Int = arrayUnsafe.length / 4

  override def apply(index: Int): A =
    newElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))

  def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) & (a1.int2 == a2.int2) & (a1.int3 == a2.int3) & (a1.int4 == a2.int4)

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  def head3: Int = arrayUnsafe(2)
  def head4: Int = arrayUnsafe(3)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 4)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex4(length, operand.int1, operand.int2, operand.int3, operand.int4)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int4Elem]]s collections. */
trait BuffInt4[A <: Int4Elem] extends Any with BuffIntN[A]
{ type ThisT <: BuffInt4[A]

  /** Constructs a new element of this [[BuffSequ]] form 4 [[Int]]s. */
  def newElem(i1: Int, i2: Int, i3: Int, i4: Int): A

  override def elemProdSize: Int = 4
  final override def length: Int = unsafeBuffer.length / 4
  final override def grow(newElem: A): Unit = unsafeBuffer.append4(newElem.int1, newElem.int2, newElem.int3, newElem.int4)

  final override def apply(index: Int): A = newElem(unsafeBuffer(index * 4), unsafeBuffer(index * 4 + 1), unsafeBuffer(index * 4 + 2),
    unsafeBuffer(index * 4 + 3))

  final override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer.setIndex4(i, newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

/** Base trait for builders of [[SeqLikeInt4]] objects via both map and flatMap methods. */
trait BuilderSeqLikeInt4[BB <: SeqLikeInt4[_]] extends BuilderSeqLikeIntN[BB]
{ type BuffT <: BuffInt4[_]
  final override def elemProdSize: Int = 4
}

/** Builders for [[SeqLikeInt4]] objects via the map f: A => B method. */
trait BuilderSeqLikeInt4Map[B <: Int4Elem, BB <: SeqLikeInt4[B]] extends BuilderSeqLikeInt4[BB] with BuilderSeqLikeIntNMap[B, BB]
{ type BuffT <: BuffInt4[B]

  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex4(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4)

  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.unsafeBuffer.append4(newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt4]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of Int4Elem,
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt4Map[B <: Int4Elem, ArrB <: ArrInt4[B]] extends BuilderSeqLikeInt4Map[B, ArrB] with BuilderArrIntNMap[B, ArrB]

trait BuilderArrInt4Flat[ArrB <: ArrInt4[_]] extends BuilderSeqLikeInt4[ArrB] with BuilderArrIntNFlat[ArrB]

/** Class for the singleton companion objects of [[ArrInt4]] final classes to extend. */
trait CompanionArrInt4[A <: Int4Elem, M <: ArrInt4[A]] extends CompanionSeqLikeIntN[A, M]
{ final override def elemNumInts: Int = 4

  def buff(initialSize: Int): BuffInt4[A]

  final def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 4
    val res = uninitialised(elems.length)
    var i: Int = 0
    while (i < elems.length)
    { res.arrayUnsafe.setIndex4(i, elems(i).int1, elems(i).int2, elems(i).int3, elems(i).int4)
      i += 1
    }
    res
  }
}