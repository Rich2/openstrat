/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** XML / HTML just stored as a [[String]]. This is not desirable, except as a temporary expedient. */
case class XmlAsString(value: String) extends XCon
{ override def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = value
}

/** An XML or an HTML element. */
trait XmlElemLike extends XCon
{ /** The XML /HTML tag String. A tag is a markup construct that begins with < and ends with > */
  def tag: String
  
  /** The number of characters in the tag. This is useful for calculating new lines in multi line elements. */
  def tagLen: Int = tag.length
  
  /** The length of the < character plus the tag. */
  def openTagOpenLen: Int = tagLen + 1

  /** The attributes of this XML / HTML element. */
  def attribs: RArr[XmlAtt]

  /** The content of this XML / HTML element. */
  def contents: RArr[XCon]

  /** The XML / HTML output for this element's attributes. */
  def attribsOut: String = attribs.length match
  { case 0 => ""
    case 1 => " " + attribs(0).out
    case _ if attribs.sumBy(_.out.length) < 80 => " " + attribs.mkStr(_.out, " ")
    case _ => " " + attribs.mkStr(_.out, "\n" + 2.spaces)
  }

  def openAtts: String = "<" + tag + attribsOut
  def openUnclosed: String = openAtts + ">"
  def openTag: String = openAtts + ">"
  def closeTag: String = "</" + tag + ">"
  def n1CloseTag: String = "\n" + closeTag
  def n2CloseTag: String = "\n\n" + closeTag
}

trait XmlLikeMulti extends XmlElemLike
{
  override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String =
    if (contents.empty) openAtts + "/>"
    else openUnclosed.nli(indent + 2) + contents.mkStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
}

trait XmlLike1Lineable extends XmlElemLike
{
  override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String = contents match
  { case RArr0() => openAtts + "/>"
    case RArr1(_) => openUnclosed + contents(0).out(0, 150) + closeTag
    case _ => openUnclosed.nli(indent + 2) + contents.mkStr(_.out(indent + 2, 150), "\n" + (indent + 2).spaces).nli(indent) + closeTag
  }
}

trait XmlConInline extends XmlElemLike
{
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int = lineLenDefault) = TextLines(out(indent, maxLineLen), 1, 30)

  override def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = lineLenDefault): String =
  { val cons = contents.map(_.outLines(indent,  maxLineLen))
    val middle = cons.length match
    { case 0 => ""
      case 1 if cons.head.numLines == 1 => cons.head.text
      case n => cons.foldLeft("") { (acc, el) => acc --- el.text } + "\n"
    }
    openTag + middle + closeTag
  }
}