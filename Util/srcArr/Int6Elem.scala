/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from 6 [[Int]]s. These are used in [[ArrInt6]] [[Array]][Int] based collections. */
trait Int6Elem extends Any, IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int
  def int4: Int
  def int5: Int
  def int6: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3); f(int4); f(int5); f(int6) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit = buffer.append6(int1, int2, int3, int4, int5, int6)
}

/** [[SeqLike]] for [[Int6Elem]]s */
trait SeqLikeInt6[A <: Int6Elem] extends Any, SlValueN[A]
{ /** Constructs a new element of this [[Buff]] from 6 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): A

  final override def elemProdSize: Int = 6

  final override def elemEq(a1: A, a2: A): Boolean =
    (a1.int1 == a2.int1) && (a1.int2 == a2.int2) && (a1.int3 == a2.int3) && (a1.int4 == a2.int4) && (a1.int5 == a2.int5) && (a1.int6 == a2.int6)
}

/** [[SeqLike]] for [[Int6Elem]]s */
trait SeqLikeInt6Imut[A <: Int6Elem] extends Any, SlImutIntN[A], SeqLikeInt6[A]
{ final override def numElems: Int = arrayUnsafe.length / 6

  override def setElemUnsafe(index: Int, newElem: A): Unit =
    arrayUnsafe.setIndex6(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5, newElem.int6)

  final override def elem(index: Int): A = elemFromInts(arrayUnsafe(6 * index), arrayUnsafe(6 * index + 1), arrayUnsafe(6 * index + 2), arrayUnsafe(6 * index + 3),
    arrayUnsafe(6 * index + 4), arrayUnsafe(6 * index + 5))
}

/** Compound object defined / specified by [[Int6Elem]]s */
trait SeqSpecInt6[A <: Int6Elem] extends Any, SeqLikeInt6Imut[A], SsIntN[A]

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int5Elem]]s. */
trait ArrInt6[A <: Int6Elem] extends Any, SeqLikeInt6Imut[A], ArrIntN[A]
{ final override def length: Int = arrayUnsafe.length / 6

  final override def apply(index: Int): A = elemFromInts(arrayUnsafe(6 * index), arrayUnsafe(6 * index + 1), arrayUnsafe(6 * index + 2),
    arrayUnsafe(6 * index + 3), arrayUnsafe(6 * index + 4), arrayUnsafe(6 * index + 5))

  def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  def head3: Int = arrayUnsafe(2)
  def head4: Int = arrayUnsafe(3)
  def head5: Int = arrayUnsafe(4)
  def head6: Int = arrayUnsafe(5)

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 6)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex6(length, operand.int1, operand.int2, operand.int3, operand.int4, operand.int5, operand.int6)
    fromArray(newArray)
  }
}

/** A specialised flat ArrayBuffer[Int] based trait for [[Int5Elem]]s collections. */
trait BuffInt6[A <: Int6Elem] extends Any, BuffIntN[A], SeqLikeInt6[A]
{ type ThisT <: BuffInt6[A]
  final override def length: Int = bufferUnsafe.length / 6
  final override def numElems: Int = bufferUnsafe.length / 6
  final override def grow(newElem: A): Unit = bufferUnsafe.append6(newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5, newElem.int6)

  final override def apply(index: Int): A = elemFromInts(bufferUnsafe(index * 6), bufferUnsafe(index * 6 + 1), bufferUnsafe(index * 6 + 2),
    bufferUnsafe(index * 6 + 3), bufferUnsafe(index * 6 + 4), bufferUnsafe(index * 6 + 5))

  final override def elem(index: Int): A = elemFromInts(bufferUnsafe(index * 6), bufferUnsafe(index * 6 + 1), bufferUnsafe(index * 6 + 2), bufferUnsafe(index * 6 + 3),
    bufferUnsafe(index * 6 + 4), bufferUnsafe(index * 6 + 5))

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
    bufferUnsafe.setIndex6(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5, newElem.int6)
}

/** Builder for [[SeqLike]]s with [[Int6Elem]]s */
trait BuilderSeqLikeInt6[BB <: SeqLikeInt6Imut[?]] extends BuilderSlIntN[BB]
{ type BuffT <: BuffInt6[?]
  final override def elemProdSize: Int = 6
}

/** Builder of [[SeqLikeInt6Imut]] objects via the map f: A => B method. */
trait BuilderSeqLikeInt6Map[B <: Int6Elem, BB <: SeqLikeInt6Imut[B]] extends BuilderSeqLikeInt6[BB], BuilderSlIntNMap[B, BB]
{ type BuffT <: BuffInt6[B]

  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex6(index, newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5, newElem.int6)

  final override def buffGrow(buff: BuffT, newElem: B): Unit =
    buff.bufferUnsafe.append6(newElem.int1, newElem.int2, newElem.int3, newElem.int4, newElem.int5, newElem.int6)
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt5]] final classes. Instances for the [[BuilderMapArr]] type class, for classes / traits
 * you control, should go in the companion object of B. The first type parameter is called B a subclass of [[Int5Elem]], because to corresponds to the B in the
 * ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt6Map[B <: Int6Elem, ArrB <: ArrInt6[B]] extends BuilderSeqLikeInt6Map[B, ArrB], BuilderArrIntNMap[B, ArrB]

/** Builder of [[ArrInt6]] objects via the flatMap f: A => ArrB method. */
trait BuilderArrInt6Flat[ArrB <: ArrInt6[?]] extends BuilderSeqLikeInt6[ArrB] with BuilderArrIntNFlat[ArrB]

/** Class for the singleton companion objects of [[ArrInt6]] final classes to extend. */
abstract class CompanionArrInt6[A <: Int6Elem, M <: ArrInt6[A]] extends CompanionSlIntN[A, M]
{ final override def elemNumInts: Int = 6

  def buff(initialSize: Int): BuffInt6[A]

  final def apply(elems: A*): M =
  { val arrLen: Int = elems.length * 6
    val res = uninitialised(elems.length)
    var i: Int = 0
    while (i < elems.length)
    { res.arrayUnsafe.setIndex6(i, elems(i).int1, elems(i).int2, elems(i).int3, elems(i).int4, elems(i).int5, elems(i).int6)
      i += 1
    }
    res
  }
}

/** Helper trait for Companion objects of buffers of [[Int6Elem]]s. */
trait CompanionBuffInt6[A <: Int6Elem, AA <: BuffInt6[A]] extends CompanionBuffIntN[A, AA]
{
  override def apply(elems: A*): AA =
  { val buffer: ArrayBuffer[Int] =  new ArrayBuffer[Int](elems.length * 6 + 6)
    elems.foreach{ elem => buffer.append6(elem.int1, elem.int2, elem.int3, elem.int4, elem.int5, elem.int6) }
    fromBuffer(buffer)
  }

  final override def elemNumInts: Int = 6
}