/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package wcode

/** Html Scala code element. */
trait HtmlScala extends HtmlCode

object HtmlScala
{ /** Factory apply method for [[HtmlScalaInline]]. */
  def apply(str: String): HtmlScalaInline = new HtmlScalaInline(str)
}

/** Html Element for multiple lines of Scala code. */
class HtmlScalaLines(val lines: StrArr) extends HtmlScala, HtmlCodeLines
{ def classAtt: ClassAtt = ClassAtt("scalalines")
  override def attribs: RArr[XAtt] = RArr(classAtt)
  override def contents: RArr[XCon] = lines.toDivLines
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