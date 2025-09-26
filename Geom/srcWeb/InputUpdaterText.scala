/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

class InputUpdaterText(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt])(using page: HtmlPageInput) extends InputUpdater[String]
{ override def typeAtt: TypeTextAtt.type = TypeTextAtt
  def valueAtt = ValueAtt(valueStr)

  page.inpTextAcc +%= this
  var depends: RArr[CallbackText] = RArr()

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

sealed trait CallbackText extends CallbackInput
case class Callback1Text(targetId: String, f: String => String) extends CallbackText

sealed trait Callback2Text extends CallbackText
{
  def otherInpIdStr: String
}
case class Callback2Text1(targetId: String, otherInpIdStr: String, f: (String, String) => String) extends Callback2Text
case class Callback2Text2(targetId: String, otherInpIdStr: String, f: (String, String) => String) extends Callback2Text