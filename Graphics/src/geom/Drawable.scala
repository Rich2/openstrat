/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import Colour.Black

/** A 2D geometric element that can be drawn producing a [[GraphicElem]]. */
trait Drawable extends GeomElem
{
  /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed.  */
  def draw(lineColour: Colour = Black, lineWidth: Double = 2): GraphicElem

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def xySlate(xOffset: Double, yOffset: Double): Drawable

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): Drawable

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: Drawable

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: Drawable

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): Drawable

  /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: AngleVec): Drawable

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  override def reflect(lineLike: LineLike): Drawable

  /** XY scaling 2D geometric transformation on a GeomElem. This allows different scaling factors across X and Y dimensions. The return type will be
   * narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): Drawable

  /** Shear 2D geometric transformation along the X Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): Drawable

  /** Shear 2D geometric transformation along the Y Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def yShear(operand: Double): Drawable
}

/** A 2D geometric element that can be drawn and filled producing [[GraphicElem]]s. */
trait Fillable extends Drawable
{
  def fill(fillColour: Colour): GraphicElem
  def fillHex(intValue: Int): GraphicElem
  def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): GraphicElem
}