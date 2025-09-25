/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

object BashAtt extends ClassAtt("bash")

/** Html Bash code element. */
trait HtmlBash extends HtmlCode
{ override def attribs: RArr[XAtt] = RArr(BashAtt)
}

/** A multi line, Html, Bash code element. */
class HtmlBashMulti(val lines: StrArr, otherAttribs: RArr[XAtt]) extends HtmlBash, HtmlCodeLines
{ override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
}

object HtmlBashMulti
{
  def apply(lines: String*): HtmlBashMulti = new HtmlBashMulti(lines.toArr, RArr())
}

/** Html Bash code element, that is on ts own line. For the general case use the [[BashLine]] class. */
trait BashOwnLine extends HtmlBash, HtmlCodeLine
{ override def attribs: RArr[XAtt] = super.attribs +% BashAtt
}

/** An HTML Bash code element that will display on its own line. */
class BashLine(val contents: RArr[XConInline], val otherAttribs: RArr[XAtt]) extends BashOwnLine
{ override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
}

object BashLine
{ /** Factory apply method to write Bash code in HTML on its own line. */
  def apply(contents: XConInline*): BashLine = new BashLine(contents.toArr, RArr())

  def apply(contents: RArr[XConInline], attribs: RArr[XAtt]): BashLine = new BashLine(contents, attribs)

  def classAtt(classStr: String, conStr: String, otherAttribs: XAtt*): BashLine = new BashLine(RArr(conStr), ClassAtt(classStr) %: otherAttribs.toArr)

  /** Creates a Bash line na registers the textContent with an HTML Text Input. */
  def inputText(input: TextInput)(f: String => String): BashLine =
  { def newId = input.next1Id(f)
    new BashLine(RArr(f(input.valueStr)), RArr(newId))
  }
}

/** Html BASH code element, that can be inlined. */
class BashInline(val str: String) extends HtmlBash, HtmlCodeInline
{ override def contents: RArr[XCon] = RArr(str)
}

object BashInline
{ /** Factory apply method for [[BashInline]]. */
  def apply(str: String): BashInline = new BashInline(str)
}

/** Attribute for the bash prompt class. Allows the prompt to be in a different colour to the BASH commands. It may be important to show what directory the
 * command is being launched from. */
object BashPromptClass extends ClassAtt("bashprompt")

/** A span set to cover a Bash prompt. This allows the prompt to be in a different colour to the BASH commands. */
class BashPromptSpan(val str: String, otherAttribs: RArr[XAtt]) extends SpanInline
{ override def contents = RArr(str)
  override def attribs: RArr[XAtt] = BashPromptClass %: otherAttribs
}

object BashPromptSpan
{ /** Factory apply method to create a [[BashPromptSpan]]. */
  def apply(str: String, attribs: XAtt*): BashPromptSpan = new BashPromptSpan(str, attribs.toArr)

  /** Factory method to create a [[BashPromptSpan]] with a class attribute. */
  def classAtt(classStr: String, conStr: String, otherAttribs: XAtt*): BashPromptSpan = new BashPromptSpan(conStr, ClassAtt(classStr) %: otherAttribs.toArr)

  /** Factory method to create a [[BashPromptSpan]] with a class attribute. */
  def classAtts(classStrs: String*)(conStr: String, otherAttribs: XAtt*): BashPromptSpan =
    new BashPromptSpan(conStr, ClassAtt(classStrs*) %: otherAttribs.toArr)

  /** Creates a Bash line na registers the textContent with an HTML Text Input. */
  def inputText(input: TextInput, otherAttribs: XAtt*)(f: String => String): BashPromptSpan =
  { def newId = input.next1Id(f)
    new BashPromptSpan(f(input.valueStr), newId %: otherAttribs.toArr)
  }
}

/** An HTML element to display a BASH prompt and command on its own line.  */
class BashWithPrompt(val prompt: String, command: String) extends BashOwnLine
{ def promptSpan: SpanInline = SpanInline(prompt, BashPromptClass)
  override def contents: RArr[XConInline] = RArr(promptSpan, command)
}

class BashWithPromptMulti(val texts: StrArr, otherAttribs: RArr[XAtt]) extends HtmlBash, HtmlTagLines
{ override def contents: RArr[XCon] = iUntilMap(texts.length / 2){i => SpanLine(BashPromptSpan(texts(i * 2)), texts(i * 2 + 1)) }
  override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
}

object BashWithPromptMulti
{
  def apply(strs: String*): BashWithPromptMulti = new BashWithPromptMulti(strs.toArr, RArr())
}