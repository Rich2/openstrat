/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** Creates an HTML Input element that can update textContent fields on the page. */
class UpdaterNumInput(val idStr: String, val value: Double, val otherAttribs: RArr[XAtt])(using val page: PageHtmlUpdater) extends InputHtml, InputLikeUpdater
{ page.inpAcc +%= this
  var depends: RArr[CallbackNum] = RArr()
  def clientCount: Int = depends.length

  override def typeAtt: TypeAtt = TypeNumberAtt
  override def valueStr: String = value.str

  /** Registers a call back to a listener with a Double => String function. */
  def next1Id(f: Double => String): IdAtt =
  { val newlistenerId: String = idStr + clientCount.str
    depends +%= Callback1Num(newlistenerId, f)
    IdAtt(newlistenerId)
  }

  /** Registers a call back to a listener with a (String, Double) => String function.  */
  def nextTextNum2(listenerID: String, input1IDStr: String, f: (String, Double) => String): Unit =
  { depends +%= CallbackTextNum2(listenerID, input1IDStr, f)   
  }
}

object UpdaterNumInput
{ /** Factory apply method to create an HTML Input element that can update textContent fields on the page. */
  def apply(idStr: String, value: Double, otherAttribs: XAtt*)(using page: PageHtmlUpdater): UpdaterNumInput =
    new UpdaterNumInput(idStr, value, otherAttribs.toRArr)
}