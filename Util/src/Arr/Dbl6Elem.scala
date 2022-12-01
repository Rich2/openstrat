/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 6 [[Double]]s. These are used in [[Dbl6Arr]] Array[Double] based collections. */
trait Dbl6Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4); f(dbl5); f(dbl6) }
}

/** Sequence like class whose elements or sequence specifying elements [[Dbl6Elem]] can be constructed from 6 [[Double]]s. */
trait Dbl6SeqLike[A <: Dbl6Elem] extends Any with DblNSeqLike[A]
{
  def elemProdSize: Int = 6

  def unsafeSetElem(index: Int, newElem: A): Unit =
  { val offset = index * 6
    unsafeArray(offset) = newElem.dbl1; unsafeArray(offset + 1) = newElem.dbl2; unsafeArray(offset + 2) = newElem.dbl3; unsafeArray(offset + 3) = newElem.dbl4
    unsafeArray(offset + 4) = newElem.dbl5; unsafeArray(offset + 5) = newElem.dbl6
  }

  override def dblBufferAppend(buffer: ArrayBuffer[Double], elem: A): Unit =
  { buffer.append(elem.dbl1); buffer.append(elem.dbl2); buffer.append(elem.dbl3); buffer.append(elem.dbl4); buffer.append(elem.dbl5)
    buffer.append(elem.dbl6)
  }
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl6Elem]]s. */
trait Dbl6SeqSpec[A <: Dbl6Elem] extends Any with Dbl6SeqLike[A] with DblNSeqSpec[A]
{
  override def ssElemEq(a1: A, a2: A): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5) & (a1.dbl6 == a2.dbl6)

  /** Constructs an element of the specifying-sequence from 6 [[Double]]s. */
  def ssElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A

  def ssIndex(index: Int): A = {
    val offset = index * 6
    ssElem(unsafeArray(offset), unsafeArray(offset + 1), unsafeArray(offset + 2), unsafeArray(offset + 3),
      unsafeArray(offset + 4), unsafeArray(offset + 5))
  }
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl6Elem]]s. */
trait Dbl6Arr[A <: Dbl6Elem] extends Any with DblNArr[A] with Dbl6SeqLike[A]
{ final override def length: Int = unsafeArray.length / 6

  def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A

  def apply(index: Int): A = {
    val offset = index * 6
    newElem(unsafeArray(offset), unsafeArray(offset + 1), unsafeArray(offset + 2), unsafeArray(offset + 3),
      unsafeArray(offset + 4), unsafeArray(offset + 5))
  }

  def head1: Double = unsafeArray(0); def head2: Double = unsafeArray(1); def head3: Double = unsafeArray(2); def head4: Double = unsafeArray(3)
  def head5: Double = unsafeArray(4); def head6: Double = unsafeArray(5)

  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6)))

  override def elemEq(a1: A, a2: A): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5) & (a1.dbl6 == a2.dbl6)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](unsafeLength + 6)
    unsafeArray.copyToArray(newArray)
    newArray(unsafeLength) = operand.dbl1; newArray(unsafeLength + 1) = operand.dbl2; newArray(unsafeLength + 2) = operand.dbl3
    newArray(unsafeLength + 3) = operand.dbl4; newArray(unsafeLength + 4) = operand.dbl5; newArray(unsafeLength + 5) = operand.dbl6
    fromArray(newArray)
  }
}

/** Helper class for companion objects of final [[Dbl6SeqSpec]] classes. */
abstract class Dbl6SeqLikeCompanion[A <: Dbl6Elem, ArrA <: Dbl6SeqLike[A]] extends DblNSeqLikeCompanion[A, ArrA]
{
  override def elemNumDbls: Int = 6

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val array = Array[Double](length)
    var count: Int = 0

    while (count < length)
    { val offset = count * 6
      array(offset) = elems(count).dbl1; array(offset + 1) = elems(count).dbl2; array(offset + 2) = elems(count).dbl3
      array(offset + 3) = elems(count).dbl4; array(offset + 4) = elems(count).dbl5; array(offset + 5) = elems(count).dbl6
      count += 1
    }
    fromArray(array)
  }
}

trait Dbl6SeqLikeCommonBuilder[BB <: Dbl6Arr[_]] extends DblNSeqLikeCommonBuilder[BB]
{ type BuffT <: Dbl6Buff[_]
  final override def elemProdSize = 6
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl6Arr]] final classes. Instances for the [[ArrMapBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl6Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl6ArrMapBuilder[B <: Dbl6Elem, ArrB <: Dbl6Arr[B]] extends Dbl6SeqLikeCommonBuilder[ArrB] with DblNArrMapBuilder[B, ArrB]
{ type BuffT <: Dbl6Buff[B]

  override def indexSet(seqLike: ArrB, index: Int, value: B): Unit =
  { seqLike.unsafeArray(index * 6) = value.dbl1; seqLike.unsafeArray(index * 6 + 1) = value.dbl2; seqLike.unsafeArray(index * 6 + 2) = value.dbl3
    seqLike.unsafeArray(index * 6 + 3) = value.dbl4; seqLike.unsafeArray(index * 6 + 4) = value.dbl5; seqLike.unsafeArray(index * 6 + 5) = value.dbl6
  }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl6Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl6Elem]]. Instances for
 *  [[ArrFlatBuilder] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait Dbl6ArrFlatBuilder[ArrB <: Dbl6Arr[_]] extends Dbl6SeqLikeCommonBuilder[ArrB] with DblNArrFlatBuilder[ArrB]

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl4Elem]]s collections. */
trait Dbl6Buff[A <: Dbl6Elem] extends Any with DblNBuff[A]
{ type ArrT <: Dbl6Arr[A]
  override def elemProdSize: Int = 6
  final override def length: Int = unsafeBuffer.length / 6

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit =
  { unsafeBuffer.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3).append(newElem.dbl4).append(newElem.dbl5).append(newElem.dbl6); () }

  def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A
  override def apply(index: Int): A = dblsToT(unsafeBuffer(index * 6), unsafeBuffer(index * 6 + 1), unsafeBuffer(index * 6 + 2),
    unsafeBuffer(index * 6 + 3), unsafeBuffer(index * 6 + 4), unsafeBuffer(index * 6 + 5))

  override def unsafeSetElem(i: Int, newElem: A): Unit =
  { unsafeBuffer(i * 6) = newElem.dbl1; unsafeBuffer(i * 6 + 1) = newElem.dbl2; unsafeBuffer(i * 6 + 2) = newElem.dbl3;
    unsafeBuffer(i * 6 + 3) = newElem.dbl4; unsafeBuffer(i * 6 + 3) = newElem.dbl5;
  }
}