/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Int]]s. These are used in [[SsInt3]] based collections. */
trait Int3Elem extends Any, IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit = { buffer.append(int1); buffer.append(int2); buffer.append(int3) }
}

/** [[SeqLike]] with [[Int3Elem]]s. */
trait SlInt3[A <: Int3Elem] extends Any, SlValueN[A]
{ /** Constructs an element from 3 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int, i3: Int): A

  final override def elemProdSize: Int = 3
  final override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) && (a1.int2 == a2.int2) && (a1.int3 == a2.int3)
}

/** [[SeqLikeImut]] with [[Int3Elem]]s that be specified by a backing [[Array]][Double]. */
trait SlImutInt3[A <: Int3Elem] extends Any, SlImutIntN[A], SlInt3[A]
{ final override def elem(index: Int): A = elemFromInts(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))
  final override def numElems: Int = arrayUnsafe.length / 3
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex3(index, newElem.int1, newElem.int2, newElem.int3)
}

/** [[SeqSpec]] with [[Int3Elems]]. Can be specified by a flat [[Array]][Double]. */
trait SsInt3[A <: Int3Elem] extends Any, SlImutInt3[A], SsIntN[A]

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int3Elem]]s. */
trait ArrInt3[A <: Int3Elem] extends Any, ArrIntN[A], SlImutInt3[A]
{ def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  def head3: Int = arrayUnsafe(2)
  final override def length: Int = arrayUnsafe.length / 3
  final override def apply(index: Int): A = elemFromInts(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))

  @targetName("appendElem") final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 3)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex3(length, operand.int1, operand.int2, operand.int3)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int3Elem]]s collections. */
trait BuffInt3[A <: Int3Elem] extends Any, BuffIntN[A], SlInt3[A]
{ type ThisT <: BuffInt3[A]
  final override def length: Int = bufferUnsafe.length / 3
  final override def numElems: Int = bufferUnsafe.length / 3
  override def grow(newElem: A): Unit = bufferUnsafe.append3(newElem.int1, newElem.int2, newElem.int3)
  final override def apply(index: Int): A = elemFromInts(bufferUnsafe(index * 3), bufferUnsafe(index * 3 + 1), bufferUnsafe(index * 3 + 2))
  final override def elem(index: Int): A = elemFromInts(bufferUnsafe(index * 3), bufferUnsafe(index * 3 + 1), bufferUnsafe(index * 3 + 2))
  override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe.setIndex3(index, newElem.int1, newElem.int2, newElem.int3)
}

/** [[BuilderCollection]] for constructing [[SeqLikeImut]]s with [[Int3Elem]]s, via the map or flatMap methods. */
trait BuilderSlInt3[BB <: SlImutInt3[?]] extends BuilderSlIntN[BB]
{ type BuffT <: BuffInt3[?]
  final override def elemProdSize: Int = 3
}

/** [[BuilderMap]] trait for constructing [[SeqLikeImut]]s with [[Int3Elem]]s via the map method. Implicit type class instances should go in the companion
 * object of the type B class. */
trait BuilderMapSlInt3[B <: Int3Elem, BB <: SlImutInt3[B]] extends BuilderSlInt3[BB], BuilderSlIntNMap[B, BB]
{ type BuffT <: BuffInt3[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex3(index, newElem.int1, newElem.int2, newElem.int3)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.bufferUnsafe.append3(newElem.int1, newElem.int2, newElem.int3)
}

/** [[BuilderFlat]] trait for constructing [[SeqLikeImut]]s with [[Int3Elem]]s via the flatMap method. Implicit type class instances should go in the companion
 * object of the [[SeqLikeImut]] class. */
trait BuilderFlatSlInt3[BB <: SlImutInt3[?]] extends BuilderSlInt3[BB], BuilderSlIntNFlat[BB]

/** [[BuilderMap]] trait for constructing [[Arr]]s with [[Int3Elem]]s by the map method. Implicit Type class instances, for classes you control, should go in
 * the companion object of the type B class. */
trait BuilderMapArrInt3[B <: Int3Elem, ArrB <: ArrInt3[B]] extends BuilderMapSlInt3[B, ArrB], BuilderArrIntNMap[B, ArrB]

/** [[BuilderFlat]] trait for constructing  [[Arr]]s with [[Int3Elem]]s via the flatMap method. Implicit type class instances for classes you control, should go
 * in the companion object of the [[Arr]] class. */
trait BuilderFlatArrInt3[ArrB <: ArrInt3[?]] extends BuilderSlInt3[ArrB], BuilderArrIntNFlat[ArrB]

/** Helper class for companion objects of [[SeqLikeImut]] classes, with [[Int3Elem]]s. */
abstract class CompanionSlInt3[A <: Int3Elem, ArrA <: SlImutInt3[A]] extends CompanionSlIntN[A, ArrA]
{ override def elemNumInts: Int = 3

  /** Apply factory method for constructing [[SeqLike]] objects from [[Int3Elem]]s. */
  final def apply(elems: A*): ArrA =
  { val arrLen: Int = elems.length * 3
    val res = uninitialised(elems.length)
    var i: Int = 0

    while (i < elems.length)
    { res.arrayUnsafe.setIndex3(i, elems(i).int1, elems(i).int2, elems(i).int3)
      i += 1
    }
    res
  }
}

/** Helper trait for Companion objects of buffers of [[Int3Elem]]s. */
trait CompanionBuffInt3[A <: Int3Elem, AA <: BuffInt3[A]] extends CompanionBuffIntN[A, AA]
{
  override def apply(elems: A*): AA =
  { val buffer: ArrayBuffer[Int] =  new ArrayBuffer[Int](elems.length * 3 + 6)
    elems.foreach{ elem => buffer.append3(elem.int1, elem.int2, elem.int3) }
    fromBuffer(buffer)
  }

  final override def elemNumInts: Int = 3
}