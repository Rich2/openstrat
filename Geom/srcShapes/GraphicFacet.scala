/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour.Black

trait GraphicFacet extends Any
{ def attribs: RArr[XmlAtt]
}

trait FillFacet extends Any with GraphicFacet

/** Starting off with simplified. Radial Gradient. Will expand later. */
case class FillRadial(cenColour: Colour, outerColour: Colour) extends FillFacet
{ override def attribs: RArr[XmlAtt] = RArr()
}

case class ShapeActive(id: Any) extends GraphicFacet
{ override def attribs: RArr[XmlAtt] = RArr()
}

trait CurveFacet extends GraphicFacet

case class DrawFacet(colour: Colour = Black, width: Double = 2.0) extends CurveFacet
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(width)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(colour)
  override def attribs: RArr[XmlAtt] = RArr(strokeWidthAttrib, strokeAttrib)
}

case class TextFacet(str: String, colour: Colour, textAlign: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Middle) extends GraphicFacet
{ def attribs: RArr[XmlAtt] = RArr()
}