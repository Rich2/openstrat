/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import ostrat.pWeb.SvgElem
import pgui._

/** 2D graphic that draws an arc of an ellipse. The trait has 2 implementations, [[CArcdraw]], a cirular arc draw and the general case is implemented
 * with [[EArcDraw.EArcDrawImp]]. */
trait EArcDraw extends CurveSegDraw with CanvElem
{
  override def curveSeg: EArc
  def xCen: Double = curveSeg.cenX
  def yCen: Double = curveSeg.cenY
  def cen: Pt2 = curveSeg.cen

  /** Translate 2D geometric transformation on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits / classes. */
  override def slateXY(xDelta: Double, yDelta: Double): EArcDraw = EArcDraw(curveSeg.slateXY(xDelta, yDelta), colour, lineWidth)

  /** Uniform scaling 2D geometric transformation on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits / classes.
   * The scale name was chosen for this operation as it is normally the desired operation and preserves [[Circle]]s and [[Square]]s. Use the xyScale
   * method for differential scaling on the X and Y axes. */
  override def scale(operand: Double): EArcDraw = EArcDraw(curveSeg.scale(operand), colour, lineWidth)

  /** Mirror, reflection 2D geometric transformation across the X axis on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negY: EArcDraw = EArcDraw(curveSeg.negY, colour, lineWidth)

  /** Mirror, reflection 2D geometric transformation across the X axis on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in
   * sub traits / classes. */
  override def negX: EArcDraw = EArcDraw(curveSeg.negX, colour, lineWidth)

  /** 2D geometric transformation using a [[ProlignMatrix]] on a EArcDraw, returns a EArcDraw. The Return type will be narrowed in sub traits /
   * classes. */
  override def prolign(matrix: ProlignMatrix): EArcDraw = EArcDraw(curveSeg.prolign(matrix), colour, lineWidth)

  override def rotate90: EArcDraw = EArcDraw(curveSeg.rotate90, colour, lineWidth)
  override def rotate180: EArcDraw = EArcDraw(curveSeg.rotate180, colour, lineWidth)
  override def rotate270: EArcDraw = EArcDraw(curveSeg.rotate270, colour, lineWidth)

  /** Rotation 2D geometric transformation on a EArcDraw taking the rotation as a scalar measured in radians, returns a EArcDraw. The Return
   * type will be narrowed in sub traits / classes. */
  override def rotate(angle: AngleVec): EArcDraw = EArcDraw(curveSeg.rotate(angle), colour, lineWidth)

  /** Reflect 2D geometric transformation across a line, line segment or ray on a EArcDraw, returns a EArcDraw. The Return type will be narrowed
   * in sub traits / classes. */
  override def reflect(lineLike: LineLike): EArcDraw = EArcDraw(curveSeg.reflect(lineLike), colour, lineWidth)

  /** XY scaling 2D geometric transformation on a EArcDraw, returns a GrpahicElem. This allows different scaling factors across X and Y dimensions.
   * The return type will be narrowed in sub classes and traits. */
  override def scaleXY(xOperand: Double, yOperand: Double): EArcDraw = ???

  /** Shear 2D geometric transformation along the X Axis on a EArcDraw, returns a EArcDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def shearX(operand: Double): EArcDraw = ???

  /** Shear 2D geometric transformation along the Y Axis on a EArcDraw, returns a EArcDraw. The return type will be narrowed in sub classes and
   * traits. */
  override def shearY(operand: Double): EArcDraw = ???
}

/** Companion object ofr the EArcDraw trait, contins the general implementation class [[EArcDrawImp]] and an apply factor method that delegates to
 * [[EArcDrawImp]]. */
object EArcDraw
{
  def apply(arc: EArc, colour: Colour, lineWidth: Double): EArcDraw = EArcDrawImp(arc, colour, lineWidth)

  case class EArcDrawImp(curveSeg: EArc, colour: Colour, lineWidth: Double) extends EArcDraw
  {
    /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
    override def rendToCanvas(cp: CanvasPlatform): Unit =  cp.eArcDraw(this)

    override def svgElems: RArr[SvgElem] = ???
  }
}