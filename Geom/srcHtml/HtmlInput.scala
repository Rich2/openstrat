/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import reflect.ClassTag

/** An HTML input element. */
trait HtmlInput extends HtmlVoid
{ def typeAtt: TypeAtt
  override def tagName: String = "input"
}

trait InputUpdater extends HtmlInput
{
  var parentCount: Int = 0
  
  /** The [[String]] of the id attribute. */
  def idStr: String

  def idAtt: IdAtt = IdAtt(idStr)

  def valueAtt: ValueAtt

  def valueStr: String

  def otherAttribs: RArr[XAtt]

  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt) ++ otherAttribs  
}


/** A text callback from an input to a textContent. */
trait CallbackInput
{ /** The id attribute on the target HTML element whose textContent is to be updated. */
  def targetId: String
}

/** HTML Input element with submit type */
case class SubmitInput(valueStr: String) extends HtmlInput
{ override def typeAtt: TypeAtt = TypeSubmitAtt
  override def attribs: RArr[XAtt] = RArr(typeAtt, ValueAtt(valueStr))
}

/** <option> HTML element is used to define an item contained in a <select>, an <optgroup>, or a <datalist> element. As such, <option> can represent menu items
 * in popups and other lists of items in an HTML document. */
class OptionElem(val valueStr: String, val contentStr: String) extends HtmlOwnLine
{ override def tagName: String = "option"
  override def attribs: RArr[XAtt] = RArr(ValueAtt(valueStr))
  override def contents: RArr[XCon] = RArr(valueStr)
}

/** Operating */
class OperatingSystem(valueStr: String, contentStr: String) extends OptionElem(valueStr, contentStr)

/** Ubuntu derivative operating system <option> HTML element. */
object UbuntuDeriv extends OperatingSystem("UbuntuDeriv", "Kubuntu/Ubuntu")

/** HTML select element used to create a drop-down list. */
class HtmlSelect(val name: String, options: StrArr) extends HtmlTagLines
{ override def tagName: String = "select"
  override def attribs: RArr[XAtt] = RArr(IdAtt(name))
  override def contents: RArr[XCon] = ??? // options.map(ValueAtt(_))
}