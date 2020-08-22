/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._, Colour.Black

/** A ShapeMember, a member of a ShapeDisp can either be a ShapeFacet or a [[ShapeDisp]]. */
sealed trait ShapeMember

/** A shape based graphic. Will probably change the name back to ShapeGraphic. */
trait ShapeDisp extends DisplayElem with ShapeMember
{ def shape: Shape
  def members: Arr[ShapeMember]
  def attribs: Arr[Attrib]
  def svgStr: String
}

trait ShapeFacet extends ShapeMember
{ def attribs: Arr[Attrib]
}

class FillColour(value: Int) extends ShapeFacet
{ def colour: Colour = new Colour(value)
  override def attribs: Arr[Attrib] = Arr(FillAttrib(colour))
  def fillAttrib: FillAttrib = FillAttrib(colour)
}

case class ShapeActive(id: Any) extends ShapeFacet
{ override def attribs: Arr[Attrib] = Arr()
}

trait CurveFacet extends ShapeFacet

case class CurveDraw(width: Double = 2.0, colour: Colour = Black) extends CurveFacet
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(width)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(colour)
  override def attribs: Arr[Attrib] = Arr(strokeWidthAttrib, strokeAttrib)
}