/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** HTML select element used to create a drop-down list. */
trait HtmlSelect extends HtmlTagLines, HtmlUpdater
{ override def contents: RArr[OptionElem]
  override def tagName: String = "select"
  override def attribs: RArr[XAtt] = idAtt %: otherAttribs
}

object HtmlSelect
{ /** Factory apply method for HTML select element. */
  def apply(name: String, contents: RArr[OptionElem], otherAtts: RArr[XAtt] = RArr()): HtmlSelect = new HtmlSelectGen(name, contents, otherAtts)

  /** Factory apply method for HTML select element. */
  def apply(name: String, contents: OptionElem*): HtmlSelect = new HtmlSelectGen(name, contents.toRArr, RArr())

  /** implementation class for the general case of [[HtmlSelect]]. */
  class HtmlSelectGen(val idStr: String, val contents: RArr[OptionElem], val otherAttribs: RArr[XAtt]) extends HtmlSelect
}

/** An HTML label followed by an [[HtmlSelect]]. */
class LabelSelect(val idStr: String, val label: String, val contents: RArr[OptionElem])(using page: HtmlPageInput) extends LabelAndInput
{ override def child1: HtmlLabel = HtmlLabel(idStr, label)
  override def child2: HtmlSelect = HtmlSelect(idStr, contents)  
}

/** HTML select element for Operating Systems. */
class SelectOS(val idStr: String, val contents: RArr[OperatingSystem], val otherAttribs: RArr[XAtt]) extends HtmlSelect

object SelectOS
{
  /** Factory apply method for HTML select operating system element. */
  def apply(name: String, contents: RArr[OperatingSystem], otherAtts: RArr[XAtt] = RArr()): SelectOS = new SelectOS(name, contents, otherAtts)

  /** Factory apply method for HTML select operating system element. */
  def apply(name: String, contents: OperatingSystem*): SelectOS = new SelectOS(name, contents.toRArr, RArr())

  /** Factory apply method for HTML select operating system element. */
  def apply(contents: OperatingSystem*): SelectOS = new SelectOS("OperatingSytem", contents.toRArr, RArr())
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