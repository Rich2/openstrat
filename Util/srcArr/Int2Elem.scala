/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Int]]s. These are used in [[ArrInt2]] Array[Int] based collections. */
trait Int2Elem extends Any, IntNElem
{ def int1: Int
  def int2: Int
  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]) : Unit = buffer.append2(int1, int2)
}

/** [[SeqLike]] trait with [[Int2Elem]]s. They maybe backed by an [[Array]] or an [[ArrayBuffer]]. */
trait SeqLikeInt2[A <: Int2Elem] extends Any, SeqLikeIntN[A]
{ final override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) && (a1.int2 == a2.int2)
}

/** [[SeqLikeImut]] trait with [[Int2Elem]]s, can bw specified with a backing [[Array]][Int]. */
trait SeqLikeImutInt2[A <: Int2Elem] extends Any, SeqLikeInt2[A], SeqLikeImutIntN[A]
{ /** Constructs an element from 2 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int): A
  
  final override def elemProdSize: Int = 2
  final override def elem(index: Int): A = elemFromInts(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))
  final override def numElems: Int = arrayUnsafe.length
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex2(index, newElem.int1, newElem.int2)
  
}

/** [[SeqSpec]] with [[Int2Elem]]s. */
trait SeqSpecInt2[A <: Int2Elem] extends Any, SeqLikeImutInt2[A], SeqSpecIntN[A]

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int2Elem]]s. */
trait ArrInt2[A <: Int2Elem] extends Any, ArrIntN[A], SeqLikeImutInt2[A]
{ def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  final override def length: Int = arrayUnsafe.length / 2
  final override def apply(index: Int): A = elemFromInts(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  @targetName("appendElem") final def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 2)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex2(length, operand.int1, operand.int2)
    fromArray(newArray)
  }
}

/** [[BuilderBoth]] trait for constructing [[SeqlikeImut]] objects, with [[Int2Elem]]s, by both map and flatMap methods. */
trait BuilderSeqLikeInt2[BB <: SeqLikeImutInt2[?]] extends BuilderSeqLikeIntN[BB]
{ type BuffT <: BuffInt2[?]
  final override def elemProdSize: Int = 2
}

/** [[BuilderMap]] trait for constructing [[SeqLikeImut]]s with [[Int2Elem]]s, via the map method. Implicit type class instances for classses you control should
 * go in the companion object of the type B class. */
trait BuilderMapSeqLikeInt2[B <: Int2Elem, BB <: SeqLikeImutInt2[B]] extends BuilderSeqLikeInt2[BB], BuilderSeqLikeIntNMap[B, BB]
{ type BuffT <: BuffInt2[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex2(index, newElem.int1, newElem.int2)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.bufferUnsafe.append2(newElem.int1, newElem.int2)
}

/** [[BuilderMap]] trait for constructing [[Arr]]s with [[Int2Elem]]s via the map method. Implicit type class instances should go in the companion object of the
 * B class. */
trait BuilderMapArrInt2[B <: Int2Elem, ArrB <: ArrInt2[B]] extends BuilderMapSeqLikeInt2[B, ArrB], BuilderArrIntNMap[B, ArrB]

/** [[BuilderFlat]] Trait for constructing [[Arr]]s with [[Int2Elem]]s via the flatMap method. Implicit type class instances for classes you control, should go
 * in the companion object of the [[Arr]] class. */
trait BuilderFlatArrInt2[ArrB <: ArrInt2[?]] extends BuilderSeqLikeInt2[ArrB], BuilderArrIntNFlat[ArrB]

/** A specialised flat ArrayBuffer[Int] based trait for [[Int2Elem]]s collections. */
trait BuffInt2[A <: Int2Elem] extends Any, BuffIntN[A], SeqLikeInt2[A]
{ type ThisT <: BuffInt2[A]

  /** Constructs a new element of this [[Buff]] from 2 [[Int]]s. */
  def newElem(i1: Int, i2: Int): A

  override def elemProdSize: Int = 2
  final override def length: Int = bufferUnsafe.length / 2
  final override def numElems: Int = bufferUnsafe.length / 2
  override def grow(newElem: A): Unit = bufferUnsafe.append2(newElem.int1, newElem.int2)
  def growInts(int1: Int, int2: Int): Unit = bufferUnsafe.append2(int1, int2)
  override def apply(index: Int): A = newElem(bufferUnsafe(index * 2), bufferUnsafe(index * 2 + 1))
  final override def elem(index: Int): A = newElem(bufferUnsafe(index * 2), bufferUnsafe(index * 2 + 1))
  override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe.setIndex2(index, newElem.int1, newElem.int2)
}

/** Helper trait for companion objects of [[SeqLikeImut]]s with [[Int2Elem]]s. */
trait CompanionSlInt2[A <: Int2Elem, ArrA <: SeqLikeImutInt2[A]] extends CompanionSlIntN[A, ArrA]
{ override def elemNumInts: Int = 2

  /** Apply factory method */
  def apply(elems: A*): ArrA =
  { val res = uninitialised(elems.length)
    var i: Int = 0
    while (i < elems.length)
    { res.arrayUnsafe.setIndex2(i, elems(i).int1, elems(i).int2)
      i += 1
    }
    res
  }
}

/** Helper trait for Companion objects of buffers of [[Int2Elem]]s. */
trait CompanionBuffInt2[A <: Int2Elem, AA <: BuffInt2[A]] extends CompanionBuffIntN[A, AA]
{
  override def apply(elems: A*): AA =
  { val buffer: ArrayBuffer[Int] =  new ArrayBuffer[Int](elems.length * 2 + 6)
    elems.foreach{ elem => buffer.append2(elem.int1, elem.int2) }
    fromBuffer(buffer)
  }

  final override def elemNumInts: Int = 2
}