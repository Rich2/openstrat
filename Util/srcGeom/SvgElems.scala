/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** An SVG element. */
trait SvgElem extends XmlMulti

case class SvgSvgElem(contents: RArr[XCon], attribs: RArr[XmlAtt]) extends SvgElem
{ override def tag: String = "svg"  
}

object SvgSvgElem
{ def apply(minX: Double, minY: Double, width: Double, height: Double, contents: XCon*): SvgSvgElem =
  new SvgSvgElem(contents.toArr, RArr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height)))

  def apply(minX: Double, minY: Double, width: Double, height: Double, arr: RArr[XCon]): SvgSvgElem =
    new SvgSvgElem(arr, RArr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height)))
}

case class SvgCircle(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "circle"  
}

case class SvgEllipse(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "ellipse"
}

case class SvgPolygon(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "polygon"
}

case class SvgRect(attribs: RArr[XmlAtt], contents: RArr[XCon] = RArr()) extends SvgElem
{ override def tag: String = "rect"
}