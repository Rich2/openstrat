/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Double]]s. These are used in [[Dbl4Arr]] Array[Double] based collections. */
trait ElemDbl4 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4) }
}

trait Dbl4SeqLike[A <: ElemDbl4] extends Any with DblNSeqLike[A]
{ override def elemProdSize: Int = 4

  final override def unsafeSetElem(index: Int, elem: A): Unit = { unsafeArray(4 * index) = elem.dbl1; unsafeArray(4 * index + 1) = elem.dbl2
    unsafeArray(4 * index + 2) = elem.dbl3; unsafeArray(4 * index + 3) = elem.dbl4 }

  override def dblBufferAppend(buffer: ArrayBuffer[Double], elem: A): Unit = { buffer.append(elem.dbl1); buffer.append(elem.dbl2)
    buffer.append(elem.dbl3); buffer.append(elem.dbl4) }
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl4]]s. */
trait Dbl4SeqSpec[A <: ElemDbl4] extends Any with Dbl4SeqLike[A] with DblNSeqSpec[A]
{ /** Method for creating new elements of the specifying sequence from 4 [[Double]]s. */
  def ssElem(d1: Double, d2: Double, d3: Double, d4: Double): A

  override def ssElemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4)
  override def ssIndex(index: Int): A = ssElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))
}
/** A specialised immutable, flat Array[Double] based collection of a type of [[ElemDbl4]]s. */
trait Dbl4Arr[A <: ElemDbl4] extends Any with DblNArr[A] with Dbl4SeqLike[A]
{ def head1: Double = unsafeArray(0)
  def head2: Double = unsafeArray(1)
  def head3: Double = unsafeArray(2)
  def head4: Double = unsafeArray(3)
  final override def length: Int = unsafeArray.length / 4
  override def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4)))

  /** Method for creating new elements from 4 [[Double]]s. */
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double): A

  override def elemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4)

  override def apply(index: Int): A =
    newElem(unsafeArray(4 * index), unsafeArray(4 * index + 1), unsafeArray(4 * index + 2), unsafeArray(4 * index + 3))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl4Arr]] final classes. Instances for the [[ArrMapBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[ElemDbl4]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl4ArrBuilder[B <: ElemDbl4, ArrB <: Dbl4Arr[B]] extends DblNArrBuilder[B, ArrB]
{ type BuffT <: Dbl4Buff[B]
  final override def elemProdSize = 4

  override def arrSet(arr: ArrB, index: Int, value: B): Unit = { arr.unsafeArray(index * 4) = value.dbl1; arr.unsafeArray(index * 4 + 1) = value.dbl2
    arr.unsafeArray(index * 4 + 2) = value.dbl3; arr.unsafeArray(index * 4 + 3) = value.dbl4 }
}
/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl4Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl4]]. Instances for
 *  [[ArrFlatBuilder] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait Dbl4ArrFlatBuilder[B <: ElemDbl4, ArrB <: Dbl4Arr[B]] extends DblNArrFlatBuilder[B, ArrB]
{ type BuffT <: Dbl4Buff[B]

  final override def elemProdSize = 4
}

/** Class for the singleton companion objects of [[Dbl4SeqSpec]] final classes to extend. */
abstract class Dbl4SeqLikeCompanion[A <: ElemDbl4, AA <: Dbl4SeqLike[A]] extends DblNSeqLikeCompanion[A, AA]
{
  def apply(elems: A*): AA =
  { val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.unsafeArray(count * 4) = elems(count).dbl1; res.unsafeArray(count * 4 + 1) = elems(count).dbl2
      res.unsafeArray(count * 4 + 2) = elems(count).dbl3; res.unsafeArray(count * 4 + 3) = elems(count).dbl4
      count += 1
    }
     res
   }

  override def elemNumDbls: Int = 4
}

/** Persists [[Dble4Elem] Collection classes. */
abstract class Dbl4SeqLikePersist[A <: ElemDbl4, ArrA <: Dbl4SeqLike[A]](val typeStr: String) extends DataDblNsPersist[A, ArrA]
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1; buf += value.dbl2; buf += value.dbl3; buf += value.dbl4 }

  override def syntaxDepthT(obj: ArrA): Int = 3
}

/** A specialised flat ArrayBuffer[Double] based trait for [[ElemDbl4]]s collections. */
trait Dbl4Buff[A <: ElemDbl4] extends Any with DblNBuff[A]
{ type ArrT <: Dbl4Arr[A]
  def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): A
  override def elemProdSize: Int = 4
  final override def length: Int = unsafeBuffer.length / 4
  override def grow(newElem: A): Unit = { unsafeBuffer.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3).append(newElem.dbl4); () }

  override def apply(index: Int): A =
    dblsToT(unsafeBuffer(index * 4), unsafeBuffer(index * 4 + 1), unsafeBuffer(index * 4 + 2), unsafeBuffer(index * 4 + 3))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuffer(i * 4) = value.dbl1; unsafeBuffer(i * 4 + 1) = value.dbl2; unsafeBuffer(i * 4 + 2) = value.dbl3; unsafeBuffer(i * 4 + 3) = value.dbl4
  }
}