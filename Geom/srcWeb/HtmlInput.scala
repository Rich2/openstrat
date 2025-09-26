/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** An HTML input element. */
trait HtmlInput extends HtmlVoid
{ def typeAtt: TypeAtt
  override def tag: String = "input"
}

trait InputUpdater extends HtmlInput
{ /** The [[String]] of the id attribute. */
  def idStr: String

  def idAtt: IdAtt = IdAtt(idStr)

  def valueStr: String
}

sealed trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def targetId: String
}

case class Callback1Text(targetId: String, f: String => String) extends CallbackInput

sealed trait Callback2Text extends CallbackInput
{
  def otherInpIdStr: String
}
case class Callback2Text1(targetId: String, otherInpIdStr: String, f: (String, String) => String) extends Callback2Text
case class Callback2Text2(targetId: String, otherInpIdStr: String, f: (String, String) => String) extends Callback2Text

case class InputUpdaterNum(idStr: String, value: Int)(using page: HtmlPageInput) extends InputUpdater
{
  override def typeAtt: TypeAtt = TypeNumberAtt

  override def valueStr: String = value.str

  /** The attributes of this XML / HTML element. */
  override def attribs: RArr[XAtt] = ???
}

class InputUpdaterText(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt])(using page: HtmlPageInput) extends InputUpdater
{ override def typeAtt: TypeTextAtt.type = TypeTextAtt
  def valueAtt = ValueAtt(valueStr)
  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt) ++ otherAttribs
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

  def next2Id1(otherInpIdStr: String, f: (String, String) => String): IdAtt =
  { val newTargetId: String = idStr + parentCount.str
    parentCount += 1
    depends +%= Callback2Text1(newTargetId, otherInpIdStr, f)
    IdAtt(newTargetId)
  }

  def next2Id2(targetID: String, otherInpIdStr: String, f: (String, String) => String): Unit =
  { depends +%= Callback2Text2(targetID, otherInpIdStr, f)
  }
}

object InputUpdaterText
{
  def apply(idStr: String, valueStr: String, otherAttribs: XAtt*)(using page: HtmlPageInput): InputUpdaterText = new InputUpdaterText(idStr, valueStr, otherAttribs.toRArr)
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