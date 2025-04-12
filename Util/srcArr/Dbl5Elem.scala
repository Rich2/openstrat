/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

/** An object that can be constructed from 5 [[Double]]s. These are used in [[ArrDbl5]] Array[Double] based collections. */
trait Dbl5Elem extends Any, DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4); f(dbl5) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]): Unit = buffer.append5(dbl1, dbl2, dbl3, dbl4, dbl5)
}

/** [[SeqLike]] with [[Dbl5Elem]]s. */
trait SlDbl5[+A <: Dbl5Elem] extends Any, SlValueN[A]
{ /** Method for creating new specifying sequence element from 5 [[Double]]s. */
  def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double @uncheckedVariance): A
  
  final override def elemProdSize: Int = 5

  final override def elemEq(a1: A @uncheckedVariance, a2: A @uncheckedVariance): Boolean =
    (a1.dbl1 == a2.dbl1) && (a1.dbl2 == a2.dbl2) && (a1.dbl3 == a2.dbl3) && (a1.dbl4 == a2.dbl4) && (a1.dbl5 == a2.dbl5)
}

/** [[SeqLikeImut]] with [[Dbl5Elem]]s. */
trait SlImutDbl5[+A <: Dbl5Elem] extends Any, SlImutDblN[A], SlDbl5[A]
{ final override def elem(index: Int): A = elemFromDbls(arrayUnsafe(5 * index), arrayUnsafe(5 * index + 1), arrayUnsafe(5 * index + 2), arrayUnsafe(5 * index + 3),
    arrayUnsafe(5 * index + 4))
  
  final override def setElemUnsafe(index: Int, newElem: A @uncheckedVariance): Unit =
    arrayUnsafe.setIndex5(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)  
}

/** [[SeqSpec]] with [[Dbl5Elem]]s can be backed by a flat [[Array]][Double]. */
trait SsDbl5[+A <: Dbl5Elem] extends Any, SlImutDbl5[A], SsDblN[A]

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl5Elem]]s. */
trait ArrDbl5[A <: Dbl5Elem] extends Any, ArrDblN[A], SlImutDbl5[A]
{ final override def length: Int = arrayUnsafe.length / 5
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def head4: Double = arrayUnsafe(3)
  def head5: Double = arrayUnsafe(4)

  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5)))

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 5)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex5(length, operand.dbl1, operand.dbl2, operand.dbl3, operand.dbl4, operand.dbl5)
    fromArray(newArray)
  }
}

/** [[BuilderCollection]] trait for constructing [[SeqLikeImut]]s with [[Dbl5Elem]]s by map and flatMap methods. */
trait BuilderSlDbl5[BB <: SlImutDbl5[?]] extends BuilderSlDblN[BB]
{ type BuffT <: BuffDbl5[?]
  final override def elemProdSize: Int = 5
}

/** [[BuilderMap]] trait for constructing [[Arr]]s with [[Dbl5Elem]]s, by the map method. Type class instances for the builder you control, should go in the
 * companion object of type B. */
trait BuilderMapArrDbl5[B <: Dbl5Elem, ArrB <: ArrDbl5[B]] extends BuilderSlDbl5[ArrB], BuilderMapArrDblN[B, ArrB]
{ type BuffT <: BuffDbl5[B]

  override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex5(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)
}

/** [[BuilderFlat]] trait for constructing [[SeqLikeImut]]s with [[Dbl5Elem]]s by the faltMap method. The builder type class instances should go in the
 * companion object of the [[SeqLikeImut]]. */
trait BuilderFlatArrDbl5[ArrB <: ArrDbl5[?]] extends BuilderSlDbl5[ArrB], BuilderFlatArrDblN[ArrB]

/** Helper class for companion objects of [[SeqLikeImut]] objects, with [[Dbl5Elem]]s. */
abstract class CompanionSlDbl5[A <: Dbl5Elem, ArrA <: SlImutDbl5[A]] extends CompanionSlDblN[A, ArrA]
{ override def numElemDbls: Int = 5

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val array = new Array[Double](length)
    var i: Int = 0
    while (i < length)
    { array.setIndex5(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3, elems(i).dbl4, elems(i).dbl5)
      i += 1
    }
    fromArray(array)
  }
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl5Elem]]s collections. */
trait BuffDbl5[A <: Dbl5Elem] extends Any, BuffDblN[A], SlDbl5[A]
{ type ArrT <: ArrDbl5[A]
  final override def length: Int = bufferUnsafe.length / 5
  override def grow(newElem: A): Unit = bufferUnsafe.append5(newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)

  final override def apply(index: Int): A = elemFromDbls(bufferUnsafe(index * 5), bufferUnsafe(index * 5 + 1), bufferUnsafe(index * 5 + 2),
    bufferUnsafe(index * 5 + 3), bufferUnsafe(index * 5 + 4))

  final override def elem(index: Int): A =
    elemFromDbls(bufferUnsafe(index * 5), bufferUnsafe(index * 5 + 1), bufferUnsafe(index * 5 + 2), bufferUnsafe(index * 5 + 3), bufferUnsafe(index * 5 + 4))

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
    bufferUnsafe.setIndex5(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)
}