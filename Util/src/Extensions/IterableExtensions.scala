/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
//import scala.collection.mutable.ArrayBuffer

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
  
  /** Converts to [[Arr]] of A. Most commonly an [[RArr]]. Prefer the mapArr method where appropriate which combines the conversion with a map operation. */
  def toArr[AA <: Arr[A]](implicit builder: ArrMapBuilder[A, AA]): AA =
  { val len = thisIter.size
    val res = builder.uninitialised(len)
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
  def iMap[B, BB <: Arr[B]](f: (Int, A) => B)(implicit build: ArrMapBuilder[B, BB]): BB =
  { var i = 0
    val buff: build.BuffT = build.newBuff()
    thisIter.foreach{el => build.buffGrow(buff, f(i, el)); i += 1 }
    build.buffToSeqLike(buff)
  }

  /** Specialised index with map to an immutable ArrBase of B. Note the function signature follows the foreach based convention of putting the
   *  collection element 2nd or last as seen for example in fold methods' (accumulator, element) => B signature. */
  def iMap[B, BB <: Arr[B]](count: Int)(f: (A, Int) => B)(implicit build: ArrMapBuilder[B, BB]): BB =
  { var i = count
    val buff: build.BuffT = build.newBuff()
    thisIter.foreach{el => build.buffGrow(buff, f(el, i)); i += 1 }
    build.buffToSeqLike(buff)
  }

  /** flatMaps over a traversable (collection / sequence) with a counter */
  def iFlatMap[B, BB <: Arr[B]](f: (Int, A) => BB)(implicit build: ArrMapBuilder[B, BB]): BB =
  { var i = 0
    val buff: build.BuffT = build.newBuff()
    thisIter.foreach{ el => f(i, el).foreach(buff.grow); i += 1 }
    build.buffToSeqLike(buff)
  }

  /** flatMaps over a traversable (collection / sequence) with a counter */
  def iFlatMap[B, BB <: Arr[B]](count: Int)(f: (Int, A) => BB)(implicit build: ArrMapBuilder[B, BB]): BB =
  { var i = count
    val buff: build.BuffT = build.newBuff()
    thisIter.foreach{ el => f(i, el).foreach(buff.grow); i += 1 }
    build.buffToSeqLike(buff)
  }

  def iForall(f: (Int, A) => Boolean): Boolean =
  { var count = 0
    var rem = thisIter
    var succeed = true
    while(rem.nonEmpty & succeed)
      if (f(count, rem.head)) {count += 1; rem = rem.tail }
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

  /** maps to a [[Arr]] of B. */
  def mapArr[B, BB <: Arr[B]](f: A => B)(implicit ev: ArrMapBuilder[B, BB]): BB = ev.iterMap[A](thisIter, f)

  /** flatMaps to a [[Arr]] of B. */
  def flatMapArr[BB <: Arr[_]](f: A => BB)(implicit ev: ArrFlatBuilder[BB]): BB ={
    val buff = ev.newBuff()
    thisIter.foreach{ el => ev.buffGrowArr(buff, f(el)) }
    ev.buffToSeqLike(buff)
  }

  /** Maps to a [[PairArr]] of B1 and B2. */
  def mapPairArr[B1, ArrB1 <: Arr[B1], B2, B <: PairElem[B1, B2], ArrB <: PairArr[B1, ArrB1, B2, B]](f1: A => B1, f2: A => B2)(
  implicit build: PairArrMapBuilder[B1, ArrB1, B2, B, ArrB]): ArrB =
  { val buff1 = build.newB1Buff()
    val buffer2 = build.newB2Buffer()
    thisIter.foreach { a =>
      buff1.grow(f1(a))
      buffer2.append(f2(a))
    }
    build.arrFromBuffs(buff1, buffer2)
  }

  /** flatMaps to a [[PairArr]] of B1 and B2. */
  def flatMapPairArr[B1, ArrB1 <: Arr[B1], B2, BB <: PairArr[B1, ArrB1, B2, _]](f1: A => ArrB1, f2: A => B2)(
    implicit build: PairArrFlatBuilder[B1, ArrB1, B2, BB]): BB =
  {
    val buff1 = build.newB1Buff()
    val buffer2 = build.newB2Buffer()//new ArrayBuffer[B2]()
    thisIter.foreach{ a =>
      val b1 = f1(a)
      b1.foreach(buff1.grow)
      val b2 = f2(a)
      iUntilForeach(0, b1.length)(_ => buffer2.append(b2))
    }
    build.arrFromBuffs(buff1, buffer2)
  }
}

/** Extension methods for [[Iterable]][A <: ValueNElem]. */
class IterableValueNElemExtensions[A <: ValueNElem](val thisIter: Iterable[A]) extends AnyVal
{
  /** product map method maps from a Traversable to an Array based ProductValues class. */
  def pMap[B <: ValueNElem , M <: ValueNArr[B]](f: A => B)(implicit factory: Int => M): M =
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
  def toArrProdHomo[B <: ValueNArr[A]](implicit factory: Int => B): B =
  { val res = factory(thisIter.size)
    var count: Int = 0
    thisIter.foreach { orig =>
      res.unsafeSetElem(count, orig)
      count += 1
    }
    res
  }
}