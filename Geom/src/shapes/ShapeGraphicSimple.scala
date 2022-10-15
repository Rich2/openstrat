/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pWeb._

trait ShapeGraphicSimple extends ShapeGraphic with GraphicSimple
{
  def nonShapeAttribs: RArr[XmlAtt]
  final override def attribs: RArr[XmlAtt] = shapeAttribs ++ nonShapeAttribs

  /** Translate geometric transformation. */
  override def slateXY(xDelta: Double, yDelta: Double): ShapeGraphicSimple

  /** Uniform scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves Circles and
   * Squares. Use the xyScale method for differential scaling. */
  override def scale(operand: Double): ShapeGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negY: ShapeGraphicSimple

  /** Mirror, reflection transformation across the X axis. This method has been left abstract in GeomElemNew to allow the return type to be narrowed
   * in sub classes. */
  override def negX: ShapeGraphicSimple

  /** Rotate 90 degrees anti clockwise or rotate 270 degrees clockwise 2D geometric transformation on a ShapeGraphicSimple, returns a ShapeGraphicSimple. The
   * return type will be narrowed in sub traits / classes. */
  override def rotate90: ShapeGraphicSimple

  /** Rotate 180 degrees 2D geometric transformation on a ShapeGraphicSimple, returns a ShapeGraphicSimple. The return type will be narrowed in sub traits /
   * classes. */
  override def rotate180: ShapeGraphicSimple

  /** Rotate 270 degrees anti clockwise or rotate 90 degrees clockwise 2D geometric transformation on a ShapeGraphicSimple, returns a ShapeGraphicSimple. The
   * return type will be narrowed in sub traits / classes. */
  override def rotate270: ShapeGraphicSimple

  override def prolign(matrix: ProlignMatrix): ShapeGraphicSimple

  override def rotate(angle: AngleVec): ShapeGraphicSimple

  override def reflect(lineLike: LineLike): ShapeGraphicSimple

  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGraphicSimple
}