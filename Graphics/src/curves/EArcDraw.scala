/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import pCanv._

/** 2D graphic that draws an arc of an ellipse. The trait has 2 implementations, [[CArcdraw]], a cirular arc draw and the general case is implemented
 * with [[EArcDraw.EArcDrawImp]]. */
trait EArcDraw extends CurveSegDraw with CanvElem
{
  override def curveSeg: EArc
  def xCen: Double = curveSeg.xCen
  def yCen: Double = curveSeg.yCen
  def cen: Pt2 = curveSeg.cen

  /** Translate 2D geometric transformation on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits / classes. */
  override def xySlate(xOffset: Double, yOffset: Double): EArcDraw = ???

  /** Translate 2D geometric transformation on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits / classes. This
   * overload might be removeable in Scala 3, but is necessary for the time being die to type inference problems. */
  override def slate(offset: Vec2Like): EArcDraw = ???

  /** Uniform scaling 2D geometric transformation on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): EArcDraw = ???

  /** Mirror, reflection 2D geometric transformation across the X axis on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: EArcDraw = ???

  /** Mirror, reflection 2D geometric transformation across the X axis on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: EArcDraw = ???

  /** 2D geometric transformation using a [[ProlignMatrix]] on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): EArcDraw = ???

  /** Rotation 2D geometric transformation on a EArcDraw taking the rotation as a scalar measured in radians, returns a EArcDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): EArcDraw = ???

  /** Reflect 2D geometric transformation across a line, line segment or ray on a EArcDraw, returns a EArcDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): EArcDraw = ???

  /** XY scaling 2D geometric transformation on a EArcDraw, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def xyScale(xOperand: Double, yOperand: Double): EArcDraw = ???

  /** Shear 2D geometric transformation along the X Axis on a EArcDraw, returns a EArcDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def xShear(operand: Double): EArcDraw = ???

  /** Shear 2D geometric transformation along the Y Axis on a EArcDraw, returns a EArcDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def yShear(operand: Double): EArcDraw = ???

  override def canEqual(that: Any): Boolean = ???
}

/** Companion object ofr the EArcDraw trait, contins the general implementation class [[EArcDrawImp]] and an apply factor method that delegates to
 * [[EArcDrawImp]]. */
object EArcDraw
{
  def apply(arc: EArc, colour: Colour, lineWidth: Double): EArcDraw = EArcDrawImp(arc, colour, lineWidth)

  case class EArcDrawImp(curveSeg: EArc, colour: Colour, lineWidth: Double) extends EArcDraw
  {
    /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
    override def rendToCanvas(cp: CanvasPlatform): Unit =  { deb("Not implemented.")} //cp.eArcDraw(this)
  }
}