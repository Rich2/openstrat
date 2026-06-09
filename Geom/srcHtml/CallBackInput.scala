/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** A text callback from an input to a textContent. */
trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def targetId: String
}

sealed trait CallbackText extends CallbackInput

case class CallBack1StrHtml(targetId: String, f: String => RArr[XCon]) extends CallbackText
case class Callback1Text(targetId: String, f: String => String) extends CallbackText

sealed trait Callback2Text extends CallbackText
case class Callback2Text1(targetId: String, input2IdStr: String, f: (String, String) => String) extends Callback2Text
case class Callback2Text2(targetId: String, input1IdStr: String, f: (String, String) => String) extends Callback2Text

sealed trait CallbackNum extends CallbackInput

case class CallbackTextNum1(targetId: String, numInpIdStr: String, f: (String, Double) => String) extends CallbackText
case class CallbackTextNum2(targetId: String, textInpIdStr: String, f: (String, Double) => String) extends CallbackNum

sealed trait Callback3Text extends CallbackText
case class Callback3Text1(targetId: String, input2IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text2(targetId: String, input1IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text3(targetId: String, input1IdStr: String, input2IdStr: String, f: (String, String, String) => String) extends Callback3Text

/** A call back for an [[UpdaterNumInput]] that takes a simple Double => String function. */
case class Callback1Num(targetId: String, f: Double => String) extends CallbackNum

case class CallbackAny(targetId: String, f: Any => RArr[XCon]) extends CallbackInput