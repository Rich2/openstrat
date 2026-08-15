/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML Input or Select Updater for [[String]]s. */
trait UpdaterStr extends UpdaterInputLike
{ /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackStr] = RArr()

  override def numListeners: Int = callBacks.length

  /** this method registers a page HTML element with the updater. Sends back an id for the listener element. This takes a String => String function to update
   * the listeners textContent property. */
  def nextStrText(f: String => String): IdAtt =
  { val newListnerId: String = idStr + numListeners.str
    callBacks +%= CallbackStrText(newListnerId, f)
    IdAtt(newListnerId)
  }

  def nextOpt2Str2Html(listenerId: String, input1: UpdaterSelect, input3: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]): Unit =
  { callBacks +%= CallbackOpt2Str2Html(listenerId, input1, input3, f)
  }

  def nextOpt2Str3Html(listenerId: String, input1: UpdaterSelect, input2: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]): Unit =
  { callBacks +%= CallbackOpt2Str3Html(listenerId, input1, input2, f)
  }

  def next2Opt2Str3Html(listenerId: String, input1: UpdaterSelect, input2: UpdaterSelect, input4: UpdaterStr, f: (OptionHtml,
    OptionHtml, String, String) => RArr[XCon]): Unit =
  { callBacks +%= Callback2Opt2Str3Html(listenerId, input1, input2, input4, f)
  }

  def next2Opt2Str4Html(listenerId: String, input1: UpdaterSelect, input2: UpdaterSelect, input3: UpdaterStr,
    f: (OptionHtml, OptionHtml, String, String) => RArr[XCon]): Unit =
  { callBacks +%= Callback2Opt2Str4Html(listenerId, input1, input2, input3, f)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target content. */
  def nextStrHtml(f: String => RArr[XCon]): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallBackStrHtml(newListenerId, f)
    IdAtt(newListenerId)
  }

  /** this method creates a new unique id [[String]]. Registers a page HTML element listener with this input-updater. Registers the listener with the second
   * input-updater Sends back an id attribute for the listener element. This takes a function of two [[String]] parameters, the first from this text input and
   * the second from the 2nd text updater, to update the listener content. */
  def next2Str1Text(input2: UpdaterStr, f: (String, String) => String): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= Callback2Str1Text(newListenerId, input2, f)
    input2.next2Str2Text(newListenerId, input2, f)
    IdAtt(newListenerId)
  }

  /** This method registers a page HTML element listener with this input-updater. that has already been registered with the first input-updater. This takes a
   * function of two [[String]] parameters, the first from the first text input and the second from this text updater, to update the listener content */
  def next2Str2Text(listenerID: String, input1: UpdaterStr, f: (String, String) => String): Unit =
  { callBacks +%= Callback2Str2Text(listenerID, input1, f)
  }

  /** This method creates a new unique id attribute for the listener. Registers a page HTML element listener with this [[UpdaterStr]]. Registers with the 2nd
   * and 3rd [[UpdaterStr]]s. This takes a function of 3 [[String]] parameters, the 1st from this text input and the 2nd and 3rd from the other 2
   * [[UpdaterStr]]s, to update the listener content */
  def next3Str1Text(input2: UpdaterStr, input3: UpdaterStr, f: (String, String, String) => String): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= Callback3Str1Text(newListenerId, input2, input3, f)
    input2.next3Str2Text(newListenerId, this, input3, f)
    input3.next3Str3Text(newListenerId, this, input2, f)
    IdAtt(newListenerId)
  }

  /** This method registers the [[Callback3Str2Text]] with this [[UpdaterStr]]. */
  def next3Str2Text(targetID: String, input1: UpdaterStr, input3: UpdaterStr, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Str2Text(targetID, input1, input3, f)
  }

  /** This method registers the [[Callback3Str3Text]] with this [[UpdaterStr]]. */
  def next3Str3Text(targetID: String, input1: UpdaterStr, input2: UpdaterStr, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Str3Text(targetID, input1, input2, f)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def nextStrDbl1Text(input2: UpdaterDblInput, f: (String, Double) => String): IdAtt =
  { val newListenerId: String = idStr + numListeners.str
    callBacks +%= CallbackStrDbl1Text(newListenerId, input2, f)
    input2.nextStrDbl2(newListenerId, this, f)
    IdAtt(newListenerId)
  }
}

/** Class to update a page from a text input from an HTML input element. */
class UpdaterInputStr(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater) extends UpdaterInputLike(page),
  UpdaterStr, UpdaterInput, InputStr

object UpdaterInputStr
{ /** Factory apply method for object to update a page from a text input. */
  def apply(idStr: String, valueStr: String, otherAttribs: XAtt*)(using page: PageHtmlUpdater): UpdaterInputStr =
    new UpdaterInputStr(idStr, valueStr, otherAttribs.toRArr)
}