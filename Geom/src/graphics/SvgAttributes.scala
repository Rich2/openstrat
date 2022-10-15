/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** SVG ViewBox. */
case class ViewBox(minX: Double, minY: Double, width: Double, height: Double) extends XmlAtt
{ override def name: String = "viewBox"
  override def valueStr: String = minX.str -- minY.str -- width.str -- height.str
}

/** XML attribute ofr width. */
case class WidthAtt(value: Double) extends XANumeric
{ override def name: String = "width"
}

/** XML attribute for height. */
case class HeightAtt(value: Double) extends  XANumeric
{ override def name: String = "height"
}

case class SvgRotate(degrees: Double, x: Double, y: Double) extends XmlAtt
{ override def name: String = "transform"
  override def valueStr: String = "rotate" + RArr(degrees, x, y).mkString(" ").enParenth
}

/** XML attribute for x posn. */
case class XAttrib(value: Double) extends XANumeric
{ override def name = "x"
}

/** XML attribute for y posn. */
case class YAttrib(value: Double) extends XANumeric
{ override def name = "y"
}