/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

trait SvgElem extends XmlElem

case class SvgSvgElem(contents: Arr[XCon], attribs: Arr[XmlAtt]) extends SvgElem
{ override def tag: String = "svg"  
}

object SvgSvgElem
{ def apply(width: Double, height: Double, contents: XCon*): SvgSvgElem = new SvgSvgElem(contents.toArr, Arr(WidthAtt(width), HeighAtt(height)))
}

case class SvgCircle(attribs: Arr[XmlAtt], contents: Arr[XCon] = Arr()) extends SvgElem
{ override def tag: String = "circle"  
}