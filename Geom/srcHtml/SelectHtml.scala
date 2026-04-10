/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** HTML select element used to create a drop-down list. */
trait SelectHtml extends HtmlTagLines, InputHtmlLike
{ override def tagName: String = "select"

  def visNum: Int
  
  /** Size attribute specifies how many options to be displayed at one time. */
  def sizeAtt = XAttInt("size", visNum)

  /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackSelect] = RArr()

  override def attribs: RArr[XAtt] = idAtt %: sizeAtt %: otherAttribs
  override def clientCount: Int = callBacks.length

  def nextId(f: String => String): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    callBacks +%= CallbackSelect(newtargetId, f)
    IdAtt(newtargetId)
  }
}

object SelectHtml
{ /** Factory apply method for HTML select element. */
  def apply(idStr: String, contents: RArr[OptionHtml], visNum: Int, otherAtts: RArr[XAtt] = RArr()): SelectHtml =
    new SelectHtmlGen(idStr, contents, visNum, otherAtts)

  /** Factory apply method for HTML select element, with 1 visible element. */
  def apply(idStr: String, contents: OptionHtml*): SelectHtml = new SelectHtmlGen(idStr, contents.toRArr, 1, RArr())

  class SelectHtmlGen(val idStr: String, val contents: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt]) extends SelectHtml
}

/** An HTML label followed by an [[SelectHtml]]. */
class LabelSelect[T<: OptionHtml](val idStr: String, val label: String, val options: RArr[T], val visNum: Int, val otherAttribs: RArr[XAtt])(using
  page: PageHtmlUpdater) extends LabelAndInput
{ override def child2: SelectHtml = SelectHtml(idStr, options, visNum, otherAttribs)
}

object LabelSelect
{
  def apply[T<: OptionHtml](idStr: String, label: String, contents: RArr[T], visNum: Int, otherAttribs: RArr[XAtt])(using page: PageHtmlUpdater): LabelSelect[T] =
    new LabelSelect[T](idStr, label, contents, visNum, otherAttribs)

  def apply[T<: OptionHtml](idStr: String, label: String, contents: T*)(using page: PageHtmlUpdater, ctT: ClassTag[T]): LabelSelect[T] =
    new LabelSelect[T](idStr, label, contents.toRArr, 1, RArr())
}