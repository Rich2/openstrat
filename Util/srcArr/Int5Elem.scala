/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from 5 [[Int]]s. These are used in [[ArrInt5]] Array[Int] based collections. */
trait Int5Elem extends Any, IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int
  def int5: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3); f(int4); f(int5) }

  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit = { buffer.append(int1); buffer.append(int2); buffer.append(int3)
    buffer.append(int4); buffer.append(int5) }
}

/** A compound object that could be a sequence or specified / defined by a sequence of [[Int5Elem]]s. */
trait SeqLikeInt5[A <: Int5Elem] extends Any, SlValueN[A]
{ /** Constructs a new element of this [[Buff]] from 5 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): A

  final override def elemProdSize: Int = 5

  final override def elemEq(a1: A, a2: A): Boolean =
    (a1.int1 == a2.int1) && (a1.int2 == a2.int2) && (a1.int3 == a2.int3) && (a1.int4 == a2.int4) && (a1.int5 == a2.int5)
}

/** A compound object that could be a sequence or specified / defined by a sequence of [[Int5Elem]]s.  */
trait SeqLikeInt5Imut[A <: Int5Elem] extends Any, SlImutIntN[A], SeqLikeInt5[A]
{ override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex5(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)
}

/** A compound object that is not a sequence but is specified / defined by an [[Int5Elem]] sequence.  */
trait SeqSpecInt5[A <: Int5Elem] extends Any with SeqLikeInt5Imut[A] with SeqSpecIntN[A]
{ override def elem(index: Int): A =
    elemFromInts(arrayUnsafe(5 * index), arrayUnsafe(5 * index + 1), arrayUnsafe(5 * index + 2), arrayUnsafe(5 * index + 3), arrayUnsafe(5 * index + 4))
}

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int5Elem]]s. */
trait ArrInt5[A <: Int5Elem] extends Any with SeqLikeInt5Imut[A] with ArrIntN[A]
{ final override def length: Int = arrayUnsafe.length / 5

  override def apply(index: Int): A =
    elemFromInts(arrayUnsafe(5 * index), arrayUnsafe(5 * index + 1), arrayUnsafe(5 * index + 2), arrayUnsafe(5 * index + 3), arrayUnsafe(5 * index + 4))

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  def head3: Int = arrayUnsafe(2)
  def head4: Int = arrayUnsafe(3)
  def head5: Int = arrayUnsafe(4)

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 5)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex5(length, operand.int1, operand.int2, operand.int3, operand.int4, operand.int5)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int5Elem]]s collections. */
trait BuffInt5[A <: Int5Elem] extends Any, BuffIntN[A], SeqLikeInt5[A]
{ type ThisT <: BuffInt5[A]

  final override def length: Int = bufferUnsafe.length / 5
  final override def grow(newElem: A): Unit = bufferUnsafe.append5(newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)

  final override def apply(index: Int): A =
    elemFromInts(bufferUnsafe(index * 5), bufferUnsafe(index * 5 + 1), bufferUnsafe(index * 5 + 2), bufferUnsafe(index * 5 + 3), bufferUnsafe(index * 5 + 4))

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
    bufferUnsafe.setIndex5(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)
}

/** Base trait for map and flatMap builders for [[SeqLike]]s with [[Int5Elem]]s. */
trait BuilderSeqLikeInt5[BB <: SeqLikeInt5Imut[?]] extends BuilderSeqLikeIntN[BB]
{ type BuffT <: BuffInt5[?]
  final override def elemProdSize: Int = 5
}

/** Builder for [[SeqLike]]s with [[Int5]] elements via the map method, where the call site knows the typeof th element, but not the type of compound
 * object. */
trait BuilderSeqLikeInt5Map[B <: Int5Elem, BB <: SeqLikeInt5Imut[B]] extends BuilderSeqLikeInt5[BB] with BuilderSeqLikeIntNMap[B, BB]
{ type BuffT <: BuffInt5[B]

  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex5(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)

  final override def buffGrow(buff: BuffT, newElem: B): Unit =
    buff.bufferUnsafe.append5(newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5)
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt5]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of B. The first type parameter is called B a sub class of [[Int5Elem]],
 *  because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt5Map[B <: Int5Elem, ArrB <: ArrInt5[B]] extends BuilderSeqLikeInt5Map[B, ArrB] with BuilderArrIntNMap[B, ArrB]

/** Builder for [[Arr]]s with [[Int5]] elements via the flatMap method. */
trait BuilderArrInt5Flat[ArrB <: ArrInt5[?]] extends BuilderSeqLikeInt5[ArrB] with BuilderArrIntNFlat[ArrB]

/** Class for the singleton companion objects of [[ArrInt5]] final classes to extend. */
abstract class CompanionArrInt5[A <: Int5Elem, M <: ArrInt5[A]] extends CompanionSeqLikeIntN[A, M]
{ final override def elemNumInts: Int = 5

  def buff(initialSize: Int): BuffInt5[A]

  final def apply(elems: A*): M =
  { val res = uninitialised(elems.length)
    var i: Int = 0
    while (i < elems.length)
    { res.arrayUnsafe.setIndex5(i, elems(i).int1, elems(i).int2, elems(i).int3, elems(i).int4, elems(i / 5).int5)
      i += 1
    }
    res
  }
}