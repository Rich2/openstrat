/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag


/** HTML Select element that updates other parts of the page on changed input. */
class UpdaterSelectStr(val idStr: String, val contents: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater) extends
  UpdaterInputLike(page), SelectHtml, UpdaterText

/** An HTML label followed by an [[SelectHtml]]. */
class LabelSelectUpdaterStr(val idStr: String, val label: String, val options: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using
  page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: UpdaterSelectStr = UpdaterSelectStr(idStr, options, visNum, otherAttribs)
}

object LabelSelectUpdaterStr
{
  def apply(idStr: String, label: String, options: RArr[OptionHtml], visNum: Int, otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater): LabelSelectUpdaterStr =
    new LabelSelectUpdaterStr(idStr, label, options, visNum, otherAttribs)

  def apply(idStr: String, label: String, options: OptionHtml*)(using page: PageHtmlUpdater): LabelSelectUpdaterStr =
    new LabelSelectUpdaterStr(idStr, label, options.toRArr, 1, RArr())
}

/** HTML Select element that updates other parts of the page on changed input. */
class UpdaterSelectAny(val idStr: String, val contents: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater) extends
  UpdaterInputLike(page), SelectHtml
{
  /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackAny] = RArr()

  def clientCount: Int = callBacks.length

  /** this method registers a page HTML element with the updater. Sends back an id for the target element. This takes a simple function of this one [[String]]
   * input to update the target content. */
  def next1Id(f: Any => RArr[XCon]): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    callBacks +%= CallbackAny(newtargetId, f)
    IdAtt(newtargetId)
  }
}

/** An HTML label followed by an [[SelectHtml]]. */
class LabelSelectUpdaterAny(val idStr: String, val label: String, val options: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using
  page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: UpdaterSelectAny = UpdaterSelectAny(idStr, options, visNum, otherAttribs)
}

object LabelSelectUpdaterAny
{
  def apply(idStr: String, label: String, options: RArr[OptionHtml], visNum: Int, otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater): LabelSelectUpdaterAny =
    new LabelSelectUpdaterAny(idStr, label, options, visNum, otherAttribs)

  def apply(idStr: String, label: String, options: OptionHtml*)(using page: PageHtmlUpdater): LabelSelectUpdaterAny =
    new LabelSelectUpdaterAny(idStr, label, options.toRArr, 1, RArr())
}