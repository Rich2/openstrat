/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from 6 [[Double]]s. These are used in [[Dbl6Arr]] Array[Double] based collections. */
trait ElemDbl6 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double
}

trait Dbl6SeqLike[A <: ElemDbl6] extends Any with DblNSeqLike[A]
{
  def elemProdSize: Int = 6

  def unsafeSetElem(index: Int, elem: A): Unit = {
    val offset = index * 6
    unsafeArray(offset) = elem.dbl1;
    unsafeArray(offset + 1) = elem.dbl2;
    unsafeArray(offset + 2) = elem.dbl3;
    unsafeArray(offset + 3) = elem.dbl4
    unsafeArray(offset + 4) = elem.dbl5;
    unsafeArray(offset + 5) = elem.dbl6
  }

  def dataElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A

  def sdIndex(index: Int): A = {
    val offset = index * 6
    dataElem(unsafeArray(offset), unsafeArray(offset + 1), unsafeArray(offset + 2), unsafeArray(offset + 3),
      unsafeArray(offset + 4), unsafeArray(offset + 5))
  }
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl6]]s. */
trait Dbl6SeqDef[A <: ElemDbl6] extends Any with Dbl6SeqLike[A] with DblNSeqDef[A]
{
  override def sdElemEq(a1: A, a2: A): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5) & (a1.dbl6 == a2.dbl6)
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[ElemDbl6]]s. */
trait Dbl6Arr[A <: ElemDbl6] extends Any with DblNArr[A] with Dbl6SeqLike[A]
{ final override def length: Int = unsafeArray.length / 6

  def head1: Double = unsafeArray(0); def head2: Double = unsafeArray(1); def head3: Double = unsafeArray(2); def head4: Double = unsafeArray(3)
  def head5: Double = unsafeArray(4); def head6: Double = unsafeArray(5)

  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6)))

  override def sdElemEq(a1: A, a2: A): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5) & (a1.dbl6 == a2.dbl6)

  override def reverseData: ThisT = {
    val res: ThisT = unsafeSameSize(sdLength)
    dataIForeach({ (i, el) => res.unsafeSetElem(sdLength - 1 - i, el) })
    res
  }

}

/** Helper class for companion objects of final [[Dbl6SeqDef]] classes. */
abstract class Dbl6SeqLikeCompanion[A <: ElemDbl6, ArrA <: Dbl6SeqLike[A]] extends DblNSeqLikeCompanion[A, ArrA]
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

/** Trait for creating the ArrTBuilder type class instances for [[Dbl6Arr]] final classes. Instances for the [[ArrBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[ElemDbl6]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl6ArrBuilder[B <: ElemDbl6, ArrB <: Dbl6Arr[B]] extends DblNArrBuilder[B, ArrB]
{ type BuffT <: Dbl6Buff[B]
  final override def elemProdSize = 6

  override def arrSet(arr: ArrB, index: Int, value: B): Unit =
  { arr.unsafeArray(index * 6) = value.dbl1
    arr.unsafeArray(index * 6 + 1) = value.dbl2
    arr.unsafeArray(index * 6 + 2) = value.dbl3
    arr.unsafeArray(index * 6 + 3) = value.dbl4
    arr.unsafeArray(index * 6 + 4) = value.dbl5
    arr.unsafeArray(index * 6 + 5) = value.dbl6
  }
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl6Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[ElemDbl6]]. Instances for
 *  [[ArrFlatBuilder] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait Dbl6ArrFlatBuilder[B <: ElemDbl6, ArrB <: Dbl6Arr[B]] extends DblNArrFlatBuilder[B, ArrB]
{ type BuffT <: Dbl6Buff[B]
  final override def elemProdSize = 6
}

/** A specialised flat ArrayBuffer[Double] based trait for [[ElemDbl4]]s collections. */
trait Dbl6Buff[A <: ElemDbl6] extends Any with DblNBuff[A]
{ type ArrT <: Dbl6Arr[A]
  override def elemProdSize: Int = 6
  final override def length: Int = unsafeBuffer.length / 6

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit =
  { unsafeBuffer.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3).append(newElem.dbl4).append(newElem.dbl5).append(newElem.dbl6); () }

  def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A
  override def sdIndex(index: Int): A = dblsToT(unsafeBuffer(index * 6), unsafeBuffer(index * 6 + 1), unsafeBuffer(index * 6 + 2),
    unsafeBuffer(index * 6 + 3), unsafeBuffer(index * 6 + 4), unsafeBuffer(index * 6 + 5))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuffer(i * 6) = value.dbl1; unsafeBuffer(i * 6 + 1) = value.dbl2; unsafeBuffer(i * 6 + 2) = value.dbl3;
    unsafeBuffer(i * 6 + 3) = value.dbl4; unsafeBuffer(i * 6 + 3) = value.dbl5;
  }
}