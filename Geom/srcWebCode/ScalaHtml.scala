/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package wcode

/** Html Scala code element. */
trait ScalaHtml extends CodeHtml

object ScalaHtml
{ /** Factory apply method for [[ScalaInline]]. */
  def apply(str: String): ScalaInline = new ScalaInline(str)
}

/** Html Element for multiple lines of Scala code. */
class ScalaLinesHtml(val lines: StrArr) extends ScalaHtml, CodeLinesHtml
{ def classAtt: ClassAtt = ClassAtt("scalalines")
  override def attribs: RArr[XAtt] = RArr(classAtt)
  override def contents: RArr[XCon] = lines.toDivLines
}

object ScalaLinesHtml
{ /** Factory apply method for HTML element for multiple lines of Scala code. */
  def apply(lines: String*): ScalaLinesHtml = new ScalaLinesHtml(lines.toArr)
}

/** Html Scala code element, that can be inlined. */
class ScalaInline(val str: String) extends ScalaHtml, CodeHtmlInline
{ override def contents: RArr[XCon] = RArr(str)
  def classAtt: ClassAtt = ClassAtt("scala")
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

object ScalaInline
{ /** Factory apply method for [[ScalaInline]]. */
  def apply(str: String): ScalaInline = new ScalaInline(str)
}

/** Html Sbt code element. */
trait SbtHtml extends CodeHtml
{ def classAtt: ClassAtt = ClassAtt("sbt")
  override def attribs: RArr[XAtt] = RArr(classAtt)
}

/** Html Sbt code element, that can be inlined. */
class SbtInline(val str: String) extends SbtHtml, CodeHtmlInline
{ override def contents: RArr[XCon] = RArr(str)
}

object SbtInline
{ /** Factory apply method for [[SbtInline]]. */
  def apply(str: String): SbtInline = new SbtInline(str)
}