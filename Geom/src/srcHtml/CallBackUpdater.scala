/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** A text callback from an [[UpdaterInputLike]] with a function for JavaScript to update textContent or an htmlContent property. */
sealed trait CallbackUpdater
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def listenerId: String
}


/** A callback for an [[UpdaterSelect]]. */
trait CallbackOption extends CallbackUpdater

/** A call back for an [[UpdaterSelect]] that takes an OptionHtml => RArr[XCon] function,  to use JavaScript to pdate the innerHTML property. */
case class CallbackOptHtml(listenerId: String, f: OptionHtml => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterSelect]] that takes an OptionHtml => String function, to use JavaScript to update the textContent property. */
case class CallbackOptText(listenerId: String, f: OptionHtml => String) extends CallbackOption

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, String, String) => String function, to use JavaScript to update the innerHTML property. */
case class CallbackOpt2Str1Html(listenerId: String, input2: UpdaterStr, input3: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]) extends
  CallbackOption

/** A call back for the 1st [[UpdaterSelect]] that takes an (OptionHtml, OptionHtml, String, String) => String function, to use JavaScript to update the
 * innerHTML property. */
case class Callback2Opt2Str1Html(listenerId: String, input2: UpdaterSelect, input3: UpdaterStr, input4: UpdaterStr,
  f: (OptionHtml, OptionHtml, String, String) => RArr[XCon]) extends CallbackOption

/** A call back for the 2nd [[UpdaterSelect]] that takes an (OptionHtml, OptionHtml, String, String) => String function, to use JavaScript to update the
 * innerHTML property. */
case class Callback2Opt2Str2Html(listenerId: String, input1: UpdaterSelect, input3: UpdaterStr, input4: UpdaterStr,
  f: (OptionHtml, OptionHtml, String, String) => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, Double) => RArr[XCon] function, to use innerHTML property. */
case class CallbackOptInt1Html(listenerId: String, input2: UpdaterIntInput, f: (OptionHtml, Int) => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, Int) => String function to use JavaScript to update the textContent property. */
case class CallbackOptInt1Text(listenerId: String, input2: UpdaterIntInput, f: (OptionHtml, Int) => String) extends CallbackOption

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, Double) => RArr[XCon] function, to use innerHTML property. */
case class CallbackOptDbl1Html(listenerId: String, input2: UpdaterDblInput, f: (OptionHtml, Double) => RArr[XCon]) extends CallbackOption

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, Double) => String function. */
case class CallbackOptDbl1Text(listenerId: String, input2: UpdaterDblInput, f: (OptionHtml, Double) => String) extends CallbackOption


/** Call back for an [[UpdaterStr]] with a function for JavaScript to update textContent or an htmlContent property. */
sealed trait CallbackStr extends CallbackUpdater

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, String, String) => RArr[XCon] function, to use JavaScript to update the innerHTML
 * property. */
case class CallbackOpt2Str2Html(listenerId: String, input1: UpdaterSelect, input3: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]) extends
  CallbackStr

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, String, String) => RArr[XCon] function, to use JavaScript to update the innerHTML
 * property. */
case class CallbackOpt2Str3Html(listenerId: String, input1: UpdaterSelect, input2: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]) extends
  CallbackStr

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, OptionHtml, String, String) => RArr[XCon] function, to use JavaScript to update the
 * innerHTML property. */
case class Callback2Opt2Str3Html(listenerId: String, input1: UpdaterSelect, input2: UpdaterSelect, input4: UpdaterStr,
  f: (OptionHtml, OptionHtml, String, String) => RArr[XCon]) extends CallbackStr

/** A call back for an [[UpdaterSelect]] that takes an (OptionHtml, OptionHtml, String, String) => RArr[XCon] function, to use JavaScript to update the
 * innerHTML property. */
case class Callback2Opt2Str4Html(listenerId: String, input1: UpdaterSelect, input2: UpdaterSelect, input3: UpdaterStr,
  f: (OptionHtml, OptionHtml, String, String) => RArr[XCon]) extends CallbackStr

/** Call back for an [[UpdaterStr]] with a String => RArr[XCon] function for JavaScript to update an htmlContent property. */
case class CallBack1StrHtml(listenerId: String, f: String => RArr[XCon]) extends CallbackStr

/** Call back for an [[UpdaterStr]] with a String => String function for JavaScript to update a textContent property. */
case class Callback1StrText(listenerId: String, f: String => String) extends CallbackStr

/** Call back for an [[UpdaterStr]] for 1st parameter of (String, String) => String function for JavaScript to update the listener's textContent property. */
case class Callback2Str1(listenerId: String, input2: UpdaterStr, f: (String, String) => String) extends CallbackStr

/** Call back for an [[UpdaterStr]] for 2nd parameter of (String, String) => String function for JavaScript to update the listener's textContent property. */
case class Callback2Str2(listenerId: String, input1: UpdaterStr, f: (String, String) => String) extends CallbackStr

/** Call back for an [[UpdaterStr]] for 1st parameter of (String, Double => String function for JavaScript to update a textContent property. */
case class CallbackStrDbl1(listenerId: String, input2: UpdaterDblInput, f: (String, Double) => String) extends CallbackStr

/** Call back from an [[UpdaterStr]] for a (String, String, String) => String function for JavaScript to update a textContent property. */
sealed trait Callback3Str extends CallbackStr

/** Call back from an [[UpdaterStr]] for the 1st parameter of a (String, String, String) => String function for JavaScript to update a textContent property. */
case class Callback3Str1(listenerId: String, input2: UpdaterStr, input3: UpdaterStr, f: (String, String, String) => String) extends Callback3Str

/** Call back from an [[UpdaterStr]] for the 2nd parameter of a (String, String, String) => String function for JavaScript to update a textContent property. */
case class Callback3Str2(listenerId: String, input1: UpdaterStr, input3: UpdaterStr, f: (String, String, String) => String) extends Callback3Str

/** Call back from an [[UpdaterStr]] for the 3rd parameter of a (String, String, String) => String function for JavaScript to update a textContent property. */
case class Callback3Str3(listenerId: String, input1: UpdaterStr, input2: UpdaterStr, f: (String, String, String) => String) extends Callback3Str


/** Call back from an [[UpdaterIntInput]]. */
sealed trait CallbackInt extends CallbackUpdater

/** A call back for an [[UpdaterIntInput]] that takes a simple Double => String function for JavaScript to update a textContent property. */
case class Callback1IntText(listenerId: String, f: Int => String) extends CallbackInt

/** A call back for an [[UpdaterIntInput]] that takes an (OptionHtml, Double => RArr[XCon] function. */
case class CallbackOptInt2Html(listenerId: String, input1: UpdaterSelect, f: (OptionHtml, Int) => RArr[XCon]) extends CallbackInt

/** A call back for an [[UpdaterIntInput]] that takes an (OptionHtml, Double) => String function. */
case class CallbackOptInt2Text(listenerId: String, input1: UpdaterSelect, f: (OptionHtml, Int) => String) extends CallbackInt


/** Call back from an [[UpdaterDblInput]]. */
sealed trait CallbackDbl extends CallbackUpdater

/** A call back for an [[UpdaterDblInput]] that takes a simple Double => String function for JavaScript to update an htmlContent property. */
case class Callback1DblHtml(listenerId: String, f: Double => RArr[XCon]) extends CallbackDbl

/** A call back for an [[UpdaterDblInput]] that takes a simple Double => String function for JavaScript to update a textContent property. */
case class Callback1DblText(listenerId: String, f: Double => String) extends CallbackDbl

/** Call back from an [[UpdaterDblInput]] for a (String, Double) => String function for JavaScript to update a textContent property. */
case class CallbackStrDbl2(listenerId: String, input1: UpdaterStr, f: (String, Double) => String) extends CallbackDbl

/** A call back for an [[UpdaterDblInput]] that takes an (OptionHtml, Double => RArr[XCon] function. */
case class CallbackOptDbl2Html(listenerId: String, input1: UpdaterSelect, f: (OptionHtml, Double) => RArr[XCon]) extends CallbackDbl

/** A call back for an [[UpdaterDblInput]] for the 2nd parameter of (OptionHtml, Double) => String function to update listener's textContent property. */
case class CallbackOptDbl2Text(listenerId: String, input1: UpdaterSelect, f: (OptionHtml, Double) => String) extends CallbackDbl