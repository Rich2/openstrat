/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** Yet another implementation of a circular arc. Similar to CArcOld but has the addition of a roation counter to allow rotation deltas greater than
 * 360 degrees and less than - 360 degrees. */
class CArc private(val xStart: Double, val yStart: Double, val xCen: Double, val yCen: Double, val xEnd: Double, val yEnd: Double,
  val counter: Int) extends EArc
{
  override def cen: Pt2 = ???

  /** Draws this geometric element to produce a [[GraphElem]] graphical element, tht can be displayed or printed. */
  override def draw(lineColour: Colour, lineWidth: Double): GraphicElem = ???

  /** Translate 2D geometric transformation. The Return type will be narrowed in sub traits. */
  override def slate(xOffset: Double, yOffset: Double): CArc = CArc(pStart.addXY(xOffset, yOffset), cen.addXY(xOffset, yOffset),
    pEnd.addXY(xOffset, yOffset), counter)

  /** Translate 2D geometric transformation on this CArc. The Return type will be narrowed in sub traits and  classes. */
  override def slate(offset: Vec2Like): CArc = CArc(pStart + offset, cen + offset, pEnd + offset, counter)

  /** Uniform 2D geometric scaling transformation. The scale name was chosen for this operation as it is normally the desired operation and preserves
   * [[Circle]]s and [[Square]]s. Use the xyScale method for differential scaling. The Return type will be narrowed in sub traits / classes. */
  override def scale(operand: Double): CArc = CArc(pStart * operand, cen * operand, pEnd * operand, counter)

  /** Mirror, reflection 2D geometric transformation across the X axis by negating y. The return type will be narrowed in sub traits / classes. */
  override def negY: CArc = ???

  /** Mirror, reflection 2D geometric transformation across the Y axis by negating X. The return type will be narrowed in sub traits / classes. */
  override def negX: CArc = ???

  /** 2D Transformation using a [[ProlignMatrix]]. The return type will be narrowed in sub classes / traits. */
  override def prolign(matrix: ProlignMatrix): CArc = ???

  /** Rotation 2D geometric transformation on a CArc. The return type will be narrowed in sub classes and traits. */
  override def rotate(angle: Angle): CArc = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a CArc. The return type will be narrowed in sub classes and
   * traits. */
  override def reflect(lineLike: LineLike): CArc3 = ???

  override def productArity: Int = ???

  override def productElement(n: Int): Any = ???

  override def canEqual(that: Any): Boolean = ???
}

object CArc
{
  def apply(pStart: Pt2, cen: Pt2, pEnd: Pt2, counter: Int): CArc = new CArc(pStart.x, pStart.y, cen.x, cen.y, pEnd.x, pEnd.y, counter)
}
