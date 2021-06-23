/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Double]]s. These are used as elements in [[Dbl2sArr]] Array[Double] based collections. */
trait Dbl2Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dblsEqual(that: Dbl2Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2
  def dblsApprox(that: Dbl2Elem, delta: Double = 1e-12): Boolean = dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta)
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl2Elem]]s. */
trait Dbl2sArr[A <: Dbl2Elem] extends Any with DblNsArr[A]
{ type ThisT <: Dbl2sArr[A]

  override def elemProductNum: Int = 2
  /** Method for creating new elements from 2 Doubles. */
  def elemBuilder(d1: Double, d2: Double): A
  def apply(index: Int): A = elemBuilder(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))
  def getPair(index: Int): (Double, Double) = (arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(2 * index) = elem.dbl1
    arrayUnsafe(2 * index + 1) = elem.dbl2
  }
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)

  def foreachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < elemsLen) { f(arrayUnsafe(count * 2), arrayUnsafe(count * 2 + 1)); count += 1 }
  }

  def elem1sArray: Array[Double] =
  { val res = new Array[Double](elemsLen)
    var count = 0
    while(count < elemsLen){ res(count) = arrayUnsafe(count * 2); count += 1 }
    res
  }

  def elem2sArray: Array[Double] =
  { val res = new Array[Double](elemsLen)
    var count = 0
    while(count < elemsLen){ res(count) = arrayUnsafe(count * 2 + 1); count += 1 }
    res
  }

  /** Functionally appends the operand of type A. This alphanumeric method is not aliased by the ++ operator, to avoid confusion with numeric operators. */
  def append(op: A): ThisT =
  { val newArray = new Array[Double](elemsLen + elemProductNum)
    arrayUnsafe.copyToArray(newArray)
    newArray(elemsLen) = op.dbl1
    newArray(elemsLen + 1) = op.dbl2
    unsafeFromArray(newArray)
  }

  override def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2)))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl2Arr]] final classes. Instances for the [[ArrTBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is
 *  called B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2sArrBuilder[B <: Dbl2Elem, ArrB <: Dbl2sArr[B]] extends DblNsArrBuilder[B, ArrB]
{ type BuffT <: Dbl2sBuffer[B]
  final override def elemSize = 2
  override def arrSet(arr: ArrB, index: Int, value: B): Unit = { arr.arrayUnsafe(index * 2) = value.dbl1; arr.arrayUnsafe(index * 2 + 1) = value.dbl2}
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[Dbl2Arr]] final classes. Instances for [[ArrTFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because it corresponds to the B in ```map[B](f: A => B)(implicit
 *  build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2sArrFlatBuilder[B <: Dbl2Elem, ArrB <: Dbl2sArr[B]] extends DblNsArrFlatBuilder[B, ArrB]
{ type BuffT <: Dbl2sBuffer[B]
  final override def elemSize = 2
}

/** Class for the singleton companion objects of [[Dbl2sArr]] final classes to extend. */
trait Dbl2sArrCompanion[A <: Dbl2Elem, ArrA <: Dbl2sArr[A]] extends DblNsArrCompanion[A, ArrA]
{ final def elemSize: Int = 2

  /** Apply factory method for creating Arrs of [[Dbl2Elem]]s. */
  final def apply(elems: A*): ArrA =
  {
    val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.arrayUnsafe(count * 2) = elems(count).dbl1
      res.arrayUnsafe(count * 2 + 1) = elems(count).dbl2
      count += 1
    }
    res
  }

  def fromList(list: List[A]): ArrA =
  {
    val arrLen: Int = list.length * 2
    val res = uninitialised(list.length)
    var count: Int = 0
    var rem = list

    while (count < arrLen)
    { res.arrayUnsafe(count) = rem.head.dbl1
      count += 1
      res.arrayUnsafe(count) = rem.head.dbl2
      count += 1
      rem = rem.tail
    }
    res
  }
}

/** Persists and assists in building [[DblNsArr]]s. */
abstract class Dbl2sArrPersist[A <: Dbl2Elem, M <: Dbl2sArr[A]](typeStr: String) extends DblNsArrPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1
    buf += value.dbl2
  }

  override def syntaxDepthT(obj: M): Int = 3
  override def showT(obj: M, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ""
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl2Elem]]s collections. */
trait Dbl2sBuffer[A <: Dbl2Elem] extends Any with DblNsBuffer[A]
{ type ArrT <: Dbl2sArr[A]
  override def elemSize: Int = 2
  override def grow(newElem: A): Unit = { buffer.append(newElem.dbl1).append(newElem.dbl2); () }
  def dblsToT(d1: Double, d2: Double): A
  def apply(index: Int): A = dblsToT(buffer(index * 2), buffer(index * 2 + 1))
}