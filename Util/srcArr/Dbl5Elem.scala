/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 5 [[Double]]s. These are used in [[Dbl5Arr]] Array[Double] based collections. */
trait Dbl5Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4); f(dbl5) }
}

trait Dbl5SeqLike[A <: Dbl5Elem] extends Any with DblNSeqLike[A]
{ def elemProdSize: Int = 5

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
    unsafeArray.setIndex5(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)

  override def dblBufferAppend(buffer: ArrayBuffer[Double], elem: A): Unit = buffer.append5(elem.dbl1, elem.dbl2, elem.dbl3, elem.dbl4, elem.dbl5)
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl5Elem]]s. */
trait Dbl5SeqSpec[A <: Dbl5Elem] extends Any with Dbl5SeqLike[A] with DblNSeqSpec[A]
{  /** Method for creating new specifying sequence elements from 5 [[Double]]s In the case of [[Dbl5Arr]] this will be the type of the elements of the
   * sequence. */
  def ssElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A

  def ssIndex(index: Int): A = ssElem(unsafeArray(5 * index), unsafeArray(5 * index + 1), unsafeArray(5 * index + 2), unsafeArray(5 * index + 3),
    unsafeArray(5 * index + 4))

  override def ssElemEq(a1: A, a2: A): Boolean =
    (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) & (a1.dbl5 == a2.dbl5)
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl5Elem]]s. */
trait Dbl5Arr[A <: Dbl5Elem] extends Any with DblNArr[A] with Dbl5SeqLike[A]
{ def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A
  final override def length: Int = unsafeArray.length / 5
  def head1: Double = unsafeArray(0)
  def head2: Double = unsafeArray(1)
  def head3: Double = unsafeArray(2)
  def head4: Double = unsafeArray(3)
  def head5: Double = unsafeArray(4)

  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5)))

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](unsafeLength + 5)
    unsafeArray.copyToArray(newArray)
    newArray.setIndex5(length, operand.dbl1, operand.dbl2, operand.dbl3, operand.dbl4, operand.dbl5)
    fromArray(newArray)
  }
}

trait Dbl5SeqLikeCommonBuilder[BB <: Dbl5SeqLike[_]] extends DblNSeqLikeCommonBuilder[BB]
{ type BuffT <: Dbl5Buff[_]
  final override def elemProdSize: Int = 5
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl5Arr]] final classes. Instances for the [[ArrMapBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl5Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl5ArrMapBuilder[B <: Dbl5Elem, ArrB <: Dbl5Arr[B]] extends Dbl5SeqLikeCommonBuilder[ArrB] with DblNArrMapBuilder[B, ArrB]
{ type BuffT <: Dbl5Buff[B]

  override def indexSet(seqLike: ArrB, index: Int, elem: B): Unit =
    seqLike.unsafeArray.setIndex5(index, elem.dbl1, elem.dbl2, elem.dbl3, elem.dbl4, elem.dbl5)
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl5Arr]] final classes. Instances for the [[ArrMapBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl5Elem]]. Instances for
 *  [[ArrFlatBuilder] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait Dbl5ArrFlatBuilder[ArrB <: Dbl5Arr[_]] extends Dbl5SeqLikeCommonBuilder[ArrB] with DblNArrFlatBuilder[ArrB]

/** Helper class for companion objects of final [[Dbl5SeqSpec]] classes. */
abstract class Dbl5SeqLikeCompanion[A <: Dbl5Elem, ArrA <: Dbl5SeqLike[A]] extends DblNSeqLikeCompanion[A, ArrA]
{ override def elemNumDbls: Int = 5

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

/** Both Persists and Builds [[Dbl5Arr]] Collection classes. */
abstract class DataDbl5sPersist[A <: Dbl5Elem, ArrA <: Dbl5SeqSpec[A]](val typeStr: String) extends DataDblNsPersist[A, ArrA]
{
  override def appendtoBuffer(buffer: ArrayBuffer[Double], value: A): Unit =
    buffer.append5(value.dbl1, value.dbl2, value.dbl3, value.dbl4, value.dbl5)

  override def syntaxDepthT(obj: ArrA): Int = 3
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl5Elem]]s collections. */
trait Dbl5Buff[A <: Dbl5Elem] extends Any with DblNBuff[A]
{ type ArrT <: Dbl5Arr[A]
  override def elemProdSize: Int = 5
  final override def length: Int = unsafeBuffer.length / 5
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A
  override def grow(newElem: A): Unit = unsafeBuffer.append5(newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)

  override def apply(index: Int): A = newElem(unsafeBuffer(index * 5), unsafeBuffer(index * 5 + 1), unsafeBuffer(index * 5 + 2),
    unsafeBuffer(index * 5 + 3), unsafeBuffer(index * 5 + 4))

  override def setElemUnsafe(i: Int, newElem: A): Unit =
    unsafeBuffer.setIndex5(i, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5)
}