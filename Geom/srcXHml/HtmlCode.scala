/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
trait HtmlCode extends HtmlUnvoid
{ override def tag: String = "code"
}

trait HtmlCodeLines extends HtmlCode, HtmlTagLines
{ /** The lines of code. */
  def lines: StrArr

  override def contents: RArr[XCon] = lines.map(HtmlDiv(_))
}

/** A multi line, HTML, code element */
trait HtmlCodeMulti extends HtmlCode, HtmlTagLines
{ /** the lines of code unindented. */
  def lines: StrArr

  override def contents: RArr[XCon] = lines.map(l => SpanLine(l))
}

/** An HTML code element that can be inlined. */
trait HtmlCodeOwnLine extends HtmlCode, HtmlOwnLineBlocked

object HtmlCodeOwnLine
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): HtmlCodeOwnLine = new HtmlCodeOwnLine
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

/** Html Scala code element. */
trait HtmlScala extends HtmlCode

object HtmlScala
{ /** Factory apply method for HTML element for multiple lines of Scala code. */
  //def apply(line1: String, line2: String, otherLines: String*): HtmlScala = new HtmlScalaLines(line1 %: line2 %: otherLines.toArr)

  /** Factory apply method for [[HtmlScalaInline]]. */
  def apply(str: String): HtmlScalaInline = new HtmlScalaInline(str)
}

/** Html Element for multiple lines of Scala code. */
class HtmlScalaLines(val lines: StrArr) extends HtmlScala, HtmlCodeLines
{ def classAtt: ClassAtt = ClassAtt("scalalines")
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

object HtmlScalaLines
{ /** Factory apply method for HTML element for multiple lines of Scala code. */
  def apply(lines: String*): HtmlScalaLines = new HtmlScalaLines(lines.toArr)
}

/** Html Scala code element, that can be inlined. */
class HtmlScalaInline(val str: String) extends HtmlScala, HtmlCodeInline
{ override def contents: RArr[XCon] = RArr(str)
  def classAtt: ClassAtt = ClassAtt("scala")
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

object HtmlScalaInline
{ /** Factory apply method for [[HtmlScalaInline]]. */
  def apply(str: String): HtmlScalaInline = new HtmlScalaInline(str)
}

/** Html Sbt code element. */
trait HtmlSbt extends HtmlCode
{ def classAtt: ClassAtt = ClassAtt("sbt")
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

/** Html Sbt code element, that can be inlined. */
class HtmlSbtInline(val str: String) extends HtmlSbt, HtmlCodeInline
{ override def contents: RArr[XCon] = RArr(str)
}

object HtmlSbtInline
{ /** Factory apply method for [[HtmlSbtInline]]. */
  def apply(str: String): HtmlSbtInline = new HtmlSbtInline(str)
}

/** Html directory path code element. */
class HtmlDirPath(val str: String) extends HtmlCodeInline
{ def classAtt: ClassAtt = ClassAtt("path")
  override def contents: RArr[XCon] = RArr(str)
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

object BashAtt extends ClassAtt("bash")

/** Html Bash code element. */
trait HtmlBash extends HtmlCode
{ override def attribs: RArr[XAtt] = RArr(BashAtt)
}

class HtmlBashLine(str: String) extends HtmlBash
{ override def contents: RArr[XCon] = RArr(HtmlDiv("<code"))
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = ???
}

/** A multi line, Html, Bash code element. */
class HtmlBashMulti(val lines: StrArr, otherAttribs: RArr[XAtt]) extends HtmlBash, HtmlCodeMulti
{ override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
}

object HtmlBashMulti
{
  def apply(lines: String*): HtmlBashMulti = new HtmlBashMulti(lines.toArr, RArr())
}

/** Html Bash code element, that is on ts own line. */
trait BashOwnLine extends HtmlBash, HtmlCodeOwnLine
{ override def attribs: RArr[XAtt] = RArr(styleAtt, BashAtt)
}
  

object BashOwnLine
{
  def apply(str: String): BashOwnLine = new HtmlBashOwnLineGen(RArr(str), RArr())

  class HtmlBashOwnLineGen(val contents: RArr[XConInline], val otherAttribs: RArr[XAtt]) extends BashOwnLine
}

/** Html BASH code element, that can be inlined. */
class BashInline(val str: String) extends HtmlBash, HtmlCodeInline
{ override def contents: RArr[XCon] = RArr(str)
}

object BashInline
{ /** Factory apply method for [[BashInline]]. */
  def apply(str: String): BashInline = new BashInline(str)
}

object BashPromptClass extends ClassAtt("bashprompt")

class BashPromptSpan(str: String) extends SpanInline(RArr(str), RArr(BashPromptClass))

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