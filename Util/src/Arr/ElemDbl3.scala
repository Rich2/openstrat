/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import scala.collection.mutable.ArrayBuffer

/** An object that can be constructed from 3 [[Double]]s. These are used in [[ArrDbl3s]] Array[Double] based collections. */
trait ElemDbl3 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dblsEqual(that: ElemDbl3): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2 & dbl3 == that.dbl3

  def dblsApprox(that: ElemDbl3, delta: Double = 1e-12): Boolean =
    dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta) & dbl3.=~(that.dbl3, delta)
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl3]]s. */
trait DataDbl3s[A <: ElemDbl3] extends Any with DataDblNs[A]
{ /** Method for creating new data elements from 3 [[Double]]s In the case of [[ArrDbl3s]] this will be the type of the elements of the sequence. */
  def dataElem(d1: Double, d2: Double, d3: Double): A

  override def elemProdSize = 3

  override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(3 * index) = elem.dbl1; arrayUnsafe(3 * index + 1) = elem.dbl2; arrayUnsafe(3 * index + 2) = elem.dbl3
  }

  override def indexData(index: Int): A = dataElem(arrayUnsafe(3 * index), arrayUnsafe(3 * index + 1), arrayUnsafe(3 * index + 2))
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[ElemDbl3]]s. */
trait ArrDbl3s[A <: ElemDbl3] extends Any with ArrDblNs[A] with DataDbl3s[A]
{
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2, el.dbl3)))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl3Arr]] final classes. Instances for the [[ArrBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[ElemDbl3]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait ArrDbl3sBuilder[B <: ElemDbl3, ArrB <: ArrDbl3s[B]] extends ArrDblNsBuilder[B, ArrB]
{ type BuffT <: BuffDbl3s[B]
  final override def elemProdSize = 3

  override def arrSet(arr: ArrB, index: Int, value: B): Unit =
  { arr.arrayUnsafe(index * 3) = value.dbl1; arr.arrayUnsafe(index * 3 + 1) = value.dbl2; arr.arrayUnsafe(index * 3 + 2) = value.dbl3
  }
}

/** Trait for creating the [[ArrFlatBuilder]] type class instances for [[Dbl3Arr]] final classes. Instances for the  for classes / traits you
 *  control, should go in the companion object of the ArrT final class. The first type parameter is called B, because to corresponds to the B in
 *  ```map(f: A => B): ArrB``` function. */
trait Dbl3sArrFlatBuilder[B <: ElemDbl3, ArrB <: ArrDbl3s[B]] extends ArrDblNsFlatBuilder[B, ArrB]
{ type BuffT <: BuffDbl3s[B]
  final override def elemProdSize = 3
}

/** Persists [[ArrDbl3s]]s. */
abstract class DataDbl3sPersist[A <: ElemDbl3, M <: DataDbl3s[A]](typeStr: String) extends DataDblNsPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1
    buf += value.dbl2
    buf += value.dbl3
  }

  override def syntaxDepthT(obj: M): Int = 3
  override def showT(obj: M, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ""
}

/** Class for the singleton companion objects of [[ArrDbl3s]] final classes to extend. */
abstract class DataDbl3sCompanion[A <: ElemDbl3, ArrA <: DataDbl3s[A]] extends DataDblNsCompanion[A, ArrA]
{ final override def elemProdSize: Int = 3

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = uninitialised(length)
    var count: Int = 0

    while (count < length)
    { res.arrayUnsafe(count * 3) = elems(count).dbl1;  res.arrayUnsafe(count * 3 + 1) = elems(count).dbl2;
      res.arrayUnsafe(count * 3 + 2) = elems(count).dbl3
      count += 1
    }
    res
  }
}

/** A specialised flat ArrayBuffer[Double] based trait for [[ElemDbl3]]s collections. */
trait BuffDbl3s[A <: ElemDbl3] extends Any with BuffDblNs[A]
{ type ArrT <: ArrDbl3s[A]
  override def elemProdSize: Int = 3

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit = { unsafeBuff.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3); () }

  def dblsToT(d1: Double, d2: Double, d3: Double): A
  def indexData(index: Int): A = dblsToT(unsafeBuff(index * 3), unsafeBuff(index * 3 + 1), unsafeBuff(index * 3 + 2))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuff(i * 4) = value.dbl1; unsafeBuff(i * 4 + 1) = value.dbl2; unsafeBuff(i * 4 + 2) = value.dbl3
  }
}