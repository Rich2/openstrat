/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

case class ViewPort(minX: Double, minY: Double, width: Double, height: Double) extends XmlAtt
{
  override def name: String = ???

  override def valueStr: String = ???
}

case class WidthAtt(value: Double) extends XmlAtt
{ override def name: String = "width"
  override def valueStr: String = value.toString
}

case class HeighAtt(value: Double) extends XmlAtt
{ override def name: String = "height"
  override def valueStr: String = value.toString
}