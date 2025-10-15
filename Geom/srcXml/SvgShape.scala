/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait SvgShape extends SvgOwnLine
{ /** AttribsIn does not require a fill attribute if none if the shape graphic has no fill. */
  def attribsIn: RArr[XAtt]

  override def attribs: RArr[XAtt] = ife(attribsIn.exists(_.name == "fill"), attribsIn, attribsIn +% FillAttrib.none)
}

/** An SVG Circle element. */
class SvgCircle(val attribsIn: RArr[XAtt], val contents: RArr[XConCompound] = RArr()) extends SvgShape
{ override def tagName: String = "circle"
}

object SvgCircle
{ /** Factory apply method for SVG circle class. */
  def apply(attribsIn: RArr[XAtt], contents: RArr[XConCompound] = RArr()): SvgCircle = new SvgCircle(attribsIn, contents)
}

/** An SVG Ellipse element. */
class SvgEllipse(val attribsIn: RArr[XAtt], val contents: RArr[XConCompound] = RArr()) extends SvgShape
{ override def tagName: String = "ellipse"
}

object SvgEllipse
{ /** Factory apply method for SVG ellipse class. */
  def apply(attribsIn: RArr[XAtt], contents: RArr[XConCompound] = RArr()): SvgEllipse = new SvgEllipse(attribsIn, contents)
}

/** An SVG Polygon element. */
class SvgPolygon(val attribsIn: RArr[XAtt], val contents: RArr[XConCompound] = RArr()) extends SvgShape
{ override def tagName: String = "polygon"
}

object SvgPolygon
{ /** Factory apply method for SVG polygon class. */
  def apply(attribs: RArr[XAtt], contents: RArr[XConCompound] = RArr()): SvgPolygon = new SvgPolygon(attribs, contents)
}

/** An SVG Rect element. */
class SvgRect(val attribsIn: RArr[XAtt], val contents: RArr[XConCompound] = RArr()) extends SvgShape
{ override def tagName: String = "rect"
}

object SvgRect
{ /** Factory apply method for SVG RECT rectangle class. */
  def apply(attribs: RArr[XAtt], contents: RArr[XConCompound] = RArr()): SvgRect = new SvgRect(attribs, contents)
}