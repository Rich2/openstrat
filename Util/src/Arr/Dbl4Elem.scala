/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 4 [[Double]]s. These are used in [[Dbl4sSeq]] Array[Double] based collections. */
trait Dbl4Elem extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl4Elem]]s. */
trait Dbl4sData[A <: Dbl4Elem] extends Any with DataDblNs[A]
{ /** Method for creating new data elements from 4 [[Double]]s In the case of [[Dbl4sSeq]] this will be the type of the elements of the sequence. */
  def dataElem(d1: Double, d2: Double, d3: Double, d4: Double): A

  override def elemProdSize: Int = 4

  final override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(4 * index) = elem.dbl1
    arrayUnsafe(4 * index + 1) = elem.dbl2
    arrayUnsafe(4 * index + 2) = elem.dbl3
    arrayUnsafe(4 * index + 3) = elem.dbl4
  }
  override def indexData(index: Int): A = dataElem(arrayUnsafe(4 * index), arrayUnsafe(4 * index + 1), arrayUnsafe(4 * index + 2), arrayUnsafe(4 * index + 3))
}
/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl4Elem]]s. */
trait Dbl4sSeq[A <: Dbl4Elem] extends Any with ArrDblNs[A] with Dbl4sData[A]
{ def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def head4: Double = arrayUnsafe(3)

  override def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2, el.dbl3, el.dbl4)))
}

/** Trait for creating the ArrTBuilder type class instances for [[Dbl4Arr]] final classes. Instances for the [[ArrBuilder]] type class, for classes /
 *  traits you control, should go in the companion object of type B, which will extend [[Dbl4Elem]]. The first type parameter is called B, because to
 *  corresponds to the B in ```map(f: A => B): ArrB``` function. */
trait Dbl4sArrBuilder[B <: Dbl4Elem, ArrB <: Dbl4sSeq[B]] extends ArrDblNsBuilder[B, ArrB]
{ type BuffT <: Dbl4sBuffer[B]
  final override def elemProdSize = 4

  override def arrSet(arr: ArrB, index: Int, value: B): Unit =
  { arr.arrayUnsafe(index * 4) = value.dbl1
    arr.arrayUnsafe(index * 4 + 1) = value.dbl2
    arr.arrayUnsafe(index * 4 + 2) = value.dbl3
    arr.arrayUnsafe(index * 4 + 3) = value.dbl4
  }
}
/** Trait for creating the ArrTBuilder and ArrTFlatBuilder type class instances for [[Dbl4Arr]] final classes. Instances for the [[ArrBuilder]] type
 *  class, for classes / traits you control, should go in the companion object of type B, which will extend [[Dbl4Elem]]. Instances for
 *  [[SeqFlatBuilder] should go in the companion object the ArrT final class. The first type parameter is called B, because to corresponds to the B
 *  in ```map(f: A => B): ArrB``` function. */
trait Dbl4sArrFlatBuilder[B <: Dbl4Elem, ArrB <: Dbl4sSeq[B]] extends ArrDblNsFlatBuilder[B, ArrB]
{ type BuffT <: Dbl4sBuffer[B]

  final override def elemProdSize = 4
}

/** Class for the singleton companion objects of [[Dbl4sSeq]] final classes to extend. */
abstract class Dbl4sDataCompanion[A <: Dbl4Elem, ArrA <: Dbl4sData[A]]
{
  val factory: Int => ArrA
  def apply(length: Int): ArrA = factory(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { res.arrayUnsafe(count * 4) = elems(count).dbl1
      res.arrayUnsafe(count * 4 + 1) = elems(count).dbl2
      res.arrayUnsafe(count * 4 + 2) = elems(count).dbl3
      res.arrayUnsafe(count * 4 + 3) = elems(count).dbl4
      count += 1
    }
     res
   }

  def doubles(elems: Double*): ArrA =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 4)
    var count: Int = 0

    while (count < arrLen)
    { res.arrayUnsafe(count) = elems(count)
      count += 1
    }
    res
  }

  def fromList(list: List[A]): ArrA =
  { val arrLen: Int = list.length * 4
    val res = factory(list.length)
    var count: Int = 0
    var rem = list

    while (count < arrLen)
    { res.arrayUnsafe(count) = rem.head.dbl1
      count += 1
      res.arrayUnsafe(count) = rem.head.dbl2
      count += 1
      res.arrayUnsafe(count) = rem.head.dbl3
      count += 1
      res.arrayUnsafe(count) = rem.head.dbl4
      count += 1
      rem = rem.tail
    }
    res
  }
}

/** Persists [[Dble4Elem] Collection classes. */
abstract class Dbl4sDataPersist[A <: Dbl4Elem, ArrA <: Dbl4sData[A]](typeStr: String) extends DataDblNsPersist[A, ArrA](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1
    buf += value.dbl2
    buf += value.dbl3
    buf += value.dbl4
  }

  override def syntaxDepthT(obj: ArrA): Int = 3
}

/** A specialised flat ArrayBuffer[Double] based trait for [[Dbl4Elem]]s collections. */
trait Dbl4sBuffer[A <: Dbl4Elem] extends Any with BuffDblNs[A]
{ type ArrT <: Dbl4sSeq[A]
  override def elemProdSize: Int = 4

  /** Grows the buffer by a single element. */
  override def grow(newElem: A): Unit = { unsafeBuff.append(newElem.dbl1).append(newElem.dbl2).append(newElem.dbl3).append(newElem.dbl4); () }

  def dblsToT(d1: Double, d2: Double, d3: Double, d4: Double): A
  override def indexData(index: Int): A = dblsToT(unsafeBuff(index * 4), unsafeBuff(index * 4 + 1), unsafeBuff(index * 4 + 2), unsafeBuff(index * 4 + 3))

  override def unsafeSetElem(i: Int, value: A): Unit =
  { unsafeBuff(i * 4) = value.dbl1; unsafeBuff(i * 4 + 1) = value.dbl2; unsafeBuff(i * 4 + 2) = value.dbl3; unsafeBuff(i * 4 + 3) = value.dbl4
  }
}