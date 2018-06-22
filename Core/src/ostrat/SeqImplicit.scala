/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

class SeqImplicit[A](thisSeq: Seq[A])
{
   /** This needs to be changed to by name parameters when by name varargs are allowed. I think this is coming in 12.3 */
   def ifAppend[B >: A](b: Boolean, elems: B*): Seq[B] = if (b) thisSeq ++ elems else thisSeq
   def ifAppendSeq[B >: A](b: Boolean, elems: Seq[B]): Seq[B] = if (b) thisSeq ++ elems else thisSeq
  // def +* (el: A): Seq[A] = if (el == null) thisSeq else thisSeq :+ el
   def fHead[B](ifEmpty: => B, fNonEmpty: (A, Seq[A]) => B): B = if (thisSeq.isEmpty) ifEmpty else fNonEmpty(thisSeq.head, thisSeq.tail)
   def fLast[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisSeq.isEmpty) ifEmpty else fNonEmpty(thisSeq.last)
   def addOpt(optEl: Option[A]): Seq[A] = optEl match
   {
      case None => thisSeq
      case Some(el) => thisSeq :+ el
   }
   def next(currEl: A): A =
   {
      val currIndex = thisSeq.indexOf(currEl)
      val nextIndex = ife(currIndex == thisSeq.length, 0, currIndex + 1)
      thisSeq(nextIndex)
   }
   def prev(currEl: A): A =
   {
      val currIndex = thisSeq.indexOf(currEl)
      val prevIndex  = ife(currIndex == 0, thisSeq.length - 1, currIndex - 1)
      thisSeq(prevIndex)
   }
   def eSeqs[B, C](f: A => Either[B, C]): (Seq[B], Seq[C]) =
   {
      def loop(rem: Seq[A], accB: Seq[B], accC: Seq[C]): (Seq[B], Seq[C]) = rem.fHead((accB, accC), (h, tail1) => f(h) match
      {
         case Left(b) => loop(tail1, accB :+ b, accC)
         case Right(c) => loop(tail1, accB, accC :+ c)
      })
      loop(thisSeq, Seq(), Seq())
   }    
   
   def foldMin[B](f: A => B)(implicit cmp: Ordering[B]): B = thisSeq.tail.foldLeft(f(thisSeq.head))((acc, el) => cmp.min(acc, f(el)))
   def foldMax[B](f: A => B)(implicit cmp: Ordering[B]): B = thisSeq.tail.foldLeft(f(thisSeq.head))((acc, el) => cmp.max(acc, f(el)))
   def foldMinMax[B](f: A => B)(implicit cmp: Ordering[B]): (B, B) =
   {
      val initVal = f(thisSeq.head)
      thisSeq.tail.foldLeft((initVal, initVal))((acc, el) =>
         {
            val (accMin, accMax) = acc
            val newVal = f(el)
            val min = cmp.min(accMin, newVal)
            val max = cmp.max(accMax, newVal)
            (min, max)
         })
   }
   def mapMinMaxStr[B](f: A => B)(implicit cmp: Ordering[B]): String =
   {
      val (min, max) = thisSeq.foldMinMax(f)(cmp)
      min.toString.commaAppend(max.toString)
   }
   def filterMap[B](fFilter: A => Boolean, fMap: A => B): Seq[B] =
      thisSeq.foldLeft[Seq[B]](Seq())((acc, h) => ife(fFilter(h), acc :+ fMap(h), acc))
   
   /** groups the Sequence into pairs and then maps over them. Throws exception if seq.length.isOdd */   
   def mapBy2[B](f: (A, A) => B): List[B] =
   {
      var rem = thisSeq
      var acc: List[B] = Nil
      while(rem.nonEmpty)
      {
         val v1 = rem.head
         rem = rem.tail
         acc :+= f(v1, rem.head)
         rem = rem.tail
      }
      acc
   }
   def pMap[B , C <: ValueProducts[B]](f: A => B)(implicit factory: Int => C): C =
   {
      val res = factory(thisSeq.length)
      var count: Int = 0
      thisSeq.foreach { orig => 
         val newValue: B = f(orig)
         res.setElem(count, newValue)
         count += 1         
      }
      res
   }
   
}
