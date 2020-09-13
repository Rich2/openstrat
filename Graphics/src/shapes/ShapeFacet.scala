/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._, Colour.Black

trait ShapeFacet
{ def attribs: Arr[XmlAtt]
}

case class FillColour(colour: Colour) extends ShapeFacet
{
  override def attribs: Arr[XmlAtt] = Arr(FillAttrib(colour))
  def fillAttrib: FillAttrib = FillAttrib(colour)
}

/** Starting off with simplified. Radial Gradient. Will expand later. */
case class FillRadial(cenColour: Colour, outerColour: Colour) extends ShapeFacet
{ override def attribs: Arr[XmlAtt] = Arr()
}

case class ShapeActive(id: Any) extends ShapeFacet
{ override def attribs: Arr[XmlAtt] = Arr()
}

trait CurveFacet extends ShapeFacet

case class CurveDraw(width: Double = 2.0, colour: Colour = Black) extends CurveFacet
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(width)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(colour)
  override def attribs: Arr[XmlAtt] = Arr(strokeWidthAttrib, strokeAttrib)
}
