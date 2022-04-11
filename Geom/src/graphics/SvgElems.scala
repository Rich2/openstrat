/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** An SVG element. */
trait SvgElem extends XmlElem

case class SvgSvgElem(contents: Arr[XCon], attribs: Arr[XmlAtt]) extends SvgElem
{ override def tag: String = "svg"  
}

object SvgSvgElem
{ def apply(minX: Double, minY: Double, width: Double, height: Double, contents: XCon*): SvgSvgElem =
  new SvgSvgElem(contents.toArr, Arr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height)))

  def apply(minX: Double, minY: Double, width: Double, height: Double, arr: Arr[XCon]): SvgSvgElem =
    new SvgSvgElem(arr, Arr(WidthAtt(width), HeightAtt(height), ViewBox(minX, minY, width, height)))
}

case class SvgCircle(attribs: Arr[XmlAtt], contents: Arr[XCon] = Arr()) extends SvgElem
{ override def tag: String = "circle"  
}

case class SvgEllipse(attribs: Arr[XmlAtt], contents: Arr[XCon] = Arr()) extends SvgElem
{ override def tag: String = "ellipse"
}

case class SvgPolygon(attribs: Arr[XmlAtt], contents: Arr[XCon] = Arr()) extends SvgElem
{ override def tag: String = "polygon"
}

case class SvgRect(attribs: Arr[XmlAtt], contents: Arr[XCon] = Arr()) extends SvgElem
{ override def tag: String = "rect"
}