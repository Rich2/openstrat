/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
trait HtmlCode extends HtmlUnvoid
{ override def tag: String = "code"
}

object HtmlCode
{
  def multiLine(str: String): HtmlCode = new HtmlCode with HtmlMultiLine
  { override def attribs: RArr[XmlAtt] = RArr()
    override def contents: RArr[XCon] = RArr(str.xCon)
  }
}

trait HtmlCodeMulti extends HtmlCode, HtmlMultiLine
{
  def lines: StrArr

  override def contents: RArr[XCon] = lines match
  { case RArr0 => RArr()
    case ls => lines.initLastMap(_ + "<Br>")(s => s).map(_.xCon)
  }
}

trait HtmlCodeInline extends HtmlCode, HtmlInline


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

trait HtmlSbt extends HtmlCode
{
  def classAtt: ClassAtt = ClassAtt("sbt")

  override def attribs: RArr[XmlAtt] = RArr(classAtt)
}

trait HtmlSbtInline extends HtmlSbt with HtmlCodeInline

object HtmlSbtInline
{
  def apply(str: String): HtmlSbtInline = new HtmlSbtInline
  { override def contents: RArr[XCon] = RArr(str.xCon)
  }
}

trait HtmlBash extends HtmlCode
{
  def classAtt: ClassAtt = ClassAtt("bash")

  override def attribs: RArr[XmlAtt] = RArr(classAtt)
}

object HtmlBash
{
  def apply(str: String): HtmlBash = new HtmlBash {
    override def contents: RArr[XCon] = RArr(str.xCon)

    /** Returns the XML / HTML source code, formatted according to the input. This allows the XML to be indented according to its context. */
    override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = ???
  }
}

class HtmlBashMulti(val lines: StrArr, otherAttribs: RArr[XmlAtt]) extends HtmlBash, HtmlCodeMulti
{ override def attribs: RArr[XmlAtt] = super.attribs ++ otherAttribs
}

object HtmlBashMulti
{
  def apply(lines: String*): HtmlBashMulti = new HtmlBashMulti(lines.toArr, RArr())
}

trait HtmlBashInline extends HtmlBash with HtmlCodeInline

object HtmlBashInline
{
  def apply(str: String): HtmlBashInline = new HtmlBashInline
  { override def contents: RArr[XCon] = RArr(str.xCon)
  }
}