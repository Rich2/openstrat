/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** Base [[ShapeDraw]] trait for multiple geometries. */
trait ShapeGeomlessDraw
{
  /** The line width of this draw graphic */
  def lineWidth: Double

  /** The line colour of this draw graphic. */
  def lineColour: Colour
}

/** A simple no compound graphic that draws a shape. The line has a single width and colour. */
trait ShapeDraw extends ShapeGeomlessDraw, ShapeGraphicSimple
{ def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(lineWidth)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(lineColour)
  override def nonShapeAttribs: RArr[XmlAtt] = RArr(strokeWidthAttrib, strokeAttrib)
}

object ShapeDraw
{
  def apply(shape: Shape, colour: Colour, lineWidth: Double): ShapeDraw = ???
}

trait ShapeLen2Draw extends ShapeGeomlessDraw, ShapeLen2GraphicSimple