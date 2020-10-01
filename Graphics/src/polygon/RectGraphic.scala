/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is a graphic based on a Rect shape. A rectangle aligned to the X and Y axes. */
trait RectGraphic extends RectangleGraphic
{ override def shape: Rect
}

/** This is a compound graphic based on a Rect shape. A rectangle aligned to the X and Y axes.  */
case class RectCompound(shape: Rect, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends RectGraphic with RectangleCompound
{
  //override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???

  override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.reflectX.slate(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))

  /** Translate geometric transformation. */
  override def slate(offset: Vec2): RectCompound = RectCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): RectCompound =
    RectCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectCompound = RectCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectX: RectCompound = RectCompound(shape.reflectX, facets, children.reflectX)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def reflectY: RectCompound = RectCompound(shape.reflectY, facets, children.reflectY)

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXParallel(yOffset: Double): RectCompound = RectCompound(shape.reflectXParallel(yOffset), facets, children.reflectXOffset(yOffset))

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYParallel(xOffset: Double): RectCompound = RectCompound(shape.reflectYParallel(xOffset), facets, children.reflectYOffset(xOffset))

  override def prolign(matrix: ProlignMatrix): RectCompound = RectCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def xyScale(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.xyScale(xOperand, yOperand), facets, children.xyScale(xOperand, yOperand) )
}