/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

/** An HTML code element. */
trait HtmlCode extends HtmlUnvoid
{ override def tag: String = "code"
}

/** A multi line, HTML, code element */
trait HtmlCodeLines extends HtmlCode, HtmlTagLines
{ /** the lines of code unindented. */
  def lines: StrArr

  override def contents: RArr[XCon] = lines.map{
    case "" => SpanLine("<br>")
    case l => SpanLine(l)
  }
}

class CodeLines(val lines: StrArr, otherAttribs: RArr[XAtt]) extends HtmlCodeLines
{ override def attribs: RArr[XAtt] = otherAttribs
}

object CodeLines
{ /** Factory apply method to display mulitple lines of code in HTML. */
  def apply(lines: String*): CodeLines = new CodeLines(lines.toArr, RArr())
}

/** An HTML code element that is on its own line. */
trait HtmlCodeLine extends HtmlCode, HtmlOwnLineBlocked

object HtmlCodeLine
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): HtmlCodeLine = new HtmlCodeLine
  { override def contents: RArr[XCon] = RArr(str)    
  }
}

/** An HTML code element that can be inlined. */
trait HtmlCodeInline extends HtmlCode, HtmlInline

object HtmlCodeInline
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): HtmlCodeInline = new HtmlCodeInline
  { override def contents: RArr[XCon] = RArr(str)
    override def attribs: RArr[XAtt] = RArr()
  }
}

/** A code output attribute. Useful in documention foe distinguishing output from code and commands entered by the user. */
object CodeOutputAtt extends ClassAtt("output")

/** Html Bash code element. */
trait CodeOutput extends HtmlCode
{ override def attribs: RArr[XAtt] = RArr(CodeOutputAtt)
}

/** An HTML element for code output, that is on its own line. */
trait CodeOutputLine extends HtmlCode, HtmlOwnLineBlocked
{ override def attribs: RArr[XAtt] = super.attribs +% CodeOutputAtt
}

object CodeOutputLine
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): CodeOutputLine = new CodeOutputLine
  { override def contents: RArr[XCon] = RArr(str)
  }
}

/** Html directory path code element. */
class HtmlDirPath(val str: String) extends HtmlCodeInline
{ def classAtt: ClassAtt = ClassAtt("path")
  override def contents: RArr[XCon] = RArr(str)
  override def attribs: RArr[XAtt] = RArr(classAtt)
}