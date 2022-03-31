/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** An object that can be constructed from 6 [[Double]]s. These are used in [[Dbl6Arr]] Array[Double] based collections. */
trait ElemDbl6 extends Any with ElemDblN
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[ElemDbl6]]s. */
trait Dbl6SeqDef[A <: ElemDbl6] extends Any with DblNSeqDef[A]
{ def elemProdSize: Int = 6
  def dataElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): A

  def sdIndex(index: Int): A =
  { val offset = index * 6
    dataElem(unsafeArray(offset), unsafeArray(offset + 1), unsafeArray(offset + 2), unsafeArray(offset + 3), unsafeArray(offset + 4), unsafeArray(offset + 5))
  }
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[ElemDbl6]]s. */
trait Dbl6Arr[A <: ElemDbl6] extends Any with DblNArr[A] with Dbl6SeqDef[A]
{ final override def length: Int = unsafeArray.length / 6

  def setElem(index: Int, elem: A): Unit =
  { val offset = index * 6
    unsafeArray(offset) = elem.dbl1; unsafeArray(offset + 1) = elem.dbl2; unsafeArray(offset + 2) = elem.dbl3; unsafeArray(offset + 3) = elem.dbl4; unsafeArray(offset + 4) = elem.dbl5
    unsafeArray(offset + 5) = elem.dbl6
  }

  def head1: Double = unsafeArray(0); def head2: Double = unsafeArray(1); def head3: Double = unsafeArray(2); def head4: Double = unsafeArray(3); def head5: Double = unsafeArray(4)
  def head6: Double = unsafeArray(5)

  def foreachArr(f: Dbls => Unit): Unit = foreach(el => f(Dbls(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6)))
}

/** Helper class for companion objects of final [[Dbl6SeqDef]] classes. */
abstract class Dbl6SeqDefCompanion[A <: ElemDbl6, ArrA <: Dbl6SeqDef[A]]
{ val factory: Int => ArrA
  def apply(length: Int): ArrA = factory(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = factory(length)
    var count: Int = 0

    while (count < length)
    { val offset = count * 6
      res.unsafeArray(offset) = elems(count).dbl1; res.unsafeArray(offset + 1) = elems(count).dbl2; res.unsafeArray(offset + 2) = elems(count).dbl3
      res.unsafeArray(offset + 3) = elems(count).dbl4; res.unsafeArray(offset + 4) = elems(count).dbl5; res.unsafeArray(offset + 5) = elems(count).dbl6
      count += 1
    }
    res
  }

  def doubles(elems: Double*): ArrA =
  { val arrLen: Int = elems.length
    val res = factory(elems.length / 6)
    var count: Int = 0
    while (count < arrLen) { res.unsafeArray(count) = elems(count); count += 1 }
    res
  }

  def fromList(list: List[A]): ArrA =
  { val res = factory(list.length)
    var count: Int = 0
    var rem = list
     
    while (count < list.length)
    { val offset = count * 6
      res.unsafeArray(offset) = rem.head.dbl1; res.unsafeArray(offset +  1) = rem.head.dbl2; res.unsafeArray(offset +  2) = rem.head.dbl3; res.unsafeArray(offset +  3) = rem.head.dbl4
      res.unsafeArray(offset +  4)= rem.head.dbl5; res.unsafeArray(offset + 5 )= rem.head.dbl6
      count += 1
      rem = rem.tail
    }
    res
  }
}