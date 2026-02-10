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