/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** classes are used on the JVM to create user input and select elements in HTML pages. But are used in JavaScript to update the parts of the DOM registered
 * with that updater. */
trait InputLike extends HtmlElem
{/** The [[String]] of the id attribute. */
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

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def next2Id1(otherInpIdStr: String, f: (String, String) => String): IdAtt =
  { val newTargetId: String = idStr + clientCount.str
    callBacks +%= Callback2Text1(newTargetId, otherInpIdStr, f)
    IdAtt(newTargetId)
  }

  def next2Id2(targetID: String, otherInpIdStr: String, f: (String, String) => String): Unit =
  { callBacks +%= Callback2Text2(targetID, otherInpIdStr, f)
  }

  def next3Id1(otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String): IdAtt =
  { val newTargetId: String = idStr + clientCount.str
    callBacks +%= Callback3Text1(newTargetId, otherInpIdStr1, otherInpIdStr2, f)
    IdAtt(newTargetId)
  }

  def next3Id2(targetID: String, otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Text2(targetID, otherInpIdStr1, otherInpIdStr2, f)
  }

  def next3Id3(targetID: String, otherInpIdStr1: String, otherInpIdStr2: String, f: (String, String, String) => String): Unit =
  { callBacks +%= Callback3Text3(targetID, otherInpIdStr1, otherInpIdStr2, f)
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