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
  final def svgInline: String = SvgSvgElem(shape.boundingRect.minX, shape.boundingRect.minY, shape.boundingRect.width, shape.boundingRect.height,
    svgJustElem).out(0, 0, 150)
  def svgOut(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = svgJustElem.out(indent, linePosn, lineLen)
  final def svgJustElem: SvgElem = svgElem(shape.boundingRect)
  def svgElem(bounds: BoundingRect): SvgElem
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

    def svgInline(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String =
    { val br = thisArr.foldLeft(thisArr.head.shape.boundingRect)(_ || _.shape.boundingRect)
      SvgSvgElem(br.minX, br.minY, br.width, br.height, thisArr.map(_.svgElem(br))).out(indent, linePosn, lineLen)
    }
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