/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
trait HtmlCode extends HtmlUnvoid
{ override def tag: String = "code"
}

trait HtmlCodeLines extends HtmlCode, HtmlMultiLine
{ /** The lines of code. */
  def lines: StrArr

  override def contents: RArr[XCon] = lines.map(HtmlDiv(_))
}

/** A multi line, HTML, code element */
trait HtmlCodeMulti extends HtmlCode, HtmlMultiLine
{ /** the lines of code unindented. */
  def lines: StrArr

  override def contents: RArr[XCon] = lines match
  { case _ if lines.length == 0 => RArr()
    case ls => lines.initLastMap(_ + "<br>")(s => s)
  }
}

/** An HTML code element that can be inlined. */
trait HtmlCodeOwnLine extends HtmlCode, HtmlOwnLine

object HtmlCodeOwnLine
{ /** Factory apply method to create an inline HTML cose element. */
  def apply(str: String): HtmlCodeOwnLine = new HtmlCodeOwnLine
  { override def contents: RArr[XCon] = RArr(str)
    override def attribs: RArr[XAtt] = RArr()
  }
}

/** An HTML code element that can be inlined. */
trait HtmlCodeInline extends HtmlCode, HtmlInline

/** Html Scala code element. */
trait HtmlScala extends HtmlCode
{ def classAtt: ClassAtt = ClassAtt("scala")
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

object HtmlScala
{ /** Factory apply method for HTML element for multiple lines of Scala code. */
  def apply(line1: String, line2: String, otherLines: String*): HtmlScala = new HtmlScalaLines(line1 %: line2 %: otherLines.toArr)
  
}

/** Html Element for multiple lines of Scala code. */
class HtmlScalaLines(val lines: StrArr) extends HtmlScala, HtmlCodeLines

object HtmlScalaLines
{ /** Factory apply method for HTML element for multiple lines of Scala code. */
  def apply(lines: String*): HtmlScalaLines = new HtmlScalaLines(lines.toArr)
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
class HtmlDirPath(val str: String) extends HtmlCodeOwnLine
{ def classAtt: ClassAtt = ClassAtt("path")
  override def contents: RArr[XCon] = RArr(str)
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

/** Html Bash code element. */
trait HtmlBash extends HtmlCode
{ def classAtt: ClassAtt = ClassAtt("bash")
  override def attribs: RArr[XAtt] = RArr(classAtt)
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

/** Html Bash code element, that can be inlined. */
trait HtmlBashOwnLine extends HtmlBash, HtmlCodeOwnLine

object HtmlBashOwnLine
{
  def apply(str: String): HtmlBashOwnLine = new HtmlBashOwnLineGen(RArr(str))

  class HtmlBashOwnLineGen(val contents: RArr[XCon]) extends HtmlBashOwnLine
}

object BashPromptClass extends ClassAtt("bashprompt")
class BashPromptSpan(str: String) extends HtmlSpan(RArr(str), RArr(BashPromptClass))

class HtmlBashPrompt(val prompt: String, command: String) extends HtmlBashOwnLine
{ def promptSpan = HtmlSpan(prompt, BashPromptClass)
  override def contents: RArr[XCon] = RArr(promptSpan, command)
}

class HtmlBashPromptMulti(val texts: StrArr, otherAttribs: RArr[XAtt]) extends HtmlBash, HtmlMultiLine
{ override def contents: RArr[XCon] = iUntilFlatMap(texts.length / 2){i => RArr(BashPromptSpan(texts(i * 2)), texts(i * 2 + 1), HtmlBr) }
  override def attribs: RArr[XAtt] = super.attribs ++ otherAttribs
}

object HtmlBashPromptMulti
{
  def apply(strs: String*): HtmlBashPromptMulti = new HtmlBashPromptMulti(strs.toArr, RArr())
}