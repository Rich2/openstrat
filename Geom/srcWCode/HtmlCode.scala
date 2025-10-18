/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

/** An HTML code element. */
trait HtmlCode extends HtmlUnvoid
{ override def tagName: String = "code"
}

/** A multi line, HTML, code element */
trait HtmlCodeLines extends HtmlCode, HtmlTagLines

object HtmlCodeLines
{ /** Factory apply method to display multiple lines of code in HTML, with no additional attributes. */
  def apply(lines: String*): HtmlCodeLines = new HtmlCodeLinesGen(lines.toArr.toSpanLines, RArr())

  /** Factory apply method to display multiple lines of code in HTML. */
  def apply(contents: RArr[XCon], otherAttribs: RArr[XAtt] = RArr()): HtmlCodeLines = new HtmlCodeLinesGen(contents, otherAttribs)

  /** Implementation class for the general case of [[HtmlCodeLines]]. */
  class HtmlCodeLinesGen(val contents: RArr[XCon], otherAttribs: RArr[XAtt]) extends HtmlCodeLines
  { override def attribs: RArr[XAtt] = otherAttribs    
  }
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

/** A code output attribute. Useful in documentation foe distinguishing output from code and commands entered by the user. */
object CodeOutputAtt extends ClassAtt("output")

/** Html Bash code element. */
trait CodeOutput extends HtmlCode
{ override def attribs: RArr[XAtt] = RArr(CodeOutputAtt)
}

/** An HTML element for code output, that is on its own line. Adds the [[CodeOutputAtt]] attribute. */
trait CodeOutputLine extends HtmlCode, HtmlOwnLineBlocked
{ override def attribs: RArr[XAtt] = super.attribs +% CodeOutputAtt
}

object CodeOutputLine
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): CodeOutputLine = new CodeOutputLineGen(RArr(str), RArr())

  /** Creates a code output line and registers the textContent with an HTML Text Input. */
  def inputText(input: InputUpdaterText)(f: String => String): CodeOutputLine =
  { def newId = input.next1Id(f)
    new CodeOutputLineGen(RArr(f(input.valueStr)), RArr(newId))
  }

  /** Implementation class for the general case of [[CodeOutputLine]]. */
  case class CodeOutputLineGen(contents: RArr[XCon], otherAttribs: RArr[XAtt]) extends CodeOutputLine
  { override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  }
}

/** Html directory path code element. */
class HtmlDirPath(val str: String) extends HtmlCodeInline
{ def classAtt: ClassAtt = ClassAtt("path")
  override def contents: RArr[XCon] = RArr(str)
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

/** An HTML code element with an [[HtmlPre]] element as its contents. */
class HtmlCodePre(val htmlPre: HtmlPre, val otherAttribs: RArr[XAtt]) extends HtmlCodeLines
{ override def contents: RArr[XCon] = RArr(htmlPre)
  override def attribs: RArr[XAtt] = BlockStyle %: otherAttribs
}

object HtmlCodePre
{ /** Factory apply method to create  */
  def apply(str: String, otherAttribs: XAtt*): HtmlCodePre = new HtmlCodePre(HtmlPre(str), otherAttribs.toRArr)

  /** Creates an HTML Escape element and registers the textContent of the inner pre element with an HTML Text Input. */
  def inputText(input: InputUpdaterText, otherAttribs: XAtt*)(f: String => String): HtmlCodePre =
  { def newId = input.next1Id(f)
    val pre = new HtmlPre(f(input.valueStr), RArr(newId))
    new HtmlCodePre(pre, otherAttribs.toRArr)
  }
}