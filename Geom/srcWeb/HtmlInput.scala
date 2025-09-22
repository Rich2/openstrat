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

case class TextInput(idStr: String, valueStr: String) extends HtmlInput
{ def idAtt: IdAtt = IdAtt(idStr)
  override def typeAtt: TypeTextAtt.type = TypeTextAtt
  def valueAtt = ValueAtt(valueStr)
  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt)
}

case class LabelTextInput(idStr: String, label: String, valueStr: String) extends SpanLine
{
  def labelElem: HtmlLabel = HtmlLabel(idStr, label)

  def textInput: TextInput = TextInput(idStr, valueStr)

  /** The content of this XML / HTML element. */
  override def contents: RArr[XCon] = RArr(labelElem, textInput)
}

/** Html Input element with submit type */
case class SubmitInput(valueStr: String) extends HtmlInput
{ override def typeAtt: TypeAtt = TypeSubmitAtt
  override def attribs: RArr[XAtt] = RArr(typeAtt, ValueAtt(valueStr))
}