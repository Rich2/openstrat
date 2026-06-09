/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** A text callback from an input to a textContent. */
trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def listenerId: String
}

sealed trait CallbackText extends CallbackInput

case class CallBack1StrHtml(listenerId: String, f: String => RArr[XCon]) extends CallbackText
case class Callback1Text(listenerId: String, f: String => String) extends CallbackText

sealed trait Callback2Text extends CallbackText
case class Callback2Text1(listenerId: String, input2IdStr: String, f: (String, String) => String) extends Callback2Text
case class Callback2Text2(listenerId: String, input1IdStr: String, f: (String, String) => String) extends Callback2Text

sealed trait CallbackNum extends CallbackInput

case class CallbackTextNum1(listenerId: String, numInpIdStr: String, f: (String, Double) => String) extends CallbackText
case class CallbackTextNum2(listenerId: String, textInpIdStr: String, f: (String, Double) => String) extends CallbackNum

sealed trait Callback3Text extends CallbackText
case class Callback3Text1(listenerId: String, input2IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text2(listenerId: String, input1IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Text
case class Callback3Text3(listenerId: String, input1IdStr: String, input2IdStr: String, f: (String, String, String) => String) extends Callback3Text

/** A call back for an [[UpdaterNumInput]] that takes a simple Double => String function. */
case class Callback1Num(listenerId: String, f: Double => String) extends CallbackNum

trait CallbackOption extends CallbackInput

/** A call back for an [[UpdaterOption]] that takes an option => String function, whee the option is typed as an Any. */
case class Callback1Option(listenerId: String, f: Any => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterOption]] that takes an option => String function, whee the option is typed as an Any. */
case class CallbackOptionNum1(listenerId: String, input2IdStr: String, f: (Any, Double) => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterOption]] that takes an option => String function, whee the option is typed as an Any. */
case class CallbackOptionNum2(listenerId: String, input1: UpdaterOption, f: (Any, Double) => RArr[XCon]) extends CallbackNum