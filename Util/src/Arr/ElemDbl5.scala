/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 5 [[Double]]s. These are used in [[Dbl5Arr]] Array[Double] based collections. */
trait ElemDbl5 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4); f(dbl5) }
}

trait Dbl5SeqLike[A <: ElemDbl5] extends Any with DblNSeqLike[A]
{ def elemProdSize: Int = 5

  final override def unsafeSetElem(index: Int, elem: A): Unit =
  { unsafeArray(5 * index) = elem.dbl1; unsafeArray(5 * index + 1) = elem.dbl2; unsafeArray(5 * index + 2) = elem.dbl3
    unsafeArray(5 * index + 3) = elem.dbl4; unsafeArray(5 * index + 4) = elem.dbl5
  }

  override def dblBufferAppend(buffer: ArrayBuffer[Double], elem: A): Unit = { buffer.append(elem.dbl1); buffer.append(elem.dbl2)
    buffer.append(elem.dbl3); buffer.append(elem.dbl4); buffer.append(elem.dbl5) }
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl5]]s. */
trait Dbl5SeqSpec[A <: ElemDbl5] extends Any with Dbl5SeqLike[A] with DblNSeqSpec[A]
{  /** Method for creating new specifying sequence elements from 5 [[Double]]s In the case of [[Dbl5Arr]] this will be the type of the elements of the
   * sequence. */
  def ssElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A


  def ssIndex(index: Int): A = ssElem(unsafeArray(5 * index), unsafeArray(5 * index + 1), unsafeArray(5 * index + 2), unsafeArray(5 * index + 3),
    unsafeArray(5 * index + 4))

  override def ssElemEq(a1: A, a2: A): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5)
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[ElemDbl5]]s. */
trait Dbl5Arr[A <: ElemDbl5] extends Any with DblNArr[A] with Dbl5SeqLike[A]
{ def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A
  final override def length: Int = unsafeArray.length / 5
  def head1: Double = unsafeArray(0)
  def head2: Double = unsafeArray(1)
  def head3: Double = unsafeArray(2)
  def head4: Double = unsafeArray(3)
  def head5: Double = unsafeArray(4)

  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5)))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl5Arr]] final classes. Instances for the [[ArrMapBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[ElemDbl5]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl5ArrMapBuilder[B <: ElemDbl5, ArrB <: Dbl5Arr[B]] extends DblNArrMapBuilder[B, ArrB]
{ type BuffT <: Dbl5Buff[B]
  final override def elemProdSize = 5

  override def indexSet(seqLike: ArrB, index: Int, value: B): Unit =
  { seqLike.unsafeArray(index * 5) = value.dbl1; seqLike.unsafeArray(index * 5 + 1) = value.dbl2; seqLike.unsafeArray(index * 5 + 2) = value.dbl3
    seqLike.unsafeArray(index * 5 + 3) = value.dbl4;  seqLike.unsafeArray(index * 5 + 4) = value.dbl5
  }
}
/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl5Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl5]]. Instances for
 *  [[ArrFlatBuilder] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait Dbl5ArrFlatBuilder[ArrB <: Dbl5Arr[_]] extends DblNArrFlatBuilder[ArrB]
{ type BuffT <: Dbl5Buff[_]
  final override def elemProdSize = 5
}

/** Helper class for companion objects of final [[Dbl5SeqSpec]] classes. */
abstract class Dbl5SeqLikeCompanion[A <: ElemDbl5, ArrA <: Dbl5SeqLike[A]] extends DblNSeqLikeCompanion[A, ArrA]
{ override def elemNumDbls: Int = 5

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val array = new Array[Double](length)
    var count: Int = 0

    while (count < length)
    { array(count * 5) = elems(count).dbl1; array(count * 5 + 1) = elems(count).dbl2; array(count * 5 + 2) = elems(count).dbl3
      array(count * 5 + 3) = elems(count).dbl4; array(count * 5 + 4) = elems(count).dbl5
      count += 1
    }

    fromArray(array)
  }
}

/** Both Persists and Builds [[Dbl5Arr]] Collection classes. */
abstract class DataDbl5sPersist[A <: ElemDbl5, ArrA <: Dbl5SeqSpec[A]](val typeStr: String) extends DataDblNsPersist[A, ArrA]
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit = { buf += value.dbl1; buf += value.dbl2; buf += value.dbl3; buf += value.dbl4
    buf += value.dbl5 }

  override def syntaxDepthT(obj: ArrA): Int = 3
}

/** A specialised flat ArrayBuffer[Double] based trait for [[ElemDbl5]]s collections. */
trait Dbl5Buff[A <: ElemDbl5] extends Any with DblNBuff[A]
{ type ArrT <: Dbl5Arr[A]
  override def elemProdSize: Int = 5
  final override def length: Int = unsafeBuffer.length / 5

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit =
  { unsafeBuffer.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3).append(newElem.dbl4).append(newElem.dbl5); () }

  def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A
  override def apply(index: Int): A = dblsToT(unsafeBuffer(index * 5), unsafeBuffer(index * 5 + 1), unsafeBuffer(index * 5 + 2),
    unsafeBuffer(index * 5 + 3), unsafeBuffer(index * 5 + 4))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuffer(i * 5) = value.dbl1; unsafeBuffer(i * 5 + 1) = value.dbl2; unsafeBuffer(i * 5 + 2) = value.dbl3
    unsafeBuffer(i * 5 + 3) = value.dbl4; unsafeBuffer(i * 5 + 3) = value.dbl5
  }
}