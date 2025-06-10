/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, math.Pi, Colour.Black, pgui.*

/** Circle class is defined by its centre and radius. It fulfills the interface for an Ellipse.
 *  @groupdesc EllipticalGroup Class members that treat this circle as a special case of an ellipse.
 *  @groupname EllipticalGroup Elliptical Members
 *  @groupprio EllipticalGroup 1010 */
final class Circle protected[geom](val radius: Double, override val cenX: Double, override val cenY: Double) extends Ellipselign, OrdinaledElem, AxisFree
{ type ThisT = Circle

  override def fTrans(f: Pt2 => Pt2): Circle =
  { val v1: Pt2 = cen.slateX(radius)
    val newV1: Pt2 = f(v1)
    val newCen = f(cen)
    val newRadius = newCen.distTo(newV1)
    Circle(newRadius, newCen)
  }

  /** Diameter of the circle. This has the same value as width, a property that hasn't been created yet. */
  @inline def diameter: Double = radius * 2

  override def area: Double = Pi * radius * radius
  override def e: Double = 0
  override def h: Double = 0
  override def boundingWidth: Double = diameter
  override def boundingHeight: Double = diameter

  override def slate(operand: VecPt2): Circle = Circle(radius, cenX + operand.x, cenY + operand.y)
  override def slate(xOperand: Double, yOperand: Double): Circle = Circle(radius, cenX + xOperand, cenY + yOperand)
  override def slateX(xOperand: Double): Circle = Circle(radius, cenX + xOperand, cenY)
  override def slateY(yOperand: Double): Circle = Circle(radius, cenX, cenY + yOperand)
  override def scale(operand: Double): Circle = Circle(radius * operand, cen.scale(operand))
  override def prolign(matrix: AxlignMatrix): Circle = fTrans(_.prolign(matrix))
  override def rotate(rotation: AngleVec): Circle = Circle(radius, cen.rotate(rotation))
  override def reflect(lineLike: LineLike): Circle = Circle(radius, cen.reflect(lineLike))
  override def boundingRect: Rect = Rect(diameter, diameter, cenX, cenY)
  override def fill(fillfacet: FillFacet): CircleFill = CircleFill(this, fillfacet)
  override def fillInt(intValue: Int): CircleFill = CircleFill(this, Colour(intValue))

  def fillRadial(cenColour: Colour, outerColour: Colour): EllipseCompound = EllipseCompound(this, RArr(FillRadial(cenColour, outerColour)), RArr())
  
  override def draw(lineWidth: Double = 2, lineColour: Colour = Colour.Black): CircleDraw = CircleDraw(this, lineWidth, lineColour)

  /** Returns a [[CircleCompound]] with a [[FillFacet]] and a [[DrawFact]]. */
  override def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2.0): EllipseCompound =
    EllipseCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)), RArr())

  override def fillActive(fillColour: Colour, pointerID: Any): EllipseCompound = EllipseCompound(this, RArr(fillColour), RArr(CircleActive(this, pointerID)))

  def fillActiveTextAbs(fillColour: Colour, pointerID: Any, str: String, fontSize: Double, fontColour: Colour = Black): EllipseCompound =
    EllipseCompound(this, RArr(fillColour), RArr(CircleActive(this, pointerID), TextFixed(str, fontSize, cen, fontColour)))

  def fillActiveTextlign(fillColour: Colour, pointerEv: Any, str: String, fontSize: Double, fontColour: Colour = Black, align: TextAlign = CenAlign):
  EllipseCompound = EllipseCompound(this, RArr(fillColour), RArr(CircleActive(this, pointerEv), Textlign(str, fontSize, cenDefault, fontColour, align)))

  def rAttrib: XmlAtt = XmlAtt("r", radius.toString)
  override def attribs: RArr[XmlAtt] = RArr(cxAttrib, cyAttrib, rAttrib)

  private def rr2: Double = diameter * 2.sqrt
  override def topRight: Pt2 = Pt2(rr2, rr2)
  override def bottomRight: Pt2 = Pt2(rr2, -rr2)
  override def bottomLeft: Pt2 = Pt2(-rr2, -rr2)
  override def topLeft: Pt2 = Pt2(-rr2, rr2)

  @inline override def radius1: Double = radius
  @inline override def radius2: Double = radius
  @inline override def a: Double = radius
  @inline override def b: Double = radius
  @inline override def c: Double = 0
  @inline override def f1: Pt2 = cen
  @inline override def f2: Pt2 = cen
  @inline override def xRadius: Double = radius
  @inline override def yRadius: Double = radius

  override def p1X: Double = cenX + radius
  override def p1Y: Double = cenY
  override def p2X: Double = cenX
  override def p2Y: Double = cenY - p0Y
  override def p3X: Double = cenX - radius
  override def p3Y: Double = cenY
  override def p0X: Double = cenX
  override def p0Y: Double = cenY + radius
  override def cenP0: Vec2 = Vec2(0, radius)
  override def cenP1: Vec2 = Vec2(radius, 0)
  override def cenP2: Vec2 = Vec2(0, -radius)
  override def cenP3: Vec2 = Vec2(-radius, 0)  

  /** Determines if the parameter point lies inside this [[Circle]]. */
  override def ptInside(pt: Pt2): Boolean = pt match
  { case Pt2(x, y) if x > cenX + radius | x < cenX - radius | y > cenY + radius | y < cenY - radius => false
    case Pt2(x, y) => radius >= ((x -cenX).squared + (y - cenY).squared).sqrt
  }
}

/** This is the companion object for the Circle case class. It provides factory methods for creating [[Circle]]s. */
object Circle extends ShapeIcon
{ override type ShapeT = Circle

  /** Factory apply method for creating a circle. The first parameter gives the radius of the circle. The default centre is at the origin. There is an apply
   * method name overload that takes the X and Y centre values as parameters There are corresponding d methods that take a diameter as the first parameter. */
  def apply(radius: Double, cen: Pt2 = Pt2Z) = new Circle(radius, cen.x, cen.y)

  /** Factory apply method for creating a circle. The first parameter gives the radius of the circle, followed by the X and Y centre values. There is an apply
   * method name overload that takes a [[Pt2]] as a second parameter with a default value of the origin. */
  def apply(radius: Double, cenX: Double, cenY: Double): Circle = new Circle(radius, cenX, cenY)

  /** Factory method for creating a circle. The first parameter gives the diameter of the circle. The default centre is at the origin. There is a name overload
   * that takes the X and Y centre values as parameters. There are corresponding apply methods that take a radius as the first parameter. */
  def d(diameter: Double, cen: Pt2 = Pt2Z) = new Circle(diameter / 2, cen.x, cen.y)

  /** Factory method for creating a circle. The first parameter gives the diameter of the circle, followed by the X and Y centre values. There is a method name
   * overload that takes a [[Pt2]] as a second parameter with a default value of the origin. There are corresponding apply methods that take a radius as the
   * first parameter. */
  def d(diameter: Double, cenX: Double, cenY: Double): Circle = new Circle(diameter / 2, cenX, cenY)

  override def reify(scale: Double, cen: Pt2): Circle = Circle(scale, cen)
  override def reify(scale: Double, xCen: Double, yCen: Double): Circle = Circle(scale, xCen, yCen)

  /** Implicit [[Slate2]] type class instance / evidence for [[Circle]] */
  given slate2Ev: Slate2[Circle] = new Slate2[Circle]
  { override def slate(obj: Circle, operand: VecPt2): Circle = obj.slate(operand)
    override def slateXY(obj: Circle, xOperand: Double, yOperand: Double): Circle = obj.slate(xOperand, yOperand)
    override def slateX(obj: Circle, xOperand: Double): Circle = obj.slateX(xOperand)
    override def slateY(obj: Circle, yOperand: Double): Circle = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[Circle]] */
  given scaleEv: Scale[Circle] = (obj, operand) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Circle]] */
  given rotateEv: Rotate[Circle] = (obj: Circle, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[Circle]] */
  given prolignEv: Prolign[Circle] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Circle]] */
  given transAxesEv: TransAxes[Circle] = new TransAxes[Circle]
  { override def negYT(obj: Circle): Circle = obj.negY
    override def negXT(obj: Circle): Circle = obj.negX
    override def rotate90(obj: Circle): Circle = obj.rotate90
    override def rotate180(obj: Circle): Circle = obj.rotate180
    override def rotate270(obj: Circle): Circle = obj.rotate270
  }

  override def fill(colour: Colour): CircleFillIcon = CircleFillIcon(colour)

  /** [[Filling]] type class instance / evidence for [[Circle]] and [[CircleFill]] */
  val fillerEv: Filling[Circle, CircleFill] = (obj, ff) => obj.fill(ff)
}

/** A circle based Graphic, may be simple or compound. */
trait CircleGraphic extends EllipseGraphic
{ override def shape: Circle

  /** The radius of this circle graphic. */
  @inline final def radius: Double = shape.radius

  /** The diameter of this circle graphic. */
  @inline final def diameter: Double = shape.diameter
}

/** A Simple circle based graphic. */
trait CircleGraphicSimple extends CircleGraphic with EllipseGraphicSimple
{ type ThisT <: CircleGraphicSimple
  final override def svgElem: SvgCircle = SvgCircle(attribs)
}

/** A simple single colour fill of a circle graphic. */
final case class CircleFill(shape: Circle, fillFacet: FillFacet) extends CircleGraphicSimple with EllipseFill with CanvElem
{ override type ThisT = CircleFill
  override def ptsTrans(f: Pt2 => Pt2): ThisT = CircleFill(shape.fTrans(f), fillFacet)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFill(this)
  override def toDraw(lineWidth: Double = 2, newColour: Colour = Black): CircleDraw = shape.draw(lineWidth, newColour)
}

/** A simple draw of a circle graphic. */
final case class CircleDraw(shape: Circle, lineWidth: Double = 2.0, lineColour: Colour = Black) extends CircleGraphicSimple with EllipseDraw
{ type ThisT = CircleDraw
  override def ptsTrans(f: Pt2 => Pt2): CircleDraw = CircleDraw(shape.fTrans(f), lineWidth, lineColour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)
}

/** A pointable polygon without visual. */
case class CircleActive(shape: Circle, pointerId: Any) extends EllipseActive with CircleGraphicSimple
{ override type ThisT = CircleActive
  override def ptsTrans(f: Pt2 => Pt2): CircleActive = CircleActive(shape.fTrans(f), pointerId)

  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.") }

  override def ptInside(pt: Pt2): Boolean = shape.ptInside(pt)
}

case class CircleFillIcon(fillColour: Colour) extends ShapeFillIcon
{ override def reify(scale: Double, cen: Pt2): CircleFill = CircleFill(Circle(scale, cen), fillColour)
  override def reify(scale: Double, xCen: Double, yCen: Double): CircleFill = CircleFill(Circle(scale, xCen, yCen), fillColour)
}

/** Compound Circle Graphic class. */
class CircleCompound(val shape: Circle, val facets: RArr[GraphicFacet], val fChilds: RArr[Circle => Graphic2Elem], val adopted: RArr[Graphic2Elem]) extends
  ShapeCompound, CircleGraphic, Simil2Elem
{
  def children: RArr[Graphic2Elem] = fChilds.map(f => f(shape)) ++ adopted

  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach {
    case c: Colour => cp.circleFill(CircleFill(shape, c))
    case DrawFacet(c, w) => cp.circleDraw(shape.draw(w, c))
    case fr: FillRadial => cp.circleFillRadial(shape, fr)

    case TextFacet(s, ratio, colour, ta, bl, min) => {
      val size = boundingWidth / ratio
      if (size >= min) cp.textGraphic(TextFixed(s, size, cenDefault, colour, ta, bl))
    }

    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  final override def mainSvgElem: SvgCircle = SvgCircle(attribs)

  override def slate(operand: VecPt2): CircleCompound = CircleCompound(shape.slate(operand), facets, fChilds, children.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): CircleCompound =
    CircleCompound(shape.slate(xOperand, yOperand), facets, fChilds, children.slate(xOperand, yOperand))

  override def slateX(xOperand: Double): CircleCompound = CircleCompound(shape.slateX(xOperand), facets, fChilds, children.slateX(xOperand))
  override def slateY(yOperand: Double): CircleCompound = CircleCompound(shape.slateY(yOperand), facets, fChilds, children.slateY(yOperand))
  override def scale(operand: Double): CircleCompound = CircleCompound(shape.scale(operand), facets, fChilds, children.scale(operand))
  override def negX: CircleCompound = CircleCompound(shape.negX, facets, fChilds, children.negX)
  override def negY: CircleCompound = CircleCompound(shape.negY, facets, fChilds, children.negY)
  override def rotate90: CircleCompound = CircleCompound(shape.rotate90, facets, fChilds, children.rotate90)
  override def rotate180: CircleCompound = CircleCompound(shape.rotate180, facets, fChilds, children.rotate180)
  override def rotate270: CircleCompound = CircleCompound(shape.rotate270, facets, fChilds, children.rotate270)
  override def prolign(matrix: AxlignMatrix): CircleCompound = CircleCompound(shape.prolign(matrix), facets, fChilds, children.prolign(matrix))
  override def rotate(rotation: AngleVec): CircleCompound = CircleCompound(shape.rotate(rotation), facets, fChilds, children.rotate(rotation))
  override def reflect(lineLike: LineLike): CircleCompound = CircleCompound(shape.reflect(lineLike), facets, fChilds, children.reflect(lineLike))
  override def addChildren(newChildren: Arr[Graphic2Elem]): CircleCompound = CircleCompound(shape, facets, fChilds, children ++ newChildren)
}

object CircleCompound
{ 
  
  def apply(shape: Circle, facets: RArr[GraphicFacet], fChilds: RArr[Circle => Graphic2Elem] = RArr(), adopted: RArr[Graphic2Elem] = RArr()): CircleCompound =
    new CircleCompound(shape, facets, fChilds, adopted)
  
  /** Implicit [[Slate2]] type class instance / evidence for [[CircleCompound]]. */
  given slate2Ev: Slate2[CircleCompound] = new Slate2[CircleCompound]
  { override def slate(obj: CircleCompound, operand: VecPt2): CircleCompound = obj.slate(operand)
    override def slateXY(obj: CircleCompound, xOperand: Double, yOperand: Double): CircleCompound = obj.slate(xOperand, yOperand)
    override def slateX(obj: CircleCompound, xOperand: Double): CircleCompound = obj.slateX(xOperand)
    override def slateY(obj: CircleCompound, yOperand: Double): CircleCompound = obj.slateY(yOperand)
  }

  /** Implicit [[Slate2]] type class instance / evidence for [[CircleCompound]]. */
  given scaleEv: Scale[CircleCompound] = (obj: CircleCompound, operand: Double) => obj.scale(operand)
  
  /** Implicit [[Slate2]] type class instance / evidence for [[CircleCompound]]. */
  given rotateEv: Rotate[CircleCompound] = (obj: CircleCompound, angle: AngleVec) => obj.rotate(angle)
  
  /** Implicit [[Slate2]] type class instance / evidence for [[CircleCompound]]. */
  given prolignEv: Prolign[CircleCompound] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[Slate2]] type class instance / evidence for [[CircleCompound]]. */
  given reflectEv: Reflect[CircleCompound] = (obj: CircleCompound, lineLike: LineLike) => obj.reflect(lineLike)
  
  /** Implicit [[Slate2]] type class instance / evidence for [[CircleCompound]]. */
  given reflectAxesEv: TransAxes[CircleCompound] = new TransAxes[CircleCompound]
  { override def negYT(obj: CircleCompound): CircleCompound = obj.negY
    override def negXT(obj: CircleCompound): CircleCompound = obj.negX
    override def rotate90(obj: CircleCompound): CircleCompound = obj.rotate90
    override def rotate180(obj: CircleCompound): CircleCompound = obj.rotate180
    override def rotate270(obj: CircleCompound): CircleCompound = obj.rotate270
  }
}