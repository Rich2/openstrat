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

/** Common trait for [[SeqLike]] classes that have [[Int2Elem]] elements. They maybe backed by an [[Array]] or an [[ArrayBuffer]]. */
trait SeqLikeInt2[A <: Int2Elem] extends Any, SlIntN[A]
{ final override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) && (a1.int2 == a2.int2)
}

/** Common trait for immutable [[Arr]] and [[SeqSpec]] classes specified by [[Int2Elem]]s with a backing [[Array]]. */
trait SeqLikeInt2Imut[A <: Int2Elem] extends Any, SeqLikeInt2[A], SlImutIntN[A]
{ /** Constructs an element from 2 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int): A
  
  final override def elemProdSize: Int = 2
  final override def elem(index: Int): A = elemFromInts(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))
  final override def numElems: Int = arrayUnsafe.length
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex2(index, newElem.int1, newElem.int2)
  
}

/** A specialised immutable, flat [[Array]][Int] based trait defined by a data sequence of a type of [[Int2Elem]]s. */
trait SeqSpecInt2[A <: Int2Elem] extends Any, SeqLikeInt2Imut[A], SsIntN[A]

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int2Elem]]s. */
trait ArrInt2[A <: Int2Elem] extends Any, ArrIntN[A], SeqLikeInt2Imut[A]
{ def head1: Int = arrayUnsafe(0)
  def head2: Int = arrayUnsafe(1)
  final override def length: Int = arrayUnsafe.length / 2
  final override def apply(index: Int): A = elemFromInts(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  @targetName("appendElem") final override def +%(operand: A): ThisT =
  { val newArray = new Array[Int](arrayLen + 2)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex2(length, operand.int1, operand.int2)
    fromArray(newArray)
  }
}

/** base trait for constructing [[SeqlikeInt2]] objects by both map and flatMap methods. */
trait BuilderSeqLikeInt2[BB <: SeqLikeInt2Imut[?]] extends BuilderSlIntN[BB]
{ type BuffT <: BuffInt2[?]
  final override def elemProdSize: Int = 2
}

/** Builds [[SeqLikeInt2Imut]] objects via the map method. */
trait BuilderSeqLikeInt2Map[B <: Int2Elem, BB <: SeqLikeInt2Imut[B]] extends BuilderSeqLikeInt2[BB], BuilderSlIntNMap[B, BB]
{ type BuffT <: BuffInt2[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex2(index, newElem.int1, newElem.int2)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.bufferUnsafe.append2(newElem.int1, newElem.int2)
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt2]] final classes. Instances for the [[BuilderArrMap]] type class, for classes / traits
 * you control, should go in the companion object of B. The first type parameter is called B a subclass of [[Int2Elem]], because to corresponds to the B in the
 * ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt2Map[B <: Int2Elem, ArrB <: ArrInt2[B]] extends BuilderSeqLikeInt2Map[B, ArrB], BuilderArrIntNMap[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrInt2]] final classes. Instances for the [[BuilderArrMap]] type class,
 * for classes / traits you control, should go in the companion object of B. Instances for [[BuilderArrFlat] should go in the companion object the ArrT final
 * class. The first type parameter is called B a subclass of Int2Elem, because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt2Flat[ArrB <: ArrInt2[?]] extends BuilderSeqLikeInt2[ArrB], BuilderArrIntNFlat[ArrB]

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

/** Helper class for companion objects of final [[SeqSpecInt2]] classes. */
trait CompanionSeqLikeInt2[A <: Int2Elem, ArrA <: SeqLikeInt2Imut[A]] extends CompanionSlIntN[A, ArrA]
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