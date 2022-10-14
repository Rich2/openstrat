/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** A class that can be constructed from a fixed number of homogeneous primitive values such as Ints, Doubles or Longs. The final class can be stored
 *  as *  an Array of primitive values. Note the classes that extend this trait do not extend [[Product]] or its numbered sub traits, because the
 *  logical size of the product may not be the same as the number of primitive values, for example a LineSeg is a product of 2 [[Pt2]]s, but is
 *  composed from 4 [[Double]] values. */
trait ElemValueN extends Any with SpecialT

trait ValueNSeqLike[A <: ElemValueN] extends Any with SeqLike[A]
{ type ThisT <: ValueNSeqLike[A]
  /** The number of atomic values, Ints, Doubles, Longs etc that specify / construct an element of this immutable flat Array based collection
   * class. */
  def elemProdSize: Int
}

/** An immutable trait defined by  a collection of homogeneous value products. The underlying array is Array[Double], Array[Int] etc. The descendant
 *  classes include both [[SeqImut]s and classes like polygons and lines. */
trait ValueNSeqDef[A <: ElemValueN] extends Any with ValueNSeqLike[A] with SeqDef[A]
{ type ThisT <: ValueNSeqDef[A]

  /** The total  number of atomic values, Ints, Doubles, Longs etc in the backing Array. */
  def dsLen: Int

  /** Checks if 2 values of the defining sequence are equal. */
  def sdElemEq(a1: A, a2: A): Boolean

  /** Reverses the order of the elements of the defining sequence. */
  def reverseData: ThisT

  /** The number of product elements in this collection. For example in a [[PolygonImp], this is the number of [[Pt2]]s in the [[Polygon]] */
  override def sdLength: Int = dsLen / elemProdSize
}

/** An immutable Arr of homogeneous value products. Currently there is no compelling use case for heterogeneous value products, but the homogeneous
 * name is being used to avoid having to change the name if and when homogeneous value product Arrs are implemented. */
trait ValueNArr[A <: ElemValueN] extends Any with SeqImut[A] with ValueNSeqLike[A]
{ type ThisT <: ValueNArr[A]

  /** The total  number of atomic values, Ints, Doubles, Longs etc in the backing Array. */
  def dsLen: Int

  /** Checks if 2 values of the defining sequence are equal. */
  def sdElemEq(a1: A, a2: A): Boolean

  /** Reverses the order of the elements of the defining sequence. */
  def reverse: ThisT

  /** The number of product elements in this collection. For example in a [[PolygonImp], this is the number of [[Pt2]]s in the [[Polygon]] */
  override def sdLength: Int = dsLen / elemProdSize

  def foldWithPrevious[B](initPrevious: A, initAcc: B)(f: (B, A, A) => B): B =
  { var acc: B = initAcc
    var prev: A = initPrevious
    foreach { newA =>
      acc = f(acc, prev, newA)
      prev = newA
    }
    acc
  }

  /** Find the index of the the first value of this sequence. */
  def findIndex(value: A): OptInt =
  { var count = 0
    var acc: OptInt = NoInt
    var continue = true

    while (continue == true & count < sdLength)
    {
      if (sdElemEq(value, apply(count)))
      { acc = SomeInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }
}

/** Trait for creating the ArrTBuilder. Instances for the [[ArrBuilder]] type class, for classes / traits you control, should go in the companion
 *  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ValueNArrBuilder[B <: ElemValueN, ArrB <: ValueNArr[B]] extends ArrBuilder[B, ArrB]
{ def elemProdSize: Int
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[ValueNArr]] final classes. Instances for the [[ArrFlatBuilder] should go in
 *  the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB```
 *  function. */
trait ValueNArrFlatBuilder[B <: ElemValueN, ArrB <: ValueNArr[B]] extends ArrFlatBuilder[ArrB]
{ def elemProdSize: Int
}

/** Specialised flat arraybuffer based collection class, where the underlying ArrayBuffer element is an atomic value like [[Int]], [[Double]] or
 *  [[Long]]. */
trait ValueNBuff[A <: ElemValueN] extends Any with Sequ[A]
{ type ArrT <: ValueNArr[A]
  def elemProdSize: Int
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
  override def fElemStr: A => String = _.toString
}

/** Class to Persist specialised for [[DatValueNs]] cLasses. */
trait ValueNSeqLikePersist[A <: ElemValueN, M <: ValueNSeqLike[A]] extends PersistCompound[M]
{ /** Atomic Value type normally Double or Int. */
  type VT
  def appendtoBuffer(buf: ArrayBuffer[VT], value: A): Unit
  def fromArray(value: Array[VT]): M
  def fromBuffer(buf: ArrayBuffer[VT]): M
  def newBuffer: ArrayBuffer[VT]
}

/** Helper trait for companion objects of [[ValueNSeqDef]] classes. These are flat Array[Int], Array[Double] etc, flat collection classes. */
trait ValueNSeqLikeCompanion[A <: ElemValueN, AA <: ValueNSeqLike[A]]
{ /** returns a collection class of type ArrA, whose backing Array is uninitialised. */
  def uninitialised(length: Int): AA

  /** This method allows you to map from a DataGen to the ArrA type. */
  /*@deprecated final def deprDataGenMap[T](alb: SeqLike[T])(f: T => A): AA =
  { val res = uninitialised(alb.sdLength)
    var count = 0
    alb.dataForeach { t =>
      res.unsafeSetElem(count, f(t))
      count += 1
    }
    res
  }*/
}