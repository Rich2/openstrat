/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
trait HtmlCode extends HtmlUnvoid
{ override def tag: String = "code"
}

/** A multi line, HTML, code element */
trait HtmlCodeMulti extends HtmlCode, HtmlMultiLine
{ /** the lines of code unindented. */
  def lines: StrArr

  override def contents: RArr[XCon] = lines match
  { case _ if lines.length == 0 => RArr()
    case ls => lines.initLastMap(_ + "<br>")(s => s).map(_.xCon)
  }
}

/** An HTML code element that can be inlined. */
trait HtmlCodeInline extends HtmlCode, HtmlInline

/** Html Scala code element. */
trait HtmlScala extends HtmlCode
{ def classAtt: ClassAtt = ClassAtt("scala")
  override def attribs: RArr[XmlAtt] = RArr(classAtt)
}

object HtmlScala
{
  def apply(str: String): HtmlScala = new HtmlScala with HtmlMultiLine
  { override def contents: RArr[XCon] = RArr(str.xCon)
  }
}

/** Html Sbt code element. */
trait HtmlSbt extends HtmlCode
{
  def classAtt: ClassAtt = ClassAtt("sbt")

  override def attribs: RArr[XmlAtt] = RArr(classAtt)
}

/** Html Sbt code element, that can be inlined. */
trait HtmlSbtInline extends HtmlSbt with HtmlCodeInline

object HtmlSbtInline
{
  def apply(str: String): HtmlSbtInline = new HtmlSbtInline
  { override def contents: RArr[XCon] = RArr(str.xCon)
  }
}

/** Html Bash code element. */
trait HtmlBash extends HtmlCode
{ def classAtt: ClassAtt = ClassAtt("bash")

  override def attribs: RArr[XmlAtt] = RArr(classAtt)
}

/** A multi line, Html, Bash code element. */
class HtmlBashMulti(val lines: StrArr, otherAttribs: RArr[XmlAtt]) extends HtmlBash, HtmlCodeMulti
{ override def attribs: RArr[XmlAtt] = super.attribs ++ otherAttribs
}

object HtmlBashMulti
{
  def apply(lines: String*): HtmlBashMulti = new HtmlBashMulti(lines.toArr, RArr())
}

/** Html Bash code element, that can be inlined. */
trait HtmlBashInline extends HtmlBash with HtmlCodeInline

object HtmlBashInline
{
  def apply(str: String): HtmlBashInline = new HtmlBashInline
  { override def contents: RArr[XCon] = RArr(str.xCon)
  }
}

class HtmlBashPrompt(val prompt: String, command: String) extends HtmlBashInline
{
  def promptAtt: ClassAtt = ClassAtt("bashprompt")
  def promptSpan = HtmlSpan(prompt, promptAtt)
  override def contents: RArr[XCon] = RArr(promptSpan, command.xCon)

  //override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String =
}