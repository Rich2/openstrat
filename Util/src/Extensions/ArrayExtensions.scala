/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Extension methods for Array[A] class */
class ArrayExtensions[A](val thisArray: Array[A]) extends AnyVal
{ /** This method and "fHead" removes the need for headOption in the majority of case. Use fHead when are interested in the
   * tail value */
  def headOnly[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisArray.length == 0) ifEmpty else fNonEmpty(thisArray(0))

  /** maps to a [[Arr]] of B. */
  def mapArr[B, BB <: Arr[B]](f: A => B)(implicit ev: ArrMapBuilder[B, BB]): BB ={
    val res = ev.uninitialised(thisArray.length)
    iForeach{(a, i) => res.setElemUnsafe(i, f(a)) }
    res
  }

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
}

/** Extension methods for Array[A <: ValueNElem] class */
class ArrayValueNElemExtensions[A <: ValueNElem](val thisArray: Array[A]) extends AnyVal
{
  def valueProducts[B <: ValueNArr[A]](implicit factory: Int => B): B =
  { val length = thisArray.length
    val valProds = factory(length)
    var count = 0
    while (count < length) {
      valProds.setElemUnsafe(count, thisArray(count))
      count += 1
    }
    valProds
  }
}

class ArrayIntExtensions(thisArray: Array[Int])
{ /** sets 2 elements at 2i and 2i + 1. */
  def setIndex2(index: Int, i1: Int, i2: Int): Unit = {
    thisArray(index * 2) = i1; thisArray(index * 2 + 1) = i2
  }

  /** Copies this Array from the given start index to the destination array. Optional parameters for
   * the start index for the destination Array and the number of elements to be copied. */
  def copyTailToArray(sourceStart: Int, dest: Array[Int], destStart: Int = 0, numElems: Int = -8): Unit =
    System.arraycopy(thisArray, sourceStart, dest, destStart, ife(numElems == -8, dest.length, numElems))
}