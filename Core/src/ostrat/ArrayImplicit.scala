/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

class ArrayImplicit[A](val thisArray: Array[A]) extends AnyVal
{
   /** This method and "fHead" removes the need for headOption in the majority of case. Use fHead when are interested in the 
     *  tail value */
   def headOnly[B](ifEmpty: => B, fNonEmpty: A => B): B = if (thisArray.length == 0) ifEmpty else fNonEmpty(thisArray(0))
   //Not sure if this is useful
   //def updateFrom(startElem: Int, newElems: A *): Unit = newElems.zipWithIndex.foreach(p => arr.update(startElem + p._2, p._1))
//   def foriToEnd(startIndex: Int, f: (i A => Unit): Unit =
//   {
//      val endIndex = thisArray.length
//      var count = startIndex
//      while(count < endIndex)
//      {
//         f(thisArray(count))
//         count += 1
//      }
//   }      
}