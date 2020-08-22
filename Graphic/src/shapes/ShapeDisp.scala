/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pXml._

/** A shape based graphic. */
trait ShapeDisp extends DisplayElem
{ def shape: Shape
  def facets: Arr[ShapeFacet]
  def attribs: Arr[Attrib]
  def svgStr: String
}

trait ShapeFacet
{ def attribs: Arr[Attrib]
}

class FillColour(value: Int) extends ShapeFacet
{ def colour: Colour = new Colour(value)
  override def attribs: Arr[Attrib] = Arr(FillAttrib(colour))
  def fillAttrib: FillAttrib = FillAttrib(colour)
}

trait CurveFacet extends ShapeFacet