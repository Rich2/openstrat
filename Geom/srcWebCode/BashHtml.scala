/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

/** The bash class attribute. */
object BashAtt extends ClassAtt("bash")

/** Html Bash code element. */
trait BashHtml extends CodeHtml
{ override def attribs: RArr[XAtt] = RArr(BashAtt)
}

/** A multi line, Html, Bash code element. */
class BashHtmlMulti(val lines: StrArr, otherAttribs: RArr[XAtt]) extends BashHtml, CodeLinesHtml
{ override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
  override def contents: RArr[XCon] = lines.toDivLines
}

object BashHtmlMulti
{
  def apply(lines: String*): BashHtmlMulti = new BashHtmlMulti(lines.toArr, RArr())
}

/** Html Bash code element, that is on its own line. For the general case use the [[BashLine]] class. */
trait BashOwnLine extends BashHtml, CodeLineHtml
{ override def attribs: RArr[XAtt] = super.attribs +% BashAtt
}

/** An HTML Bash code element that will display on its own line. */
class BashLine(val contents: RArr[XConInedit], val otherAttribs: RArr[XAtt]) extends BashOwnLine
{ override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
}

object BashLine extends HtmlElemCompanion[BashLine]
{ /** Factory apply method to write Bash code in HTML on its own line. */
  def apply(contents: XConInedit*): BashLine = new BashLine(contents.toArr, RArr())

  /** Factory apply method to write Bash code in HTML on its own line. There is an apply name overload that takes the contents as repeat parameters, but with no
   * attributes. */
  def apply(contents: RArr[XConInedit], attribs: RArr[XAtt]): BashLine = new BashLine(contents, attribs)

  override def fromStr(str: String, attribs: RArr[XAtt]): BashLine = new BashLine(RArr(str), attribs)

  /** Factory method to write Bash code in HTML on its own line with a class attribute. */
  def classAtt(classStr: String, conStr: String, otherAttribs: XAtt*): BashLine = new BashLine(RArr(conStr), ClassAtt(classStr) %: otherAttribs.toArr)

  /** Creates a Bash line and registers the textContent with a (String, String, String) => String callback to this [[BashLine]]'s textContent. */
  def listen3Str(input1: UpdaterStr, input2: UpdaterStr, input3: UpdaterStr)(f: (String, String, String) => String): BashLine =
  { val newId: IdAtt = input1.next3Id1(input2, input3, f)
    new BashLine(RArr(f(input1.valueStr, input2.valueStr, input3.valueStr)), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML Text Input and an HTML number input. */
  def listenStrDbl(input1: UpdaterStr, input2: UpdaterDblInput)(f: (String, Double) => String): BashLine =
  { val newId: IdAtt = input1.nextStrDblId1(input2, f)
    new BashLine(RArr(f(input1.valueStr, input2.value)), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML number Input. */
  def listenDbl(input: UpdaterDblInput)(f: Double => String): BashLine =
  { val newId: IdAtt = input.next1(f)
    new BashLine(RArr(f(input.value)), RArr(newId))
  }

  /** Creates a Bash line and registers the htmlContent with an HTML Select Input. */
  def listenOptHtml(input1: UpdaterOption)(f: OptionHtml => RArr[XConInedit]): BashLine =
  { val newId: IdAtt = input1.nextOptHtml(f)
    new BashLine(f(input1.initOption), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML Select Input. */
  def listenOptText(input1: UpdaterOption)(f: OptionHtml => String): BashLine =
  { val newId: IdAtt = input1.nextOptText(f)
    new BashLine(RArr(f(input1.initOption)), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptIntText(input1: UpdaterOption, input2: UpdaterIntInput)(f: (OptionHtml, Int) => String): BashLine =
  { val newId: IdAtt = input1.nextOptIntText1(input2, f)
    new BashLine(RArr(f(input1.initOption, input2.value)), RArr(newId))
  }
  
  /** Creates a Bash line and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptDblHtml(input1: UpdaterOption, input2: UpdaterDblInput)(f: (OptionHtml, Double) => RArr[XConInedit]): BashLine =
  { val newId: IdAtt = input1.nextOptDbl1Html(input2, f)
    new BashLine(f(input1.initOption, input2.value), RArr(newId))
  }

  /** Creates a Bash line and registers the textContent with an HTML Select Input and an HTML number input. */
  def listenOptDblText(input1: UpdaterOption, input2: UpdaterDblInput)(f: (OptionHtml, Double) => String): BashLine =
  { val newId: IdAtt = input1.nextOptDblText1(input2, f)
    new BashLine(RArr(f(input1.initOption, input2.value)), RArr(newId))
  }
}

/** Html BASH code element, that can be inlined. */
class BashInline(val str: String) extends BashHtml, CodeInline
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
class BashPromptSpan(val str: String, otherAttribs: RArr[XAtt]) extends SpanInlineInedit
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

  /** Creates a span set to cover a Bash prompt. This allows the prompt to be in a different colour to the BASH commands. registers the textContent with an HTML
   * Text Input. */
  def listenText(input: UpdaterStr, otherAttribs: XAtt*)(f: String => String): BashPromptSpan =
  { val newId: IdAtt = input.next1Text(f)
    new BashPromptSpan(f(input.valueStr), newId %: otherAttribs.toArr)
  }

  /** Creates span set to cover a Bash prompt. This allows the prompt to be in a different colour to the BASH commands. Registers the textContent with 2 HTML
   * Text Inputs. */
  def listen2Text(input1: UpdaterStr, input2: UpdaterStr, otherAttribs: XAtt*)(f: (String, String) => String): BashPromptSpan =
  { val newId: IdAtt = input1.next2Text1(input2, f)
    new BashPromptSpan(f(input1.valueStr, input2.valueStr), newId %: otherAttribs.toRArr)
  }

  /** Creates a span set to cover a Bash prompt. This allows the prompt to be in a different colour to the BASH commands. Registers the textContent with 3 HTML
   * Text Inputs. */
  def listen3Text(input1: UpdaterStr, input2: UpdaterStr, input3: UpdaterStr, otherAttribs: XAtt*)(f: (String, String, String) => String): BashPromptSpan =
  { val newId: IdAtt = input1.next3Id1(input2, input3, f)
    new BashPromptSpan(f(input1.valueStr, input2.valueStr, input3.valueStr), newId %: otherAttribs.toRArr)
  }

  /** Creates a span set to cover a Bash prompt. This allows the prompt to be in a different colour to the BASH commands. Rregisters the textContent with an
   * HTML Text and a number of Inputs. */
  def listenTextNum(input1: UpdaterStr, input2: UpdaterDblInput, otherAttribs: XAtt*)(f: (String, Double) => String): BashPromptSpan =
  { val newId: IdAtt = input1.nextStrDblId1(input2, f)
      new BashPromptSpan(f(input1.valueStr, input2.value), newId %: otherAttribs.toRArr)
    }

    /** Creates a span set to cover a Bash prompt. This allows the prompt to be in a different colour to the BASH commands. Registers the textContent with an
     * HTML number Input. */
    def listenNum(input: UpdaterDblInput, otherAttribs: XAtt*)(f: Double => String): BashPromptSpan =
    { def newId: IdAtt = input.next1(f)
      new BashPromptSpan(f(input.value), newId %: otherAttribs.toRArr)
    }
}

/** An HTML element to display a BASH prompt and command on its own line.  */
class BashWithPrompt(val prompt: String, command: String) extends BashOwnLine
{ def promptSpan: SpanInlineInedit = SpanInlineInedit(prompt, BashPromptClass)
  override def contents: RArr[XConInedit] = RArr(promptSpan, command)
}

class BashWithPromptMulti(val texts: StrArr, otherAttribs: RArr[XAtt]) extends BashHtml, HtmlTagLines
{ override def contents: RArr[XCon] = iUntilMap(texts.length / 2){i => SpanLine(BashPromptSpan(texts(i * 2)), texts(i * 2 + 1)) }
  override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
}

object BashWithPromptMulti
{
  def apply(strs: String*): BashWithPromptMulti = new BashWithPromptMulti(strs.toArr, RArr())
}