/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML code element. */
trait HtmlCode/*(contentStr: String, attribs: RArr[XmlAtt] = RArr())*/ extends HtmlUnvoid
{ override def tag: String = "code"
}

object HtmlCode
{
  def multiLine(str: String): HtmlCode = new HtmlCode with HtmlMultiLine
  { override def attribs: RArr[XmlAtt] = RArr()
    override def contents: RArr[XCon] = RArr(str.xCon)
  }
}

trait HtmlCodeInline extends HtmlCode with HtmlInline


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

trait HtmlBashInline extends HtmlBash with HtmlCodeInline

object HtmlBashInline
{
  def apply(str: String): HtmlBashInline = new HtmlBashInline
  { override def contents: RArr[XCon] = RArr(str.xCon)
  }
}