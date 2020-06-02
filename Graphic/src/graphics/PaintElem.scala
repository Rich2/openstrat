/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A GraphicElem is either an element that can be rendered to a display or printed. */
trait PaintElem extends GraphicElem
{
  /** Renders this functional immutable Graphic PaintElem, using the imperative methods of the abstract [[ostrat.pCanv.CanvasPlatform]] interface. */
  def rendToCanvas(cp: pCanv.CanvasPlatform): Unit

  /** Translate geometric transformation. */
  def slate(offset: Vec2): PaintElem

  /** Translate geometric transformation. */
  def slate(xOffset: Double, yOffset: Double): PaintElem

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  def scale(operand: Double): PaintElem

  /** Mirror, reflection transformation across the line x = xOffset, which is parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): PaintElem

  /** Mirror, reflection transformation across the line y = yOffset, which is parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): PaintElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorX: PaintElem

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  def mirrorY: PaintElem

  def prolign(matrix: ProlignMatrix): PaintElem

  /** Rotates 90 degrees or Pi/2 radians anticlockwise. */
  def rotate90: PaintElem
  
  /** Rotates 180 degrees or Pi radians. */  
  def rotate180: PaintElem

  /** Rotates 90 degrees or Pi/2 radians clockwise. */
  def rotate270: GraphicElem

  override def rotateRadians(radians: Double): PaintElem
}

/** Companion object for PaintElem contains various implicit instances for the transformation type classes. */
object PaintElem
{ implicit val slateImplicit: Slate[PaintElem] = (obj: PaintElem, offset: Vec2) => obj.slate(offset)
  implicit val scaleImplicit: Scale[PaintElem] = (obj: PaintElem, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[PaintElem] = (obj: PaintElem, radians: Double) => obj.rotateRadians(radians)
  
  implicit val mirrorAxisImplicit: MirrorAxis[PaintElem] = new MirrorAxis[PaintElem]
  { /** Reflect, mirror across a line parallel to the X axis. */
    override def mirrorXOffset(obj: PaintElem, yOffset: Double): PaintElem = obj.mirrorXOffset(yOffset)

    /** Reflect, mirror across a line parallel to the Y axis. */
    override def mirrorYOffset(obj: PaintElem, xOffset: Double): PaintElem = obj.mirrorYOffset(xOffset)
  }

  implicit val prolignImplicit: Prolign[PaintElem] = (obj, matrix) => obj.prolign(matrix)
}

/** Trait to be removed. */
trait PaintElemOld extends GraphicElemOld with PaintElem
{ type SimerT <: PaintElemOld

  override def slate(offset: Vec2): SimerT

  /** Translate geometric transformation. */
  override def slate(xOffset: Double, yOffset: Double): SimerT

  def scale(operand: Double): SimerT

  def mirrorYOffset(xOffset: Double): SimerT

  def mirrorXOffset(yOffset: Double): SimerT

  override def mirrorX: SimerT

  override def mirrorY: SimerT

  override def rotate90: SimerT
  override def rotate180: SimerT
  override def rotate270: SimerT

  override def rotateRadians(radians: Double): SimerT
  //def prolign(matrix: Any): AlignT

  
}
