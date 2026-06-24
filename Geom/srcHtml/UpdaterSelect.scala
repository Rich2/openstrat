/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** HTML Select element that updates other parts of the page on changed input. */
class UpdaterOption(val idStr: String, val contents: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater) extends
  UpdaterInputLike(page), SelectHtml
{
  /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackOption] = RArr()

  override def clientCount: Int = callBacks.length
  
  def listenerInit(f: OptionHtml => RArr[XCon]): RArr[XCon] = f(contents(0))
  
  def strToOption(valStr: String): OptionHtml = contents.find(_.valueStr == valStr).getOrElse(OptionNotFound)

  def initOption: OptionHtml = contents(0)

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target HTML content. */
  def next1Id(f: OptionHtml => RArr[XCon]): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= Callback1OptHtml(newListenerId, f)
    IdAtt(newListenerId)
  }

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a function of two [[String]] parameters,
   * the first from this text input and the second from another text updater, to update the target content. */
  def nextOptionNumId1(input2: UpdaterDblInput, f: (OptionHtml, Double) => RArr[XCon]): IdAtt =
  { val newListenerId: String = idStr + clientCount.str
    callBacks +%= CallbackOptionNum1(newListenerId, input2.idStr, f)
    input2.nextOptionNum2(newListenerId, this, f)
    IdAtt(newListenerId)
  }
}

/** An HTML label followed by an [[SelectHtml]]. */
class LabelSelectUpdaterAny(val idStr: String, val label: String, val options: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using
  page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: UpdaterOption = UpdaterOption(idStr, options, visNum, otherAttribs)
}

object LabelSelectUpdaterAny
{
  def apply(idStr: String, label: String, options: RArr[OptionHtml], visNum: Int, otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater): LabelSelectUpdaterAny =
    new LabelSelectUpdaterAny(idStr, label, options, visNum, otherAttribs)

  def apply(idStr: String, label: String, options: OptionHtml*)(using page: PageHtmlUpdater): LabelSelectUpdaterAny =
    new LabelSelectUpdaterAny(idStr, label, options.toRArr, 1, RArr())
}