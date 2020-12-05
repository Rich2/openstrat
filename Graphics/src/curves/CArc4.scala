/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Yet another implementation of a circular arc. Similar to CArcOld but has the addition of a roation counter to allow rotation deltas greater than
 * 360 degrees and less than - 360 degrees. */
class CArc4 private(val xStart: Double, val yStart: Double, val xCen: Double, val yCen: Double, val xEnd: Double, val yEnd: Double,
  val counter: Int) extends CurveSeg
{
  /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed. */
  override def draw(lineColour: Colour, lineWidth: Double): GraphicElem = ???

  /** Translate 2D geometric transformation on this GeomElem. The Return type will be narrowed in sub traits and  classes. */
  override def slate(offset: Vec2Like): GeomElem = ???

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def slate(xOffset: Double, yOffset: Double): GeomElem = ???

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): GeomElem = ???

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: GeomElem = ???

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: GeomElem = ???

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): GeomElem = ???

  /** Rotation 2D geometric transformation on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: Angle): GeomElem = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a GeomElem. The return type will be narrowed in sub classes and
   * traits. */
  override def reflect(lineLike: LineLike): GeomElem = ???

  /** XY scaling 2D geometric transformation on a GeomElem. This allows different scaling factors across X and Y dimensions. The return type will be
   * narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): GeomElem = ???

  /** Shear 2D geometric transformation along the X Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def xShear(operand: Double): GeomElem = ???

  /** Shear 2D geometric transformation along the Y Axis on a GeomElem. The return type will be narrowed in sub classes and traits. */
  override def yShear(operand: Double): GeomElem = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}
