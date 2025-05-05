/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import annotation.*, collection.mutable.ArrayBuffer, annotation.unchecked.uncheckedVariance

/** An object that can be constructed from 7 [[Double]]s. These are used in [[SeqSpecDbl7]] classes including [[ArrDbl7]] sequence collections. */
trait Dbl7Elem extends Any, DblNElem
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

/** [[SeqLike]] with [[Dbl7Elem]]s that can be encoded by a sequence of 7 [[Double]]s. Includes [[ArrDbl7]], [[SeqSpecDbl7]] and [[BuffDbl7]]. */
trait SeqLikeDbl7[+A <: Dbl7Elem] extends Any, SeqLikeValueN[A]
{ /** Method for creating new specifying sequence element from 7 [[Double]]s. */
  def elemFromDbls(d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): A

  final override def elemProdSize: Int = 7
}

/** [[SeqLikeImut]] with [[Dbl7Elem]]s, that can be encoded by a sequence of 7 [[Double]]s. Includes [[ArrDbl7]]s and [[SeqSpecDbl7]] */
trait SeqLikeImutDbl7[+A <: Dbl7Elem] extends Any, SeqLikeImutDblN[A], SeqLikeDbl7[A]
{ def elem(index: Int): A =
  { val offset = 7 * index
    elemFromDbls(arrayUnsafe(offset), arrayUnsafe(offset + 1), arrayUnsafe(offset + 2), arrayUnsafe(offset + 3), arrayUnsafe(offset + 4),
      arrayUnsafe(offset + 5), arrayUnsafe(offset + 6))
  }

  final override def numElems: Int = arrayLen / 7

  override def setElemUnsafe(index: Int, newElem: A @uncheckedVariance): Unit =
    arrayUnsafe.setIndex7(index, newElem.dbl1, newElem.dbl2, newElem.dbl3, newElem.dbl4, newElem.dbl5, newElem.dbl6, newElem.dbl7)

  final def elemEq(a1: A @uncheckedVariance, a2: A @uncheckedVariance): Boolean = (a1.dbl1 == a2.dbl1) && (a1.dbl2 == a2.dbl2) && (a1.dbl3 == a2.dbl3) &&
    (a1.dbl4 == a2.dbl4) && (a1.dbl5 == a2.dbl5) && (a1.dbl6 == a2.dbl6) && (a1.dbl7 == a2.dbl7)  
}

/** [[SeqSpec]] with [[Dbl7Elem]]s, can be specified by a flat backing [[Array]][Double].. */
trait SeqSpecDbl7[+A <: Dbl7Elem] extends Any, SeqLikeImutDbl7[A], SeqSpecDblN[A]

/** A specialised immutable, flat Array[Double] based collection of a type of [[Dbl7Elem]]s. */
trait ArrDbl7[A <: Dbl7Elem] extends Any, ArrDblN[A], SeqLikeImutDbl7[A]
{ def head1: Double = arrayUnsafe(0); def head2: Double = arrayUnsafe(1); def head3: Double = arrayUnsafe(2); def head4: Double = arrayUnsafe(3)
  def head5: Double = arrayUnsafe(4); def head6: Double = arrayUnsafe(5); def head7: Double = arrayUnsafe(6)
  final override def length: Int = arrayUnsafe.length / 7
  def foreachArr(f: DblArr => Unit): Unit = foreach(el => f(DblArr(el.dbl1, el.dbl2, el.dbl3, el.dbl4, el.dbl5, el.dbl6, el.dbl7)))

  def apply(index: Int): A =
  { val offset = 7 * index
    elemFromDbls(arrayUnsafe(offset), arrayUnsafe(offset + 1), arrayUnsafe(offset + 2), arrayUnsafe(offset + 3), arrayUnsafe(offset + 4),
      arrayUnsafe(offset + 5), arrayUnsafe(offset + 6))
  }

  @targetName("appendElem") inline final def +%(operand: A): ThisT =
  { val newArray = new Array[Double](arrayLen + 7)
    arrayUnsafe.copyToArray(newArray)
    newArray.setIndex7(length, operand.dbl1, operand.dbl2, operand.dbl3, operand.dbl4, operand.dbl5, operand.dbl6, operand.dbl7)
    fromArray(newArray)
  }
}

/** Helper class for companion objects of [[SeqLikeImut]]s with [[Dbl7Elem]]s. */
abstract class CompanionSlDbl7[A <: Dbl7Elem, ArrA <: SeqLikeImutDbl7[A]] extends CompanionSlDblN[A, ArrA]
{ override def numElemDbls: Int = 7
  def apply(length: Int): ArrA = uninitialised(length)

  def apply(elems: A*): ArrA =
  { val length = elems.length
    val res = uninitialised(length)
    var i: Int = 0
    while (i < length)
    { res.arrayUnsafe.setIndex7(i, elems(i).dbl1, elems(i).dbl2, elems(i).dbl3, elems(i).dbl4, elems(i).dbl5, elems(i).dbl6, elems(i).dbl7)
      i += 1
    }
    res
  }

  def doubles(elems: Double*): ArrA =
  { val arrLen: Int = elems.length
    val res = uninitialised(elems.length / 7)
    var count: Int = 0
    while (count < arrLen) { res.arrayUnsafe(count) = elems(count); count += 1 }
    res
  }

   def fromList(list: List[A]): ArrA =
   { val res = uninitialised(list.length)
     var count: Int = 0
     var rem = list
     
     while (count < list.length)
     { val offset = count * 7      
       res.arrayUnsafe(offset) = rem.head.dbl1; res.arrayUnsafe(offset +  1) = rem.head.dbl2; res.arrayUnsafe(offset +  2) = rem.head.dbl3;
       res.arrayUnsafe(offset + 3) = rem.head.dbl4; res.arrayUnsafe(offset + 4) = rem.head.dbl5; res.arrayUnsafe(offset + 5) = rem.head.dbl6;
       res.arrayUnsafe(offset + 7) = rem.head.dbl7
       count += 1
       rem = rem.tail
     }
     res
  }
}