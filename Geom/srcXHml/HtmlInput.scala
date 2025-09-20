/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

case class ForAtt(valueStr: String) extends XAttSimple
{ override def name: String = "for"
}

class HtmlLabel(fieldName: String, label: String) extends HtmlInline
{ override def tag: String = "label"
  override def attribs: RArr[XAtt] = RArr(ForAtt(fieldName))
  override def contents: RArr[XCon] = RArr(label)
}

class HtmlInput(val idStr: String, valueStr: String) extends HtmlVoid
{ override def tag: String = "input"

  def idAtt: IdAtt = IdAtt(idStr)
  def typeAtt = TypeTextAtt
  def valueAtt = ValueAtt(valueStr)

  override def attribs: RArr[XAtt] = RArr(IdAtt(idStr), typeAtt, valueAtt)
}

/*class HtmlInput(override val attribs: RArr[XAtt]) extends HtmlVoid
{ override def tag: String = "input"
}*/

object HtmlInput{
  //def submit(label: String): HtmlInput = new HtmlInput(RArr(TypeSubmitAtt, ValueAtt(label)))
}