/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Content for XML and HTML elements. */
trait XCon
{ /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. */
  def out(indent: Int, maxLineLen: Int = 150): String

  /** I don't think this has been properly implemented. I believe the Boolean in the return value indicates if it is a single line output. */
  def outEither(indent: Int, maxLineLen: Int = 150): (Boolean, String) = (false, out(indent, maxLineLen))
}

/** XML / HTML text that can have its line breaks changed. */
case class XConText(value: String) extends XCon
{ override def out(indent: Int, maxLineLen: Int): String = value
  override def outEither(indent: Int, maxLineLen: Int): (Boolean, String) = (true, out(indent, maxLineLen))

  def outLines(indent: Int, line1: Int, maxLineLen: Int): TextLines =
  {
    def loop(rem: String, lines: StrArr, currLine: String): StrArr = rem match{
      case "" => lines +% currLine
      case s if s.head.isWhitespace => loop(rem.tail, lines, currLine)
      case s => wordLoop(rem, "", currLine.length) match {
        case None => loop(rem, lines +% currLine, "\n" + indent.spaces)
        case Some((newRem, newWord)) => loop(newRem, lines, currLine -- newWord)
      }
    }

    def wordLoop(rem: String, currWord: String, lineLen: Int): Option[(String, String)] = rem match {
      case "" => Some(rem, currWord)
      case s if s(0).isWhitespace => Some(rem, currWord)
      case s if lineLen >= maxLineLen => None
      case s => wordLoop(rem.tail, currWord :+ s.head, lineLen + 1)
    }

    def in2Loop(rem: String, currStr:String, lineLen: Int): Option[TextIn2Line] = rem match{
      case "" => Some(TextIn2Line(currStr, lineLen))
      case s if s(0).isWhitespace => in2Loop(rem.tail, currStr, lineLen)
      case s if lineLen >= maxLineLen => None
    }

    loop(value, StrArr(), "") match{
      case sa if sa.length == 1 => TextIn1Line(sa(0), sa(0).length)
      case sa => TextOwnLines(sa.foldStr(s => s), sa.length)
    }
  }
}

/** XML / HTML just stored as a [[String]]. This is not desirable, except as a temporary expedient. */
case class XmlAsString(value: String) extends XCon
{ override def out(indent: Int, maxLineLen: Int): String = value
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
  override def contents: RArr[XCon] = RArr(XConText(str))
}