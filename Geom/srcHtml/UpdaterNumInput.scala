/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** Creates an HTML Input element that takes numbers can update textContent fields on the page. */
trait UpdaterNumInput extends UpdaterInputLike, InputHtml
{ override def typeAtt: TypeAtt = TypeNumberAtt
}

/** Creates an HTML Input element that takes [[Int]]s can update textContent fields on the page. */
class UpdaterIntInput(val idStr: String, val value: Int, val minVal: Int, val maxVal: Int, val step: Int, val otherAttribs: RArr[HAtt])(
  using page: PageHtmlUpdater) extends UpdaterInputLike(page), UpdaterNumInput, UpdaterInput
{ var listeners: RArr[CallbackInt] = RArr()

  def clientCount: Int = listeners.length
  override def valueStr: String = value.str

  override def attribs: RArr[HAtt] = super.attribs +% XAttInt("min", minVal) +% XAttInt("max", maxVal)

  /** Registers a call back to a listener with an Int => String function. */
  def next1(f: Int => String): IdAtt =
  { val newlistenerId: String = idStr + clientCount.str
    listeners +%= Callback1IntText(newlistenerId, f)
    IdAtt(newlistenerId)
  }
  
  /** Registers a call back to a listener with a (String, Int) => String function. */
  def nextOptInt2Html(listenerID: String, input1: UpdaterOption, f: (OptionHtml, Int) => RArr[XCon]): Unit =
  { listeners +%= CallbackOptInt2Html(listenerID, input1, f)
  }

  /** Registers a call back to a listener with a (String, Double) => String function. */
  def nextOptInt2Text(listenerID: String, input1: UpdaterOption, f: (OptionHtml, Int) => String): Unit ={
    listeners +%= CallbackOptInt2Text(listenerID, input1, f)
  }
}

object UpdaterIntInput
{ /** Factory apply method to create an HTML Input element that can update textContent fields on the page. */
  def apply(idStr: String, value: Int, minVal: Int, maxVal: Int, step: Int, otherAttribs: XAtt*)(using page: PageHtmlUpdater): UpdaterIntInput =
    new UpdaterIntInput(idStr, value, minVal, maxVal, step, otherAttribs.toRArr)
}

/** Creates an HTML Input element that takes [[Double]]s can update textContent fields on the page. */
class UpdaterDblInput(val idStr: String, val value: Double, val otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater) extends UpdaterInputLike(page),
  UpdaterNumInput, UpdaterInput
{ var listeners: RArr[CallbackDbl] = RArr()
  def clientCount: Int = listeners.length
  
  override def valueStr: String = value.str

  /** Registers a call back to a listener with a Double => String function. */
  def next1(f: Double => String): IdAtt =
  { val newlistenerId: String = idStr + clientCount.str
    listeners +%= Callback1DblText(newlistenerId, f)
    IdAtt(newlistenerId)
  }

  /** Registers a call back as the second inout to a listener function with a (String, Double) => String function.  */
  def nextStrDbl2(listenerID: String, input1IDStr: String, f: (String, Double) => String): Unit =
  { listeners +%= CallbackStrDbl2(listenerID, input1IDStr, f)   
  }
  
  /** Registers a call back to a listener with a (String, Double) => String function. */
  def nextOptDbl2Html(listenerID: String, input1: UpdaterOption, f: (OptionHtml, Double) => RArr[XCon]): Unit =
  { listeners +%= CallbackOptDbl2Html(listenerID, input1, f)
  }

  /** Registers a call back to a listener with a (String, Double) => String function. */
  def nextOptDbl2Text(listenerID: String, input1: UpdaterOption, f: (OptionHtml, Double) => String): Unit =
  { listeners +%= CallbackOptDbl2Text(listenerID, input1, f)
  }
}

object UpdaterDblInput
{ /** Factory apply method to create an HTML Input element that can update textContent fields on the page. */
  def apply(idStr: String, value: Double, otherAttribs: XAtt*)(using page: PageHtmlUpdater): UpdaterDblInput =
    new UpdaterDblInput(idStr, value, otherAttribs.toRArr)
}

class LabelUpdaterIntInput(val idStr: String, val label: String, val valueNum: Int, minVal: Int, maxVal: Int, step: Int = 1)(using page: PageHtmlUpdater) extends
  LabelInputLike
{ override def child2: UpdaterIntInput = UpdaterIntInput(idStr, valueNum, minVal, maxVal, step)
}

object LabelUpdaterIntInput
{ /** Factory apply method for label and number input HTML elements. */
  def apply(idStr: String, label: String, valueNum: Int, minVal: Int, maxVal: Int, step: Int = 1)(using page: PageHtmlUpdater): LabelUpdaterIntInput =
    new LabelUpdaterIntInput(idStr, label, valueNum, minVal, maxVal, step)
}

class LabelUpdaterDblInput(val idStr: String, val label: String, val valueNum: Double)(using page: PageHtmlUpdater) extends LabelInputLike
{ override def child2: UpdaterDblInput = UpdaterDblInput(idStr, valueNum)
}

object LabelUpdaterDblInput
{ /** Factory apply method for label and number input HTML elements. */
  def apply(idStr: String, label: String, valueNum: Double)(using page: PageHtmlUpdater): LabelUpdaterDblInput =
    new LabelUpdaterDblInput(idStr, label, valueNum)
}