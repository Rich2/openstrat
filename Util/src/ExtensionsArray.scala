/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import collection.mutable.ArrayBuffer, reflect.ClassTag

/** Extension methods for Array[A] class */
class ArrayExtensions[A](val thisArray: Array[A]) extends AnyVal
{ /** This method and "fHead" removes the need for headOption in the majority of cases. */
  def headElse(ifEmpty: => A): A = if (thisArray.length == 0) ifEmpty else thisArray(0)

  /** This method and "fHead" removes the need for headOption in the majority of cases. */
  def headElseMap[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisArray.length == 0) ifEmpty else fNonEmpty(thisArray(0))

  /** maps to this [[Array]] to an [[Arr]] of B. if this Array is null the Arr will have length 0. */
  def mapArr[B, ArrB <: Arr[B]](f: A => B)(implicit ev: BuilderArrMap[B, ArrB]): ArrB =
  { val len: Int = if(thisArray == null) 0 else thisArray.length
    val res: ArrB = ev.uninitialised(len)
    var i = 0
    while (i < len)
    { res.setElemUnsafe(i, f(thisArray(i)))
      i += 1
    }
    res
  }

  /** Extension method to convert to [[RArr]]. If this [[Array]] is null the [[RArr]] will have length 0. */
  def toRArr(implicit ct: ClassTag[A]) =
  { val array2 = if (thisArray == null) new Array[A](0) else thisArray
    new RArr[A](thisArray)
  }

  /** foreach loop with counter */
  def iForeach(f: (A, Int) => Unit, count: Int = 0): Unit =
  { var counter = count
    var rem = thisArray
    while (rem.nonEmpty)
    { f(rem.head, counter)
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

  /** Extension method. Removes element at given index and returns a new Array.  */
  def removeAt(i: Int)(implicit ct: ClassTag[A]): Array[A] =
  { val oldLength = thisArray.length
    ifExcep(i >= oldLength | i < 0, s"$i index is out or range for this Array")
    val newLength = (oldLength - 1).max0
    val newArray = new Array[A](newLength)
    iUntilForeach(i){j => newArray(j) = thisArray(j)}
    iUntilForeach(i + 1, oldLength){j => newArray(j - 1) = thisArray(j)}
    newArray
  }

  /** Returns [[Some]] of the indexed value if the value exists else returns [[None]]. */
  def applyOpt(index: Int): Option[A] = if(thisArray.length > index) Some(thisArray(index)) else None
}

/** Extension methods for Array[A <: ValueNElem] class */
class ArrayValueNElemExtensions[A <: ValueNElem](val thisArray: Array[A]) extends AnyVal
{
  def valueProducts[B <: ArrValueN[A]](implicit factory: Int => B): B =
  { val length = thisArray.length
    val valProds = factory(length)
    var count = 0
    while (count < length)
    { valProds.setElemUnsafe(count, thisArray(count))
      count += 1
    }
    valProds
  }
}

class ArrayIntExtensions(thisArray: Array[Int])
{ /** sets 2 elements at 2i and 2i + 1. */
  def setIndex2(index: Int, i1: Int, i2: Int): Unit = { thisArray(index * 2) = i1; thisArray(index * 2 + 1) = i2 }

  /** sets 3 elements at 3i, 3i + 1, 3i + 2. */
  def setIndex3(index: Int, i1: Int, i2: Int, i3: Int): Unit = { thisArray(index * 3) = i1; thisArray(index * 3 + 1) = i2
    thisArray(index * 3 + 2) = i3 }

  /** sets 4 elements at 4i, 4i + 1, 4i + 2, 4i + 3. */
  def setIndex4(index: Int, i1: Int, i2: Int, i3: Int, i4: Int): Unit = { thisArray(index * 4) = i1; thisArray(index * 4 + 1) = i2
    thisArray(index * 4 + 2) = i3; thisArray(index * 4 + 3) = i4 }

  /** sets 5 elements at 5i, 5i + 1, 5i + 2, 5i + 3, 5i + 4. */
  def setIndex5(index: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Unit = { thisArray(index * 5) = i1; thisArray(index * 5 + 1) = i2
    thisArray(index * 5 + 2) = i3; thisArray(index * 5 + 3) = i4; thisArray(index * 5 + 4) = i5 }

  /** sets 6 elements at 6i, 6i + 1, 6i + 2, 6i + 3, 6i + 4, 6i + 5. */
  def setIndex6(index: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): Unit = { thisArray(index * 6) = i1; thisArray(index * 6 + 1) = i2
    thisArray(index * 6 + 2) = i3; thisArray(index * 6 + 3) = i4; thisArray(index * 6 + 4) = i5; thisArray(index * 6 + 5) = i6 }

  /** Copies this Array from the given start index to the destination array. Optional parameters for the start index for the destination Array and the
   *  number of elements to be copied. */
  def copyDropToArray(sourceStart: Int, dest: Array[Int], destStart: Int = 0, numElems: Int = -8): Unit =
    System.arraycopy(thisArray, sourceStart, dest, destStart, ife(numElems == -8, dest.length, numElems))
}

class BufferIntExtensions(thisBuffer: ArrayBuffer[Int])
{ /** sets 2 elements at 2i and 2i + 1. */
  @inline def setIndex2(index: Int, i1: Int, i2: Int): Unit = { thisBuffer(index * 2) = i1; thisBuffer(index * 2 + 1) = i2 }

  /** sets 3 elements at 3i, 3i + 1, 3i + 2. */
  def setIndex3(index: Int, i1: Int, i2: Int, i3: Int): Unit = { thisBuffer(index * 3) = i1; thisBuffer(index * 3 + 1) = i2
    thisBuffer(index * 3 + 2) = i3
  }

  /** sets 4 elements at 4i, 4i + 1, 4i + 2, 4i + 3. */
  def setIndex4(index: Int, i1: Int, i2: Int, i3: Int, i4: Int): Unit = { thisBuffer(index * 4) = i1; thisBuffer(index * 4 + 1) = i2
    thisBuffer(index * 4 + 2) = i3; thisBuffer(index * 4 + 3) = i4 }

  /** sets 5 elements at 5i, 5i + 1, 5i + 2, 5i + 3, 5i + 4. */
  def setIndex5(index: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int): Unit = { thisBuffer(index * 5) = i1; thisBuffer(index * 5 + 1) = i2
    thisBuffer(index * 5 + 2) = i3; thisBuffer(index * 5 + 3) = i4; thisBuffer(index * 5 + 4) = i5 }

  /** sets 6 elements at 6i, 6i + 1, 6i + 2, 6i + 3, 6i + 4, 6i + 5. */
  def setIndex6(index: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int): Unit = { thisBuffer(index * 6) = i1; thisBuffer(index * 6 + 1) = i2
    thisBuffer(index * 6 + 2) = i3; thisBuffer(index * 6 + 3) = i4; thisBuffer(index * 6 + 4) = i5; thisBuffer(index * 6 + 5) = i6 }

  /** Appends 2 [[Int]] elements to this [[ArrayBuffer]][Int]. */
  @inline def append2(int1: Int, int2: Int): Unit = { thisBuffer.append(int1); thisBuffer.append(int2) }

  /** Appends 3 [[Int]] elements to this [[ArrayBuffer]][Int]. */
  @inline def append3(int1: Int, int2: Int, int3: Int): Unit = { thisBuffer.append(int1); thisBuffer.append(int2); thisBuffer.append(int3) }

  /** Appends 4 [[Int]] elements to this [[ArrayBuffer]][Int]. */
  @inline def append4(int1: Int, int2: Int, int3: Int, int4: Int): Unit = { thisBuffer.append(int1); thisBuffer.append(int2); thisBuffer.append(int3)
    thisBuffer.append(int4) }

  /** Appends 5 [[Int]] elements to this [[ArrayBuffer]][Int]. */
  @inline def append5(int1: Int, int2: Int, int3: Int, int4: Int, int5: Int): Unit = { thisBuffer.append(int1); thisBuffer.append(int2);
    thisBuffer.append(int3); thisBuffer.append(int4); thisBuffer.append(int5) }

  /** Appends 6 [[Int]] elements to this [[ArrayBuffer]][Int]. */
  @inline def append6(int1: Int, int2: Int, int3: Int, int4: Int, int5: Int, int6: Int): Unit = { thisBuffer.append(int1); thisBuffer.append(int2);
    thisBuffer.append(int3); thisBuffer.append(int4); thisBuffer.append(int5); thisBuffer.append(int6) }
}

class ArrayDblExtensions(thisArray: Array[Double])
{ /** sets 2 elements at 2i and 2i + 1. */
  def setIndex2(index: Int, d1: Double, d2: Double): Unit = { thisArray(index * 2) = d1; thisArray(index * 2 + 1) = d2 }

  /** sets 3 elements at 3i and 3i + 1, 3i + 2. */
  def setIndex3(index: Int, d1: Double, d2: Double, d3: Double): Unit = { thisArray(index * 3) = d1; thisArray(index * 3 + 1) = d2
    thisArray(index * 3 + 2) = d3 }

  /** sets 4 elements at 4i and 4i + 1, 4i + 2, 4i + 3. */
  def setIndex4(index: Int, d1: Double, d2: Double, d3: Double, d4: Double): Unit = { thisArray(index * 4) = d1; thisArray(index * 4 + 1) = d2
    thisArray(index * 4 + 2) = d3; thisArray(index * 4 + 3) = d4
  }

  /** sets 5 elements at 5i and 5i + 1, 5i + 2, 5i + 3, 5i + 4. */
  def setIndex5(index: Int, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): Unit = { thisArray(index * 5) = d1
    thisArray(index * 5 + 1) = d2; thisArray(index * 5 + 2) = d3; thisArray(index * 5 + 3) = d4; thisArray(index * 5 + 4) = d5 }

  /** sets 6 elements at 6i and 6i + 1, 6i + 2, 6i + 3, 6i + 4, 6i + 5. */
  def setIndex6(index: Int, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): Unit =
  { thisArray(index * 6) = d1; thisArray(index * 6 + 1) = d2; thisArray(index * 6 + 2) = d3; thisArray(index * 6 + 3) = d4
    thisArray(index * 6 + 4) = d5; thisArray(index * 6 + 5) = d6
  }

  /** sets 7 elements at 7i and 7i + 1, 7i + 2, 7i + 3, 7i + 4, 7i + 5, 7i + 6. */
  def setIndex7(index: Int, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): Unit =
  { thisArray(index * 7) = d1; thisArray(index * 7 + 1) = d2; thisArray(index * 7 + 2) = d3; thisArray(index * 7 + 3) = d4
    thisArray(index * 7 + 4) = d5; thisArray(index * 7 + 5) = d6; thisArray(index * 7 + 6) = d7
  }

  /** Copies this Array from the given start index to the destination array. Optional parameters for the start index for the destination Array and the
   *  number of elements to be copied. */
  def copyDropToArray(sourceStart: Int, dest: Array[Double], destStart: Int = 0, numElems: Int = -8): Unit =
    System.arraycopy(thisArray, sourceStart, dest, destStart, ife(numElems == -8, dest.length, numElems))
}

class BufferDblExtensions(thisBuffer: ArrayBuffer[Double]) {
  /** sets 2 [[Double]] elements at 2i and 2i + 1. */
  @inline def setIndex2(index: Int, d1: Double, d2: Double): Unit = { thisBuffer(index * 2) = d1; thisBuffer(index * 2 + 1) = d2 }

  /** sets 3 [[Double]] elements at 3i and 3i + 1, 3i + 2. */
  @inline def setIndex3(index: Int, d1: Double, d2: Double, d3: Double): Unit = { thisBuffer(index * 3) = d1; thisBuffer(index * 3 + 1) = d2
    thisBuffer(index * 3 + 2) = d3 }

  /** sets 4 [[Double]] elements at 4i and 4i + 1, 4i + 2, 4i + 3. */
  @inline def setIndex4(index: Int, d1: Double, d2: Double, d3: Double, d4: Double): Unit = { thisBuffer(index * 4) = d1
    thisBuffer(index * 4 + 1) = d2; thisBuffer(index * 4 + 2) = d3; thisBuffer(index * 4 + 3) = d4 }

  /** sets 5 [[Double]] elements at 5i and 5i + 1, 5i + 2, 5i + 3. 5i + 4. */
  @inline def setIndex5(index: Int, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double): Unit = { thisBuffer(index * 5) = d1
    thisBuffer(index * 5 + 1) = d2; thisBuffer(index * 5 + 2) = d3; thisBuffer(index * 5 + 3) = d4; thisBuffer(index * 5 + 4) = d5 }

  /** sets 6 elements at 6i and 6i + 1, 6i + 2, 6i + 3, 6i + 4, 6i + 5. */
  def setIndex6(index: Int, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double): Unit =
  { thisBuffer(index * 6) = d1; thisBuffer(index * 6 + 1) = d2; thisBuffer(index * 6 + 2) = d3; thisBuffer(index * 6 + 3) = d4
    thisBuffer(index * 6 + 4) = d5; thisBuffer(index * 6 + 5) = d6
  }

  /** sets 7 elements at 7i and 7i + 1, 7i + 2, 7i + 3, 7i + 4, 7i + 5, 7i + 6. */
  def setIndex7(index: Int, d1: Double, d2: Double, d3: Double, d4: Double, d5: Double, d6: Double, d7: Double): Unit =
  { thisBuffer(index * 7) = d1; thisBuffer(index * 7 + 1) = d2; thisBuffer(index * 7 + 2) = d3; thisBuffer(index * 7 + 3) = d4
    thisBuffer(index * 7 + 4) = d5; thisBuffer(index * 7 + 5) = d6; thisBuffer(index * 7 + 6) = d6
  }

  /** Appends 2 [[Double]] elements to this [[ArrayBuffer]][Double]. */
  @inline def append2(dbl1: Double, dbl2: Double): Unit = { thisBuffer.append(dbl1); thisBuffer.append(dbl2) }

  /** Appends 3 [[Double]] elements to this [[ArrayBuffer]][Double]. */
  @inline def append3(dbl1: Double, dbl2: Double, dbl3: Double): Unit = { thisBuffer.append(dbl1); thisBuffer.append(dbl2); thisBuffer.append(dbl3) }

  /** Appends 4 [[Double]] elements to this [[ArrayBuffer]][Double]. */
  @inline def append4(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double): Unit = { thisBuffer.append(dbl1); thisBuffer.append(dbl2)
    thisBuffer.append(dbl3); thisBuffer.append(dbl4) }

  /** Appends 5 [[Double]] elements to this [[ArrayBuffer]][Double]. */
  @inline def append5(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, dbl5: Double): Unit = { thisBuffer.append(dbl1); thisBuffer.append(dbl2)
    thisBuffer.append(dbl3); thisBuffer.append(dbl4); thisBuffer.append(dbl5) }

  /** Appends 6 [[Double]] elements to this [[ArrayBuffer]][Double]. */
  @inline def append6(dbl1: Double, dbl2: Double, dbl3: Double, dbl4: Double, dbl5: Double, dbl6: Double): Unit = { thisBuffer.append(dbl1)
    thisBuffer.append(dbl2); thisBuffer.append(dbl3); thisBuffer.append(dbl4); thisBuffer.append(dbl5); thisBuffer.append(dbl6) }

  def appends(dbls: Double *): Unit = dbls.foreach(thisBuffer.append(_))
}

/** Needs Changing. */
class BufferRefExtensions[A <: AnyRef](thisBuff: ArrayBuffer[A])
{ /** Converts this ArrayBuffer straight to an [[RArr]]. */
  @inline def toArr(implicit ct: ClassTag[A]): RArr[A] = new RArr[A](thisBuff.toArray[A])
  
  /** Utility method to implicitly find the [[ClassTag]] and produced convert to [[RArr]] wrapped in [[Succ]]. */
  def succRArr(implicit ct: ClassTag[A]): Succ[RArr[A]] = Succ(new RArr(thisBuff.toArray))

  def toReverseRefs(implicit ct: ClassTag[A]): RArr[A] =
  {  val len = thisBuff.length
    val acc: Array[A] = new Array[A](len)
    var count = 0

    while (count < len)
    { acc(count) = thisBuff(len - 1 - count)
      count += 1
    }
    new RArr(acc)
  }
}