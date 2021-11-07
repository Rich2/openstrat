/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Extension methods for [[Iterable]][A]. */
class IterableExtensions[A](val thisIter: Iterable[A]) extends AnyVal
{ /** This method and "fHead" removes the need for headOption in the majority of case. Use fHead when are interested in the tail value */
  def headOnly[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisIter.isEmpty) ifEmpty else fNonEmpty(thisIter.head)
  def fLast[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisIter.isEmpty) ifEmpty else fNonEmpty(thisIter.last)
  def ifEmpty[B](vEmpty: => B, vNonEmpty: => B): B = if (thisIter.isEmpty) vEmpty else vNonEmpty
  /** Checks condition against head. Returns false if the collection is empty. */
  def ifHead(f: A => Boolean) : Boolean = thisIter.ifEmpty(false, f(thisIter.head))  
  def headOrElse(vEmpty: A): A = if (thisIter.isEmpty) vEmpty else thisIter.head
  def toStrsFold(seperator: String = "", f: A => String = _.toString): String =
    thisIter.ifEmpty("", thisIter.tail.foldLeft(f(thisIter.head))(_ + seperator + f(_)))
  def toStrsCommaFold(fToStr: A => String = _.toString): String = thisIter.toStrsFold(", ", fToStr)
  def toStrsCommaNoSpaceFold(fToStr: A => String = _.toString): String = thisIter.toStrsFold(",", fToStr)
  def toStrsSemiFold(fToStr: A => String = _.toString): String = thisIter.toStrsFold("; ", fToStr)
  def toStrsCommaParenth(fToStr: A => String = _.toString): String = toStrsCommaFold(fToStr).enParenth
  def toStrsSemiParenth(fToStr: A => String = _.toString): String = toStrsSemiFold(fToStr).enParenth

  /** If the collection is nonEmpty, return head of list convert to string or return the defualt string. */
  def headToStringElse(elseString: => String): String = headOnly(elseString, _.toString)
  
  /** Converts to ArrImut of A. Most commonly a Refs. Prefer the mapArr method where appropriate which combines the converson with a map operation. */
  def toImut[AA <: ArrBase[A]](implicit bu: ArrBuilder[A, AA]): AA =
  { val len = thisIter.size
    val res = bu.newArr(len)
    iForeach((i, a) => res.unsafeSetElem(i, a))
    res
  }

  def sumBy(f: A => Int): Int =
  { var acc = 0
    thisIter.foreach(acc += f(_))
    acc
  }

  /** Index with foreach. Performs a side effecting function on the index and each element of this sequence. It takes a function as a parameter. The
   *  function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about
   *  discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0 if just
   *  the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value
   *  of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example
   *  in fold methods' (accumulator, element) => B signature. */
  def iForeach(f: (Int, A) => Unit): Unit =
  { var i = 0
    thisIter.foreach { elem => f(i, elem); i += 1 }
  }

  /** Index with foreach. Performs a side effecting function on the index and each element of this sequence. It takes a function as a parameter. The
   *  function may return Unit. If it does return a non Unit value it is discarded. The [U] type parameter is there just to avoid warnings about
   *  discarded values and can be ignored by method users. The method has 2 versions / name overloads. The default start for the index is 0 if just
   *  the function parameter is passed. The second version name overload takes an [[Int]] for the first parameter list, to set the start value
   *  of the index. Note the function signature follows the foreach based convention of putting the collection element 2nd or last as seen for example
   *  in fold methods' (accumulator, element) => B signature. */
  def iForeach(initialIndex: Int)(f: (Int, A) => Unit): Unit =
  { var i = initialIndex
    thisIter.foreach { elem => f(i, elem); i += 1 }
  }

  /** Specialised index with map to an immutable ArrBase of B. Note the function signature follows the foreach based convention of putting the
   *  collection element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. */
  def iMap[B, BB <: ArrBase[B]](f: (Int, A) => B)(implicit build: ArrBuilder[B, BB]): BB =
  { var i = 0
    val buff: build.BuffT = build.newBuff()
    thisIter.foreach{el => build.buffGrow(buff, f(i, el)); i += 1 }
    build.buffToBB(buff)
  }

  /** Specialised index with map to an immutable ArrBase of B. Note the function signature follows the foreach based convention of putting the
   *  collection element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. */
  def iMap[B, BB <: ArrBase[B]](count: Int)(f: (A, Int) => B)(implicit build: ArrBuilder[B, BB]): BB =
  { var i = count
    val buff: build.BuffT = build.newBuff()
    thisIter.foreach{el => build.buffGrow(buff, f(el, i)); i += 1 }
    build.buffToBB(buff)
  }

  /** flatMaps over a traversable (collection / sequence) with a counter */
  def iFlatMap[B, BB <: ArrBase[B]](f: (A, Int) => BB, count: Int = 0)(implicit build: ArrBuilder[B, BB]): BB =
  { var i = count
    val buff: build.BuffT = build.newBuff()
    thisIter.foreach{el => build.buffGrowArr(buff, f(el, i)); i += 1 }
    build.buffToBB(buff)
  }
   

  def iForall(f: (A, Int) => Boolean): Boolean = 
  { var count = 0
    var rem = thisIter
    var succeed = true
    while(rem.nonEmpty & succeed)
      if (f(rem.head, count)) {count += 1; rem = rem.tail }
        else succeed = false
    succeed    
  }
   
  def toStrFold2[B](secondAcc: B)(f: (B, A) => (String, B)): String =
  { var acc: String = ""
    var acc2: B = secondAcc
    thisIter.foreach{ el =>
      val pair = f(acc2, el)
      acc += pair._1
      acc2 = pair._2               
    }
    acc
  }
   
  def iterHead[B](ifEmpty: => B, fNonEmpty: (A, Iterable[A]) => B): B = if (thisIter.isEmpty) ifEmpty else fNonEmpty(thisIter.head, thisIter.tail)
  
  def foldWithPrevious[B](initPrevious: A, initAcc: B)(f: (B, A, A) => B): B =
  { var acc: B = initAcc
    var prev: A = initPrevious
    thisIter.foreach { newA =>
      acc = f(acc, prev, newA)
      prev = newA
    }
    acc
  }

  /** Maps to a ArrImut an immutable Array of B. */
  def mapArr[B, BB <: ArrBase[B]](f: A => B)(implicit ev: ArrBuilder[B, BB]): BB = ev.iterMap[A](thisIter, f)
}

/** Extension methods for [[Iterable]][A <: ValueNElem]. */
class IterableValueNElemExtensions[A <: ElemValueN](val thisIter: Iterable[A]) extends AnyVal
{
  /** product map method maps from a Traversable to an Array based ProductValues class. */
  def pMap[B <: ElemValueN , M <: ArrValueNs[B]](f: A => B)(implicit factory: Int => M): M =
  { val res = factory(thisIter.size)
    var count: Int = 0
    thisIter.foreach { orig =>
      val newValue: B = f(orig)
      res.unsafeSetElem(count, newValue)
      count += 1
    }
    res
  }

  /** Copies from a Traversable to an Array based ProductValues class. Not sure about this method or the implicit builder that underlies. It perhaps
   *  duplicates. */
  def toArrProdHomo[B <: ArrValueNs[A]](implicit factory: Int => B): B =
  { val res = factory(thisIter.size)
    var count: Int = 0
    thisIter.foreach { orig =>
      res.unsafeSetElem(count, orig)
      count += 1
    }
    res
  }
}