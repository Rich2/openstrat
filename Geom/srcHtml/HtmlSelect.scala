/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** HTML select element used to create a drop-down list. */
class HtmlSelect[T<: OptionElem](val idStr: String, val contents: RArr[T], val visNum: Int, val otherAttribs: RArr[XAtt]) extends HtmlTagLines, HtmlInputLike
{ override def tagName: String = "select"
  def sizeAtt = XAttInt("size", visNum)
  override def attribs: RArr[XAtt] = idAtt %: sizeAtt %: otherAttribs

  /** The number of page elements that have registered to receive updates from this inout. */
  override def clientCount: Int = ???
}

object HtmlSelect
{ /** Factory apply method for HTML select element. */
  def apply[T<: OptionElem](idStr: String, contents: RArr[T], visNum: Int, otherAtts: RArr[XAtt] = RArr()): HtmlSelect[T] =
    new HtmlSelect[T](idStr, contents, visNum, otherAtts)

  /** Factory apply method for HTML select element, with 1 visible element. */
  def apply[T<: OptionElem](idStr: String, contents: T*)(using ClassTag[T]): HtmlSelect[T] = new HtmlSelect[T](idStr, contents.toRArr, 1, RArr())
}

/** An HTML label followed by an [[HtmlSelect]]. */
class LabelSelect[T<: OptionElem](val idStr: String, val label: String, val options: RArr[T], val visNum: Int, val otherAttribs: RArr[XAtt])(using
  page: HtmlPageInput) extends LabelAndInput
{ //override def child1: HtmlLabel = HtmlLabel(idStr, label)
  override def child2: HtmlSelect[T] = new HtmlSelect[T](idStr, options, visNum, otherAttribs)
}

object LabelSelect
{
  def apply[T<: OptionElem](idStr: String, label: String, contents: RArr[T], visNum: Int, otherAttribs: RArr[XAtt])(using page: HtmlPageInput): LabelSelect[T] =
    new LabelSelect[T](idStr, label, contents, visNum, otherAttribs)

  def apply[T<: OptionElem](idStr: String, label: String, contents: T*)(using page: HtmlPageInput, ctT: ClassTag[T]): LabelSelect[T] =
    new LabelSelect[T](idStr, label, contents.toRArr, 1, RArr())
}

/** HTML option element is used to define an item contained in a select, an optgroup, or a datalist element. As such, option can represent menu items* in popups
 * and other lists of items in an HTML document. */
class OptionElem(val valueStr: String, val contentStr: String) extends HtmlOwnLine
{ override def tagName: String = "option"
  override def attribs: RArr[XAtt] = RArr(ValueAtt(valueStr))
  override def contents: RArr[XCon] = RArr(valueStr)
}

/** Operating system HTML Option element. */
class OperatingSystem(valueStr: String, contentStr: String) extends OptionElem(valueStr, contentStr)

/** Ubuntu derivative operating system HTML option element. */
object UbuntuDeriv extends OperatingSystem("UbuntuDeriv", "Kubuntu/Ubuntu")

/** Arch Linux derivative operating system HTML option element. */
object ArchDeriv extends OperatingSystem("ArchDeriv", "Arch/CachyOS")