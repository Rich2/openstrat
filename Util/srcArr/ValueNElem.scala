/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A class that can be constructed from a fixed number of homogeneous primitive values such as Ints, Doubles or Longs. The final class can be stored
 *  as *  an Array of primitive values. Note the classes that extend this trait do not extend [[Product]] or its numbered sub traits, because the
 *  logical size of the product may not be the same as the number of primitive values, for example a LineSeg is a product of 2 [[Pt2]]s, but is
 *  composed from 4 [[Double]] values. */
trait ValueNElem extends Any with SpecialT

trait SeqLikeValueN[A <: ValueNElem] extends Any with SeqLike[A]
{ type ThisT <: SeqLikeValueN[A]
  /** The number of atomic values, Ints, Doubles, Longs etc that specify / construct an element of this immutable flat Array based collection
   * class. */
  def elemProdSize: Int

  /** The total  number of atomic values, Ints, Doubles, Longs etc in the backing Array. */
  def unsafeLength: Int
}

/** An immutable trait defined by  a collection of homogeneous value products. The underlying array is Array[Double], Array[Int] etc. The descendant
 *  classes include both [[Arr]s and classes like polygons and lines. */
trait SeqSpecValueN[A <: ValueNElem] extends Any with SeqLikeValueN[A] with SeqSpec[A]
{ type ThisT <: SeqSpecValueN[A]

  /** Checks if 2 values of the specifying sequence are equal. */
  def ssElemEq(a1: A, a2: A): Boolean

  /** Reverses the order of the elements of the specifying sequence. */
  def reverse: ThisT

  /** The number of product elements in this collection. For example in a [[PolygonImp], this is the number of [[Pt2]]s in the [[Polygon]] */
  override def ssLength: Int = unsafeLength / elemProdSize

  def ssForAll(f: A => Boolean): Boolean =
  { var res = true
    var i = 0
    while (i < ssLength & res)
    { if (!f(ssIndex(i))) res = false
      i += 1
    }
  res
  }
}

/** An immutable Arr of homogeneous value products. Currently there is no compelling use case for heterogeneous value products, but the homogeneous
 * name is being used to avoid having to change the name if and when homogeneous value product Arrs are implemented. */
trait ArrValueN[A <: ValueNElem] extends Any with  ArrNoParam[A] with SeqLikeValueN[A]
{ type ThisT <: ArrValueN[A]

  /** Checks if 2 values of the specifying sequence are equal. */
  def elemEq(a1: A, a2: A): Boolean

  /** The number of product elements in this collection. For example in a [[PolygonImp], this is the number of [[Pt2]]s in the [[Polygon]] */
  override def length: Int = unsafeLength / elemProdSize

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

    while (continue == true & count < length)
    {
      if (elemEq(value, apply(count)))
      { acc = SomeInt(count)
        continue = false
      }
      count += 1
    }
    acc
  }
}

/** Specialised flat arraybuffer based collection class, where the underlying ArrayBuffer element is an atomic value like [[Int]], [[Double]] or
 *  [[Long]]. */
trait BuffValueN[A <: ValueNElem] extends Any with BuffSequ[A]
{ type ArrT <: ArrValueN[A]
  def elemProdSize: Int
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
  override def fElemStr: A => String = _.toString
}

/** Base trait for all [[SeqLikeValueN]] builders. */
trait BuilderSeqLikeValueN[BB <: SeqLike[_]] extends BuilderSeqLike[BB]
{ def elemProdSize: Int
}

/** Map builder for [[SeqLikeValueN]] classes. */
trait BuilderSeqLikeValueNMap[B <: ValueNElem, BB <: SeqLike[B]] extends BuilderSeqLikeValueN[BB] with BuilderSeqLikeMap[B, BB]

/** Constructs [[SeqLikeValueN]] objects via flatMap method. Element type not known at call site. */
trait BuilderSeqLikeValueNFlat[BB <: SeqLikeValueN[_]] extends BuilderSeqLikeValueN[BB] with BuilderSeqLikeFlat[BB]

/** Trait for creating the ArrTBuilder. Instances for the [[BuilderArrMap]] type class, for classes / traits you control, should go in the companion
 *  object of B. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderArrValueNMap[B <: ValueNElem, ArrB <: ArrValueN[B]] extends BuilderSeqLikeValueNMap[B, ArrB] with BuilderArrMap[B, ArrB]
{ type BuffT <: BuffValueN[B]
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[ArrValueN]] final classes. Instances for the [[BuilderArrFlat] should go in
 *  the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB```
 *  function. */
trait BuilderArrValueNFlat[ArrB <: ArrValueN[_]] extends BuilderSeqLikeValueN[ArrB] with BuilderArrFlat[ArrB]