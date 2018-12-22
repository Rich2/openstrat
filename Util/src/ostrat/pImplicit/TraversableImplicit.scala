/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pImplicit

/** Extension methods for Traversable[A] */
class TraversableImplicit[A](val thisTrav: Traversable[A]) extends AnyVal
{ /** This method and "fHead" removes the need for headOption in the majority of case. Use fHead when are interested in the tail value */
  def headOnly[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisTrav.isEmpty) ifEmpty else fNonEmpty(thisTrav.head)
  def fLast[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisTrav.isEmpty) ifEmpty else fNonEmpty(thisTrav.last)
  def ifEmpty[B](vEmpty: => B, vNonEmpty: => B): B = if (thisTrav.isEmpty) vEmpty else vNonEmpty  
  def ifHead(f: A => Boolean) : Boolean = thisTrav.ifEmpty(false, f(thisTrav.head))  
  def headOrElse(vEmpty: A): A = if (thisTrav.isEmpty) vEmpty else thisTrav.head
  def toStrFold(seperator: String = "", f: A => String = _.toString): String =
    thisTrav.ifEmpty("", thisTrav.tail.foldLeft(f(thisTrav.head))(_ - seperator - f(_)))   
  def commaFold(fToStr: A => String = _.toString): String = thisTrav.toStrFold(", ", fToStr)
  def semiFold(fToStr: A => String = _.toString): String = thisTrav.toStrFold("; ", fToStr)
   
  /** maps over a traversable (collection / sequence) with a counter */
  def iMap[B](f: (A, Int) => B, count: Int = 0): List[B] =
  { var i = count
    var acc: List[B] = Nil
    thisTrav.foreach{el => acc ::= f(el, i); i += 1 }
    acc.reverse
  }
   
  /** flatMaps over a traversable (collection / sequence) with a counter */
  def iFlatMap[B](f: (A, Int) => Seq[B], count: Int = 0): Seq[B] =
  { var i = count
    var acc: Seq[B] = Seq()
    thisTrav.foreach{el => acc ++= f(el, i); i += 1 }
    acc
  }
   
  /** foreach loop with counter */
  def iForeach(f: (A, Int) => Unit, count: Int = 0): Unit =
  { var counter = count
    var rem = thisTrav
    while(rem.nonEmpty)
    { f(rem.head, counter)
      counter += 1
      rem = rem.tail
    }      
  }
   
  def mapVar1[B, C](initialVar: B, f: (A, B) => (B, C)): Seq[C] =
  { var varB: B = initialVar
    var acc: Seq[C] = Seq()
    thisTrav.foreach{el =>
      val pair: (B, C) = f(el, varB)
      varB = pair._1
      acc :+= pair._2
    }
    acc
  }
   
  def flatMapVar1[B, C](initialVar: B, initialAcc: C)(f: (A, B, C) => (B, C)): C =
  { var varB: B = initialVar
    var acc: C = initialAcc
    thisTrav.foreach{el =>
      val pair: (B, C) = f(el, varB, acc)
      varB = pair._1
      acc = pair._2
    }
    acc
  } 
   
  def toStrFold2[B](secondAcc: B)(f: (B, A) => (String, B)): String =
  { var acc: String = ""
    var acc2: B = secondAcc
    thisTrav.foreach{ el =>
      val pair = f(acc2, el)
      acc += pair._1
      acc2 = pair._2               
    }
    acc
  }
   
  def travHead[B](ifEmpty: => B, fNonEmpty: (A, Traversable[A]) => B): B = if (thisTrav.isEmpty) ifEmpty else fNonEmpty(thisTrav.head, thisTrav.tail)
  
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
    goodLoop(thisTrav.toList, Nil)      
  }
   
  /** Not sure what this method does */
  def typedSpan[B <: A](typeCheckFunction: A => Boolean): (List[B], List[A]) =
  {
    def loop(rem: List[A], acc: List[B]): (List[B], List[A]) = rem match
    { case h :: tail if typeCheckFunction(h) => loop(tail, acc :+ h.asInstanceOf[B])
      case s => (acc, s)
    }
    loop(thisTrav.toList, Nil)
  }   
   
  def trav2ProdD2[B, C <: ProdD2, D <: DoubleProduct2s[C]](secondTrav: Traversable[B], f: (A, B) => C)(implicit factory: Int => D): D =
  { val elemNum = thisTrav.size * secondTrav.size
    val res = factory(elemNum)
    var count = 0
    thisTrav.foreach {a =>
      secondTrav.foreach{ b => res.setElem(count, f(a, b)); count += 1 }
    }
    res
  }
   
  def toProdD2[A1 >: A <: ProdD2, B <: DoubleProduct2s[A1]](implicit factory: Int => B): B =
  { val res = factory(thisTrav.size)
    var count = 0
    thisTrav.foreach{ el => res.setElem(count, el); count += 1 }
    res
  }
  
  def foldWithPrevious[B](initPrevious: A, initAcc: B)(f: (B, A, A) => B): B =
  { var acc: B = initAcc
    var prev: A = initPrevious
    thisTrav.foreach { newA =>
      acc = f(acc, prev, newA)
      prev = newA
    }
    acc
  }
}
