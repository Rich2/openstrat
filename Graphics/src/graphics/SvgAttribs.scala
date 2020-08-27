/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

case class FillAttrib(colour: Colour) extends Attrib
{ override def name: String = "fill"
  override def valueStr: String = colour.svgStr
}

case class StrokeWidthAttrib(lineWidth: Double) extends Attrib
{ override def name: String = "stroke-width"
  override def valueStr: String = lineWidth.toString
}

case class StrokeAttrib(colour: Colour) extends Attrib
{ override def name: String = "stroke"
  override def valueStr: String = colour.svgStr
}

case class SvgAttrib(vec2s: Vec2sLike) extends Attrib
{ override def name: String = "points"
  override def valueStr: String = ???
}