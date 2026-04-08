/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** HTML select element used to create a drop-down list. */
class HtmlSelect[T<: OptionElem](val idStr: String, val contents: RArr[T], val visNum: Int, val otherAttribs: RArr[XAtt]) extends HtmlTagLines, HtmlInputLike
{ override def tagName: String = "select"

  /** Size attribute specifies how many options to be displayed at one time. */
  def sizeAtt = XAttInt("size", visNum)

  /** List of call backs to other parts of the web page that needed to be updated in response to new input. */
  var callBacks: RArr[CallbackSelect[T]] = RArr()

  override def attribs: RArr[XAtt] = idAtt %: sizeAtt %: otherAttribs
  override def clientCount: Int = callBacks.length

  def nextId(f: T => String): IdAtt =
  { val newtargetId: String = idStr + clientCount.str
    callBacks +%= CallbackSelect(newtargetId, f)
    IdAtt(newtargetId)
  }
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
{ override def child2: HtmlSelect[T] = new HtmlSelect[T](idStr, options, visNum, otherAttribs)
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

case class CallbackSelect[T <: OptionElem](targetId: String, f: T => String) extends CallbackInput

/** Operating system HTML Option element. */
class OperatingSystem(valueStr: String, contentStr: String) extends OptionElem(valueStr, contentStr)

/** Ubuntu derivative operating system HTML option element. */
object UbuntuDeriv extends OperatingSystem("UbuntuDeriv", "Kubuntu/Ubuntu")

/** Arch Linux derivative operating system HTML option element. */
object ArchDeriv extends OperatingSystem("ArchDeriv", "Arch/CachyOS")