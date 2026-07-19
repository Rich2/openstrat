/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import reflect.ClassTag

/** HTML Input element with submit type */
class SubmitButton(val nameAttStr: String, val valueStr: String, val otherAttribs: RArr[HAtt]) extends InputPost
{ override def typeAtt: TypeAtt = SubmitTypeAtt
  override def idStr: String = nameAttStr
  override def attribs: RArr[HAtt] = RArr(typeAtt, nameAtt, ValueAtt(valueStr)) ++ otherAttribs
}

object SubmitButton
{ /** Factory apply method for submit input HTML element. */
  def apply(nameAttStr: String, valueStr: String = "Submit", otherAttribs: RArr[HAtt] = RArr()): SubmitButton =
    new SubmitButton(nameAttStr, valueStr, otherAttribs)
}