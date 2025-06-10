/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, Colour.Black, math.{Pi, sqrt}, pgui.*

/** The Ellipse trait can either be implemented as an [[Ellipse]] class or as a [[Circle]]. Which also fulfills the Ellipse interface. The factory methods in
 * the Ellipse companion object return [Ellipse]]. */
trait Ellipse extends EllipseBased, ShapeCentred
{ override def cenX: Double = p1X \/ p3X
  override def cenY: Double = p1Y \/ p3Y
  final override def cen: Pt2 = Pt2(cenX, cenY)
  final override def p0: Pt2 = Pt2(p0X, p0Y)
  final override def p1: Pt2 = Pt2(p1X, p1Y)
  override def p2X: Double = 2 * cenX - p0X
  override def p2Y: Double = 2 * cenY - p0Y
  final override def p2: Pt2 = Pt2(p2X, p2Y)
  final override def p3: Pt2 = Pt2(p3X, p3Y)
  override def radius1: Double = p3.distTo(p1) / 2
  override def radius2: Double = cen.distTo(p0)
  
  def axis1: LSeg2 = LSeg2(p3X, p3Y, p1X, p1Y)
  def axis2: LSeg2 = LSeg2(p2X, p2Y, p0X, p0Y)
  
  def axes: LSeg2Arr = LSeg2Arr(axis1, axis2)

  def axis1IsMajor: Boolean = radius1 >= radius2

  /** The major radius of this ellipse, often referred to as a in maths. */
  def a: Double = ife(axis1IsMajor, radius1, radius2)

  /** The major radius of this ellipse,often referred to as b in maths. */
  def b: Double = ife(axis1IsMajor, radius2, radius1)

  /** The first focus point, placed towards p1 or p0. */
  def f1: Pt2 = ife(axis1IsMajor, cen + cenP1 * e, cen + cenP0 * e)

  /** The second focus point, placed towards p3 or p2. */
  def f2: Pt2 = ife(axis1IsMajor, cen - cenP1 * e, cen - cenP0 * e)

  /** Linear eccentricity. The distance from the centre to the focus */
  def c: Double = (a.squared - b.squared).sqrt

  /** The h value of this ellipse. */
  def h: Double

/** The alignment angle of the ellipse to axis 1. */
  def alignAngle: Angle

  /** Eccentricity of ellipse. */
  def e: Double = (1 - b.squared / a.squared).sqrt

  def area: Double
  def cxAttrib: XmlAtt = XmlAtt("cx", cenX.toString)
  def cyAttrib: XmlAtt = XmlAtt("cy", cenY.toString)
  def rxAttrib: XmlAtt = XmlAtt("rx", radius1.toString)
  def ryAttrib: XmlAtt = XmlAtt("ry", radius2.toString)
  def attribs: RArr[XmlAtt] = RArr(cxAttrib, cyAttrib, rxAttrib, ryAttrib)
  def boundingRect: Rect

  def fTrans(f: Pt2 => Pt2): Ellipse = Ellipse.cenAxes1axes4(f(cen), f(p1), f(p0))

  override def slate(operand: VecPt2): Ellipse
  override def slate(xDelta: Double, yDelta: Double): Ellipse
  override def slateX(xOperand: Double): Ellipse
  override def slateY(yOperand: Double): Ellipse
  override def scale(operand: Double): Ellipse
  override def prolign(matrix: AxlignMatrix): Ellipse = fTrans(_.prolign(matrix))
  override def scaleXY(xOperand: Double, yOperand: Double): Ellipse = fTrans(_.xyScale(xOperand, yOperand))
  override def rotate90: Ellipse = fTrans(_.rotate90)
  override def rotate180: Ellipse = fTrans(_.rotate180)
  override def rotate270: Ellipse = fTrans(_.rotate270)
  override def rotate(rotation: AngleVec): Ellipse = fTrans(_.rotate(rotation))
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

  def textArrows(arrowColour: Colour = Black): RArr[GraphicSvgElem] =
  { val tcen: RArr[GraphicSvgElem] = cen.textArrow("cen", colour = arrowColour)
    val tp0: RArr[GraphicSvgElem] = p0.textArrowToward(cen, "p0", colour = arrowColour)
    val tp1: RArr[GraphicSvgElem] = p1.textArrowToward(cen, "p1", colour = arrowColour)
    val tp2: RArr[GraphicSvgElem] = p2.textArrowToward(cen, "p2", colour = arrowColour)
    val tp3: RArr[GraphicSvgElem] = p3.textArrowToward(cen, "p3", colour = arrowColour)
    val tf1: RArr[GraphicSvgElem] = f1.textArrow("f1", colour = arrowColour)
    val tf2: RArr[GraphicSvgElem] = f2.textArrow("f2", colour = arrowColour)
    tcen ++ tp0 ++ tp1 ++ tp2 ++ tp3 ++ tf1 ++ tf2
  }

  def toPolygon: PolygonGen =
  { val topN: Int = (c / 3).toInt.max(3)
    val rightN: Int = (b / (3 * e)).toInt.max(3)
    val newLen: Int = (topN + rightN - 2) * 4
    val newArray: Array[Double]  = new Array[Double](newLen)
    var i = 0
    var topY = 0.0
    while (i <= topN)
    { val x = -c + i * 2 * c / topN
      val xi = i * 2
      newArray(xi) = x
      val bottomEnd = (topN * 2 + rightN - 2) * 2
      val xni = bottomEnd - i * 2 - 2
      newArray(xni) = x
      topY = ((1 - x.squared / a.squared) * b.squared).sqrt
      newArray(xi + 1) = topY
      newArray(xni + 1) = -topY
      i += 1
    }

    i = 1
    while (i < rightN)
    { val y = topY - 2 * topY * i / rightN
      val xi = (topN + i) * 2
      val x = ((1 - y.squared / b.squared) * a.squared).sqrt
      newArray(xi) = x
      newArray(xi + 1) = y
      val xni = newLen - i * 2
      newArray(xni) = -x
      newArray(xni + 1) = y
      i += 1
    }
    val poly0 = new PolygonGen(newArray)
    val aa = alignAngle
    debvar(aa)
    val rot = aa.rotationFrom0
    debvar(rot)
    poly0.rotate(rot)//.slate(cenX, cenY)
  }
}

/** Companion object for the Ellipse trait contains the EllipseImp implementation class and factory methods for Ellipse that delegate to EllipseImp. */
object Ellipse
{ /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseGen]] class. */
  def apply(radius1: Double, radius0: Double, cenX: Double, cenY: Double): Ellipselign =
    new EllipselignGen(cenX, cenY + radius0, cenX + radius1, cenY,  cenX - radius1, cenY)

  /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseGen]] class. */
  def apply(radius1: Double, radius0: Double, cen: Pt2 = Pt2Z): Ellipselign =
    new EllipselignGen(cen.x, cen.y + radius0, cen.x + radius1, cen.y, cen.x - radius0, cen.y)

  def apply(xRadius: Double, yRadius: Double, rotation: AngleVec): EllipseGen = new EllipseGen(0, yRadius, xRadius, 0, -xRadius, 0).rotate(rotation)

  def apply(xRadius: Double, yRadius: Double, rotation: AngleVec, cen: Pt2): EllipseGen =
    new EllipseGen(0, yRadius, xRadius, 0, -xRadius, 0).rotate(rotation).slate(cen)

  def apply(xRadius: Double, yRadius: Double, rotation: AngleVec, cenX: Double, cenY: Double): EllipseGen =
  { val e1 = new EllipseGen(0, yRadius, xRadius, 0, -xRadius, 0)
    val e2 = e1.rotate(rotation)
    e2.slate(cenX, cenY)
  }

  /** Factory method for an Ellipse. The apply factory methods in this Ellipse companion object default to an [[EllipseGen]] class. */
  def diameter(diameter1: Double, diameter0: Double, cen: Pt2 = Pt2Z): Ellipse =
    new EllipseGen(cen.x, cen.y + diameter0 / 2, cen.x + diameter1 / 2, cen.y, cen.x - diameter0 / 2, cen.y)

  /** Factory method that creates an ellipse from the centre point, axes point 1 and axes point 4. */
  def cenAxes1axes4(cen: Pt2, axes1: Pt2, axes0: Pt2): EllipseGen = new EllipseGen(axes0.x, axes0.y, axes1.x, axes1.y, 2 * cen.x - axes1.x, 2 * cen.y - axes1.y)

  def cenAxes1Radius2(xCen: Double, yCen: Double, xAxes1: Double, yAxes1: Double, radius2: Double): Ellipse = ??? // new EllipseGen(xCen, yCen, xAxes1, yAxes1, radius2)

  given slateEv: Slate2[Ellipse] = new Slate2[Ellipse]
  { override def slate(obj: Ellipse, operand: VecPt2): Ellipse = cenAxes1axes4(obj.cen.slate(operand), obj.p1.slate(operand), obj.p0.slate(operand))

    override def slateXY(obj: Ellipse, xOperand: Double, yOperand: Double): Ellipse =
      cenAxes1axes4(obj.cen.slate(xOperand, yOperand), obj.p1.slate(xOperand, yOperand), obj.p0.slate(xOperand, yOperand))

    override def slateX(obj: Ellipse, xOperand: Double): Ellipse =
      cenAxes1axes4(obj.cen.slateX(xOperand), obj.p1.slateX(xOperand), obj.p0.slateX(xOperand))

    override def slateY(obj: Ellipse, yOperand: Double): Ellipse =
      cenAxes1axes4(obj.cen.slateY(yOperand), obj.p1.slateY(yOperand), obj.p0.slateY(yOperand))
  }
  
  given scaleEv: Scale[Ellipse] = (obj: Ellipse, operand: Double) => obj.scale(operand)

  given rotateEv: Rotate[Ellipse] =
    (ell: Ellipse, angle: AngleVec) => Ellipse.cenAxes1axes4(ell.cen.rotate(angle), ell.p1.rotate(angle), ell.p0.rotate(angle))

  given prolignEv: Prolign[Ellipse] = (obj, matrix) => obj.prolign(matrix)

  given xyScaleEv: ScaleXY[Ellipse] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  given reflectAxesEv: TransAxes[Ellipse] = new TransAxes[Ellipse]
  { override def negYT(obj: Ellipse): Ellipse = obj.negY
    override def negXT(obj: Ellipse): Ellipse = obj.negX
    override def rotate90(obj: Ellipse): Ellipse = obj.rotate90
    override def rotate180(obj: Ellipse): Ellipse = obj.rotate180
    override def rotate270(obj: Ellipse): Ellipse = obj.rotate270
  }

  given shearEv: Shear[Ellipse] = new Shear[Ellipse]
  { override def shearXT(obj: Ellipse, yFactor: Double): Ellipse = obj.shearX(yFactor)
    override def shearYT(obj: Ellipse, xFactor: Double): Ellipse = obj.shearY(xFactor)
  }
}

/** The implementation class for Ellipses that are not Circles. The Ellipse is encoded as 3 [[Pt2]]s or 6 scalars, although it is possible to encode an
 * ellipse with 5 scalars. Encoding the Ellipse this way greatly helps human visualisation of transformations upon an ellipse. */
final class EllipseGen(val p0X: Double, val p0Y: Double, val p1X: Double, val p1Y: Double, val p3X: Double, val p3Y: Double) extends Ellipse, AxisFree
{ override type ThisT = EllipseGen
  override def area: Double = Pi * radius1 * radius2
  override def e: Double = sqrt(a.squared - b.squared) / a
  override def h: Double = (a - b).squared / (a + b).squared

  def boundingRect: Rect =
  { val xd0: Double = radius1.squared * (alignAngle.cos).squared + radius2.squared * (alignAngle.sin).squared
    val xd = xd0.sqrt
    val yd0: Double = radius1.squared * (alignAngle.sin).squared + radius2.squared * (alignAngle.cos).squared
    val yd = yd0.sqrt
    Rect(2 * xd, 2 * yd, cenX, cenY)
  }

  override def alignAngle: Angle = cen.angleTo(p1)

  def s0Angle = alignAngle.p90

  override def slate(operand: VecPt2): EllipseGen =
    new EllipseGen(p0X + operand.x, p0Y + operand.y, p1X + operand.x, p1Y + operand.y, p3X + operand.x, p3Y + operand.y)

  override def slate(xOperand: Double, yOperand: Double): EllipseGen =
    new EllipseGen(p0X + xOperand, p0Y + yOperand, p1X + xOperand, p1Y + yOperand, p3X + xOperand, p3Y + yOperand)

  override def slateX(xOperand: Double): EllipseGen = new EllipseGen(p0X + xOperand, p0Y, p1X + xOperand, p1Y, p3X + xOperand, p3Y)
  override def slateY(yOperand: Double): EllipseGen = new EllipseGen(p0X, p0Y + yOperand, p1X, p1Y + yOperand, p3X, p3Y + yOperand)
  override def scale(operand: Double): EllipseGen = new EllipseGen(p0X * operand, p0Y * operand, p1X * operand, p1Y * operand, p3X * operand, p3Y * operand)
  override def reflect(lineLike: LineLike): EllipseGen = ??? // EllipseGen.cenAxes1Axes4(cen.reflect(lineLike), p1.reflect(lineLike), p0.reflect(lineLike))
  override def rotate(rotation: AngleVec): EllipseGen = EllipseGen.p013(p0.rotate(rotation), p1.rotate(rotation), p3.rotate(rotation))
  override def shearX(operand: Double): EllipseGen = ??? //EllipseGen.cenAxes1Axes4(cen.xShear(operand), p1.xShear(operand), p0.xShear(operand))
  override def shearY(operand: Double): EllipseGen = ??? //EllipseGen.cenAxes1Axes4(cen.yShear(operand), p1.yShear(operand), p0.yShear(operand))
  override def ptInside(pt: Pt2): Boolean = ???
}

/** Companion object for the EllipseImp class, contains factory methods. */
object EllipseGen
{
  def apply(xRadius: Double, yRadius: Double, rotation: AngleVec, cen: Pt2 = Pt2Z): EllipseGen =
    new EllipseGen(0, yRadius, xRadius, 0, -xRadius, 0).rotate(rotation).slate(cen)

  def apply(xRadius: Double, yRadius: Double, rotation: AngleVec, cenX: Double, cenY: Double): EllipseGen =
    new EllipseGen(0, yRadius, xRadius, 0, -xRadius, 0).rotate(rotation).slate(cenX, cenY)

  def p013(p0: Pt2, p1: Pt2, p3: Pt2): EllipseGen = new EllipseGen(p0.x, p0.y, p1.x, p1.y, p3.x, p3.y)
  //def cenAxes1Axes4(cen: Pt2, pAxes1: Pt2, pAxes4: Pt2): EllipseGen = new EllipseGen(cen.x, cen.y, pAxes1.x, pAxes1.y, cen.distTo(pAxes4))
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

/** Compound graphic trait for an ellipse. Note [[CircleCompound]] is a subclass of this trait. */
trait EllipseCompound extends ShapeCompound, EllipseGraphic, Aff2Elem
{ override def mainSvgElem: SvgElem = SvgEllipse(attribs)
  override def slate(operand: VecPt2): EllipseCompound
  override def slate(xDelta: Double, yDelta: Double): EllipseCompound
  override def slateX(xDelta: Double): EllipseCompound
  override def slateY(yDelta: Double): EllipseCompound
  override def scale(operand: Double): EllipseCompound
  override def negY: EllipseCompound
  override def negX: EllipseCompound
  override def rotate90: EllipseCompound
  override def rotate180: EllipseCompound
  override def rotate270: EllipseCompound
  override def prolign(matrix: AxlignMatrix): EllipseCompound
  override def rotate(rotation: AngleVec): EllipseCompound
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

    override def slateX(xOperand: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.slateX(xOperand), facets, children.slateX(xOperand))
    override def slateY(yOperand: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.slateY(yOperand), facets, children.slateY(yOperand))
    override def scale(operand: Double): EllipseCompoundImplement = EllipseCompoundImplement(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: AxlignMatrix): EllipseCompoundImplement = EllipseCompoundImplement(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(rotation: AngleVec): EllipseCompoundImplement = EllipseCompoundImplement(shape.rotate(rotation), facets, children.rotate(rotation))
    override def reflect(lineLike: LineLike): EllipseCompoundImplement = ??? //EllipseGenGraphic(shape.reflect(line), facets, children.reflect(line))
    override def scaleXY(xOperand: Double, yOperand: Double): EllipseCompoundImplement = ???
    override def shearX(operand: Double): EllipseCompoundImplement = ???
    override def shearY(operand: Double): EllipseCompoundImplement = ???
    //override def slateTo(newCen: Pt2): EllipseCompoundImplement = ???
  }
}