/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.immutable.ArraySeq

/** Extension methods for Array[A] class */
class ArrayExtensions[A](val thisArray: Array[A]) extends AnyVal
{ /** This method and "fHead" removes the need for headOption in the majority of case. Use fHead when are interested in the
   * tail value */
  def headOnly[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisArray.length == 0) ifEmpty else fNonEmpty(thisArray(0))



  /** foreach loop with counter */
  def iForeach(f: (A, Int) => Unit, count: Int = 0): Unit = {
    var counter = count
    var rem = thisArray
    while (rem.nonEmpty) {
      f(rem.head, counter)
      counter += 1
      rem = rem.tail
    }
  }
  def ifEmpty[B](vEmpty: => B, vNonEmpty: => B): B = if (thisArray.isEmpty) vEmpty else vNonEmpty
  def toStrsFold(seperator: String = "", f: A => String = _.toString): String =
    thisArray.ifEmpty("", thisArray.tail.foldLeft(f(thisArray(0)))(_ + seperator + f(_)))
  def toStrsCommaFold(fToStr: A => String = _.toString): String = thisArray.toStrsFold(", ", fToStr)
  def toStrsSemiFold(fToStr: A => String = _.toString): String = thisArray.toStrsFold("; ", fToStr)
  def toStrsCommaParenth(fToStr: A => String = _.toString): String = toStrsCommaFold(fToStr).enParenth
  def toStrsSemiParenth(fToStr: A => String = _.toString): String = toStrsSemiFold(fToStr).enParenth
  def toStr: String = "Arr" + toStrsSemiParenth()
  def toArraySeq: ArraySeq[A] = ArraySeq.unsafeWrapArray(thisArray)
}

/** Extension methods for Array[A <: ValueNElem] class */
class ArrayValueNElemExtensions[A <: ElemValueN](val thisArray: Array[A]) extends AnyVal
{
  def valueProducts[B <: ArrValueNs[A]](implicit factory: Int => B): B = {
    val length = thisArray.length
    val valProds = factory(length)
    var count = 0
    while (count < length) {
      valProds.unsafeSetElem(count, thisArray(count))
      count += 1
    }
    valProds
  }
}