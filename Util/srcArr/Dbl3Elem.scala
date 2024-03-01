/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, scala.collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Double]]s. These are used in [[ArrDbl3]] Array[Double] based collections. */
trait Dbl3Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dblsEqual(that: Dbl3Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2 & dbl3 == that.dbl3

  def dblsApprox(that: Dbl3Elem, delta: Double = 1e-12): Boolean =
    dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta) & dbl3.=~(that.dbl3, delta)

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]) : Unit = buffer.append3(dbl1, dbl2, dbl3)
}

/** A Sequence like class of [[Dbl3Elem]] elements that can be constructed from 3 [[Double]]s. */
trait SeqLikeDbl3[A <: Dbl3Elem] extends Any with SeqLikeDblN[A]
{ override def elemProdSize = 3
  override def setElemUnsafe(index: Int, newElem: A): Unit = arrayUnsafe.setIndex3(index, newElem.dbl1, newElem.dbl2, newElem.dbl3)
}

/** Class for the singleton companion objects of [[Dbl3seqLike]] final classes to extend. */
abstract class CompanionSeqLikeDbl3[A <: Dbl3Elem, ArrA <: SeqLikeDbl3[A]] extends CompanionSeqLikeDblN[A, ArrA]
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

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl3Elem]]s. */
trait SeqSpecDbl3[A <: Dbl3Elem] extends Any with SeqLikeDbl3[A] with SeqSpecDblN[A]
{ /** Method for creating new elements of the specifying-sequence from 3 [[Double]]s. */
  def ssElem(d1: Double, d2: Double, d3: Double): A

  override def ssElemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3)
  override def ssIndex(index: Int): A = ssElem(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[Dbl3Elem]]s. */
trait ArrDbl3[A <: Dbl3Elem] extends Any with ArrDblN[A] with SeqLikeDbl3[A]
{ final override def length: Int = arrayUnsafe.length / 3
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3)))

  /** Method for creating new elements from 3 [[Double]]s. */
  def newElem(d1: Double, d2: Double, d3: Double): A

  final override def elemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3)
  final override def apply(index: Int): A = newElem(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 3)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex3(length, operand.dbl1, operand.dbl2, operand.dbl3)
    fromArray(newArray)
  }
}

/** Constructs [[SeqLikeDbl3]] objects. */
trait BuilderSeqLikeDbl3[BB <: SeqLikeDbl3[?]] extends BuilderSeqLikeDblN[BB]
{ type BuffT <: Dbl3Buff[?]
  final override def elemProdSize = 3
}

trait BuilderSeqLikeDbl3Map[B <: Dbl3Elem, BB <: SeqLikeDbl3[B]] extends BuilderSeqLikeDbl3[BB] with BuilderSeqLikeDblNMap[B, BB]
{ type BuffT <: Dbl3Buff[B]
  final override def indexSet(seqLike: BB, index: Int, newElem: B): Unit = seqLike.arrayUnsafe.setIndex3(index, newElem.dbl1, newElem.dbl2, newElem.dbl3)
}

/** Trait for creating the ArrTBuilder type class instances for [[ArrDbl3]] final classes. Instances for the [[BuilderArrMap]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDbl3Map[B <: Dbl3Elem, ArrB <: ArrDbl3[B]] extends BuilderSeqLikeDbl3Map[B, ArrB] with BuilderArrDblNMap[B, ArrB]

/** Trait for creating the [[BuilderArrFlat]] type class instances for [[ArrDbl3]] final classes. Instances for the  for classes / traits you
 *  control, should go in the companion object of the ArrT final class. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait BuilderArrDbl3Flat[ArrB <: ArrDbl3[?]] extends BuilderSeqLikeDbl3[ArrB] with BuilderArrDblNFlat[ArrB]

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl3Elem]]s collections. */
trait Dbl3Buff[A <: Dbl3Elem] extends Any with BuffDblN[A]
{ type ArrT <: ArrDbl3[A]

  /** Constructs a new element of this buffer from 3 [[Double]]s. */
  def newElem(d1: Double, d2: Double, d3: Double): A

  override def elemProdSize: Int = 3
  final override def length: Int = unsafeBuffer.length / 3
  override def grow(newElem: A): Unit = unsafeBuffer.append3(newElem.dbl1, newElem.dbl2, newElem.dbl3)
  def apply(index: Int): A = newElem(unsafeBuffer(index * 3), unsafeBuffer(index * 3 + 1), unsafeBuffer(index * 3 + 2))

  override def setElemUnsafe(i: Int, newElem: A): Unit = unsafeBuffer.setIndex3(i, newElem.dbl1, newElem.dbl2, newElem.dbl3)
}