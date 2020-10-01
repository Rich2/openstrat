/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** GraphicSimple is a non compound graphic element that can be rendered to a display (or printed) or is an active element in a display, but can't be
 *  both that require a [[GraphicCompound]]. */
trait GraphicSimple extends GraphicElem
{
  /** Translate geometric transformation. */
  override def slate(offset: Vec2): GraphicSimple

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): GraphicSimple

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): GraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: GraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: GraphicSimple

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  override def reflectXParallel(yOffset: Double): GraphicSimple

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  override def reflectYParallel(xOffset: Double): GraphicSimple

  override def prolign(matrix: ProlignMatrix): GraphicSimple

  override def rotateRadians(radians: Double): GraphicSimple

  override def reflect(line: Line): GraphicSimple

  override def xyScale(xOperand: Double, yOperand: Double): GraphicSimple
}

/** Companion object for the [[GraphicSimple]] trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicSimple
{
  implicit val slateImplicit: Slate[GraphicSimple] = (obj: GraphicSimple, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[GraphicSimple] = (obj: GraphicSimple, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[GraphicSimple] = (obj: GraphicSimple, radians: Double) => obj.rotateRadians(radians)

  implicit val reflectAxisImplicit: ReflectAxes[GraphicSimple] = new ReflectAxes[GraphicSimple]
  { /** Reflect, mirror across the X axis. */
    override def negYT(obj: GraphicSimple): GraphicSimple = obj.negY

    /** Reflect, mirror across the Y axis. */
    override def negXT(obj: GraphicSimple): GraphicSimple = obj.negX
  }

  implicit val reflectAxisOffsetImplicit: ReflectAxesOffset[GraphicSimple] = new ReflectAxesOffset[GraphicSimple]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def reflectXOffsetT(obj: GraphicSimple, yOffset: Double): GraphicSimple = obj.reflectXParallel(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def reflectYOffsetT(obj: GraphicSimple, xOffset: Double): GraphicSimple = obj.reflectYParallel(xOffset)
  }

  implicit val prolignImplicit: Prolign[GraphicSimple] = (obj, matrix) => obj.prolign(matrix)
}
