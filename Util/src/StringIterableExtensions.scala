/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

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

   /** Make with commas. Make 1 string, by appending with ", " separator from this collection of strings. */
   def mkComma: String = iter.mkString(", ")

   /** Make with semicolons. Make 1 string, by appending with "; " separator from this collection of strings. */
   def mkSemi: String = iter.mkString("; ")

   /** Make with semicolons inside parentheses. Make 1 string, by appending with "; " separator from this collection of strings and then enclose with a
    * pair of parentheses. */
   def mkSemiParenth: String = mkSemi.enParenth

   /** Make with commas inside parentheses. Make 1 string, by appending with ", " separator from this collection of stringsand then enclose with a pair
    * of parentheses. */
   def mkCommaParenth: String = mkComma.enParenth

   def insertSpaces: String = strFold(" ")
   def insertSlashes: String = strFold(" / ")
   def encurly: String =
   {
      var acc: String = "{ "
      var count = 0
      iter.foreach {s => count match
      {
         case 0 => { acc = acc + s; count = 1 }
         case _ => acc = acc + "\n  " + s
      }}
      acc = ife(count == 0, acc + "}", acc + "\n}")
      acc
   }
}