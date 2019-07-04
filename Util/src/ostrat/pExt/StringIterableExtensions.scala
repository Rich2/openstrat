/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pExt

case class StringIterableExtensions(iter: Iterable[String])
{   
   def strFold(seperatorString: String = ""): String = iter.ifEmpty("", iter.tail.foldLeft(iter.head)(_ + seperatorString + _))
   def strFold2[A](initialAcc2: A, initialString: String = "")(f: (String, A) => (String, A)) =
   {
      def loop(rem: Iterable[String], strAcc: String, acc2: A): String = rem.ifEmpty(strAcc,
         {
         val (newStrAcc, newAcc2) = f(rem.head, acc2)
         loop(rem.tail, strAcc + newStrAcc, newAcc2)
         })
      loop(iter, initialString, initialAcc2)   
   }
   def commaFold: String = strFold(", ")
   def semiFold: String = strFold("; ")
   def semiParenth: String = semiFold.enParenth
   def commaParenth: String = commaFold.enParenth
   def insertSpaces: String = strFold(" ")
   def insertSlashes: String = strFold(" / ")   
}
