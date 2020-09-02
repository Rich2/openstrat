/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** Xml Content. This trait allows us to use ordinary strings and XMl elements as the default content of Xml elements. For stricter typing every
 *  element should have its own type, but creating the content type for each element is time consuming. In a few cases like the HTML style element the
 *  XCon type is not appropriate.  */
trait XCon extends IndentCon
//{
//   def out(ind: Int): String
//}
/** An XML Element */
trait XmlEl extends XCon
{ def tag: String
  def atts: Seq[XAtt] = Seq[XAtt]()
  def startTagClose: String
  def conMulti: Boolean = false
  def attMulti: Boolean = atts.exists(_.multiLine) | atts.length > 5 
  override def multiLine: Boolean = conMulti | attMulti
  final def openAtts(indent: Int): String = "<" + tag + startTagAtts(indent) + startTagClose
   
  private def startTagAtts(indent: Int): String = atts match
  {
    case Seq() => ""
     
    case _ if attMulti =>
    {
      def loop(rem: Seq[XAtt], acc: String, i: Int): String = rem match
      { case Seq() => acc + "\n" + indent.spaces
        case Seq(h, tail @ _*) if h.multiLine => loop(tail, acc.nli + indent.spaces + h.out(indent + 2), 5)
        //case Seq(h, tail @ _*) if h. == 5 => loop(tail, acc.nl - h.out(indent + 2), 1)
        case Seq(h, tail @ _*) => loop(tail, acc -- h.out(indent + 2), i + 1)
      }
      loop(atts, "", 0)
    }
    case _ => atts.foldLeft("")(_ -- _.out(indent + 2))
  }
}

trait XmlVoid extends XmlEl
{ override def startTagClose = "/>"
  override def out(indent: Int): String = openAtts(indent)    
}

trait XNotVoid extends XmlEl
{ def mems: Seq[IndentCon]
  override def startTagClose: String = ">"
  def closeTag = "</" + tag + ">"
  override def out(indent: Int) = openAtts(indent) + mems.out(indent) + closeTag   
}

trait XEmpty extends XNotVoid
{ override def mems: Seq[XCon] = Seq()
}

trait XmlSimple extends XNotVoid
{ def str: String
  def mems: Seq[XCon] = Seq(IndText(str))      
}