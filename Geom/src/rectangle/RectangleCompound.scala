/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._

/** A compound graphic for rectangles. */
trait RectangleCompound extends PolygonCompound with RectangleGraphic
{
  override def attribs: Arr[XmlAtt] = ???

  override def svgStr: String = ???

  override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.negY.slateXY(0, bounds.minY + bounds.maxY).
    attribs ++ facets.flatMap(_.attribs))

  /** Translate geometric transformation. */
  override def slateXY(xDelta: Double, yDelta: Double): RectangleCompound =
    RectangleCompound(shape.slateXY(xDelta, yDelta), facets, children.SlateXY(xDelta, yDelta))

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): RectangleCompound = RectangleCompound(shape.scale(operand), facets, children.scale(operand))
  
  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: RectangleCompound = RectangleCompound(shape.negY, facets, children.negY)

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: RectangleCompound = RectangleCompound(shape.negX, facets, children.negX)

  override def prolign(matrix: ProlignMatrix): RectangleCompound = RectangleCompound(shape.prolign(matrix), facets, children.prolign(matrix))

  override def rotate90: RectangleCompound = ???

  override def rotate(angle: AngleVec): RectangleCompound = RectangleCompound(shape.rotate(angle), facets, children.rotate(angle))

  override def reflect(lineLike: LineLike): RectangleCompound = ???

  override def scaleXY(xOperand: Double, yOperand: Double): RectangleCompound = ???
}

/** Companion object for RectangleCompound. Conatains the [[RectangleCompound.RectangleCompoundImp]] implementatin class for the general case of
 * Rectangles and an apply factor method that delegats to it. */
object RectangleCompound
{
  def apply(shape: Rectangle, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) : RectangleCompound =
    new RectangleCompoundImp(shape, facets, children)

  case class RectangleCompoundImp(shape: Rectangle, facets: Arr[GraphicFacet], children: Arr[GraphicElem] = Arr()) extends RectangleCompound with
    AxisFree
  {
    override type ThisT = RectangleCompoundImp
    override def attribs: Arr[XmlAtt] = ???

    override def svgStr: String = ???

    override def svgElem(bounds: BoundingRect): SvgRect = SvgRect(shape.negY.slateXY(0, bounds.minY + bounds.maxY).
      attribs ++ facets.flatMap(_.attribs))

    /** Translate geometric transformation. */
    override def slateXY(xDelta: Double, yDelta: Double): RectangleCompoundImp =
      RectangleCompoundImp(shape.slateXY(xDelta, yDelta), facets, children.SlateXY(xDelta, yDelta))

    /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
     * Squares. Use the xyScale method for differential scaling. */
    override def scale(operand: Double): RectangleCompoundImp = RectangleCompoundImp(shape.scale(operand), facets, children.scale(operand))


    override def prolign(matrix: ProlignMatrix): RectangleCompoundImp = RectangleCompoundImp(shape.prolign(matrix), facets, children.prolign(matrix))

    override def rotate(angle: AngleVec): RectangleCompoundImp = RectangleCompoundImp(shape.rotate(angle), facets, children.rotate(angle))

    override def reflect(lineLike: LineLike): RectangleCompoundImp = ???

    override def scaleXY(xOperand: Double, yOperand: Double): RectangleCompoundImp = ???

    override def shearX(operand: Double): PolygonCompound = ???

    override def shearY(operand: Double): PolygonCompound = ???
  }
}