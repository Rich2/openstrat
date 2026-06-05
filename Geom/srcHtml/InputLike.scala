/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** classes are used on the JVM to create user input and select elements in HTML pages. But are used in JavaScript to update the parts of the DOM registered
 * with that updater. */
trait InputLike extends HtmlElem
{/** The [[String]] of the id attribute fot this input element. */
  def idStr: String

  def valueStr: String
  
  /** Other attributes in addition to the [[IDAtt]]. */
  def otherAttribs: RArr[XAtt]

  /** The ID attribute of this element. */
  def idAtt: IdAtt = IdAtt(idStr)
}

trait InputLikeUpdater extends InputLike
{
  def page: PageHtmlUpdater

  /** The number of page elements that have registered to receive updates from this inout. */
  def clientCount: Int
}

/** HTML Input or Select Updater for [[String]]s. */
trait UpdaterText extends InputLikeUpdater
{
  page.inpAcc +%= this

  /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackText] = RArr()

  def clientCount: Int = callBacks.length
  
  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target content. */
  def next1Id(f: String => String): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    callBacks +%= Callback1Text(newtargetId, f)
    IdAtt(newtargetId)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target content. */
  def nextHtmlId(f: String => RArr[XCon]): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    callBacks +%= CallBack1StrHtml(newtargetId, f)
    IdAtt(newtargetId)
  }

  /** this method creates a new unique id [[String]]. Registers a page HTML element listener with this input-updater. Registers the listener with the second
   * input-updater Sends back an id attribute for the listener element. This takes a function of two [[String]] parameters, the first from this text input and
   * the second from the 2nd text updater, to update the listener content. */
  def next2Id1(input2: UpdaterText, f: (String, String) => String): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= Callback2Text1(newListenerId, input2.idStr, f)
    input2.next2Id2(newListenerId, idStr, f)
    IdAtt(newListenerId)
  }

  /** This method registers a page HTML element listener with this input-updater. that has already been registered with the first input-updater. This takes a
   * function of two [[String]] parameters, the first from the first text input and the second from this text updater, to update the listener content */
  def next2Id2(listenerID: String, inp1IdStr: String, f: (String, String) => String): Unit =
  { callBacks +%= Callback2Text2(listenerID, inp1IdStr, f)
  }

  /** This method creates a new unique id attribute for the listener. Registers a page HTML element listener with this input-updater. registers with the 2nd and
   * 3rd first input-updater. This takes a function of 3 [[String]] parameters, the 1st from this text input and the 2nd and 3rd frpm the other 2 text updaters,
   * to update the listener content */
  def next3Id1(input2: UpdaterText, input3: UpdaterText, f: (String, String, String) => String): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= Callback3Text1(newListenerId, input2.idStr, input3.idStr, f)
    input2.next3Id2(newListenerId, idStr, input3.idStr, f)
    input3.next3Id3(newListenerId, idStr, input2.idStr, f)
    IdAtt(newListenerId)
  }

  def next3Id2(targetID: String, input1IdStr: String, input3IdStr: String, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Text2(targetID, input1IdStr, input3IdStr, f)
  }

  def next3Id3(targetID: String, input1IdStr: String, input2IdStr: String, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Text3(targetID, input1IdStr, input2IdStr, f)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def nextTextNumId1(input2: UpdaterNumInput, f: (String, Double) => String): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= CallbackTextNum1(newListenerId, input2.idStr, f)
    input2.nextTextNum2(newListenerId, idStr, f)
    IdAtt(newListenerId)
  }
}

/** A text callback from an input to a textContent. */
trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def targetId: String
}

/** An HTML span containing a label and an input / select element. */
trait LabelAndInput extends SpanInlineBlockOwnline, Parent2T[HtmlElem]
{ /** [[String]] for the id attribute. */
  def idStr: String

  /** The label [[String]]. */
  def label: String

  override def child1: LabelHtml = LabelHtml(idStr, label)
  override def contents: RArr[XCon] = RArr(child1, child2)
}