/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

/** An object that can be constructed from 6 [[Double]]s. These are used in [[ArrDbl6]] Array[Double] based collections. */
trait Dbl6Elem extends Any, DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double

  final override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4); f(dbl5); f(dbl6) }

  override def dblBufferAppend(buffer: ArrayBuffer[Double]): Unit = buffer.append6(dbl1, dbl2, dbl3, dbl4, dbl5, dbl6)
}

/** Sequence like class whose elements or sequence specifying elements [[Dbl6Elem]] can be constructed from 6 [[Double]]s. */
trait SeqLikeDbl6[+A <: Dbl6Elem] extends Any, SeqLikeDblN[A]
{ /** Constructs an element of the specifying-sequence from 6 [[Double]]s. */
  def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A

  final override def elemProdSize: Int = 6
  final override def numElems: Int = arrayLen / 6

  final def elem(index: Int): A =
  { val offset = index * 6
    elemFromDbls(arrayUnsafe(offset), arrayUnsafe(offset + 1), arrayUnsafe(offset + 2), arrayUnsafe(offset + 3), arrayUnsafe(offset + 4),
      arrayUnsafe(offset + 5))
  }

  final override def setElemUnsafe(index: Int, newElem: A @uncheckedVariance): Unit =
    arrayUnsafe.setIndex6(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5, newElem.dbl6)

  final override def elemEq(a1: A @uncheckedVariance, a2: A @uncheckedVariance): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5) & (a1.dbl6 == a2.dbl6)
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl6Elem]]s. */
trait Dbl6SeqSpec[+A <: Dbl6Elem] extends Any, SeqLikeDbl6[A], SeqSpecDblN[A]

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl6Elem]]s. */
trait ArrDbl6[A <: Dbl6Elem] extends Any, ArrDblN[A], SeqLikeDbl6[A]
{ final override def length: Int = arrayUnsafe.length / 6

  def apply(index: Int): A =
  { val offset = index * 6
    elemFromDbls(arrayUnsafe(offset), arrayUnsafe(offset + 1), arrayUnsafe(offset + 2), arrayUnsafe(offset + 3),
      arrayUnsafe(offset + 4), arrayUnsafe(offset + 5))
  }

  def head1: Double = arrayUnsafe(0); def head2: Double = arrayUnsafe(1); def head3: Double = arrayUnsafe(2); def head4: Double = arrayUnsafe(3)
  def head5: Double = arrayUnsafe(4); def head6: Double = arrayUnsafe(5)

  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6)))

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 6)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex6(length, operand.dbl1, operand.dbl2, operand.dbl3, operand.dbl4, operand.dbl5, operand.dbl6)
    fromArray(newArray)
  }
}

/** Helper class for companion objects of final [[Dbl6SeqSpec]] classes. */
abstract class CompanionSqLikeDbl6[A <: Dbl6Elem, ArrA <: SeqLikeDbl6[A]] extends CompanionSeqLikeDblN[A, ArrA]
{ override def numElemDbls: Int = 6

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val array = Array[Double](length)
    var i: Int = 0
    while (i < length)
    { array.setIndex6(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3, elems(i).dbl4, elems(i).dbl5, elems(i).dbl6)
      i += 1
    }
    fromArray(array)
  }
}

/** Builder for [[SeqLikeDbl6]] types. */
trait BuilderSeqLikeDbl6[BB <: ArrDbl6[?]] extends BuilderSeqLikeDblN[BB]
{ type BuffT <: BuffDbl6[?]
  final override def elemProdSize = 6
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrDbl6]] final classes. Instances for the [[BuilderArrMap]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl6Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDbl6Map[B <: Dbl6Elem, ArrB <: ArrDbl6[B]] extends BuilderSeqLikeDbl6[ArrB], BuilderArrDblNMap[B, ArrB]
{ type BuffT <: BuffDbl6[B]

  override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex6(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5, newElem.dbl6)
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[ArrDbl6]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl6Elem]]. Instances for [[BuilderArrFlat]]
 *  should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB```
 *  function. */
trait BuilderArrDbl6Flat[ArrB <: ArrDbl6[?]] extends BuilderSeqLikeDbl6[ArrB], BuilderArrDblNFlat[ArrB]

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl4Elem]]s collections. */
trait BuffDbl6[A <: Dbl6Elem] extends Any, BuffDblN[A]
{ type ArrT <: ArrDbl6[A]
  override def elemProdSize: Int = 6
  final override def length: Int = bufferUnsafe.length / 6
  final override def numElems: Int = bufferUnsafe.length / 6
  def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A
  override def grow(newElem: A): Unit = bufferUnsafe.append6(newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5, newElem.dbl6)

  final override def apply(index: Int): A = elemFromDbls(bufferUnsafe(index * 6), bufferUnsafe(index * 6 + 1), bufferUnsafe(index * 6 + 2),
    bufferUnsafe(index * 6 + 3), bufferUnsafe(index * 6 + 4), bufferUnsafe(index * 6 + 5))

  final override def elem(index: Int): A = elemFromDbls(bufferUnsafe(index * 6), bufferUnsafe(index * 6 + 1), bufferUnsafe(index * 6 + 2), bufferUnsafe(index * 6 + 3),
    bufferUnsafe(index * 6 + 4), bufferUnsafe(index * 6 + 5))

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
    bufferUnsafe.setIndex6(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5, newElem.dbl6)
}