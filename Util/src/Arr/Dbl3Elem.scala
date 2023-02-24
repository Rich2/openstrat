/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, scala.collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Double]]s. These are used in [[Dbl3Arr]] Array[Double] based collections. */
trait Dbl3Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dblsEqual(that: Dbl3Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2 & dbl3 == that.dbl3

  def dblsApprox(that: Dbl3Elem, delta: Double = 1e-12): Boolean =
    dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta) & dbl3.=~(that.dbl3, delta)

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3) }
}

/** A Sequence like class of [[Dbl3Elem]] elements that can be constructed from 3 [[Double]]s. */
trait Dbl3SeqLike[A <: Dbl3Elem] extends Any with DblNSeqLike[A]
{ override def elemProdSize = 3
  override def setElemUnsafe(index: Int, newElem: A): Unit = unsafeArray.setIndex3(index, newElem.dbl1, newElem.dbl2, newElem.dbl3)
  override def dblBufferAppend(buffer: ArrayBuffer[Double], elem: A) : Unit = buffer.append3(elem.dbl1, elem.dbl2, elem.dbl3)
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl3Elem]]s. */
trait Dbl3SeqSpec[A <: Dbl3Elem] extends Any with Dbl3SeqLike[A] with DblNSeqSpec[A]
{ /** Method for creating new elements of the specifying-sequence from 3 [[Double]]s. */
  def ssElem(d1: Double, d2: Double, d3: Double): A

  override def ssElemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3)
  override def ssIndex(index: Int): A = ssElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[Dbl3Elem]]s. */
trait Dbl3Arr[A <: Dbl3Elem] extends Any with DblNArr[A] with Dbl3SeqLike[A]
{ final override def length: Int = unsafeArray.length / 3
  def head1: Double = unsafeArray(0)
  def head2: Double = unsafeArray(1)
  def head3: Double = unsafeArray(2)
  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3)))

  /** Method for creating new elements from 3 [[Double]]s. */
  def newElem(d1: Double, d2: Double, d3: Double): A

  final override def elemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3)
  final override def apply(index: Int): A = newElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](unsafeLength + 3)
    unsafeArray.copyToArray(newArray)
    newArray.setIndex3(length, operand.dbl1, operand.dbl2, operand.dbl3)
    fromArray(newArray)
  }
}

trait Dbl3SeqLikeCommonBuilder[BB <: Dbl3SeqLike[_]] extends DblNSeqLikeCommonBuilder[BB]
{ type BuffT <: Dbl3Buff[_]
  final override def elemProdSize = 3
}

trait Dbl3SeqLikeMapBuilder[B <: Dbl3Elem, BB <: Dbl3SeqLike[B]] extends Dbl3SeqLikeCommonBuilder[BB] with DblNSeqLikeMapBuilder[B, BB]
{ type BuffT <: Dbl3Buff[B]
  final override def indexSet(seqLike: BB, index: Int, elem: B): Unit = seqLike.unsafeArray.setIndex3(index, elem.dbl1, elem.dbl2, elem.dbl3)
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl3Arr]] final classes. Instances for the [[ArrMapBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl3ArrMapBuilder[B <: Dbl3Elem, ArrB <: Dbl3Arr[B]] extends Dbl3SeqLikeMapBuilder[B, ArrB] with DblNArrMapBuilder[B, ArrB]

/** Trait for creating the [[ArrFlatBuilder]] type class instances for [[Dbl3Arr]] final classes. Instances for the  for classes / traits you
 *  control, should go in the companion object of the ArrT final class. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait Dbl3ArrFlatBuilder[ArrB <: Dbl3Arr[_]] extends Dbl3SeqLikeCommonBuilder[ArrB] with DblNArrFlatBuilder[ArrB]

/** Persists [[Dbl3SeqSpec]]s. */
abstract class Dbl3SeqDefPersist[A <: Dbl3Elem, M <: Dbl3SeqLike[A]](val typeStr: String) extends DataDblNsPersist[A, M]
{ override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit = { buf += value.dbl1; buf += value.dbl2; buf += value.dbl3 }
  override def syntaxDepthT(obj: M): Int = 3
  override def showDecT(obj: M, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ""
}

/** Class for the singleton companion objects of [[Dbl3seqLike]] final classes to extend. */
abstract class Dbl3SeqLikeCompanion[A <: Dbl3Elem, ArrA <: Dbl3SeqLike[A]] extends DblNSeqLikeCompanion[A, ArrA]
{ final override def elemNumDbls: Int = 3

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = uninitialised(length)
    var i: Int = 0
    while (i < length)
    { res.unsafeArray.setIndex3(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3)
      i += 1
    }
    res
  }
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl3Elem]]s collections. */
trait Dbl3Buff[A <: Dbl3Elem] extends Any with DblNBuff[A]
{ type ArrT <: Dbl3Arr[A]

  /** Constructs a new element of this buffer from 3 [[Double]]s. */
  def newElem(d1: Double, d2: Double, d3: Double): A

  override def elemProdSize: Int = 3
  final override def length: Int = unsafeBuffer.length / 3
  override def grow(newElem: A): Unit = unsafeBuffer.append3(newElem.dbl1, newElem.dbl2, newElem.dbl3)
  def apply(index: Int): A = newElem(unsafeBuffer(index * 3), unsafeBuffer(index * 3 + 1), unsafeBuffer(index * 3 + 2))

  override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer.setIndex3(i, newElem.dbl1, newElem.dbl2, newElem.dbl3)
}