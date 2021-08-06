/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 2 [[Double]]s. These are used as elements in [[Dbl2sSeq]] Array[Double] based collections. */
trait Dbl2Elem extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dblsEqual(that: Dbl2Elem): Boolean = dbl1 == that.dbl1 & dbl2 == that.dbl2
  def dblsApprox(that: Dbl2Elem, delta: Double = 1e-12): Boolean = dbl1.=~(that.dbl1, delta) & dbl2.=~(that.dbl2, delta)
}

/** A specialised immutable, flat Array[Double] based trait defined by a data sequence of a type of [[Dbl2Elem]]s. */
trait Dbl2sData[A <: Dbl2Elem] extends Any with DblNsData[A]
{
  /** Method for creating new data elements from 2 [[Double]]s In the case of [[Dbl2sSeq]] this will be thee type of the elements of the sequence. */
  def dataElem(d1: Double, d2: Double): A

  override def elemProdSize: Int = 2

  override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(2 * index) = elem.dbl1
    arrayUnsafe(2 * index + 1) = elem.dbl2
  }

  override def indexData(index: Int): A = dataElem(arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  def elem1sArray: Array[Double] =
  { val res = new Array[Double](elemsNum)
    var count = 0
    while(count < elemsNum){ res(count) = arrayUnsafe(count * 2); count += 1 }
    res
  }

  def elem2sArray: Array[Double] =
  { val res = new Array[Double](elemsNum)
    var count = 0
    while(count < elemsNum){ res(count) = arrayUnsafe(count * 2 + 1); count += 1 }
    res
  }
}

/** A specialised immutable, flat Array[Double] based sequence of a type of [[Dbl2Elem]]s. */
trait Dbl2sSeq[A <: Dbl2Elem] extends Any with ArrDblNs[A] with Dbl2sData[A]
{ type ThisT <: Dbl2sSeq[A]
  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def getPair(index: Int): (Double, Double) = (arrayUnsafe(2 * index), arrayUnsafe(2 * index + 1))

  def foreachPairTail[U](f: (Double, Double) => U): Unit =
  { var count = 1
    while(count < elemsNum) { f(arrayUnsafe(count * 2), arrayUnsafe(count * 2 + 1)); count += 1 }
  }

  /** Functionally appends the operand of type A. This alphanumeric method is not aliased by the ++ operator, to avoid confusion with numeric operators. */
  def append(op: A): ThisT =
  { val newArray = new Array[Double](elemsNum + elemProdSize)
    arrayUnsafe.copyToArray(newArray)
    newArray(elemsNum) = op.dbl1
    newArray(elemsNum + 1) = op.dbl2
    unsafeFromArray(newArray)
  }

  override def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2)))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl2Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl2Elem]]. The first type parameter is
 *  called B, because it corresponds to the B in ```map[B](f: A => B)(implicit build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2sSeqBuilder[B <: Dbl2Elem, ArrB <: Dbl2sSeq[B]] extends ArrDblNsBuilder[B, ArrB]
{ type BuffT <: Dbl2sBuffer[B]
  final override def elemProdSize = 2
  override def arrSet(arr: ArrB, index: Int, value: B): Unit = { arr.arrayUnsafe(index * 2) = value.dbl1; arr.arrayUnsafe(index * 2 + 1) = value.dbl2}
}

/** Trait for creating the ArrTFlatBuilder type class instances for [[Dbl2Arr]] final classes. Instances for [[SeqFlatBuilder] should go in the
 *  companion object the ArrT final class. The first type parameter is called B, because it corresponds to the B in ```map[B](f: A => B)(implicit
 *  build: ArrTBuilder[B, ArrB]): ArrB``` function. */
trait Dbl2sArrFlatBuilder[B <: Dbl2Elem, ArrB <: Dbl2sSeq[B]] extends ArrDblNsFlatBuilder[B, ArrB]
{ type BuffT <: Dbl2sBuffer[B]
  final override def elemProdSize = 2
}

/** Class for the singleton companion objects of [[Dbl2sSeq]] final classes to extend. */
trait Dbl2sDataCompanion[A <: Dbl2Elem, ArrA <: Dbl2sData[A]] extends DblNsDataCompanion[A, ArrA]
{ final def elemProdSize: Int = 2

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

/** Persists and assists in building [[ArrDblNs]]s. */
abstract class Dbl2sDataPersist[A <: Dbl2Elem, M <: Dbl2sData[A]](typeStr: String) extends DblNsDataPersist[A, M](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1
    buf += value.dbl2
  }

  override def syntaxDepthT(obj: M): Int = 3
  override def showT(obj: M, way: Show.Way, maxPlaces: Int, minPlaces: Int): String = ""
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl2Elem]]s collections. */
trait Dbl2sBuffer[A <: Dbl2Elem] extends Any with BuffDblNs[A]
{ type ArrT <: Dbl2sSeq[A]
  override def elemProdSize: Int = 2
  override def grow(newElem: A): Unit = { unsafeBuff.append(newElem.dbl1).append(newElem.dbl2); () }
  def dblsToT(d1: Double, d2: Double): A
  override def indexData(index: Int): A = dblsToT(unsafeBuff(index * 2), unsafeBuff(index * 2 + 1))
  override def unsafeSetElem(i: Int, value: A): Unit = { unsafeBuff(i * 2) = value.dbl1; unsafeBuff(i * 2 + 1) = value.dbl2 }
  override def fElemStr: A => String = _.toString
}