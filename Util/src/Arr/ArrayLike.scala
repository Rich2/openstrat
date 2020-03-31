package ostrat
import annotation.unchecked.uncheckedVariance, collection.immutable._, reflect.ClassTag

/** This the base trait for all Array based collections that compile time platform Array classes. So currently there are just two classes for each
 * type A, An ArrImut that wrappes a standard immutable Array to produce an immutable array, and a ArrBuff that wrappes an ArrayBuffer. Currently this
 * just in a standard ArrayBuffer. Where A is a compound value types or an AnyVal type. */
trait ArrayLike[+A] extends Any with ArrayBase[A @uncheckedVariance]
{ type ThisT <: ArrayLike[A]
  def returnThis: ThisT = ???

  @inline def apply(index: Int): A
  @inline def head: A = apply(0)
  @inline def last: A = apply(length - 1)
  @inline def empty: Boolean = length <= 0
  @inline def nonEmpty: Boolean = length > 0
  def ifEmpty[B](vEmpty: => B, vNonEmpty: => B): B = if (length == 0) vEmpty else vNonEmpty
  def fHeadElse[B](noHead: => B)(ifHead: A => B): B = ife(length >= 1, ifHead(head), noHead)
  def headToStringElse(ifEmptyString: String): String = ife(length >= 1, head.toString, ifEmptyString)

  /** transitional method to be removed. */
  @deprecated def toArraySeq(implicit ct: ClassTag[A] @uncheckedVariance): ArraySeq[A] =
  { val newArray: Array[A] = new Array[A](length)
    iForeach((v, i) => newArray(i) = v)
    ArraySeq.unsafeWrapArray(newArray)
  }

  def foreach[U](f: A => U): Unit =
  { var count = 0
    while(count < length)
    { f(apply(count))
      count = count + 1
    }
  }

  /** foreach with index. The startIndex parameter is placed 2nd to allow it to have a default value of zero. */
  def iForeach[U](f: (A, Int) => U, startIndex: Int = 0): Unit =
  { var count = 0
    var i: Int = startIndex
    while(count < length )
    { f(apply(count), i)
      count+= 1
      i += 1
    }
  }

  def map[B, BB <: ArrImut[B]](f: A => B)(implicit ev: ArrBuild[B, BB]): BB =
  { val res = ev.imutNew(length)
    iForeach((a, i) => ev.imutSet(res, i, f(a)))
    res
  }

  /** This was an extension method I'm not sure why. It was also called bind. */
 // @deprecated def flatMapOld[BB <: ArrImut[_]](f: A => BB)(implicit ev: ArrFlatBuild[BB]): BB = ev.flatMap[A](this, f)

  def flatMap[BB <: ArrImut[_]](f: A => BB)(implicit ev: ArrFlatBuild[BB]): BB =
  {
    val buff: ev.BuffT = ev.buffNew()
    foreach{ a =>
      val newVals = f(a)
      ev.buffGrowArr(buff, newVals)
    }
    ev.buffToArr(buff)
  }

  def iMap[B, BB <: ArrImut[B]](f: (A, Int) => B)(implicit ev: ArrBuild[B, BB]): BB =
  { val res = ev.imutNew(length)
    iForeach((a, i) => ev.imutSet(res, i, f(a, i)))
    res
  }

  def iFlatMap[BB <: ArrImut[_]](f: (A, Int) => BB)(implicit ev: ArrFlatBuild[BB]): BB = ???


  /* Maps from A to B like normal map,but has an additional accumulator of type C that is discarded once the traversal is completed */
  def mapWithAcc[B, BB <: ArrImut[B], C](initC: C)(f: (A, C) => (B, C))(implicit  ev: ArrBuild[B, BB]): BB =
  { val res = ev.imutNew(length)
    var accC: C = initC
    iForeach { (a, i) =>
      val (newB, newC) = f(a, accC)
      res.unsafeSetElem(i, newB)
      accC = newC
    }
    res
  }

  def eMap[B, BB <: ArrImut[B]](f: A => EMon[B])(implicit ev: ArrBuild[B, BB]): EMon[BB] =
  { val acc = ev.buffNew()
    var continue = true
    var count = 0
    var errs: Refs[String] = Refs()
    while(count < length & continue == true)
      f(apply(count)).foldErrs { g => ev.buffGrow(acc, g); count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(ev.buffToArr(acc)), Bad(errs))
  }

  def eMapList[B](f: A => EMon[B]): EMon[List[B]] =
  { var acc: List[B] = Nil
    var continue = true
    var count = 0
    var errs: Refs[String] = Refs()
    while(count < length & continue == true)
      f(apply(count)).foldErrs { g => acc ::= g; count += 1 } { e => errs = e; continue = false }
    ife(continue, Good(acc.reverse), Bad(errs))
  }

  /** map 2 elements of A to 1 element of B. Ignores the last element on a collection of odd numbered length. */
  def map2To1[B, BB <: ArrImut[B]](f: (A, A) => B)(implicit ev: ArrBuild[B, BB]): BB =
  { val res = ev.imutNew(length)
    var count = 0
    while (count + 1  < length)
    {  ev.imutSet(res, count, f(apply(count), apply(count + 1)))
      count += 2
    }
    res
  }

  def filter[AA <: ArrImut[A] @uncheckedVariance](f: A => Boolean)(implicit ev: ArrBuild[A, AA] @uncheckedVariance): AA =
  { val buff = ev.buffNew()
    foreach(a => oif(f(a), ev.buffGrow(buff, a)))
    ev.buffToArr(buff)
  }

  def filterNot[AA <: ArrImut[A] @uncheckedVariance](f: A => Boolean)(implicit ev: ArrBuild[A, AA] @uncheckedVariance): AA =
  { val buff = ev.buffNew()
    foreach(a => oif(!f(a), ev.buffGrow(buff, a)))
    ev.buffToArr(buff)
  }

  def filterToList(f: A => Boolean): List[A] =
  {
    var acc: List[A] = Nil
    foreach{ a => if (f(a)) acc ::= a }
    acc.reverse
  }

  /** FlatMaps over a function from A to any Iterable. */
  def iterFlatMap[B, BB <: ArrImut[B]](f: A => Iterable[B])(implicit ev: ArrBuild[B, BB]): BB =
  { val buff = ev.buffNew(length)
    foreach(a => ev.buffGrowIter(buff, f(a)))
    ev.buffToArr(buff)
  }

  def foldLeft[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    foreach(a => acc = f(acc, a))
    acc
  }

  def indexOf(elem: A @uncheckedVariance): Int =
  { var result = -1
    var count  = 0
    while (count < length & result == -1)
    { if (elem == apply(count)) result = count
    else count += 1
    }
    result
  }

  /** Return the index of the first element where predicate is true, or -1 if predicate not true forall. */
  def indexWhere(f: A => Boolean): Int =
  { var count = 0
    var result = -1
    while(count < length & result == -1)
    { if(f(apply(count))) result = count
      count += 1
    }
    result
  }

  def foreachTail[U](f: A => U): Unit =
  { var count = 1
    while(count < length) { f(apply(count)); count += 1 }
  }

  def foreachInit[U](f: A => U): Unit =
  { var count = 0
    while(count < length - 1)
    { f(apply(count))
      count += 1
    }
  }

  def foldTailLeft[B](initial: B)(f: (B, A) => B) =
  { var acc: B = initial
    foreachTail(a => acc = f(acc, a))
    acc
  }

  def foldHeadTail[B](initial: B)(fHead: (B, A) => B)(fTail: (B, A) => B) =
  { var acc: B = initial
    var start: Boolean = true
    foreach{a => if(start == true)
    {acc = fHead(acc, a); start = false}
    else acc = fTail(acc, a)
    }
    acc
  }

  /** Consider changing this name, as might not be appropriate to all sub classes. */
  def foreachReverse[U](f: A => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(apply(count)) }
  }

  def iForeachReverse[U](f: (A, Int) => U): Unit =
  { var count = length
    while(count > 0) { count -= 1; f(apply(count), count) }
  }

  def forAll(p: (A) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < length) if (p(apply(count))) count += 1 else acc = false
    acc
  }

  def iForAll(p: (A, Int) => Boolean): Boolean =
  { var acc: Boolean = true
    var count = 0
    while (acc & count < length) if (p(apply(count), count)) count += 1 else acc = false
    acc
  }


  def contains[A1 >: A](elem: A1): Boolean =
  { var count = 0
    var res = false
    while (res == false & count < length){ if (elem == apply(count)) res = true; count += 1 }
    res
  }

  /** maps ValueProduct collection to List */
  def mapList[B <: AnyRef](f: A => B): List[B] =
  { var res: List[B] = Nil
    foreachReverse(res ::= f(_))
    res
  }

  def toStrsFold(seperator: String = "", f: A => String = _.toString): String =
  { var acc: String = ""
    var start = true
    foreach{ a => if(start == true) { acc = f(a); start = false } else acc = acc + seperator + f(a)}
    acc
  }

  /** Counts the number of elements that fulfil the condition A => Boolean */
  def existsCount(f: A => Boolean): Int =
  { var count = 0
    foreach(el => if (f(el)) count += 1)
    count
  }

  /** Not sure about this method. */
  def mkString(seperator: String): String = ife(length == 0, "",
    { var acc = head.toString
      var count = 1
      while(count < length)
      { acc += seperator + apply(count).toString
        count += 1
      }
      acc
    }
  )

  def toList: List[A] =
  { var acc: List[A] = Nil
    foreachReverse(acc ::= _)
    acc
  }

  def sumBy(f: A => Int): Int =
  { var acc = 0
    foreach(acc += f(_))
    acc
  }

  /** Collects values of B by applying partial function to only those elements of A, for which the PartialFunction is defined. */
  def collect[B, BB <: ArrImut[B]](pf: PartialFunction[A, B])(implicit ev: ArrBuild[B, BB]): BB =
  { val acc = ev.buffNew()
    foreach{a => if (pf.isDefinedAt(a)) ev.buffGrow(acc, pf(a)) }
    ev.buffToArr(acc)
  }

  /** Collects a List values of B by applying partial function to only those elements of A, for which the PartialFunction is defined. */
  def collectList[B](pf: PartialFunction[A, B]): List[B] =
  { var acc: List[B] = Nil
    foreach{a => if (pf.isDefinedAt(a)) acc ::= pf(a) }
    acc.reverse
  }

  /** maps from A to EMon[B], collects the good values. */
  def mapCollectGoods[B, BB <: ArrImut[B]](f: A => EMon[B])(implicit ev: ArrBuild[B, BB]): BB =
  { val acc = ev.buffNew()
    foreach(f(_).forGood(ev.buffGrow(acc, _)))
    ev.buffToArr(acc)
  }

  def max[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    foreachTail{el => acc = ord.max(acc, el) }
    acc
  }

  def min[B >: A](implicit ord: math.Ordering[B]): A =
  { var acc = apply(0)
    foreachTail{el => acc = ord.min(acc, el) }
    acc
  }

  def fMax[B](f: (A) => B)(implicit cmp: math.Ordering[B]): B =
  { var acc = f(head)
    foreachTail{ el => acc = cmp.max(acc, f(el)) }
    acc
  }

  def fMin[B](f: (A) => B)(implicit cmp: math.Ordering[B]): B =
  { var acc = f(head)
    foreachTail{el => acc = cmp.min(acc, f(el)) }
    acc
  }

  def toStrsCommaFold(fToStr: A => String = _.toString): String = toStrsFold(", ", fToStr)
  def toStrsCommaNoSpaceFold(fToStr: A => String = _.toString): String = toStrsFold(",", fToStr)
  def toStrsSemiFold(fToStr: A => String = _.toString): String = toStrsFold("; ", fToStr)
  def toStrsCommaParenth(fToStr: A => String = _.toString): String = toStrsCommaFold(fToStr).enParenth
  def toStrsSemiParenth(fToStr: A => String = _.toString): String = toStrsSemiFold(fToStr).enParenth
}

case class ArrayLikeShow[A, R <: ArrayLike[A]](evA: Show[A]) extends ShowSeqLike[A, R]
{ def showComma(obj: R): String = obj.toStrsCommaFold()
  def showSemi(obj: R): String = obj.toStrsSemiFold()
}
