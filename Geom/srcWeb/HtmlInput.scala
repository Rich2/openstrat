/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

case class ForAtt(valueStr: String) extends XAttSimple
{ override def name: String = "for"
}

class HtmlLabel(val fieldName: String, val label: String) extends HtmlInline
{ override def tag: String = "label"
  override def attribs: RArr[XAtt] = RArr(ForAtt(fieldName))
  override def contents: RArr[XCon] = RArr(label)
}

case class TextInput(idStr: String, valueStr: String)(using page: HtmlPageInput) extends HtmlInput
{ def idAtt: IdAtt = IdAtt(idStr)
  override def typeAtt: TypeTextAtt.type = TypeTextAtt
  def valueAtt = ValueAtt(valueStr)
  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt)
  page.inpAcc +%= this

  var depends: RArr[String => String] = RArr()
  def dependsLen: Int = depends.length
  def nextId(f: String => String): IdAtt =
  { val newStr: String = idStr + dependsLen.str
    depends +%= f
    IdAtt(newStr)
  }
}

class LabelTextInput(val idStr: String, val label: String, val valueStr: String)(using page: HtmlPageInput) extends SpanLine, Parent2T[HtmlInline]
{ override def child1: HtmlLabel = HtmlLabel(idStr, label)
  override def child2: TextInput = TextInput(idStr, valueStr)
  override def contents: RArr[XCon] = RArr(child1, child2)
}

object LabelTextInput
{
  def apply(idStr: String, label: String, valueStr: String)(using page: HtmlPageInput): LabelTextInput = new LabelTextInput(idStr, label, valueStr)
}

/** Html Input element with submit type */
case class SubmitInput(valueStr: String) extends HtmlInput
{ override def typeAtt: TypeAtt = TypeSubmitAtt
  override def attribs: RArr[XAtt] = RArr(typeAtt, ValueAtt(valueStr))
}

case class LabelInputsLine(arrayUnsafe: Array[LabelTextInput]) extends SpanLine
{
  def mems: RArr[LabelTextInput] = new RArr(arrayUnsafe)
  override def contents: RArr[XCon] = mems.childArr
}

object LabelInputsLine
{
  def apply(mems: LabelTextInput*)(using ct: ClassTag[HtmlInline]): LabelInputsLine = new LabelInputsLine(mems.toArray)
}