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
  def ifHead(f: A => Boolean) : Boolean = thisIter.ifEmpty(false, f(thisIter.head))  
  def headOrElse(vEmpty: A): A = if (thisIter.isEmpty) vEmpty else thisIter.head
  def toStrsFold(seperator: String = "", f: A => String = _.toString): String =
    thisIter.ifEmpty("", thisIter.tail.foldLeft(f(thisIter.head))(_ + seperator + f(_)))
  def toStrsCommaFold(fToStr: A => String = _.toString): String = thisIter.toStrsFold(", ", fToStr)
  def toStrsSemiFold(fToStr: A => String = _.toString): String = thisIter.toStrsFold("; ", fToStr)
  def toArr(implicit ct: ClassTag[A]): Arr[A] = thisIter.toArray.toArr

  /** Maps over a Traversable (collection / sequence) with a counter. */
  def iMap[B](f: (A, Int) => B, count: Int = 0)(implicit ct: ClassTag[B]): Arr[B] =
  { var i = count
    val buff: Buff[B] = Buff()
    thisIter.foreach{el => buff += f(el, i); i += 1 }
    buff.toArr
  }
   
  /** flatMaps over a traversable (collection / sequence) with a counter */
  def iFlatMap[B](f: (A, Int) => Arr[B], count: Int = 0)(implicit ct: ClassTag[B]): Arr[B] =
  { var i = count
    val buff: Buff[B] = Buff()
    thisIter.foreach{el => buff ++= f(el, i); i += 1 }
    buff.toArr
  }
   
  /** foreach loop with counter */
  def iForeach(f: (A, Int) => Unit, count: Int = 0): Unit =
  { var counter = count
    var rem = thisIter
    while(rem.nonEmpty)
    { f(rem.head, counter)
      counter += 1
      rem = rem.tail
    }      
  }
   
  def mapVar1[B, C](initialVar: B, f: (A, B) => (B, C)): Seq[C] =
  { var varB: B = initialVar
    var acc: Seq[C] = Seq()
    thisIter.foreach{el =>
      val pair: (B, C) = f(el, varB)
      varB = pair._1
      acc :+= pair._2
    }
    acc
  }
   
  def flatMapVar1[B, C](initialVar: B, initialAcc: C)(f: (A, B, C) => (B, C)): C =
  { var varB: B = initialVar
    var acc: C = initialAcc
    thisIter.foreach{el =>
      val pair: (B, C) = f(el, varB, acc)
      varB = pair._1
      acc = pair._2
    }
    acc
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
  
  /** Folds over this traverable with a to Emon function, accumulating errors */      
  def eMonMap[B](f: A => EMon[B]): EMon[List[B]] =      
  {
    def goodLoop(rem: List[A], goodAcc: List[B]): EMon[List[B]] = rem match
    {
      case Nil => Good(goodAcc)
      case h :: tail => f(h).fold(errs => badLoop(tail, errs), g => goodLoop(tail, goodAcc :+ g))
    }    
    
    def badLoop(rem: List[A], errAcc: StrList): EMon[List[B]] = rem match
    {
      case Nil => Bad(errAcc)
      case h :: tail => f(h).fold(newErrs => badLoop(tail, errAcc ++ newErrs), g => badLoop(tail, errAcc))
    }
    goodLoop(thisIter.toList, Nil)      
  }
   
  /** Not sure what this method does */
  def typedSpan[B <: A](typeCheckFunction: A => Boolean): (List[B], List[A]) =
  {
    def loop(rem: List[A], acc: List[B]): (List[B], List[A]) = rem match
    { case h :: tail if typeCheckFunction(h) => loop(tail, acc :+ h.asInstanceOf[B])
      case s => (acc, s)
    }
    loop(thisIter.toList, Nil)
  }   
  
  /** This needs to be renamed. */
  def iter2ProdD2[B, C <: ProdD2, D <: ProductD2s[C]](secondIter: Iterable[B], f: (A, B) => C)(implicit factory: Int => D): D =
  { val elemNum = thisIter.size * secondIter.size
    val res = factory(elemNum)
    var count = 0
    thisIter.foreach {a =>
      secondIter.foreach{ b => res.setElem(count, f(a, b)); count += 1 }
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
    
  /** product map method maps from a Traversable to an Array based ProductValues class. */
  def pMap[B , C <: ProductVals[B]](f: A => B)(implicit factory: Int => C): C =
  { val res = factory(thisIter.size)
    var count: Int = 0
    thisIter.foreach { orig =>
      val newValue: B = f(orig)
      res.setElem(count, newValue)
      count += 1         
    }
    res
  }
  
  /** Copies from a Traversable to an Array based ProductValues class. */
  def toPValues[B <: ProductVals[A]](implicit factory: Int => B): B =
  { val res = factory(thisIter.size)
    var count: Int = 0
    thisIter.foreach { orig =>      
      res.setElem(count, orig)
      count += 1         
    }
    res
  }  
}
