/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML option element is used to define an item contained in a select, an optgroup, or a datalist element. As such, option can represent menu items* in popups
 * and other lists of items in an HTML document. */
trait OptionHtml extends HtmlOwnLine
{ override def tagName: String = "option"
  def valueStr: String
  def contentStr: String
  override def attribs: RArr[HAtt] = RArr(ValueAtt(valueStr))
  override def contents: RArr[XCon] = RArr(valueStr)
}

object OptionHtml
{
  def apply(valueStr: String, contentStr: String): OptionHtml = OptionHtmlGen(valueStr, contentStr)

  /** Implementation class for the general case of  HTML option element. */ 
  case class OptionHtmlGen(valueStr: String, contentStr: String) extends OptionHtml
}

case object OptionNotFound extends OptionHtml
{ override def valueStr: String = "Notfound"
  override def contentStr: String = "Option not found"
}

/** Operating system HTML Option element. */
trait OperatingSystem extends OptionHtml

/** HTML option element for an unlisted operating system . */
case object OtherOperatingSystem extends OperatingSystem
{ override def valueStr: String = "otherOS"
  override def contentStr: String = "Other Operating System"
}

/** Operating system HTML Option element. */
trait LinuxKernelSystem extends OperatingSystem

/** Operating system HTML Option element. */
trait LinuxSystem extends LinuxKernelSystem

/** Ubuntu derivative operating system HTML option element. */
case object UbuntuDeriv extends LinuxSystem
{ override def valueStr: String = "UbuntuDeriv"
  override def contentStr: String = "Kubuntu/Ubuntu"
}

/** Arch Linux derivative operating system HTML option element. */
case object ArchDeriv extends LinuxSystem
{ override def valueStr: String = "ArchDeriv"
  override def contentStr: String = "Arch/CachyOS"
}