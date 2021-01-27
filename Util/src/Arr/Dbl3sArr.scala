/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from 3 [[Double]]s. These are used in [[Dbl3sArr]] Array[Double] based collections. */
trait Dbl3Elem extends Any with ValueNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl3Elem]]s. */
trait Dbl3sArr[A <: Dbl3Elem] extends Any with DblNsArr[A]
{ def productSize = 3
  def newElem(d1: Double, d2: Double, d3: Double): A
  def apply(index: Int): A = newElem(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))
  override def unsafeSetElem(index: Int, elem: A): Unit = { arrayUnsafe(3 * index) = elem.dbl1; arrayUnsafe(3 * index + 1) = elem.dbl2; arrayUnsafe(3 * index + 2) = elem.dbl3 }
  def head1: Double = arrayUnsafe(0); def head2: Double = arrayUnsafe(1); def head3: Double = arrayUnsafe(2)

  //def toArrs: ArrOld[ArrOld[Double]] = mapArrSeq(el => ArrOld(el.dbl1, el.dbl2, el.dbl3))
  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2, el.dbl3)))
}

/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl3Arr]] final classes. Instances for the [[ArrTBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl3Elem]]. Instances for
 *  [[ArrTFlatBuilder] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait Dbl3sArrBuilders[A <: Dbl3Elem, ArrA <: Dbl3sArr[A]] extends DblNsArrBuilders[A, ArrA]
{ type BuffT <: Dbl3sBuffer[A]

  final override def elemSize = 3
  //def newArray(length: Int): Array[Double] = new Array[Double](length * 2)

  override def arrSet(arr: ArrA, index: Int, value: A): Unit =
  { arr.arrayUnsafe(index * 3) = value.dbl1; arr.arrayUnsafe(index * 3 + 1) = value.dbl2; arr.arrayUnsafe(index * 3 + 2) = value.dbl3
  }

  override def buffGrow(buff: BuffT, value: A): Unit = ??? //{ buff.append(value.dbl1,) ??? //buff.buffer.append(value)
}

/** Class for the singleton companion objects of [[Dbl3sArr]] final classes to extend. */
abstract class Dbl3sArrCompanion[A <: Dbl3Elem, ArrA <: Dbl3sArr[A]]
{ val factory: Int => ArrA
  def apply(length: Int): ArrA = factory(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { res.arrayUnsafe(count * 3) = elems(count).dbl1;  res.arrayUnsafe(count * 3 + 1) = elems(count).dbl2;
      res.arrayUnsafe(count * 3 + 2) = elems(count).dbl3
      count += 1
    }
    res
  }
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl3Elem]]s collections. */
trait Dbl3sBuffer[A <: Dbl3Elem] extends Any with DblNsBuffer[A]
{ type ArrT <: Dbl3sArr[A]
  override def elemSize: Int = 3

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit = { buffer.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3); () }

  def dblsToT(d1: Double, d2: Double, d3: Double): A
  def apply(index: Int): A = dblsToT(buffer(index * 3), buffer(index * 3 + 1), buffer(index * 3 + 2))
}