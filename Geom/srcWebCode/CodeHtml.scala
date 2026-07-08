/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

/** An HTML code element. */
trait CodeHtml extends HtmlUnvoid
{ override def tagName: String = "code"
}

/** A multi line, HTML, code element */
trait CodeLinesHtml extends CodeHtml, HtmlTagLines

object CodeLinesHtml
{ /** Factory apply method to display multiple lines of code in HTML, with no additional attributes. */
  def apply(lines: String*): CodeLinesHtml = new HtmlCodeLinesGen(lines.toArr.toDivLines, RArr())

  /** Factory apply method to display multiple lines of code in HTML. */
  def apply(contents: RArr[XCon], otherAttribs: RArr[XAtt] = RArr()): CodeLinesHtml = new HtmlCodeLinesGen(contents, otherAttribs)

  /** Implementation class for the general case of [[CodeLinesHtml]]. */
  class HtmlCodeLinesGen(val contents: RArr[XCon], otherAttribs: RArr[XAtt]) extends CodeLinesHtml
  { override def attribs: RArr[XAtt] = otherAttribs
  }
}

/** An HTML code element that is on its own line. */
trait CodeLineHtml extends CodeHtml, HtmlOwnLineBlocked

object CodeLineHtml
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): CodeLineHtml = new CodeLineHtml
  { override def contents: RArr[XCon] = RArr(str)    
  }
}

/** An HTML code element that can be inlined. */
trait CodeInline extends CodeHtml, HtmlInedit
{ override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = outLines(indent, line1InputLen, maxLineLen).text
}

object CodeInline extends HtmlElemCompanion[CodeInline]
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(contents: XCon*): CodeInline = new CodeInlineGen(contents.toRArr, RArr())

  /** Factory apply method to create an inline HTML cose element. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt]): CodeInline = new CodeInlineGen(contents, attribs)

  override def fromStr(str: String, attribs: RArr[XAtt]): CodeInline = new CodeInlineGen(RArr(str), attribs)

  /** Implementation class for the general casee of [[CodeInline]].  */
  case class CodeInlineGen(contents: RArr[XCon], attribs: RArr[XAtt]) extends CodeInline
}

/** Html code to display a change of code. */
trait CodeChangeLine extends DivLine
{ def oldCode: CodeInline
  def newCode: CodeInline
  override def contents: RArr[XCon] = RArr("Change", oldCode, "to", newCode)
}

object CodeChangeLine
{ /** Factory apply method for sequence of HYML code lines formed from an [[StrArr]]. */
  def apply(oldCode: String, newCode: String, attribs: XAtt*): CodeChangeLine = CodeChangeLineGen(CodeInline(oldCode), CodeInline(newCode), attribs.toRArr)

  /** Creates a code change line and registers the textContents with an HTML Text Input. */
  def listenText(input: UpdaterStr)(f1: String => String)(f2: String => String): CodeChangeLine =
  { val newId1 = input.next1Text(f1)
    val oldCode: CodeInline = CodeInline(RArr(f1(input.valueStr)), RArr(newId1))
    val newId2 = input.next1Text(f2)
    val newCode: CodeInline = CodeInline(RArr(f2(input.valueStr)), RArr(newId2))
    CodeChangeLineGen(oldCode, newCode, RArr())
  }
  case class CodeChangeLineGen(oldCode: CodeInline, newCode: CodeInline, attribs: RArr[XAtt]) extends CodeChangeLine
}

/** A code output attribute. Useful in documentation foe distinguishing output from code and commands entered by the user. */
object CodeOutputAtt extends ClassAtt("output")

/** Html code output element. */
trait CodeOutput extends CodeHtml
{ override def attribs: RArr[XAtt] = RArr(CodeOutputAtt)
}

/** An HTML element for code output, that is on its own line. Adds the [[CodeOutputAtt]] attribute. */
trait CodeOutputLine extends CodeHtml, HtmlOwnLineBlocked
{ override def attribs: RArr[XAtt] = super.attribs +% CodeOutputAtt
}

object CodeOutputLine extends HtmlElemCompanion[CodeOutputLine]
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): CodeOutputLine = new CodeOutputLineGen(RArr(str), RArr())

  override def fromStr(str: String, attribs: RArr[XAtt]): CodeOutputLine = new CodeOutputLineGen(RArr(str), attribs)

  /** Implementation class for the general case of [[CodeOutputLine]]. */
  case class CodeOutputLineGen(contents: RArr[XCon], otherAttribs: RArr[XAtt]) extends CodeOutputLine
  { override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  }
}

/** Sequence of HYML code lines formed from an [[StrArr]]. */
case class CodeOutputLines(strs: Arr[String], otherAttribs: RArr[XAtt]) extends CodeOutput, HtmlTagLines
{ override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  override def contents: RArr[XCon] = strs.map(s => DivHtml(s))
}

object CodeOutputLines
{ /** Factory apply method for sequence of HYML code lines formed from an [[StrArr]]. */
  def apply(contents: String*): CodeOutputLines = new CodeOutputLines(contents.toArr, RArr())
}

/** Html directory path code element. */
class HtmlDirPath(val str: String) extends CodeInline
{ def classAtt: ClassAtt = ClassAtt("path")
  override def contents: RArr[XCon] = RArr(str)
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

/** An HTML code element with an [[PreHtml]] element as its contents. */
trait PreCode extends PreHtml
{ def codeElem: CodeSpecial
  override def contents: RArr[XCon] = RArr(codeElem)
  override def attribs: RArr[XAtt] = RArr(BlockStyle)// %: otherAttribs
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = openTag(0, 0) + codeElem.out + closeTag
  override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines = TextLines(openTag(0, 0) + codeElem.out + closeTag)

  trait CodeSpecial extends CodeHtml
  { /** The code as a [[String]] including newlines and indents. */
    def codeStr: String

    override def contents: RArr[XCon] = RArr(codeStr)
    override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = openTag(indent + 2, line1InputLen + 5) + codeStr --- closeTag
    override def outLines(indent: Int, line1InputLen: Int, maxLineLen: Int): TextLines = TextLines(openTag(indent + 2, line1InputLen + 5), codeStr, closeTag)
  }
}

object PreCode
{ /** Factory apply method to create  */
  def apply(str: String, otherAttribs: XAtt*): PreCode = PreCodeGen(str, otherAttribs.toRArr)

  /** Creates an HTML Escape element and registers the textContent of the inner pre element with an HTML Text Input. The function passed to the updater will not
   * escape the HTML code characters. */
  def listenText(input: UpdaterInputStr, otherAttribs: XAtt*)(f1: String => String): PreCode =
  { val f2: String => String = s1 => f1(s1).escapeHtml
    def newId: IdAtt = input.next1Text(f2)

    new PreCode
    {  override def codeElem: CodeSpecial = new CodeSpecial
      { override def codeStr: String = f2(input.valueStr)
        override def attribs: RArr[XAtt] = RArr(newId, BlockStyle)
      }
    }
  }

  /** Creates an HTML Code Pre element and registers the textContent with 2 HTML Text Updaters. */
  def listen2Text(input1: UpdaterStr, input2: UpdaterStr, otherAttribs: XAtt*)(f1: (String, String) => String): PreCode =
  { val f2: (String, String) => String = (s1, s2) => f1(s1, s2).escapeHtml
    val idAtt: IdAtt = input1.next2Text1(input2, f2)
    new PreCode
    { override def codeElem: CodeSpecial = new CodeSpecial
      { override def attribs: RArr[XAtt] = RArr(idAtt, BlockStyle) ++ otherAttribs.toRArr
        override def codeStr: String = f2(input1.valueStr, input2.valueStr)
      }
    }
  }

  /** Creates an HTML Code Pre element and registers the textContent with 3 HTML Text Updaters. */
  def listen3Text(input1: UpdaterStr, input2: UpdaterStr, input3: UpdaterStr, otherAttribs: XAtt*)(f1: (String, String, String) => String): PreCode =
  { val f2: (String, String, String) => String = (s1, s2, s3) => f1(s1, s2, s3).escapeHtml
    val idAtt: IdAtt = input1.next3Id1(input2, input3, f2)
    new PreCode
    { override def codeElem: CodeSpecial = new CodeSpecial
      { override def attribs: RArr[XAtt] = RArr(idAtt, BlockStyle) ++ otherAttribs.toRArr
        override def codeStr: String = f2(input1.valueStr, input2.valueStr, input3.valueStr)
      }
    }
  }

  /** An HTML code element with an [[PreHtml]] element as its contents. */
  case class PreCodeGen(codeStrIn: String, otherAttribs: RArr[XAtt]) extends PreCode
  { val codeElem: CodeSpecial = new CodeSpecial
    { override def attribs: RArr[XAtt] = RArr(BlockStyle)
      override def codeStr: String = codeStrIn.escapeHtml
    }
  }
}