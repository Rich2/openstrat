/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

/** The bash class attribute. */
object BashAtt extends ClassAtt("bash")

/** Html Bash code element. */
trait BashHtml extends CodeHtml
{ override def attribs: RArr[HAtt] = RArr(BashAtt)
}

/** A multi line, Html, Bash code element. */
class BashHtmlMulti(val lines: StrArr, otherAttribs: RArr[HAtt]) extends BashHtml, CodeLinesHtml
{ override def attribs: RArr[HAtt] = super.attribs ++ otherAttribs
  override def contents: RArr[XCon] = lines.toDivLines
}

object BashHtmlMulti
{ /** Multi line Bash element. */
  def apply(lines: String*): BashHtmlMulti = new BashHtmlMulti(lines.toArr, RArr())
}

/** Html Bash code element, that is on its own line. For the general case use the [[BashLine]] class. */
trait BashOwnLine extends BashHtml, CodeLineHtml
{ override def attribs: RArr[HAtt] = super.attribs +% BashAtt
}

/** An HTML Bash code element that will display on its own line. */
class BashLine(val contents: RArr[XConInedit], val otherAttribs: RArr[HAtt]) extends BashOwnLine
{ override def attribs: RArr[HAtt] = super.attribs ++ otherAttribs
}

object BashLine extends HtmlIneditCompanion[BashLine]
{ /** Factory apply method to write Bash code in HTML on its own line. There is an apply name overload that takes the contents as repeat parameters, but with no
   * attributes. */
  def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): BashLine = new BashLine(contents, attribs)
}

/** Html BASH code element, that can be inlined. */
class BashInline(val str: String) extends BashHtml, CodeInline
{ override def contents: RArr[XCon] = RArr(str)
}

object BashInline
{ /** Factory apply method for [[BashInline]]. */
  def apply(str: String): BashInline = new BashInline(str)
}

/** The name for the Bash Prompt CSS class in the HTML attribute and for CSS rules. */
val BashPromptClassStr: String = "BashPrompt"

/** Attribute for the bash prompt class. Allows the prompt to be in a different colour to the BASH commands. It may be important to show what directory the
 * command is being launched from. */
object BashPromptAtt extends ClassAtt(BashPromptClassStr)

/** A span set to cover a Bash prompt. This allows the prompt to be in a different colour to the BASH commands. */
class BashPromptSpan(val contents: RArr[XConInedit], otherAttribs: RArr[HAtt]) extends SpanInlineInedit
{ override def attribs: RArr[HAtt] = BashPromptAtt %: otherAttribs
}

object BashPromptSpan extends HtmlIneditCompanion[BashPromptSpan]
{ /** Factory apply method for creating a Bash Prompt as an HTML Span element. */
  override def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): BashPromptSpan = new BashPromptSpan(contents, attribs)
}

/** CSS rule for Bash prompt. */
class BashPromptCssRule(val decsArr: RArr[CssDecBase]) extends CssClassRule
{ override def classStr: String = BashPromptClassStr
}

object BashPromptCssRule
{ /** Factory apply method to construct a CSS class rule for Bash prompts. There is an apply name overload that takes the declarations as repeat parameters. */
  def apply(decsArr: RArr[CssDecBase]): BashPromptCssRule = new BashPromptCssRule(decsArr)

  /** Factory apply method to construct a CSS class rule for Bash prompts. There is an apply name overload that takes the declarations as an [[RArr]]. */
  def apply(decs: CssDecBase*): BashPromptCssRule = new BashPromptCssRule(decs.toRArr)
}

/** An HTML element to display a BASH prompt and command on its own line.  */
class BashWithPrompt(val prompt: String, command: String) extends BashOwnLine
{ def promptSpan: SpanInlineInedit = SpanInlineInedit(RArr(prompt), RArr(BashPromptAtt))
  override def contents: RArr[XConInedit] = RArr(promptSpan, command)
}

/** Not sure about this class. */
class BashWithPromptMulti(val texts: StrArr, otherAttribs: RArr[HAtt]) extends BashHtml, HtmlTagLines
{ override def contents: RArr[XCon] = iUntilMap(texts.length / 2){i => SpanLine(BashPromptSpan(texts(i * 2)), texts(i * 2 + 1)) }
  override def attribs: RArr[HAtt] = super.attribs ++ otherAttribs
}

object BashWithPromptMulti
{ /** Not sure about this factory apply method. */
  def apply(strs: String*): BashWithPromptMulti = new BashWithPromptMulti(strs.toArr, RArr())
}

case class BashCssClassRule(decsArr: RArr[CssDecBase]) extends CssClassRule
{ override def classStr: String = "bash"
}