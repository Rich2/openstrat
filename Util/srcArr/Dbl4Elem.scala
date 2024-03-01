/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Double]]s. These are used in [[Dbl4Arr]] Array[Double] based collections. */
trait Dbl4Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]): Unit = buffer.append4(dbl1, dbl2, dbl3, dbl4)
}

/** [[SeqLike]] with [[Dbl4Elem]] elements. */
trait SeqLikeDbl4[A <: Dbl4Elem] extends Any with SeqLikeDblN[A]
{ override def elemProdSize: Int = 4

  final override def setElemUnsafe(index: Int, newElem: A): Unit =
    arrayUnsafe.setIndex4(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4)
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl4Elem]]s. */
trait Dbl4SeqSpec[A <: Dbl4Elem] extends Any with SeqLikeDbl4[A] with SeqSpecDblN[A]
{ /** Method for creating new elements of the specifying sequence from 4 [[Double]]s. */
  def ssElem(d1: Double, d2: Double, d3: Double, d4: Double): A

  override def ssElemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4)
  override def ssIndex(index: Int): A = ssElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))
}
/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl4Elem]]s. */
trait Dbl4Arr[A <: Dbl4Elem] extends Any with ArrDblN[A] with SeqLikeDbl4[A]
{ def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def head4: Double = arrayUnsafe(3)
  final override def length: Int = arrayUnsafe.length / 4
  override def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4)))

  /** Method for creating new elements from 4 [[Double]]s. */
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double): A

  override def elemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4)

  override def apply(index: Int): A =
    newElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 4)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex4(length, operand.dbl1, operand.dbl2, operand.dbl3, operand.dbl4)
    fromArray(newArray)
  }
}

trait BuilderArrDbl4[ArrB <: Dbl4Arr[?]] extends BuilderArrDblN[ArrB]
{ type BuffT <: BuffDbl4[?]
  final override def elemProdSize = 4
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl4Arr]] final classes. Instances for the [[BuilderArrMap]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl4Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDbl4Map[B <: Dbl4Elem, ArrB <: Dbl4Arr[B]] extends BuilderArrDbl4[ArrB] with BuilderArrDblNMap[B, ArrB]
{ type BuffT <: BuffDbl4[B]

  final override def indexSet(seqLike: ArrB, index: Int, newElem: B): Unit =
    seqLike.arrayUnsafe.setIndex4(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4)
}
/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl4Arr]] final classes. Instances for the [[BuilderArrMap]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl4Elem]]. Instances for
 *  [[BuilderArrFlat] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait BuilderArrDbl4Flat[ArrB <: Dbl4Arr[?]] extends BuilderArrDbl4[ArrB] with BuilderArrDblNFlat[ArrB]

/** Class for the singleton companion objects of [[Dbl4SeqSpec]] final classes to extend. */
abstract class CompanionSeqLikeDbl4[A <: Dbl4Elem, AA <: SeqLikeDbl4[A]] extends CompanionSeqLikeDblN[A, AA]
{ /* Apply factory method for [[Dbl4SeqLike]]. If you are constructing the elements inline the tuple4s factory method may be preferred. */
  final def apply(elems: A*): AA =
  { val length = elems.length
    val res = uninitialised(length)
    var i: Int = 0
    while (i < length)
    { res.arrayUnsafe.setIndex4(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3, elems(i).dbl4)
      i += 1
    }
     res
   }

  override def numElemDbls: Int = 4

  /** Constructs the [[SeqLikeDbl4]] from a sequence of [[Tuple4]]s. */
  def tuple4s(tuples: (Double, Double, Double, Double)*): AA =
  { val length = tuples.length
    val res = uninitialised(length)
    var i: Int = 0
    while (i < length) {
      res.arrayUnsafe.setIndex4(i, tuples(i)._1, tuples(i)._2, tuples(i)._3, tuples(i)._4)
      i += 1
    }
    res
  }
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl4Elem]]s collections. */
trait BuffDbl4[A <: Dbl4Elem] extends Any with BuffDblN[A]
{ type ArrT <: Dbl4Arr[A]
  def newElem(d1: Double, d2: Double, d3: Double, d4: Double): A
  override def elemProdSize: Int = 4
  final override def length: Int = unsafeBuffer.length / 4
  override def grow(newElem: A): Unit = unsafeBuffer.append4(newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4)

  override def apply(index: Int): A =
    newElem(unsafeBuffer(index * 4), unsafeBuffer(index * 4 + 1), unsafeBuffer(index * 4 + 2), unsafeBuffer(index * 4 + 3))

  override def setElemUnsafe(i: Int, newElem: A): Unit =unsafeBuffer.setIndex4(i, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4)
}