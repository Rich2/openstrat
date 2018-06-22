/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

trait Indenter extends IndentCon

object IndentCon
{
   implicit class ImpIndentConSeq[A <: IndentCon](thisSeq: Seq[A])
   {
      def out(indent: Int): String = thisSeq match
      {
         case Seq() => ""
         case Seq(head) if !head.multiLine => head.out(indent + 2)   
         case _ => thisSeq.foldLeft("")(_.nl - (indent + 2).toSpaces + _.out(indent + 2)).nl - indent.toSpaces  
      }
      def encOut(indent: Int, begStr: String, endStr: String) = thisSeq match
      {
         case Seq() => begStr + endStr
         case Seq(head) if !head.multiLine => begStr + head.out(indent + 2) + endStr   
         case _ => thisSeq.toStrFold("\n" + indent.toSpaces, _.out(indent + 2)) + endStr  
      }
      def curlyedOut(indent: Int) = thisSeq.encOut(indent, "{", "}")
      def multiLine: Boolean = thisSeq.length match
      {
         case 0 => false
         case 1 => thisSeq.head.multiLine
         case _ => true //thisSeq.foldLeft(0)(_ + _.length) > 60
      }
   }
}

trait IndentCon
{
   def out(ind: Int): String   
   def outLen: Int = out(0).length
   def multiLine: Boolean = outLen > 40
}



//trait IndentMulti
//{
//   def conSeq: Seq[IndentCon] 
//}
//}