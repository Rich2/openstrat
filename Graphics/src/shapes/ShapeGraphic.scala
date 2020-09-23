/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

/** A shape based graphic. */
trait ShapeGraphic extends GraphicElem
{ def shape: Shape
  def attribs: Arr[XmlAtt]
  def svgStr: String
  def shapeAttribs: Arr[XmlAtt] = shape.attribs
}

trait ShapeGraphicSimple extends ShapeGraphic with GraphicSimple
{
  def nonShapeAttribs: Arr[XmlAtt]
  final override def attribs: Arr[XmlAtt] = shapeAttribs ++ nonShapeAttribs
}

/** Companion object for the ShapeGraphic class. */
object ShapeGraphic
{
  implicit class ArrImplicit(val thisArr: Arr[ShapeGraphic])
  {
    def svgList: String = thisArr.foldLeft("")(_ + "\n" + _.svgStr)
  }
}

/** A simple plain colour fill graphic. */
trait ShapeFill extends ShapeGraphicSimple
{ /** The colour of this fill graphic. */
  def colour: Colour
  
  /** The fill attribute for SVG. */
  def fillAttrib: FillAttrib = FillAttrib(colour)
  override def nonShapeAttribs: Arr[XmlAtt] = Arr(fillAttrib)
}

/** A simple no compound graphic that draws a shape. The line has a sinlge width and colour. */
trait ShapeDraw extends ShapeGraphicSimple
{ /** The line width of this draw graphic */
  def lineWidth: Double
  
  /** The line colour of this draw graphic. */
  def lineColour: Colour
  
  def strokeWidthAttrib: StrokeWidthAttrib = StrokeWidthAttrib(lineWidth)
  def strokeAttrib: StrokeAttrib = StrokeAttrib(lineColour)
  def nonShapeAttribs: Arr[XmlAtt] = Arr(strokeWidthAttrib, strokeAttrib)
}