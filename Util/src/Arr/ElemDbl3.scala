/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Double]]s. These are used in [[Dbl3Arr]] Array[Double] based collections. */
trait ElemDbl3 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dblsEqual(that: ElemDbl3): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2 & dbl3 == that.dbl3

  def dblsApprox(that: ElemDbl3, delta: Double = 1e-12): Boolean =
    dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta) & dbl3.=~(that.dbl3, delta)
}

trait Dbl3SeqLike[A <: ElemDbl3] extends Any with DblNSeqLike[A]
{ override def elemProdSize = 3

  override def unsafeSetElem(index: Int, elem: A): Unit = { unsafeArray(3 * index) = elem.dbl1; unsafeArray(3 * index + 1) = elem.dbl2
    unsafeArray(3 * index + 2) = elem.dbl3 }
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl3]]s. */
trait Dbl3SeqSpec[A <: ElemDbl3] extends Any with Dbl3SeqLike[A] with DblNSeqSpec[A]
{ /** Method for creating new data elements from 3 [[Double]]s In the case of [[Dbl3Arr]] this will be the type of the elements of the sequence. */
  def dataElem(d1: Double, d2: Double, d3: Double): A


  override def sdElemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3)
  override def sdIndex(index: Int): A = dataElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[ElemDbl3]]s. */
trait Dbl3Arr[A <: ElemDbl3] extends Any with DblNArr[A] with Dbl3SeqLike[A]
{ final override def length: Int = unsafeArray.length / 3
  def head1: Double = unsafeArray(0)
  def head2: Double = unsafeArray(1)
  def head3: Double = unsafeArray(2)
  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3)))

  /** Method for creating new data elements from 3 [[Double]]s In the case of [[Dbl3Arr]] this will be the type of the elements of the sequence. */
  def dataElem(d1: Double, d2: Double, d3: Double): A

  override def elemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3)

  override def apply(index: Int): A = dataElem(unsafeArray(3 * index), unsafeArray(3 * index + 1), unsafeArray(3 * index + 2))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl3Arr]] final classes. Instances for the [[ArrBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[ElemDbl3]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl3ArrBuilder[B <: ElemDbl3, ArrB <: Dbl3Arr[B]] extends DblNArrBuilder[B, ArrB]
{ type BuffT <: Dbl3Buff[B]
  final override def elemProdSize = 3

  override def arrSet(arr: ArrB, index: Int, value: B): Unit =
  { arr.unsafeArray(index * 3) = value.dbl1; arr.unsafeArray(index * 3 + 1) = value.dbl2; arr.unsafeArray(index * 3 + 2) = value.dbl3
  }
}

/** Trait for creating the [[ArrFlatBuilder]] type class instances for [[Dbl3Arr]] final classes. Instances for the  for classes / traits you
 *  control, should go in the companion object of the ArrT final class. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait Dbl3ArrFlatBuilder[B <: ElemDbl3, ArrB <: Dbl3Arr[B]] extends DblNArrFlatBuilder[B, ArrB]
{ type BuffT <: Dbl3Buff[B]
  final override def elemProdSize = 3
}

/** Persists [[Dbl3SeqSpec]]s. */
abstract class Dbl3SeqDefPersist[A <: ElemDbl3, M <: Dbl3SeqLike[A]](val typeStr: String) extends DataDblNsPersist[A, M]
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1
    buf += value.dbl2
    buf += value.dbl3
  }

  override def syntaxDepthT(obj: M): Int = 3
  override def showDecT(obj: M, way: ShowStyle, maxPlaces: Int, minPlaces: Int): String = ""
}

/** Class for the singleton companion objects of [[Dbl3seqLike]] final classes to extend. */
abstract class Dbl3SeqLikeCompanion[A <: ElemDbl3, ArrA <: Dbl3SeqLike[A]] extends DblNSeqLikeCompanion[A, ArrA]
{ final override def elemNumDbls: Int = 3

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.unsafeArray(count * 3) = elems(count).dbl1;  res.unsafeArray(count * 3 + 1) = elems(count).dbl2;
      res.unsafeArray(count * 3 + 2) = elems(count).dbl3
      count += 1
    }
    res
  }
}

/** A specialised flat ArrayBuffer[Double] based trait for [[ElemDbl3]]s collections. */
trait Dbl3Buff[A <: ElemDbl3] extends Any with DblNBuff[A]
{ type ArrT <: Dbl3Arr[A]
  override def elemProdSize: Int = 3
  final override def length: Int = unsafeBuffer.length / 3

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3); () }

  def dblsToT(d1: Double, d2: Double, d3: Double): A
  def apply(index: Int): A = dblsToT(unsafeBuffer(index * 3), unsafeBuffer(index * 3 + 1), unsafeBuffer(index * 3 + 2))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuffer(i * 4) = value.dbl1; unsafeBuffer(i * 4 + 1) = value.dbl2; unsafeBuffer(i * 4 + 2) = value.dbl3
  }
}