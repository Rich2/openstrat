/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

case class FillAttrib(valueStr: String) extends XmlAtt
{ override def name: String = "fill"
}

object FillAttrib
{ def apply(colour: Colour): FillAttrib = FillAttrib(colour.svgStr)
  val none: FillAttrib = FillAttrib("none")
}

case class StrokeWidthAttrib(lineWidth: Double) extends XmlAtt
{ override def name: String = "stroke-width"
  override def valueStr: String = lineWidth.toString
}

case class StrokeAttrib(colour: Colour) extends XmlAtt
{ override def name: String = "stroke"
  override def valueStr: String = colour.svgStr
}