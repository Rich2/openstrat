/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Int]]s. These are used in [[ArrInt4]] Array[Int] based collections. */
trait Int4Elem extends Any, IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3); f(int4) }

  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit =
  { buffer.append(int1); buffer.append(int2); buffer.append(int3); buffer.append(int4) }
}

/** [[SeqLike]] with [[Int4Elem]]s. */
trait SlInt4[A <: Int4Elem] extends Any, SlValueN[A]
{ /** Constructs element [[Int4Elem]] from 4 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int, i3: Int, i4: Int): A

  final override def elemProdSize: Int = 4
  final def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) && (a1.int2 == a2.int2) && (a1.int3 == a2.int3) && (a1.int4 == a2.int4)
}

/** [[SeqLikeImut]] with [[Int4Elem]]s. */
trait SlInt4Imut[A <: Int4Elem] extends Any, SlImutIntN[A], SlInt4[A]
{ final override def numElems: Int = arrayUnsafe.length / 4
  final override def elem(index: Int): A = elemFromInts(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex4(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

/** A [[SeqSpec]] with [[Int4Elem]]s can be specified by a backiing [[Array]][Int]. */
trait SsInt4[A <: Int4Elem] extends Any, SlInt4Imut[A], SsIntN[A]

/** [[Arr]] trait for [[Int4Elem]]s, can be specified by a flat [[Array]][Int]. */
trait ArrInt4[A <: Int4Elem] extends Any, SlInt4Imut[A], ArrIntN[A]
{ final override def length: Int = arrayUnsafe.length / 4

  override def apply(index: Int): A =
    elemFromInts(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  def head3: Int = arrayUnsafe(2)
  def head4: Int = arrayUnsafe(3)

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 4)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex4(length, operand.int1, operand.int2, operand.int3, operand.int4)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int4Elem]]s collections. */
trait BuffInt4[A <: Int4Elem] extends Any, BuffIntN[A], SlInt4[A]
{ type ThisT <: BuffInt4[A]
  final override def length: Int = bufferUnsafe.length / 4
  final override def numElems: Int = bufferUnsafe.length / 4
  final override def grow(newElem: A): Unit = bufferUnsafe.append4(newElem.int1, newElem.int2, newElem.int3, newElem.int4)

  final override def apply(index: Int): A = elemFromInts(bufferUnsafe(index * 4), bufferUnsafe(index * 4 + 1), bufferUnsafe(index * 4 + 2),
    bufferUnsafe(index * 4 + 3))

  final override def elem(index: Int): A = elemFromInts(bufferUnsafe(index * 4), bufferUnsafe(index * 4 + 1), bufferUnsafe(index * 4 + 2), bufferUnsafe(index * 4 + 3))
  final override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe.setIndex4(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

/** [[BuilderCollection]] trait for constructing [[SeqLikeImut]] objects, with [[Int4Elem]]s, via both map and flatMap methods. */
trait BuilderSlInt4[BB <: SlInt4Imut[?]] extends BuilderSlIntN[BB]
{ type BuffT <: BuffInt4[?]
  final override def elemProdSize: Int = 4
}

/** [[BuilderMap]] for constructing [[SeqLikeImut]] objects, with [[Int4Elem]]s, via the map method. */
trait BuilderMapSlInt4[B <: Int4Elem, BB <: SlInt4Imut[B]] extends BuilderSlInt4[BB], BuilderSlIntNMap[B, BB]
{ type BuffT <: BuffInt4[B]

  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex4(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4)

  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.bufferUnsafe.append4(newElem.int1, newElem.int2, newElem.int3, newElem.int4)
}

/** [[BuilderMap]] trait for cronstructing [[Arr]]s with [[Int4Elem]]s. Implcit type class instances for classes  you control, should go in the companion object
 * of the type B class. */
trait BuilderMapArrInt4[B <: Int4Elem, ArrB <: ArrInt4[B]] extends BuilderMapSlInt4[B, ArrB], BuilderArrIntNMap[B, ArrB]

/** [[BuilderFlat]] traitfor constucting [[Arr]]s with [[Int4Elem]]s, via the flatMpa method. Implicit type class instances for classes you control should go in
 * the companion object of the [[Arr]] class. */
trait BuilderFlatArrInt4[ArrB <: ArrInt4[?]] extends BuilderSlInt4[ArrB], BuilderArrIntNFlat[ArrB]

/** Helper trait for companion objects of [[Arr]] classes with [[Int4Elem]]s. */
trait CompanionArrInt4[A <: Int4Elem, M <: ArrInt4[A]] extends CompanionSlIntN[A, M]
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