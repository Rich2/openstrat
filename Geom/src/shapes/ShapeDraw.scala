/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A simple no compound graphic that draws a shape. The line has a single width and colour. */
trait ShapeDraw extends ShapeGraphicSimple
{ /** The line width of this draw graphic */
  def lineWidth: Double

  /** The line colour of this draw graphic. */
  def lineColour: Colour

  def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(lineWidth)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(lineColour)
  override def nonShapeAttribs: Arr[XmlAtt] = Arr(strokeWidthAttrib, strokeAttrib)
}

object ShapeDraw
{
  def apply(shape: Shape, colour: Colour, lineWidth: Double): ShapeDraw = ???
}