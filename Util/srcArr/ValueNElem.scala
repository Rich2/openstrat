/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.unchecked.uncheckedVariance

/** A class that can be constructed from a fixed number of homogeneous primitive values such as [[Int]]s, [[Double]]s or [[Long]]s. Note the classes that extend
 * this trait do not extend [[Product]] or its numbered sub traits, because the logical size of the product may not be the same as the number of primitive
 * values, for example a [[LineSeg]] is a product of 2 [[Pt2]]s, but is composed from 4 [[Double]] values. */
trait ValueNElem extends Any, SpecialT

/** A [[SeqLike]], a sequence or an object that can be specified by a sequence such as a polygon, composed of elements that can be constructed from a fixed
 * number of homgenious primitive values such as [[Double]]s or [[Int]]s. */
trait SeqLikeValueN[+A <: ValueNElem] extends Any, SeqLike[A]
{ type ThisT <: SeqLikeValueN[A]

  /** The number of atomic values, [[Int]]s, [[Double]]s, [[Long]]s etc that specify / construct an element of this immutable flat Array based collection
   * class. */
  def elemProdSize: Int

  /** Checks if 2 values of the specifying sequence are equal. */
  def elemEq(a1: A @uncheckedVariance, a2: A @uncheckedVariance): Boolean
}

/** An immutable [[SeqLike]] object, that is a sequence or can be specified by a sequence, whose elements can be constructed from a fixed number of primitive
 * values. Hence, not a mutable [[Buff]]. */
trait SeqLikeImutValueN[+A <: ValueNElem] extends Any, SeqLikeValueN[A], SeqLikeBacked[A]
{ type ThisT <: SeqLikeImutValueN[A]
  
  /** The total  number of atomic values, [[Int]]s, [[Double]]s, [[Long]]s etc in the backing Array. */
  def arrayLen: Int
}

/** A [[SeqSpec]]. An immutable object that is not a sequence, but that can be specified by one such as a polyogn, whose elements that can be constructed from a
 * fixed number of homogeneous primitive values such as [[Double]]s or [[Int]]s. */
trait SeqSpecValueN[+A <: ValueNElem] extends Any, SeqLikeImutValueN[A], SeqSpec[A]
{ type ThisT <: SeqSpecValueN[A]

  /** Reverses the order of the elements of the specifying sequence. */
  def reverse: ThisT

  def elemsForAll(f: A => Boolean): Boolean =
  { var res = true
    var i = 0
    while (i < numElems & res)
    { if (!f(elem(i))) res = false
      i += 1
    }
  res
  }
}

/** An immutable [[Arr]] whose elements can be constructed from a fixed number of homogeneous primitive values. */
trait ArrValueN[A <: ValueNElem] extends Any, ArrNoParam[A], SeqLikeImutValueN[A]
{ type ThisT <: ArrValueN[A]

  /** The number of product elements in this collection. For example in a [[PolygonGen]], this is the number of [[Pt2]]s in the [[Polygon]] */
  override def length: Int = arrayLen / elemProdSize

  def foldWithPrevious[B](initPrevious: A, initAcc: B)(f: (B, A, A) => B): B =
  { var acc: B = initAcc
    var prev: A = initPrevious
    foreach { newA =>
      acc = f(acc, prev, newA)
      prev = newA
    }
    acc
  }

  /** Find the index of the first value of this sequence. */
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

/** A [[Buff]], a specialised flat [[Arraybuffer]] based collection class, where the underlying ArrayBuffer element is an atomic value like [[Int]], [[Double]]
 * or [[Long]]. */
trait BuffValueN[A <: ValueNElem] extends Any, Buff[A], SeqLikeValueN[A]
{ type ArrT <: ArrValueN[A]
  def elemProdSize: Int
  def grow(newElem: A): Unit
  def grows(newElems: ArrT): Unit
}

/** [[BuilderBoth]], common trait for map and flatMap builders for [[SeqLike]]s with [[ValueNElem]]s. */
trait BuilderSeqLikeValueN[BB <: SeqLikeImutValueN[?]] extends BuilderSeqLike[BB]
{ def elemProdSize: Int
}

/** Map builder for [[SeqLikeImutValueN]] classes. */
trait BuilderMapSeqLikeValueN[B <: ValueNElem, BB <: SeqLikeImutValueN[B]] extends BuilderSeqLikeValueN[BB], BuilderMapSeqLike[B, BB]

/** Constructs [[SeqLikeImutValueN]] objects via flatMap method. Element type not known at call site. */
trait BuilderFlatSeqLikeValueN[BB <: SeqLikeImutValueN[?]] extends BuilderSeqLikeValueN[BB], BuilderFlatSeqLike[BB]

/** [[BuilderMap]] trait for creating [[Arr]] classes with [[ValueNElem]]s via the map method. Instances for the [[BuilderArrMap]] type class, for classes /
 * traits you control, should go in the companion object of B. The first type parameter is called B, because to corresponds to the B in 
 * ```map(f: A => B): ArrB``` function. */
trait BuilderMapArrValueN[B <: ValueNElem, ArrB <: ArrValueN[B]] extends BuilderMapSeqLikeValueN[B, ArrB], BuilderArrMap[B, ArrB]
{ type BuffT <: BuffValueN[B]
}

/** [[BuilderFlat]] for constructing [[Arr]]s with [[ValueNElem]]s. Instances for the [[BuilderArrFlat] should go in the companion object of the [[Arr]] final
 * class. The first type parameter is called B, because to corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait BuilderFlatArrValueN[ArrB <: ArrValueN[?]] extends BuilderSeqLikeValueN[ArrB], BuilderArrFlat[ArrB]