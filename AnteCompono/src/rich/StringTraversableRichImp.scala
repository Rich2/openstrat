/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich

case class StringTraversable(trav: Traversable[String])
{   
   def strFold(seperatorString: String = ""): String = trav.ifEmpty("", trav.tail.foldLeft(trav.head)(_ + seperatorString + _))
   def strFold2[A](initialAcc2: A, initialString: String = "")(f: (String, A) => (String, A)) =
   {
      def loop(rem: Traversable[String], strAcc: String, acc2: A): String = rem.ifEmpty(strAcc,
         {
         val (newStrAcc, newAcc2) = f(rem.head, acc2)
         loop(rem.tail, strAcc - newStrAcc, newAcc2)
         })
      loop(trav, initialString, initialAcc2)   
   }
   def commaFold: String = strFold(", ")
   def semicolonFold: String = strFold("; ")
   def commaParenth: String = commaFold.enParenth
   def insertSpaces: String = strFold(" ")
   def insertSlashes: String = strFold(" / ")   
}
