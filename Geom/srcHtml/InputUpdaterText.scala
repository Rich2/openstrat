/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** Class to update a page from a text input. */
class InputUpdaterText(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt])(using page: HtmlPageInput) extends InputUpdater
{ override def typeAtt: TypeTextAtt.type = TypeTextAtt
  def valueAtt = ValueAtt(valueStr)

  page.inpAcc +%= this
  var depends: RArr[CallbackText] = RArr()

  def dependsLen: Int = depends.length

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target content. */
  def next1Id(f: String => String): IdAtt =
  { val newtargetId: String = idStr + parentCount.str
    parentCount += 1
    depends +%= Callback1Text(newtargetId, f)
    IdAtt(newtargetId)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def next2Id1(otherInpIdStr: String, f: (String, String) => String): IdAtt =
  { val newTargetId: String = idStr + parentCount.str
    parentCount += 1
    depends +%= Callback2Text1(newTargetId, otherInpIdStr, f)
    IdAtt(newTargetId)
  }

  def next2Id2(targetID: String, otherInpIdStr: String, f: (String, String) => String): Unit =
  { depends +%= Callback2Text2(targetID, otherInpIdStr, f)
  }

  def next3Id1(otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String): IdAtt =
  { val newTargetId: String = idStr + parentCount.str
    parentCount += 1
      depends +%= Callback3Text1(newTargetId, otherInpIdStr1, otherInpIdStr2, f)
      IdAtt(newTargetId)
    }

  def next3Id2(targetID: String, otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String): Unit =
  { depends +%= Callback3Text2(targetID, otherInpIdStr1, otherInpIdStr2, f)
  }

  def next3Id3(targetID: String, otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String): Unit =
  { depends +%= Callback3Text3(targetID, otherInpIdStr1, otherInpIdStr2, f)
  }
}

object InputUpdaterText
{
  def apply(idStr: String, valueStr: String, otherAttribs: XAtt*)(using page: HtmlPageInput): InputUpdaterText = new InputUpdaterText(idStr, valueStr, otherAttribs.toRArr)
}

sealed trait CallbackText extends CallbackInput
case class Callback1Text(targetId: String, f: String => String) extends CallbackText

sealed trait Callback2Text extends CallbackText
{ def otherInpIdStr: String
}
case class Callback2Text1(targetId: String, otherInpIdStr: String, f: (String, String) => String) extends Callback2Text
case class Callback2Text2(targetId: String, otherInpIdStr: String, f: (String, String) => String) extends Callback2Text

sealed trait Callback3Text extends CallbackText
{ def otherInpIdStr1: String
  def otherInpIdStr2: String
}
case class Callback3Text1(targetId: String, otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text2(targetId: String, otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text3(targetId: String, otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String) extends Callback3Text