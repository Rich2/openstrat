/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** SVG ViewBox. */
case class ViewBox(minX: Double, minY: Double, width: Double, height: Double) extends XmlAtt
{ override def name: String = "viewBox"
  override def valueStr: String = minX.str -- minY.str -- width.str -- height.str
}

case class WidthAtt(valueStr: String) extends XmlAtt
{ override def name: String = "width"
}

object WidthAtt
{ def apply(inp: Double): WidthAtt = new WidthAtt(inp.toString)
}

/** XML attribute for height. */
case class HeightAtt(valueStr: String) extends  XmlAtt
{ override def name: String = "height"
}

object HeightAtt
{ def apply(inp: Double): HeightAtt = new HeightAtt(inp.toString)
}

case class SvgRotate(degrees: Double, x: Double, y: Double) extends XmlAtt
{ override def name: String = "transform"
  override def valueStr: String = "rotate" + RArr(degrees, x, y).mkString(" ").enParenth
}

/** XML attribute for x posn. */
case class XXmlAtt(valueStr: String) extends XmlAtt
{ override def name = "x"
}

object XXmlAtt
{ def apply(inp: Double): XXmlAtt = new XXmlAtt(inp.toString)
}

/** XML attribute for y posn. */
case class YXmlAtt(valueStr: String) extends XmlAtt
{ override def name = "y"
}

object YXmlAtt
{ def apply(inp: Double): YXmlAtt = new YXmlAtt(inp.toString)
}

object CentreBlockAtt extends ClassAtt("centreBlock")

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