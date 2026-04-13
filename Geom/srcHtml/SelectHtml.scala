/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** HTML select element used to create a drop-down list. */
abstract class SelectHtml(options: RArr[OptionHtml], selectedIn: OptionHtml) extends HtmlTagLines, InputLike
{ override def tagName: String = "select"

  def visNum: Int
  
  /** Size attribute specifies how many options to be displayed at one time. */
  def sizeAtt = XAttInt("size", visNum)

  override def attribs: RArr[XAtt] = idAtt %: sizeAtt %: otherAttribs

  final override def contents: RArr[OptionHtml] = options
  
  final def selected: OptionHtml = contents(0)

  final override def valueStr: String = selected.valueStr
}

object SelectHtml
{ /** Factory apply method for HTML select element. */
  def apply(idStr: String, contents: RArr[OptionHtml], visNum: Int, otherAtts: RArr[XAtt] = RArr()): SelectHtml =
    new SelectHtmlGen(idStr, contents, visNum, otherAtts)

  /** Factory apply method for HTML select element, with 1 visible element. */
  def apply(idStr: String, contents: OptionHtml*): SelectHtml = new SelectHtmlGen(idStr, contents.toRArr, 1, RArr())

  class SelectHtmlGen(val idStr: String, optionsIn: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt]) extends
    SelectHtml(optionsIn, optionsIn(0))
}

/** HTML Select element that updates other parts of the page on changed input. */
class SelectUpdater(val idStr: String, options: RArr[OptionHtml], val visNum: Int, val otherAttribs: RArr[XAtt])(using val page: PageHtmlUpdater) extends
  SelectHtml(options, options(0)), InputLikeUpdaterText

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