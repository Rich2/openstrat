/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour.Black

trait GraphicFacet extends Any
{ def attribs: RArr[XAtt]
}

/** A fill graphic. */
trait FillFacet extends Any with GraphicFacet

/** Starting off with simplified. Radial Gradient. Will expand later. */
case class FillRadial(cenColour: Colour, outerColour: Colour) extends FillFacet
{ override def attribs: RArr[XAtt] = RArr()
}

/** An interactive Shape that the user can interact with. */
case class ShapeActive(id: Any) extends GraphicFacet
{ override def attribs: RArr[XAtt] = RArr()
}

trait CurveFacet extends GraphicFacet

case class DrawFacet(colour: Colour = Black, width: Double = 2.0) extends CurveFacet
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(width)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(colour)
  override def attribs: RArr[XAtt] = RArr(strokeWidthAttrib, strokeAttrib)
}

case class TextFacet(str: String, sizeRatio: Double, colour: Colour, textAlign: TextAlign = CenAlign, baseLine: BaseLine = BaseLine.Middle,
  minSize: Double = 4) extends GraphicFacet
{ def attribs: RArr[XAtt] = RArr()
}