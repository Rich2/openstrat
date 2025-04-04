/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, Colour.Black, math.{Pi, sqrt}, pgui.*

/** The Ellipse trait can either be implemented as an [[Ellipse]] class or as a [[Circle]]. Which also fulfills the Ellipse interface. The factory methods in
 * the Ellipse companion object return [Ellipse]]. */
trait Ellipse extends EllipseBased with ShapeCentred
{ final override def cen: Pt2 = Pt2(cenX, cenY)

  final def axesPt1: Pt2 = Pt2(axesPt1x, axesPt1y)
  final def axesPt2: Pt2 = Pt2(axesPt2x, axesPt2y)
  override def axesPt3: Pt2 = Pt2(axesPt3x, axesPt3y)

  /** The major radius of this ellipse, often referred to as a in maths. */
  def rMajor: Double

  /** The major radius of this ellipse,often referred to as b in maths. */
  def rMinor: Double

  /** The h value of this ellipse. */
  def h: Double

/** The alignment angle of the ellipse to axis 1. */
  def alignAngle: Angle

  /** Eccentricity of ellipse. */
  def e: Double

  def area: Double
  def cxAttrib: XmlAtt = XmlAtt("cx", cenX.toString)
  def cyAttrib: XmlAtt = XmlAtt("cy", cenY.toString)
  def rxAttrib: XmlAtt = XmlAtt("rx", radius1.toString)
  def ryAttrib: XmlAtt = XmlAtt("ry", radius2.toString)
  def attribs: RArr[XmlAtt] = RArr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: Rect

  def fTrans(f: Pt2 => Pt2): Ellipse = Ellipse.cenAxes1axes4(f(cen), f(axesPt1), f(axesPt4))

  override def slate(operand: VecPt2): Ellipse
  override def slate(xDelta: Double, yDelta: Double): Ellipse
  override def scale(operand: Double): Ellipse
  override def prolign(matrix: ProlignMatrix): Ellipse = fTrans(_.prolign(matrix))
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = fTrans(_.xyScale(xOperand, yOperand))
  override def rotate90: Ellipse = fTrans(_.rotate90)
  override def rotate180: Ellipse = fTrans(_.rotate180)
  override def rotate270: Ellipse = fTrans(_.rotate270)
  override def rotate(angle: AngleVec): Ellipse = fTrans(_.rotate(angle))
  override def negY: Ellipse
  override def negX: Ellipse
  override def reflect(lineLike: LineLike): Ellipse = fTrans(_.reflect(lineLike))
  override def shearX(operand: Double): Ellipse = fTrans(_.xShear(operand))
  override def shearY(operand: Double): Ellipse = fTrans(_.yShear(operand))
  override def boundingWidth: Double = ???
  override def boundingHeight: Double = ???
  override def fill(fillfacet: FillFacet): EllipseFill = EllipseFill(this, fillfacet)
  override def fillInt(intValue: Int): EllipseFill = EllipseFill(this, Colour(intValue))
  override def draw(lineWidth: Double, lineColour: Colour = Black): EllipseDraw = EllipseDraw(this, lineColour, lineWidth)

  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): EllipseCompound =
    EllipseCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)))

  override def fillActive(fillColour: Colour, pointerID: Any): EllipseCompound = EllipseCompound(this, RArr(fillColour), RArr(EllipseActive(this, pointerID)))

  override def fillActiveText(fillColour: Colour, pointerEv: Any, str: String, fontRatio: Double, fontColour: Colour, align: TextAlign,
    baseLine: BaseLine, minSize: Double): EllipseCompound =
    EllipseCompound(this, RArr(fillColour, TextFacet(str, fontRatio, fontColour, align, baseLine, minSize)))
}

/** Companion object for the Ellipse trait contains the EllipseImp implementation class and factory methods for Ellipse that delegate to EllipseImp. */
object Ellipse
{ /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseImp]] class. */
  def apply(radius1: Double, radius0: Double, cenX: Double, cenY: Double): Ellipse = new EllipseImp(cenX, cenY, cenX + radius1, cenY,  radius0)

  /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseImp]] class. */
  def apply(radius1: Double, radius0: Double, cen: Pt2 = Pt2Z): Ellipse = new EllipseImp(cen.x, cen.y, cen.x + radius1, cen.y, radius0)

  /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseImp]] class. */
  def diameter(diameter1: Double, diameter0: Double, cen: Pt2 = Pt2Z): Ellipse =
    new EllipseImp(cen.x, cen.y, cen.x + diameter1 / 2, cen.y, diameter0 / 2)

  /** Factory method that creates an ellipse from the centre point, axes point 1 and axes point 4. */
  def cenAxes1axes4(cen: Pt2, axes1: Pt2, axes4: Pt2): EllipseImp =
  { val radius0: Double = cen.distTo(axes4)
    new EllipseImp(cen.x, cen.y, axes1.x, axes1.y, radius0)
  }

  def cenAxes1Radius2(xCen: Double, yCen: Double, xAxes1: Double, yAxes1: Double, radius2: Double): Ellipse = new EllipseImp(xCen, yCen, xAxes1, yAxes1, radius2)

  implicit val slateImplicit: SlateXY[Ellipse] = (ell, dx, dy) => cenAxes1axes4(ell.cen.slate(dx, dy), ell.axesPt1.slate(dx, dy), ell.axesPt4.slate(dx, dy))
  implicit val scaleImplicit: Scale[Ellipse] = (obj: Ellipse, operand: Double) => obj.scale(operand)

  implicit val rotateImplicit: Rotate[Ellipse] =
    (ell: Ellipse, angle: AngleVec) => Ellipse.cenAxes1axes4(ell.cen.rotate(angle), ell.axesPt1.rotate(angle), ell.axesPt4.rotate(angle))

  implicit val prolignImplicit: Prolign[Ellipse] = (obj, matrix) => obj.prolign(matrix)

  implicit val xyScaleImplicit: ScaleXY[Ellipse] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  implicit val reflectAxesImplicit: TransAxes[Ellipse] = new TransAxes[Ellipse]
  { override def negYT(obj: Ellipse): Ellipse = obj.negY
    override def negXT(obj: Ellipse): Ellipse = obj.negX
    override def rotate90(obj: Ellipse): Ellipse = obj.rotate90
    override def rotate180(obj: Ellipse): Ellipse = obj.rotate180
    override def rotate270(obj: Ellipse): Ellipse = obj.rotate270
  }

  implicit val shearImplicit: Shear[Ellipse] = new Shear[Ellipse]
  { override def shearXT(obj: Ellipse, yFactor: Double): Ellipse = obj.shearX(yFactor)
    override def shearYT(obj: Ellipse, xFactor: Double): Ellipse = obj.shearY(xFactor)
  }

  /** The implementation class for Ellipses that are not Circles. The Ellipse is encoded as 3 [[Pt2]]s or 6 scalars, although it is possible to encode an
   * ellipse with 5 scalars. Encoding the Ellipse this way greatly helps human visualisation of transformations upon an ellipse. */
  final case class EllipseImp(cenX: Double, cenY: Double, axesPt1x: Double, axesPt1y: Double, radius2: Double) extends Ellipse, AxisFree
  { override type ThisT = EllipseImp
    override def axesPt2x: Double = 2 * cenX - axesPt4x
    override def axesPt2y: Double = 2 * cenY - axesPt4y
    override def axesPt3x: Double = 2 * cenX - axesPt1x
    override def axesPt3y: Double = 2 * cenY - axesPt1y
    override def axesPt4: Pt2 = cen + s0Angle.toVec2(radius2)
    override def axesPt4x: Double = axesPt4.x
    override def axesPt4y: Double = axesPt1.y
    override def radius1: Double = cen.distTo(axesPt1)
    override def cenP1: Vec2 = cen >> axesPt1
    override def cenP2: Vec2 = cen >> axesPt2
    override def cenP3: Vec2 = cen >> axesPt3
    override def cenP4: Vec2 = cen >> axesPt4
    override def rMajor: Double = radius1.max(radius2)
    override def rMinor: Double = radius1.min(radius2)
    override def area: Double = Pi * radius1 * radius2
    override def e: Double = sqrt(rMajor.squared - rMinor.squared) / rMajor
    override def h: Double = (rMajor - rMinor).squared / (rMajor + rMinor).squared

    def boundingRect: Rect =
    { val xd0: Double = radius1.squared * (alignAngle.cos).squared + radius2.squared * (alignAngle.sin).squared
      val xd = xd0.sqrt
      val yd0: Double = radius1.squared * (alignAngle.sin).squared + radius2.squared * (alignAngle.cos).squared
      val yd = yd0.sqrt
      Rect(2 * xd, 2 * yd, cenX, cenY)
    }

    override def alignAngle: Angle = cen.angleTo(axesPt1)
    def s0Angle = alignAngle.p90

    override def slate(operand: VecPt2): EllipseImp = EllipseImp(cenX + operand.x, cenY * operand.y, axesPt1x + operand.x, axesPt1y + operand.y, radius2)
    override def slate(xDelta: Double, yDelta: Double): EllipseImp = EllipseImp(cenX + xDelta, cenY + yDelta, axesPt1x + xDelta, axesPt1y + yDelta, radius2)
    override def scale(operand: Double): EllipseImp = EllipseImp(cenX * operand, cenY * operand, axesPt1x * operand, axesPt1y * operand, radius2 * operand)
    override def reflect(lineLike: LineLike): EllipseImp = EllipseImp.cenAxes1Axes4(cen.reflect(lineLike), axesPt1.reflect(lineLike), axesPt4.reflect(lineLike))
    override def rotate(angle: AngleVec): EllipseImp = ???
    override def shearX(operand: Double): EllipseImp = EllipseImp.cenAxes1Axes4(cen.xShear(operand), axesPt1.xShear(operand), axesPt4.xShear(operand))
    override def shearY(operand: Double): EllipseImp = EllipseImp.cenAxes1Axes4(cen.yShear(operand), axesPt1.yShear(operand), axesPt4.yShear(operand))

    override def ptInside(pt: Pt2): Boolean = ???
  }

  /** Companion object for the EllipseImp class, contains factory methods. */
  object EllipseImp
  {
    def apply(cen: Pt2, pAxes1: Pt2, radius2: Double): EllipseImp = new EllipseImp(cen.x, cen.y, pAxes1.x, pAxes1.y, radius2)
    def cenAxes1Axes4(cen: Pt2, pAxes1: Pt2, pAxes4: Pt2): EllipseImp = new EllipseImp(cen.x, cen.y, pAxes1.x, pAxes1.y, cen.distTo(pAxes4))
  }
}

/** An Ellipse based Graphic. The Ellipse can be defined as a circle. */
trait EllipseGraphic extends ShapeGraphicCentred
{ override def shape: Ellipse
}

/** A Simple circle based graphic. Not sure if this trait is useful. */
trait EllipseGraphicSimple extends EllipseGraphic with ShapeGraphicSimple with SimilarAffPreserve
{ type ThisT <: EllipseGraphicSimple
  type ThisT2 <: EllipseGraphicSimple
  override def svgElem: SvgElem = SvgEllipse(attribs)
}

/** A simple single colour fill of a circle graphic. */
trait EllipseFill extends EllipseGraphicSimple with ShapeFill with CanvElem
{ type ThisT <: EllipseFill
  type ThisT2 = EllipseFill
  override def fTrans2(f: Pt2 => Pt2): ThisT2 = EllipseFill(shape.fTrans(f), fillFacet)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseFill(this)

  override def toDraw(lineWidth: Double = 2, newColour: Colour = Black): EllipseDraw = shape.draw(lineWidth, newColour)
}

/** Companion object for the EllipseFill class. */
object EllipseFill
{
  def apply(shape: Ellipse, fillFacet: FillFacet): EllipseFill = EllipseFillImp(shape, fillFacet)

  /** A simple single colour fill of an ellipse graphic. */
  final case class EllipseFillImp(shape: Ellipse, fillFacet: FillFacet) extends EllipseFill
  { type ThisT = EllipseFill

    override def ptsTrans(f: Pt2 => Pt2): ThisT = EllipseFill(shape.fTrans(f), fillFacet)
    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseFill(this)
  }
}

trait EllipseDraw extends EllipseGraphicSimple with ShapeDraw with CanvElem
{
  type ThisT <: EllipseDraw
  type ThisT2 = EllipseDraw
  override def fTrans2(f: Pt2 => Pt2): EllipseDraw = EllipseDraw(shape.fTrans(f), lineColour, lineWidth)
}

object EllipseDraw
{
  def apply(shape: Ellipse, lineColour: Colour = Black, lineWidth: Double = 2.0): EllipseDraw = EllipseDrawImp(shape, lineColour, lineWidth)

  /** Implementation class for [[EllipseDraw]]. */
  final case class EllipseDrawImp(shape: Ellipse, lineColour: Colour = Black, lineWidth: Double = 2.0) extends EllipseDraw
  { type ThisT = EllipseDraw

    override def ptsTrans(f: Pt2 => Pt2): EllipseDraw = EllipseDrawImp(shape.fTrans(f), lineColour, lineWidth)

    override def rendToCanvas(cp: CanvasPlatform): Unit = cp.ellipseDraw(this)
  }
}

trait EllipseActive extends EllipseGraphicSimple with GraphicClickable
{ type ThisT <: EllipseActive
  type ThisT2 = EllipseActive
  override def fTrans2(f: Pt2 => Pt2): EllipseActive = EllipseActive(shape.fTrans(f), pointerId)
  final override def nonShapeAttribs: RArr[XmlAtt] = RArr()
}

object EllipseActive
{
  def apply(shape: Ellipse, pointerId: Any): EllipseActive = EllipseActiveImp(shape, pointerId)

  /** Implementation class for [[EllipseDraw]]. */
  final case class EllipseActiveImp(shape: Ellipse, pointerId: Any) extends EllipseActive
  { type ThisT = EllipseActive

    override def ptInside(pt: Pt2): Boolean = shape.ptInside(pt)

    /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
    override def rendToCanvas(cp: CanvasPlatform): Unit = ???

    override def ptsTrans(f: Pt2 => Pt2): EllipseActive = EllipseActiveImp(shape.fTrans(f), pointerId)
  }
}


/** Compound graphic trait for an ellipse. Note [[CircleCompound]] is a sub class of this trait. */
trait EllipseCompound extends ShapeCompound with EllipseGraphic
{ override def mainSvgElem: SvgElem = SvgEllipse(attribs)
  override def slate(operand: VecPt2): EllipseCompound
  override def slate(xDelta: Double, yDelta: Double): EllipseCompound
  override def scale(operand: Double): EllipseCompound
  override def negY: EllipseCompound
  override def negX: EllipseCompound
  override def prolign(matrix: ProlignMatrix): EllipseCompound
  override def rotate(angle: AngleVec): EllipseCompound
  override def reflect(lineLike: LineLike): EllipseCompound
  override def scaleXY(xOperand: Double, yOperand: Double): EllipseCompound = EllipseCompound(shape.scaleXY(xOperand, yOperand), facets, children)
  override def shearX(operand: Double): EllipseCompound = EllipseCompound(shape.shearX(operand), facets, children)
  override def shearY(operand: Double): EllipseCompound = EllipseCompound(shape.shearY(operand), facets, children)
  override def addChildren(newChildren: Arr[Graphic2Elem]): EllipseCompound = EllipseCompound(shape, facets, children ++ newChildren)
}

/** Companion object for the [[EllipseCompound]] trait contains factory apply method and implicit instances for the 2D geometric transformations.  */
object EllipseCompound
{
  def apply(shape: Ellipse, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()): EllipseCompound =
    new EllipseCompoundImplement(shape, facets, children)

  /** The implementation class for a general ellipse that is not defined as a circle. Most users will not need to interact with this class. It been
   * created non anonymously because the type might be useful for certain specialised performance usecases. */
  final case class EllipseCompoundImplement(shape: Ellipse, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends
    EllipseCompound with AxisFree
  {
    override type ThisT = EllipseCompoundImplement
    override def mainSvgElem: SvgEllipse = SvgEllipse(attribs)
    /** Return type narrowed to [[SvgEllipse]] from [[SvgElem]] */
    /*    override def svgElem: SvgEllipse =
        { val newEllipse = shape.negY.slateXY(0, boundingRect.bottom + boundingRect.top)
          val newAtts = newEllipse.attribs
          val atts2 = if (shape.alignAngle == 0.degs) newAtts else newAtts +% SvgRotate(- shape.alignAngle.degs, shape.cenX, shape.cenY)
          SvgEllipse(atts2 ++ facets.flatMap(_.attribs))
        }*/

    override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach {
      case c: Colour => cp.ellipseFill(EllipseFill(shape, c))
      //case CurveDraw(w, c) => cp.circleDraw(shape, w, c)
      //case fr: FillRadial => cp.circleFillRadial(shape, fr)*/
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    override def slate(operand: VecPt2): EllipseCompoundImplement = EllipseCompoundImplement(shape.slate(operand), facets, children.slate(operand))

    override def slate(xDelta: Double, yDelta: Double): EllipseCompoundImplement =
      EllipseCompoundImplement(shape.slate(xDelta, yDelta), facets, children.slate(xDelta, yDelta))

    override def scale(operand: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: ProlignMatrix): EllipseCompoundImplement = EllipseCompoundImplement(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(angle: AngleVec): EllipseCompoundImplement = EllipseCompoundImplement(shape.rotate(angle), facets, children.rotate(angle))
    override def reflect(lineLike: LineLike): EllipseCompoundImplement = ??? //EllipseGenGraphic(shape.reflect(line), facets, children.reflect(line))
    override def scaleXY(xOperand: Double, yOperand: Double): EllipseCompoundImplement = ???
    override def shearX(operand: Double): EllipseCompoundImplement = ???
    override def shearY(operand: Double): EllipseCompoundImplement = ???
    //override def slateTo(newCen: Pt2): EllipseCompoundImplement = ???
  }
}