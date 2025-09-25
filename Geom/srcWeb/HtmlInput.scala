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

sealed trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def targetId: String
}

case class Callback1Text(targetId: String, f: String => String) extends CallbackInput

sealed trait Callback2Text extends CallbackInput
{
  def inp2Id: String
}
case class Callback2Text1(targetId: String, inp2Id: String,  f: (String, String) => String) extends Callback2Text
case class Callback2Text2(targetId: String, inp2Id: String, f: (String, String) => String) extends Callback2Text

case class TextInput(idStr: String, valueStr: String)(using page: HtmlPageInput) extends HtmlInput
{ def idAtt: IdAtt = IdAtt(idStr)
  override def typeAtt: TypeTextAtt.type = TypeTextAtt
  def valueAtt = ValueAtt(valueStr)
  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt)
  page.inpAcc +%= this

  var depends: RArr[CallbackInput] = RArr()
  var parentCount: Int = 0
  def dependsLen: Int = depends.length

  def next1Id(f: String => String): IdAtt =
  { val newtargetId: String = idStr + parentCount.str
    parentCount += 1
    depends +%= Callback1Text(newtargetId, f)
    IdAtt(newtargetId)
  }

  def next2Id1(inp2Id: String, f: (String, String) => String): IdAtt =
  { val newTargetId: String = idStr + parentCount.str
    parentCount += 1
    depends +%= Callback2Text1(newTargetId, inp2Id, f)
    IdAtt(newTargetId)
  }

  def next2Id2(targetID: String, inp2Id: String, f: (String, String) => String): Unit =
  { depends +%= Callback2Text2(targetID, inp2Id, f)
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