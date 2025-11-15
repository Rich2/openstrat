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
  def apply(lines: String*): HtmlCodeLines = new HtmlCodeLinesGen(lines.toArr.toDivLines, RArr())

  /** Factory apply method to display multiple lines of code in HTML. */
  def apply(contents: RArr[XCon], otherAttribs: RArr[XAtt] = RArr()): HtmlCodeLines = new HtmlCodeLinesGen(contents, otherAttribs)

  /** Implementation class for the general case of [[HtmlCodeLines]]. */
  class HtmlCodeLinesGen(val contents: RArr[XCon], otherAttribs: RArr[XAtt]) extends HtmlCodeLines
  { override def attribs: RArr[XAtt] = otherAttribs
//    override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = outLines(indent, line1InputLen, maxLineLen).text
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
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = outLines(indent, line1InputLen, maxLineLen).text
}

object HtmlCodeInline
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): HtmlCodeInline = new HtmlCodeInline
  { override def contents: RArr[XCon] = RArr(str)
    override def attribs: RArr[XAtt] = RArr()
  }
}

class CodeChangeLine(val oldCode: String, val newCode: String, val attribs: RArr[XAtt]) extends HtmlDivLine
{ override def contents: RArr[XCon] = RArr("Change", HtmlCodeInline(oldCode), "to", HtmlCodeInline(newCode))

}

object CodeChangeLine
{
  def apply(oldCode: String, newCode: String, attribs: XAtt*): CodeChangeLine = new CodeChangeLine(oldCode, newCode, attribs.toRArr)
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

case class CodeOutputLines(strs: StrArr, otherAttribs: RArr[XAtt]) extends CodeOutput, HtmlTagLines
{ override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  override def contents: RArr[XCon] = strs.map(s => HtmlDiv(s))
}

object CodeOutputLines
{
  def apply(contents: String*): CodeOutputLines = new CodeOutputLines(contents.toArr, RArr())
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

  /** Creates an HTML Escape element and registers the textContent of the inner pre element with an HTML Text Input. The function passed to the updater will not
   * escape the HTML code characters. */
  def inputText(input: InputUpdaterText, otherAttribs: XAtt*)(f: String => String): HtmlCodePre =
  { def newId = input.next1Id(f)
    val pre = new HtmlPre(f(input.valueStr).escapeHtml, RArr(newId))
    new HtmlCodePre(pre, otherAttribs.toRArr)
  }

  /** Creates an HTML Code Pre element and registers the textContent with 2 HTML Text Inputs. */
  def input2Text(input1: InputUpdaterText, input2: InputUpdaterText, otherAttribs: XAtt*)(f: (String, String) => String): HtmlCodePre =
  { def targetId = input1.next2Id1(input2.idStr, f)
    input2.next2Id2(targetId.valueStr, input1.idStr, f)
    val pre = new HtmlPre(f(input1.valueStr, input2.valueStr).escapeHtml, RArr(targetId))
    new HtmlCodePre(pre, otherAttribs.toRArr)
  }
}