/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer

/** An object that can be constructed from 5 [[Double]]s. These are used in [[ArrDbl5s]] Array[Double] based collections. */
trait ElemDbl5 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
}
/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl5]]s. */
trait DataDbl5s[A <: ElemDbl5] extends Any with DataDblNs[A]
{ /** Method for creating new data elements from 5 [[Double]]s In the case of [[ArrDbl5s]] this will be the type of the elements of the sequence. */
  def dataElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A

  def elemProdSize: Int = 5
  def indexData(index: Int): A = dataElem(arrayUnsafe(5 * index), arrayUnsafe(5 * index + 1), arrayUnsafe(5 * index + 2), arrayUnsafe(5 * index + 3),
    arrayUnsafe(5 * index + 4))

  final override def unsafeSetElem(index: Int, elem: A): Unit =
  { arrayUnsafe(5 * index) = elem.dbl1
    arrayUnsafe(5 * index + 1) = elem.dbl2
    arrayUnsafe(5 * index + 2) = elem.dbl3
    arrayUnsafe(5 * index + 3) = elem.dbl4
    arrayUnsafe(5 * index + 4) = elem.dbl5
  }
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[ElemDbl5]]s. */
trait ArrDbl5s[A <: ElemDbl5] extends Any with ArrDblNs[A] with DataDbl5s[A]
{ def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): A

  def head1: Double = arrayUnsafe(0)
  def head2: Double = arrayUnsafe(1)
  def head3: Double = arrayUnsafe(2)
  def head4: Double = arrayUnsafe(3)
  def head5: Double = arrayUnsafe(4)

  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5)))
}

/** Helper class for companion objects of final [[ArrDbl5s]] classes. */
abstract class DataDbl5sCompanion[A <: ElemDbl5, ArrA <: DataDbl5s[A]]
{
  val factory: Int => ArrA
  def apply(length: Int): ArrA = factory(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { res.arrayUnsafe(count * 5) = elems(count).dbl1
      res.arrayUnsafe(count * 5 + 1) = elems(count).dbl2
      res.arrayUnsafe(count * 5 + 2) = elems(count).dbl3
      res.arrayUnsafe(count * 5 + 3) = elems(count).dbl4
      res.arrayUnsafe(count * 5 + 4) = elems(count).dbl5
      count += 1
    }
    res
  }

  def doubles(elems: Double*): ArrA =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 5)
    var count: Int = 0

    while (count < arrLen)
    { res.arrayUnsafe(count) = elems(count)
      count += 1
    }
    res
  }

  def fromList(list: List[A]): ArrA =
  { val arrLen: Int = list.length * 5
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
      res.arrayUnsafe(count) = rem.head.dbl5
      count += 1
      rem = rem.tail
    }
    res
  }
}

/** Both Persists and Builds [[ArrDbl5s]] Collection classes. */
abstract class DataDbl5sPersist[A <: ElemDbl5, ArrA <: DataDbl5s[A]](typeStr: String) extends DataDblNsPersist[A, ArrA](typeStr)
{
  override def appendtoBuffer(buf: ArrayBuffer[Double], value: A): Unit =
  { buf += value.dbl1
    buf += value.dbl2
    buf += value.dbl3
    buf += value.dbl4
    buf += value.dbl5
  }

  override def syntaxDepthT(obj: ArrA): Int = 3
}