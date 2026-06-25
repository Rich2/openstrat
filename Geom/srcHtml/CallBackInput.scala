/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** A text callback from an [[UpdaterInputLike]] with a function for JavaScript to update textContent or an htmlContent property. */
trait CallbackUpdater
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def listenerId: String
}

/** Call back for an [[UpdaterStr]] with a function for JavaScript to update textContent or an htmlContent property. */
sealed trait CallbackStr extends CallbackUpdater

/** Call back for an [[UpdaterStr]] with a String => RArr[XCon] function for JavaScript to update an htmlContent property. */
case class CallBack1StrHtml(listenerId: String, f: String => RArr[XCon]) extends CallbackStr

/** Call back for an [[UpdaterStr]] with a String => String function for JavaScript to update a textContent property. */
case class Callback1StrText(listenerId: String, f: String => String) extends CallbackStr

sealed trait Callback2Str extends CallbackStr
case class Callback2Str1(listenerId: String, input2IdStr: String, f: (String, String) => String) extends Callback2Str
case class Callback2Str2(listenerId: String, input1IdStr: String, f: (String, String) => String) extends Callback2Str

/** Call back from an [[UpdaterDblInput]] for a Double => String function for JavaScript to update a textContent property. */
case class CallbackStrDbl1(listenerId: String, numInpIdStr: String, f: (String, Double) => String) extends CallbackStr

/** Call back from an [[UpdaterDblInput]] for a (String, Double) => String function for JavaScript to update a textContent property. */
case class CallbackStrDbl2(listenerId: String, textInpIdStr: String, f: (String, Double) => String) extends CallbackDbl

/** Call back from an [[UpdaterStr]] for a (String, String, String) => String function for JavaScript to update a textContent property. */
sealed trait Callback3Str extends CallbackStr

/** Call back from an [[UpdaterStr]] for the 1st parameter of a (String, String, String) => String function for JavaScript to update a textContent property. */
case class Callback3Str1(listenerId: String, input2IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Str

/** Call back from an [[UpdaterStr]] for the 2nd parameter of a (String, String, String) => String function for JavaScript to update a textContent property. */
case class Callback3Str2(listenerId: String, input1IdStr: String, input3IdStr: String, f: (String, String, String) => String) extends Callback3Str

/** Call back from an [[UpdaterStr]] for the 3rd parameter of a (String, String, String) => String function for JavaScript to update a textContent property. */
case class Callback3Str3(listenerId: String, input1IdStr: String, input2IdStr: String, f: (String, String, String) => String) extends Callback3Str

/** Call back from an [[UpdaterIntInput]]. */
sealed trait CallbackInt extends CallbackUpdater

/** A call back for an [[UpdaterDblInput]] that takes a simple Double => String function for JavaScript to update a textContent property. */
case class Callback1IntText(listenerId: String, f: Double => String) extends CallbackInt

/** Call back from an [[UpdaterDblInput]]. */
sealed trait CallbackDbl extends CallbackUpdater

/** A call back for an [[UpdaterDblInput]] that takes a simple Double => String function for JavaScript to update an htmlContent property. */
case class Callback1DblHtml(listenerId: String, f: Double => RArr[XCon]) extends CallbackDbl

/** A call back for an [[UpdaterDblInput]] that takes a simple Double => String function for JavaScript to update a textContent property. */
case class Callback1DblText(listenerId: String, f: Double => String) extends CallbackDbl

/** A callback for an [[UpdaterOption]]. */
trait CallbackOption extends CallbackUpdater

/** A call back for an [[UpdaterOption]] that takes an Option => RArr[XCon] function,  to use JavaScript to pdate the textContent property. */
case class Callback1OptHtml(listenerId: String, f: OptionHtml => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterOption]] that takes an OptionHtml => String function, to use JavaScript to update the textContent property. */
case class Callback1OptText(listenerId: String, f: OptionHtml => String) extends CallbackOption

/** A call back for an [[UpdaterOption]] that takes an (OptionHtml, Int) => String function. */
case class CallbackOptInt1Text(listenerId: String, input2IdStr: String, f: (OptionHtml, Int) => String) extends CallbackOption

/** A call back for an [[UpdaterOption]] that takes an (OptionHtml, Double) => RArr[XCon] function. */
case class CallbackOptDbl1Html(listenerId: String, input2IdStr: String, f: (OptionHtml, Double) => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterDblInput]] that takes an (OptionHtml, Double => RArr[XCon] function. */
case class CallbackOptDbl2Html(listenerId: String, input1: UpdaterOption, f: (OptionHtml, Double) => RArr[XCon]) extends CallbackDbl

/** A call back for an [[UpdaterOption]] that takes an (OptionHtml, Double) => String function. */
case class CallbackOptDbl1Text(listenerId: String, input2IdStr: String, f: (OptionHtml, Double) => String) extends CallbackOption

/** A call back for an [[UpdaterDblInput]] that takes an (OptionHtml, Double) => String function. */
case class CallbackOptDbl2Text(listenerId: String, input1: UpdaterOption, f: (OptionHtml, Double) => String) extends CallbackDbl