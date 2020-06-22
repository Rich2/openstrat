/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A DisplayElem is either an element that can be rendered to a display (or printed) or is an active element in a display or both. */
trait DisplayElem extends TransElem
{
  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pCanv.CanvasPlatform): Unit = {}
  
  /** Translate geometric transformation. */
  def slate(offset: Vec2): DisplayElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): DisplayElem

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): DisplayElem

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): DisplayElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): DisplayElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorX: DisplayElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorY: DisplayElem

  def prolign(matrix: ProlignMatrix): DisplayElem

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: DisplayElem

  /** Rotates 180 degrees or Pi radians. */
  def rotate180: DisplayElem

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: DisplayElem

  def rotateRadians(radians: Double): DisplayElem

  def mirror(line: LineSeg): DisplayElem

  override def scaleXY(xOperand: Double, yOperand: Double): DisplayElem
}

/** Companion object for the DisplayElem trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object DisplayElem
{
  implicit val slateImplicit: Slate[DisplayElem] = (obj: DisplayElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[DisplayElem] = (obj: DisplayElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[DisplayElem] = (obj: DisplayElem, radians: Double) => obj.rotateRadians(radians)

  implicit val mirrorAxisImplicit: MirrorAxis[DisplayElem] = new MirrorAxis[DisplayElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: DisplayElem, yOffset: Double): DisplayElem = obj.mirrorXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: DisplayElem, xOffset: Double): DisplayElem = obj.mirrorYOffset(xOffset)
  }

  implicit val rotateAxesImplicit: RotateAxes[DisplayElem] = new RotateAxes[DisplayElem]
  { /** Rotates object of type T, 90 degrees or Pi/2 radians anticlockwise. */
    override def rotateT90(obj: DisplayElem): DisplayElem = obj.rotate90

    /** Rotates object of type T, 180 degrees or Pi radians. */
    override def rotateT180(obj: DisplayElem): DisplayElem = obj.rotate180

    /** Rotates object of type T, 90 degrees or Pi/2 radians clockwise. */
    override def rotateT270(obj: DisplayElem): DisplayElem = obj.rotate270
  }

  implicit val prolignImplicit: Prolign[DisplayElem] = (obj, matrix) => obj.prolign(matrix)
}