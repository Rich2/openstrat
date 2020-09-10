/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** SVG ViewBox. */
case class ViewBox(minX: Double, minY: Double, width: Double, height: Double) extends XmlAtt
{ override def name: String = "viewBox"
  override def valueStr: String = minX.str -- minY.str -- width.str -- height.str
}

/** XML attribute ofr width. */
class WidthAtt(value: Double) extends XANumeric("width", value)
object WidthAtt
{ def apply(value: Double): WidthAtt = new WidthAtt(value)
}

/** XML attribute for height. */
class HeightAtt(value: Double) extends  XANumeric("height", value)
object HeightAtt
{ def apply(value: Double): HeightAtt = new HeightAtt(value)
}

case class SvgRotate(degrees: Double, x: Double, y: Double) extends XmlAtt
{ override def name: String = "transform"
  override def valueStr: String = "rotate" + Arr(degrees, x, y).mkString(" ").enParenth
}