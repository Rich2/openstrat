/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pImplicit

/** Extension methods for the Seq[A] class. These are bought into scope by the seqToImplicit method in the package object. */
class SeqImplicit[A](thisSeq: Seq[A])
{
  /** This needs to be changed to by name parameters when by name varargs are allowed. I think this is coming in 12.3 */
  def ifAppend[B >: A](b: Boolean, elems: B*): Seq[B] = if (b) thisSeq ++ elems else thisSeq
  
  /** This method and "headOnly" method on TraversableImplicit removes the need for headOption in the majority of case. Use head Only if you are only
   *  interested in the head value */
  def fHead[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisSeq.isEmpty) ifEmpty else fNonEmpty(thisSeq.head)
  
  def addOpt(optEl: Option[A]): Seq[A] = optEl match
  { case None => thisSeq
    case Some(el) => thisSeq :+ el
  }
  
  def next(currEl: A): A =
  { val currIndex = thisSeq.indexOf(currEl)
    val nextIndex = ife(currIndex == thisSeq.length, 0, currIndex + 1)
    thisSeq(nextIndex)
  }
  
  def prev(currEl: A): A =
  { val currIndex = thisSeq.indexOf(currEl)
    val prevIndex  = ife(currIndex == 0, thisSeq.length - 1, currIndex - 1)
    thisSeq(prevIndex)
  }
   
  def foldMin[B](f: A => B)(implicit cmp: Ordering[B]): B = thisSeq.tail.foldLeft(f(thisSeq.head))((acc, el) => cmp.min(acc, f(el)))
  def foldMax[B](f: A => B)(implicit cmp: Ordering[B]): B = thisSeq.tail.foldLeft(f(thisSeq.head))((acc, el) => cmp.max(acc, f(el)))
  
  def foldMinMax[B](f: A => B)(implicit cmp: Ordering[B]): (B, B) =
  { val initVal = f(thisSeq.head)
    thisSeq.tail.foldLeft((initVal, initVal)){ (acc, el) =>
      val (accMin, accMax) = acc
      val newVal = f(el)
      val min = cmp.min(accMin, newVal)
      val max = cmp.max(accMax, newVal)
      (min, max)
    }
  }
  
  def mapMinMaxStr[B](f: A => B)(implicit cmp: Ordering[B]): String =
  { val (min, max) = thisSeq.foldMinMax(f)(cmp)
    min.toString.commaAppend(max.toString)
  }
  
  def filterMap[B](fFilter: A => Boolean, fMap: A => B): Seq[B] = thisSeq.foldLeft[Seq[B]](Seq())((acc, h) => ife(fFilter(h), acc :+ fMap(h), acc))
   
  /** groups the Sequence into pairs and then maps over them. Throws exception if seq.length.isOdd */   
  def mapBy2[B](f: (A, A) => B): List[B] =
  { var rem = thisSeq
    var acc: List[B] = Nil
    while(rem.nonEmpty)
    { val v1 = rem.head
      rem = rem.tail
      acc :+= f(v1, rem.head)
      rem = rem.tail
    }
    acc
  }
  
  /** product map method maps from a sequence to an Array[Double] based ProductValues class. */
  def pMap[B , C <: ProductVals[B]](f: A => B)(implicit factory: Int => C): C =
  { val res = factory(thisSeq.length)
    var count: Int = 0
    thisSeq.foreach { orig =>
      val newValue: B = f(orig)
      res.setElem(count, newValue)
      count += 1         
    }
    res
  }
  
  def valueProducts[B <: ProductVals[A]](implicit factory: Int => B): B =
  { val length = thisSeq.length
    val valProds = factory(length)
    var count = 0
    while (count < length){ valProds.setElem(count, thisSeq(count)); count += 1 }
    valProds
  }
}
