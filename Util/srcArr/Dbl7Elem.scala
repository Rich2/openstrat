/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation._, collection.mutable.ArrayBuffer

/** An object that can be constructed from 7 [[Double]]s. These are used in [[SeqSpecDbl7]] classes including [[ArrDbl7]] sequence collections. */
trait Dbl7Elem extends Any with DblNElem
{ def dbl1: Double
  def dbl2: Double
  def dbl3: Double
  def dbl4: Double
  def dbl5: Double
  def dbl6: Double
  def dbl7: Double

  override def dblForeach(f: Double => Unit): Unit = { f(dbl1); f(dbl2); f(dbl3); f(dbl4); f(dbl5); f(dbl6); f(dbl7) }
  override def dblBufferAppend(buffer: ArrayBuffer[Double]): Unit = buffer.appends(dbl1, dbl2, dbl3, dbl4, dbl5, dbl6, dbl7)
}

/** A class that can be encoded by a sequence of 7 [[Double]]s. Includes [[ArrDbl7]]s and [[SeqSpecDbl7]] */
trait SeqLikeDbl7[A <: Dbl7Elem] extends Any with SeqLikeDblN[A]
{ def elemProdSize: Int = 7

  override def setElemUnsafe(index: Int, newElem: A): Unit =
    unsafeArray.setIndex7(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5, newElem.dbl6, newElem.dbl7)
}

/** A specialised immutable, flat Array[Double] based trait defined by data sequence of a type of [[Dbl7Elem]]s. */
trait SeqSpecDbl7[A <: Dbl7Elem] extends Any with SeqLikeDbl7[A] with SeqSpecDblN[A]
{
  def ssElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): A

  def ssElemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) &
    (a1.dbl5 == a2.dbl5) & (a1.dbl6 == a2.dbl6) & (a1.dbl7 == a2.dbl7)

  def ssIndex(index: Int): A =
  { val offset = 7 * index
    ssElem(unsafeArray(offset), unsafeArray(offset + 1), unsafeArray(offset + 2), unsafeArray(offset + 3), unsafeArray(offset + 4),
      unsafeArray(offset + 5), unsafeArray(offset + 6))
  }
}

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl7Elem]]s. */
trait ArrDbl7[A <: Dbl7Elem] extends Any with ArrDblN[A] with SeqLikeDbl7[A]
{ def head1: Double = unsafeArray(0); def head2: Double = unsafeArray(1); def head3: Double = unsafeArray(2); def head4: Double = unsafeArray(3)
  def head5: Double = unsafeArray(4); def head6: Double = unsafeArray(5); def head7: Double = unsafeArray(6)
  final override def length: Int = unsafeArray.length / 7
  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6, el.dbl7)))

  def apply(index: Int): A =
  { val offset = 7 * index
    newElem(unsafeArray(offset), unsafeArray(offset + 1), unsafeArray(offset + 2), unsafeArray(offset + 3), unsafeArray(offset + 4),
      unsafeArray(offset + 5), unsafeArray(offset + 6))
  }

  def newElem(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): A

  override def elemEq(a1: A, a2: A): Boolean = (a1.dbl1 == a2.dbl1) & (a1.dbl2 == a2.dbl2) & (a1.dbl3 == a2.dbl3) & (a1.dbl4 == a2.dbl4) &
    (a1.dbl5 == a2.dbl5) & (a1.dbl6 == a2.dbl6) & (a1.dbl7 == a2.dbl7)

  @targetName("append") inline final override def +%(operand: A): ThisT =
  { val newArray = new Array[Double](unsafeLength + 7)
    unsafeArray.copyToArray(newArray)
    newArray.setIndex7(length, operand.dbl1, operand.dbl2, operand.dbl3, operand.dbl4, operand.dbl5, operand.dbl6, operand.dbl7)
    fromArray(newArray)
  }
}

/** Helper class for companion objects of final [[SeqSpecDbl7]] sequence-defined classes. */
abstract class CompanionSeqLikeDbl7[A <: Dbl7Elem, ArrA <: SeqLikeDbl7[A]] extends CompanionSeqLikeDblN[A, ArrA]
{ override def elemNumDbls: Int = 7
  def apply(length: Int): ArrA = uninitialised(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = uninitialised(length)
    var i: Int = 0
    while (i < length)
    { res.unsafeArray.setIndex7(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3, elems(i).dbl4, elems(i).dbl5, elems(i).dbl6, elems(i).dbl7)
      i += 1
    }
    res
  }

  def doubles(elems: Double*): ArrA =
  { val arrLen: Int = elems.length
    val res = uninitialised(elems.length / 7)
    var count: Int = 0
    while (count < arrLen) { res.unsafeArray(count) = elems(count); count += 1 }
    res
  }

   def fromList(list: List[A]): ArrA =
   { val res = uninitialised(list.length)
     var count: Int = 0
     var rem = list
     
     while (count < list.length)
     { val offset = count * 7      
       res.unsafeArray(offset) = rem.head.dbl1; res.unsafeArray(offset +  1) = rem.head.dbl2; res.unsafeArray(offset +  2) = rem.head.dbl3;
       res.unsafeArray(offset + 3) = rem.head.dbl4; res.unsafeArray(offset + 4) = rem.head.dbl5; res.unsafeArray(offset + 5) = rem.head.dbl6;
       res.unsafeArray(offset + 7) = rem.head.dbl7
       count += 1
       rem = rem.tail
     }
     res
  }
}