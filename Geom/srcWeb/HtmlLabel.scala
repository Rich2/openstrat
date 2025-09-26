/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

case class ForAtt(valueStr: String) extends XAttSimple
{ override def name: String = "for"
}

class HtmlLabel(val fieldName: String, val label: String) extends HtmlInline
{ override def tag: String = "label"
  override def attribs: RArr[XAtt] = RArr(ForAtt(fieldName))
  override def contents: RArr[XCon] = RArr(label)
}

class LabelTextInput(val idStr: String, val label: String, val valueStr: String)(using page: HtmlPageInput) extends SpanLine, Parent2T[HtmlInline]
{ override def child1: HtmlLabel = HtmlLabel(idStr, label)
  override def child2: InputUpdaterText = InputUpdaterText(idStr, valueStr)
  override def contents: RArr[XCon] = RArr(child1, child2)
}

object LabelTextInput
{
  def apply(idStr: String, label: String, valueStr: String)(using page: HtmlPageInput): LabelTextInput = new LabelTextInput(idStr, label, valueStr)
}