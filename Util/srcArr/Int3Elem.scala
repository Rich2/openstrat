/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Int]]s. These are used in [[SeqSpecInt3]] based collections. */
trait Int3Elem extends Any with IntNElem
{ def int1: Int
  def int2: Int
  def int3: Int

  override def intForeach(f: Int => Unit): Unit = { f(int1); f(int2); f(int3) }
  override def intBufferAppend(buffer: ArrayBuffer[Int]): Unit = { buffer.append(int1); buffer.append(int2); buffer.append(int3) }
}

/** Sequence like class that has [[Int3Elem]]s as its elements or its specifying sequence. */
trait SeqLikeInt3[A <: Int3Elem] extends Any, SeqLikeValueN[A]
{ final override def elemEq(a1: A, a2: A): Boolean = (a1.int1 == a2.int1) && (a1.int2 == a2.int2) && (a1.int3 == a2.int3)
}

/** Sequence like class that has [[Int3Elem]]s as its elements or its specifying sequence. */
trait SeqLikeInt3Imut[A <: Int3Elem] extends Any, SeqLikeIntNImut[A], SeqLikeInt3[A]
{ /** Constructs an element from 3 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int, i3: Int): A

  final override def elemProdSize: Int = 3
  final override def elem(index: Int): A = elemFromInts(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))
  final override def numElems: Int = arrayUnsafe.length / 3
  final override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex3(index, newElem.int1, newElem.int2, newElem.int3)
}

/** A specialised immutable, flat Array[Double] based trait defined by a data sequence of a type of [[Int3Elem]]s. */
trait SeqSpecInt3[A <: Int3Elem] extends Any, SeqLikeInt3Imut[A], SeqSpecIntN[A]

/** A specialised immutable, flat Array[Int] based collection of a type of [[Int3Elem]]s. */
trait ArrInt3[A <: Int3Elem] extends Any, ArrIntN[A], SeqLikeInt3Imut[A]
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
trait BuffInt3[A <: Int3Elem] extends Any, BuffIntN[A], SeqLikeInt3[A]
{ type ThisT <: BuffInt3[A]

  /** Constructs a new element of this [[BuffSequ]] from 3 [[Int]]s. */
  def elemFromInts(i1: Int, i2: Int, i3: Int): A

  final override def elemProdSize: Int = 3
  final override def length: Int = bufferUnsafe.length / 3
  final override def numElems: Int = bufferUnsafe.length / 3
  override def grow(newElem: A): Unit = bufferUnsafe.append3(newElem.int1, newElem.int2, newElem.int3)
  final override def apply(index: Int): A = elemFromInts(bufferUnsafe(index * 3), bufferUnsafe(index * 3 + 1), bufferUnsafe(index * 3 + 2))
  final override def elem(index: Int): A = elemFromInts(bufferUnsafe(index * 3), bufferUnsafe(index * 3 + 1), bufferUnsafe(index * 3 + 2))
  override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe.setIndex3(index, newElem.int1, newElem.int2, newElem.int3)
}

/** Builder for [[SeqLike]]s with [[Int3Elem]]s. */
trait BuilderSeqLikeInt3[BB <: SeqLikeInt3Imut[?]] extends BuilderSeqLikeIntN[BB]
{ type BuffT <: BuffInt3[?]
  final override def elemProdSize: Int = 3
}

/** Builder for [[SeqLike]]s with [[Int3Elem]]s via the map method, meaning the element type is known at the call site.. */
trait BuilderSeqLikeInt3Map[B <: Int3Elem, BB <: SeqLikeInt3Imut[B]] extends BuilderSeqLikeInt3[BB], BuilderSeqLikeIntNMap[B, BB]
{ type BuffT <: BuffInt3[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex3(index, newElem.int1, newElem.int2, newElem.int3)
  final override def buffGrow(buff: BuffT, newElem: B): Unit = buff.bufferUnsafe.append3(newElem.int1, newElem.int2, newElem.int3)
}

trait BuilderSeqLikeInt3Flat[BB <: SeqLikeInt3Imut[?]] extends BuilderSeqLikeInt3[BB], BuilderSeqLikeIntNFlat[BB]

/** Trait for creating the ArrTBuilder type class instances for [[ArrInt3]] final classes. Instances for the [[BuilderArrMap]] type class, for classes / traits
 * you control, should go in the companion object of B. The first type parameter is called B a subclass of Int3Elem, because to corresponds to the B in the
 * ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt3Map[B <: Int3Elem, ArrB <: ArrInt3[B]] extends BuilderSeqLikeInt3Map[B, ArrB], BuilderArrIntNMap[B, ArrB]

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrInt3]] final classes. Instances for the [[BuilderArrMap]] type class,
 * for classes / traits you control, should go in the companion object of B. Instances for [[BuilderArrFlat] should go in the companion object the ArrT final
 * class. The first type parameter is called B a subclass of Int3Elem, because to corresponds to the B in the ```map(f: A => B): ArrB``` function. */
trait BuilderArrInt3Flat[ArrB <: ArrInt3[?]] extends BuilderSeqLikeInt3[ArrB], BuilderArrIntNFlat[ArrB]

/** Helper class for companion objects of final [[SeqSpecInt3]] classes. */
abstract class CompanionSeqLikeInt3[A <: Int3Elem, ArrA <: SeqLikeInt3Imut[A]] extends CompanionSeqLikeIntN[A, ArrA]
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