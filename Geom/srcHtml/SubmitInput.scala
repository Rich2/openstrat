/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** HTML Input element with submit type */
class SubmitButton(val idStr: String, val valueStr: String, val otherAttribs: RArr[XAtt]) extends  InputHtml
{ override def typeAtt: TypeAtt = SubmitTypeAtt
  override def attribs: RArr[XAtt] = RArr(typeAtt, ValueAtt(valueStr)) ++ otherAttribs
}

object SubmitButton
{ /** Factory apply method for submit input HTML element. */
  def apply(idStr: String, valueStr: String, otherAttribs: RArr[XAtt] = RArr()): SubmitButton = new SubmitButton(idStr, valueStr, otherAttribs)
}