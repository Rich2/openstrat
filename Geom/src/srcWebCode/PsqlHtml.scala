/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

/** The psql class attribute. */
object PsqlAtt extends ClassAtt("psql")

/** Html psql code element. */
trait PsqlHtml extends CodeHtml

/** Html psql code element, that is on its own line. For the general case use the [[PsqlLine]] class. */
trait PsqlOwnLine extends PsqlHtml, CodeLineHtml
{ override def attribs: RArr[HAtt] = super.attribs +% PsqlAtt
}

/** An HTML psql code element that will display on its own line. */
class PsqlLine(val contents: RArr[XConInedit], val otherAttribs: RArr[HAtt]) extends PsqlOwnLine
{ override def attribs: RArr[HAtt] = super.attribs ++ otherAttribs
}

object PsqlLine extends HtmlIneditCompanion[PsqlLine]
{ /** Factory apply method to write psql code in HTML on its own line. There is an apply name overload that takes the contents as repeat parameters, but with no
 * attributes. */
  def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): PsqlLine = new PsqlLine(contents, attribs)
}

/** The name for the psql Prompt CSS class in the HTML attribute and for CSS rules. */
val PsqlPromptClassStr: String = "PsqlPrompt"

/** Attribute for the psql prompt class. Allows the prompt to be in a different colour to the psql commands. It may be important to show what user is logged
 * in. */
object PsqlPromptAtt extends ClassAtt(PsqlPromptClassStr)

/** A span set to cover a Psql prompt. This allows the prompt to be in a different colour to the Psql commands. */
class PsqlPromptSpan(val contents: RArr[XConInedit], otherAttribs: RArr[HAtt]) extends SpanInlineInedit
{ override def attribs: RArr[HAtt] = PsqlPromptAtt %: otherAttribs
}

object PsqlPromptSpan extends HtmlIneditCompanion[PsqlPromptSpan]
{ /** Factory apply method for creating a Psql Prompt as an HTML Span element. */
  override def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): PsqlPromptSpan = new PsqlPromptSpan(contents, attribs)
}

/** CSS rule for psql prompt. */
class PsqlPromptCssRule(val decsArr: RArr[CssDecBase]) extends CssClassRule
{ override def classStr: String = PsqlPromptClassStr
}

object PsqlPromptCssRule
{ /** Factory apply method to construct a CSS class rule for psql prompts. There is an apply name overload that takes the declarations as repeat parameters. */
  def apply(decsArr: RArr[CssDecBase]): PsqlPromptCssRule = new PsqlPromptCssRule(decsArr)

  /** Factory apply method to construct a CSS class rule for psql prompts. There is an apply name overload that takes the declarations as an [[RArr]]. */
  def apply(decs: CssDecBase*): PsqlPromptCssRule = new PsqlPromptCssRule(decs.toRArr)
}