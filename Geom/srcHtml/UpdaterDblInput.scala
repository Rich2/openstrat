/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** Creates an HTML Input element that can update textContent fields on the page. */
class UpdaterDblInput(val idStr: String, val value: Double, val otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater) extends UpdaterInputLike(page), InputHtml
{ var listeners: RArr[CallbackDbl] = RArr()
  def clientCount: Int = listeners.length

  override def typeAtt: TypeAtt = TypeNumberAtt
  override def valueStr: String = value.str

  /** Registers a call back to a listener with a Double => String function. */
  def next1(f: Double => String): IdAtt =
  { val newlistenerId: String = idStr + clientCount.str
    listeners +%= Callback1Dbl(newlistenerId, f)
    IdAtt(newlistenerId)
  }

  /** Registers a call back as the second inout to a listener function with a (String, Double) => String function.  */
  def nextStrDbl2(listenerID: String, input1IDStr: String, f: (String, Double) => String): Unit =
  { listeners +%= CallbackStrDbl2(listenerID, input1IDStr, f)   
  }

  /** Registers a call back to a listener with a (String, Double) => String function. */
  def nextOptionNum2(listenerID: String, input1: UpdaterOption, f: (OptionHtml, Double) => RArr[XCon]): Unit =
  { listeners +%= CallbackOptionNum2(listenerID, input1, f)
  }
}

object UpdaterDblInput
{ /** Factory apply method to create an HTML Input element that can update textContent fields on the page. */
  def apply(idStr: String, value: Double, otherAttribs: XAtt*)(using page: PageHtmlUpdater): UpdaterDblInput =
    new UpdaterDblInput(idStr, value, otherAttribs.toRArr)
}