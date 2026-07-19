/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import scala.reflect.ClassTag

/** HTML label element. */
class LabelHtml(val contents: RArr[XCon], val forStr: String, val otherAtts: RArr[HAtt]) extends HtmlInedit
{ override def tagName: String = "label"
  override def attribs: RArr[HAtt] = ForAtt(forStr) %: otherAtts
}

object LabelHtml
{
  def apply(labelStr: String, forStr: String, otherAtts: HAtt*): LabelHtml = new LabelHtml(RArr(labelStr), forStr, otherAtts.toRArr)
}

/** The for attribute used in HTML labels. */
case class ForAtt(valueStr: String) extends XAttShort
{ override def name: String = "for"
}