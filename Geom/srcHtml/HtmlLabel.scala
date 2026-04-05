/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import scala.reflect.ClassTag

/** HTML label element. */
class HtmlLabel(val fieldName: String, val label: String) extends HtmlInedit
{ override def tagName: String = "label"
  override def attribs: RArr[XAtt] = RArr(ForAtt(fieldName))
  override def contents: RArr[XCon] = RArr(label)
}

/** The for attribute used in HTML labels. */
case class ForAtt(valueStr: String) extends XAttSimple
{ override def name: String = "for"
}