/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat

/** Not sure what the Con means. */
trait IndentCon
{ def out(ind: Int): String
  def outLen: Int = out(0).length
  def multiLine: Boolean = outLen > 40
}

//trait Indenter extends IndentCon

object IndentCon
{
  implicit class ImpIndentConSeq[A <: IndentCon](thisSeq: Seq[A])
  {
    def out(indent: Int): String = thisSeq match
    { case Seq() => ""
      case Seq(head) if !head.multiLine => head.out(indent + 2)
      case _ => thisSeq.foldLeft("")(_.nl + (indent + 2).spaces + _.out(indent + 2)).nl + indent.spaces
    }

    def encOut(indent: Int, begStr: String, endStr: String) = thisSeq match
    { case Seq() => begStr + endStr
      case Seq(head) if !head.multiLine => begStr + head.out(indent + 2) + endStr
      case _ => thisSeq.toStrsFold("\n" + indent.spaces, _.out(indent + 2)) + endStr
    }

    def curlyedOut(indent: Int) = thisSeq.encOut(indent, "{", "}")

    def multiLine: Boolean = thisSeq.length match
    { case 0 => false
      case 1 => thisSeq.head.multiLine
      case _ => true //thisSeq.foldLeft(0)(_ + _.length) > 60
    }
  }
}





//trait IndentMulti
//{
//   def conSeq: Seq[IndentCon] 
//}
//}