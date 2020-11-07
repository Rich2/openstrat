/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** This is a compound graphic based on a Rect shape. A rectangle aligned to the X and Y axes.  */
case class RectCompound(shape: Rect, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends RectGraphic with RectangleCompound
{
  //override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???

  override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.negY.slate(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))

  /** Translate geometric transformation. */
  override def slate(offset: Vec2Like): RectCompound = RectCompound(shape.slate(offset), facets, children.slate(offset))

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): RectCompound =
    RectCompound(shape.slate(xOffset, yOffset), facets, children.slate(xOffset, yOffset))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectCompound = RectCompound(shape.scale(operand), facets, children.scale(operand))

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: RectCompound = RectCompound(shape.negY, facets, children.negY)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: RectCompound = RectCompound(shape.negX, facets, children.negX)

  override def prolign(matrix: ProlignMatrix): RectCompound = RectCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def xyScale(xOperand: Double, yOperand: Double): RectCompound =
    RectCompound(shape.xyScale(xOperand, yOperand), facets, children.xyScale(xOperand, yOperand) )

  override def slateTo(newCen: Pt2): RectCompound = RectCompound(shape.slateTo(newCen), facets, children.slate(cen.vecTo(newCen)))
}

object RectCompound
{
  implicit val slateImplicit: Slate[RectCompound] = (obj: RectCompound, offset: Vec2Like) => obj.slate(offset)
  implicit val scaleImplicit: Scale[RectCompound] = (obj: RectCompound, operand: Double) => obj.scale(operand)
  implicit val XYScaleImplicit: XYScale[RectCompound] = (obj, xOperand, yOperand) => obj.xyScale(xOperand, yOperand)
  implicit val prolignImplicit: Prolign[RectCompound] = (obj, matrix) => obj.prolign(matrix)
  implicit val slateToImplicit: SlateTo[RectCompound] = (obj: RectCompound, newCen: Pt2) => obj.slateTo(newCen)

  implicit val reflectAxesImplicit: ReflectAxes[RectCompound] = new ReflectAxes[RectCompound]
  { override def negYT(obj: RectCompound): RectCompound = obj.negY
    override def negXT(obj: RectCompound): RectCompound = obj.negX
  }
}