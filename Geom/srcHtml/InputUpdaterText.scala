/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** Class to update a page from a text input. */
class InputUpdaterText(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt])(using val page: PageHtmlUpdater) extends UpdaterText, InputHtml
{ override def typeAtt: TypeTextAtt.type = TypeTextAtt
}

object InputUpdaterText
{ /** Factory apply method for object to update a page from a text input. */
  def apply(idStr: String, valueStr: String, otherAttribs: XAtt*)(using page: PageHtmlUpdater): InputUpdaterText =
    new InputUpdaterText(idStr, valueStr, otherAttribs.toRArr)
}

sealed trait CallbackText extends CallbackInput

case class CallBack1StrHtml(targetId: String, f: String => RArr[XCon]) extends CallbackText
case class Callback1Text(targetId: String, f: String => String) extends CallbackText

sealed trait Callback2Text extends CallbackText
case class Callback2Text1(targetId: String, input2IdStr: String, f: (String, String) => String) extends Callback2Text
case class Callback2Text2(targetId: String, input1IdStr: String, f: (String, String) => String) extends Callback2Text

case class CallbackTextNum1(targetId: String, numInpIdStr: String, f: (String, Double) => String) extends CallbackText
case class CallbackTextNum2(targetId: String, textInpIdStr: String, f: (String, Double) => String) extends CallbackInput

sealed trait Callback3Text extends CallbackText
case class Callback3Text1(targetId: String, input2IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text2(targetId: String, input1IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text3(targetId: String, input1IdStr: String, input2IdStr: String, f: (String, String, String) => String) extends Callback3Text