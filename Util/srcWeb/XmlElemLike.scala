/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Content for XML and HTML elements. */
trait XCon
{ /** Returns the XML source code, formatted according to the input. This allows the XML to be indented according to its context. */
  def out(indent: Int, maxLineLen: Int = 150): String

  def outEither(indent: Int, maxLineLen: Int = 150): (Boolean, String) = (false, out(indent, maxLineLen))
}

/** XConStr is a wrapper to convert [[String]]s to XCon, XML Element content. */
case class XConStr(value: String) extends XCon
{ override def out(indent: Int, maxLineLen: Int): String = value
  override def outEither(indent: Int, maxLineLen: Int): (Boolean, String) = (true, out(indent, maxLineLen))
}

object XConStr
{ /** Implicitly converts a string in to a piece of XML / HTML content. */
  implicit def StringToXConStr(value: String): XConStr = new XConStr(value)
}

/** An XML or an HTML element */
trait XmlElemLike extends XCon
{ /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  def tag: String

  /** The attributes of this XML / HTML element. */
  def attribs: RArr[XmlAtt]

  /** The content of this XML / HTML element. */
  def contents: RArr[XCon]

  def attribsOut: String = ife(attribs.empty, "", " " + attribs.foldStr(_.str, " "))
  def openAtts: String = "<" + tag + attribsOut
  def openUnclosed: String = openAtts + ">"
  def openTag: String = openAtts + ">"
  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

trait XmlLikeMulti extends XmlElemLike
{
  override def out(indent: Int = 0, maxLineLen: Int = 150): String =
    if (contents.empty) openAtts + "/>"
    else openUnclosed.nli(indent + 2) + contents.foldStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

trait XmlLikeInline extends XmlElemLike
{
  override def outEither(indent: Int, maxLineLen: Int = 150): (Boolean, String) = (true, out(indent, maxLineLen))

  override def out(indent: Int = 0, maxLineLen: Int = 150): String =
  { val cons = contents.map(_.outEither(indent, maxLineLen))
    val middle = cons.length match {
      case 1 if cons.head._1 => cons.head._2
      case n => cons.foldLeft("") { (acc, el) => acc --- el._2 } + "\n"
    }
    openTag + middle + closeTag
  }
}

trait XmlLikeStr extends XmlLikeInline
{ def str: String
  override def contents: RArr[XCon] = RArr(str.xCon)
}

/** XConStr is a wrapper to convert [[String]]s to XCon, XML Element content. */
case class XConStrLines(value: String) extends XCon
{ override def out(indent: Int, maxLineLen: Int): String = value
}