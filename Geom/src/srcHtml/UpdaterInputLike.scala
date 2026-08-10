/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** An HTML page updater from an HTML inout or select element. */
abstract class UpdaterInputLike(val page: PageHtmlUpdater) extends InputLike
{ page.inpAcc +%= this

  /** The number of page elements that have registered to receive updates from this inout. */
  def clientCount: Int
}

/** An HTML Input element that updates its page, via JavaScript, rather than though HTTP requests to the server. */
trait UpdaterInput extends UpdaterInputLike, InputHtml
{ override def attribs: RArr[HAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt) ++ otherAttribs
}

/** HTML Input or Select Updater for [[String]]s. */
trait UpdaterStr extends UpdaterInputLike
{ /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackStr] = RArr()

  def clientCount: Int = callBacks.length

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target content. */
  def next1Text(f: String => String): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    callBacks +%= Callback1StrText(newtargetId, f)
    IdAtt(newtargetId)
  }

  def nextOpt2Str2Html(listenerId: String, input1: UpdaterSelect, input3: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]): Unit =
  { callBacks +%= CallbackOpt2Str2Html(listenerId, input1, input3, f)
  }

  def nextOpt2Str3Html(listenerId: String, input1: UpdaterSelect, input2: UpdaterStr, f: (OptionHtml, String, String) => RArr[XCon]): Unit =
  { callBacks +%= CallbackOpt2Str3Html(listenerId, input1, input2, f)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target content. */
  def next1Html(f: String => RArr[XCon]): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    callBacks +%= CallBack1StrHtml(newtargetId, f)
    IdAtt(newtargetId)
  }

  /** this method creates a new unique id [[String]]. Registers a page HTML element listener with this input-updater. Registers the listener with the second
   * input-updater Sends back an id attribute for the listener element. This takes a function of two [[String]] parameters, the first from this text input and
   * the second from the 2nd text updater, to update the listener content. */
  def next2Text1(input2: UpdaterStr, f: (String, String) => String): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= Callback2Str1(newListenerId, input2, f)
    input2.next2Id2(newListenerId, input2, f)
    IdAtt(newListenerId)
  }

  /** This method registers a page HTML element listener with this input-updater. that has already been registered with the first input-updater. This takes a
   * function of two [[String]] parameters, the first from the first text input and the second from this text updater, to update the listener content */
  def next2Id2(listenerID: String, input1: UpdaterStr, f: (String, String) => String): Unit =
  { callBacks +%= Callback2Str2(listenerID, input1, f)
  }

  /** This method creates a new unique id attribute for the listener. Registers a page HTML element listener with this [[UpdaterStr]]. Registers with the 2nd
   * and 3rd [[UpdaterStr]]s. This takes a function of 3 [[String]] parameters, the 1st from this text input and the 2nd and 3rd from the other 2
   * [[UpdaterStr]]s, to update the listener content */
  def next3Id1(input2: UpdaterStr, input3: UpdaterStr, f: (String, String, String) => String): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= Callback3Str1(newListenerId, input2, input3, f)
    input2.next3Id2(newListenerId, this, input3, f)
    input3.next3Id3(newListenerId, this, input2, f)
    IdAtt(newListenerId)
  }

  /** This method registers the [[Callback3Str2]] with this [[UpdaterStr]]. */
  def next3Id2(targetID: String, input1: UpdaterStr, input3: UpdaterStr, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Str2(targetID, input1, input3, f)
  }

  /** This method registers the [[Callback3Str3]] with this [[UpdaterStr]]. */
  def next3Id3(targetID: String, input1: UpdaterStr, input2: UpdaterStr, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Str3(targetID, input1, input2, f)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def nextStrDblId1(input2: UpdaterDblInput, f: (String, Double) => String): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= CallbackStrDbl1(newListenerId, input2, f)
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