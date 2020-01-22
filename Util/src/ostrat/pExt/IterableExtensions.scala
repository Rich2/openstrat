/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pExt
import reflect.ClassTag

/** Extension methods for Traversable[A] */
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
  def toImut[AA <: ArrImut[A]](implicit bu: ArrBuild[A, AA]): AA =
  {
    val len = thisIter.size
    val res = bu.imutNew(len)
    iForeach((a, i) => res.unsafeSetElem(i, a))
    res
  }
  def toArr(implicit ct: ClassTag[A]): ArrOld[A] = thisIter.toArray.toArrOld
  def sumBy(f: A => Int): Int =
  {
    var acc = 0
    thisIter.foreach(acc += f(_))
    acc
  }

  /** Maps over a Traversable (collection / sequence) with a counter. */
  def iMap[B](f: (A, Int) => B, count: Int = 0)(implicit ct: ClassTag[B]): ArrOld[B] =
  { var i = count
    val buff: Buff[B] = Buff()
    thisIter.foreach{el => buff += f(el, i); i += 1 }
    buff.toArr
  }
   
  /** flatMaps over a traversable (collection / sequence) with a counter */
  def iFlatMap[B](f: (A, Int) => ArrOld[B], count: Int = 0)(implicit ct: ClassTag[B]): ArrOld[B] =
  { var i = count
    val buff: Buff[B] = Buff()
    thisIter.foreach{el => buff ++= f(el, i); i += 1 }
    buff.toArr
  }
   
  /** foreach loop with index. The startIndex parameter is placed 2nd to allow it to have a default value of zero. */
  def iForeach(f: (A, Int) => Unit, initialIndex: Int = 0): Unit =
  { var i = initialIndex
    thisIter.foreach { elem => f(elem, i); i += 1 }
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

  /** This needs to be renamed. */
  def iter2ProdD2[B, C <: ProdDbl2, D <: ArrProdDbl2[C]](secondIter: Iterable[B], f: (A, B) => C)(implicit factory: Int => D): D =
  { val elemNum = thisIter.size * secondIter.size
    val res = factory(elemNum)
    var count = 0
    thisIter.foreach {a =>
      secondIter.foreach{ b => res.unsafeSetElem(count, f(a, b)); count += 1 }
    }
    res
  }
   
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
  def mapArr[B, BB <: ArrImut[B]](f: A => B)(implicit ev: ArrBuild[B, BB]): BB = ev.iterMap[A](thisIter, f)

  /** product map method maps from a Traversable to an Array based ProductValues class. */
  def pMap[B , M <: ArrProdHomo[B]](f: A => B)(implicit factory: Int => M): M =
  { val res = factory(thisIter.size)
    var count: Int = 0
    thisIter.foreach { orig =>
      val newValue: B = f(orig)
      res.unsafeSetElem(count, newValue)
      count += 1         
    }
    res
  }
  
  /** Copies from a Traversable to an Array based ProductValues class. */
  def toPValues[B <: ArrProdHomo[A]](implicit factory: Int => B): B =
  { val res = factory(thisIter.size)
    var count: Int = 0
    thisIter.foreach { orig =>      
      res.unsafeSetElem(count, orig)
      count += 1         
    }
    res
  }  
}
