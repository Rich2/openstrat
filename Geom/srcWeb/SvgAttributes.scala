/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** SVG ViewBox. */
case class ViewBox(minX: Double, minY: Double, width: Double, height: Double) extends XmlAtt
{ override def name: String = "viewBox"
  override def valueStr: String = minX.str -- minY.str -- width.str -- height.str
}

/** XML attribute for width. */
/*class WidthAtt(val dblValue: Double) extends XAttNumeric
{ override def name: String = "width"
}

object WidthAtt
{
  def altMeth(inp: Double): WidthAtt = new WidthAtt(inp)
}*/



case class WidthAtt(valueStr: String) extends XmlAtt
{ override def name: String = "width"
}

object WidthAtt{
  def apply(inp: Double): WidthAtt = new WidthAtt(inp.toString)
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
case class XAttrib(valueStr: String) extends XmlAtt
{ override def name = "x"
}

object XAttrib
{ def apply(inp: Double): XAttrib = new XAttrib(inp.toString)
}

/** XML attribute for y posn. */
case class YAttrib(valueStr: String) extends XmlAtt
{ override def name = "y"
}

object YAttrib
{ def apply(inp: Double): YAttrib = new YAttrib(inp.toString)
}