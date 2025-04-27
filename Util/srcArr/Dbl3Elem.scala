/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, annotation.unchecked.uncheckedVariance, collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Double]]s. These are used in [[ArrDbl3]] Array[Double] based collections. */
trait Dbl3Elem extends Any, DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dblsEqual(that: Dbl3Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2 & dbl3 == that.dbl3

  def dblsApprox(that: Dbl3Elem, delta: Double = 1e-12): Boolean =
    dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta) & dbl3.=~(that.dbl3, delta)

  final override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3) }
  final override def dblBufferAppend(buffer: ArrayBuffer[Double]) : Unit = buffer.append3(dbl1, dbl2, dbl3)
}

/** [[SeqLike]] with [[Dbl3Elem]]s, elements that can be constructed from 3 [[Double]]s. */
trait SlDbl3[+A <: Dbl3Elem] extends Any, SlValueN[A]
{ /** Method for creating [[Dbl3Elem]]s from 3 [[Double]]s. */
  def elemFromDbls(d1: Double, d2: Double, d3: Double): A

  final override def elemProdSize = 3
  final override def elemEq(a1: A @uncheckedVariance, a2: A @uncheckedVariance): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3)
}

/** [[SeqLikeImut]] of [[Dbl3Elem]]s, elements that can be constructed from 3 [[Double]]s. */
trait SlImutDbl3[+A <: Dbl3Elem] extends Any, SlImutDblN[A], SlDbl3[A]
{ final override def elem(index: Int): A = elemFromDbls(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))
  final override def numElems: Int = arrayLen / 3
  override def setElemUnsafe(index: Int, newElem: A @uncheckedVariance): Unit = arrayUnsafe.setIndex3(index, newElem.dbl1, newElem.dbl2, newElem.dbl3)
}

/** Helper trait for companion objects of [[SeqLikeImut]] final classes, with [[Dbl3Elem]]s. */
abstract class CompanionSlDbl3[A <: Dbl3Elem, ArrA <: SlImutDbl3[A]] extends CompanionSlDblN[A, ArrA]
{ final override def numElemDbls: Int = 3

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = uninitialised(length)
    var i: Int = 0
    while (i < length)
    { res.arrayUnsafe.setIndex3(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3)
      i += 1
    }
    res
  }
}

/** [[SeqSpec]] trait with [[Dbl3Elem]]s backed by a flat [[Array]][Double]. */
trait SsDbl3[+A <: Dbl3Elem] extends Any, SlImutDbl3[A], SsDblN[A]

/** A specialised immutable, flat Array[Double] based sequence of a type of [[Dbl3Elem]]s. */
trait ArrDbl3[A <: Dbl3Elem] extends Any, ArrDblN[A], SlImutDbl3[A]
{ final override def length: Int = arrayUnsafe.length / 3
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3)))
  final override def apply(index: Int): A = elemFromDbls(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))

  @targetName("appendElem") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 3)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex3(length, operand.dbl1, operand.dbl2, operand.dbl3)
    fromArray(newArray)
  }
}

/** [[BuilderBoth]] trait for [[SeqLikeImut]] objects, with [[Dbl3Elem]]s via map and flatMap methods. */
trait BuilderSlDbl3[BB <: SlImutDbl3[?]] extends BuilderSlDblN[BB]
{ type BuffT <: Dbl3Buff[?]
  final override def elemProdSize = 3
}

/** [[BuilderMap]] trait for constructing [[SeqLikeImut]] objects with [[Dbl3Elem]]s via the map method. */
trait BuilderMapSlDbl3[B <: Dbl3Elem, BB <: SlImutDbl3[B]] extends BuilderSlDbl3[BB], BuilderMapSlDblN[B, BB]
{ type BuffT <: Dbl3Buff[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex3(index, newElem.dbl1, newElem.dbl2, newElem.dbl3)
}

/** [[BuilderMap]] trait for constructing [[Arr]]s with [[Dbl3Elem]]s via the map method. Instances for the [[BuilderArrMap]] type class, for classes / traits
 * you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. */
trait BuilderMapArrDbl3[B <: Dbl3Elem, ArrB <: ArrDbl3[B]] extends BuilderMapSlDbl3[B, ArrB], BuilderArrDblNMap[B, ArrB]

/** [[BuilderFlat]] trait for constructing [[Arr]]s with [[Dbl3Elem]]s via the flatMap method. [[BuilderArrFlat]] type class instances for [[ArrDbl3]] final
 * classes1, should go in the companion object of the [[Arr]] final class. */
trait BuilderFlatArrDbl3[ArrB <: ArrDbl3[?]] extends BuilderSlDbl3[ArrB], BuilderFlatArrDblN[ArrB]

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl3Elem]]s collections. */
trait Dbl3Buff[A <: Dbl3Elem] extends Any, BuffDblN[A], SlDbl3[A]
{ type ArrT <: ArrDbl3[A]
  override def grow(newElem: A): Unit = bufferUnsafe.append3(newElem.dbl1, newElem.dbl2, newElem.dbl3)
  final override def apply(index: Int): A = elemFromDbls(bufferUnsafe(index * 3), bufferUnsafe(index * 3 + 1), bufferUnsafe(index * 3 + 2))
  final override def elem(index: Int): A = elemFromDbls(bufferUnsafe(index * 3), bufferUnsafe(index * 3 + 1), bufferUnsafe(index * 3 + 2))
  final override def length: Int = bufferUnsafe.length / 3
  final override def numElems: Int = bufferUnsafe.length / 3
  override def setElemUnsafe(index: Int, newElem: A): Unit = bufferUnsafe.setIndex3(index, newElem.dbl1, newElem.dbl2, newElem.dbl3)
}