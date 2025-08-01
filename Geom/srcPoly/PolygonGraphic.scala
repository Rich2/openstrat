/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pgui.*, pWeb.*, Colour.Black

/** A Polygon based graphic. If you just want a general polygon as opposed to specifically specified Polygons such as [[Rectangle]], [[Square]] or [[Triangle]]
 * use the implementation class [[PolygonCompound]]. */
trait PolygonGraphic extends ShapeGraphic
{ override def shape: Polygon

  def x1: Double = shape.v0x
  def y1: Double = shape.v0y

  /** The number of vertices. */
  def vertsNum: Int = shape.numVerts

  /** Checks for 2 or more vertices */
  def ifv2: Boolean = shape.numVerts >= 2

  /** Checks for 3 or more vertices */
  def ifv3: Boolean = shape.numVerts >= 3
  def xVertsArray: Array[Double] = shape.xVertsArray
  def yVertsArray: Array[Double] = shape.yVertsArray
  @inline def vertsForeach(f: Pt2 => Unit): Unit = shape.vertsForeach(f)
  @inline def vertsMap[A, ArrT <: Arr[A]](f: Pt2 => A)(implicit build: BuilderArrMap[A, ArrT]): ArrT = shape.vertsMap(f)

  def vertsFoldLeft[B](f: (B, Pt2) => B)(implicit default: DefaultValue[B]): B = vertsFoldLeft(default.default)(f)

  def vertsFoldLeft[B](init: B)(f: (B, Pt2) => B): B =
  { var acc = init
    vertsForeach{v => acc = f(acc, v) }
    acc
  }

  override def slate(operand: VecPt2): PolygonGraphic
  override def slate(xOperand: Double, yOperand: Double): PolygonGraphic
  override def slateX(xOperand: Double): PolygonGraphic
  override def slateY(yOperand: Double): PolygonGraphic
  override def scale(operand: Double): PolygonGraphic
  override def negY: PolygonGraphic
  override def negX: PolygonGraphic
  override def prolign(matrix: AxlignMatrix): PolygonGraphic
  override def rotate90: PolygonGraphic
  override def rotate180: PolygonGraphic
  override def rotate270: PolygonGraphic
}

/** Companion object for Polygon Graphic, contains implicit instances for the 2D geometric transformations. */
object PolygonGraphic
{ /** Implicit [[Slate2]] type class instance / evidence for [[PolygonGraphic]]. */
  given slat2eEv: Slate2[PolygonGraphic] = new Slate2[PolygonGraphic]
  { override def slate(obj: PolygonGraphic, operand: VecPt2): PolygonGraphic = obj.slate(operand)
    override def slateXY(obj: PolygonGraphic, xOperand: Double, yOperand: Double): PolygonGraphic = obj.slate(xOperand, yOperand)
    override def slateX(obj: PolygonGraphic, xOperand: Double): PolygonGraphic = obj.slateX(xOperand)
    override def slateY(obj: PolygonGraphic, yOperand: Double): PolygonGraphic = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[PolygonGraphic]]. */
  given scaleEv: Scale[PolygonGraphic] = (obj: PolygonGraphic, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[PolygonGraphic]]. */
  given rotateEv: Rotate[PolygonGraphic] = (obj: PolygonGraphic, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[PolygonGraphic]]. */
  given scaleXYEv: ScaleXY[PolygonGraphic] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Prolign]] type class instance / evidence for [[PolygonGraphic]]. */
  given prolignEv: Prolign[PolygonGraphic] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[PolygonGraphic]]. */
  given transAxesEv: TransAxes[PolygonGraphic] = new TransAxes[PolygonGraphic]
  { override def negYT(obj: PolygonGraphic): PolygonGraphic = obj.negY
    override def negXT(obj: PolygonGraphic): PolygonGraphic = obj.negX
    override def rotate90(obj: PolygonGraphic): PolygonGraphic = obj.rotate90
    override def rotate180(obj: PolygonGraphic): PolygonGraphic = obj.rotate180
    override def rotate270(obj: PolygonGraphic): PolygonGraphic = obj.rotate270
  }
}

/** This trait may be removed. */
trait PolygonGraphicSimple extends PolygonGraphic with ShapeGraphicSimple
{ override def svgElem: SvgElem = SvgPolygon(attribs)
  override def slate(operand: VecPt2): PolygonGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): PolygonGraphicSimple
  override def slateX(xOperand: Double): PolygonGraphicSimple
  override def slateY(yOperand: Double): PolygonGraphicSimple
  override def scale(operand: Double): PolygonGraphicSimple
  override def shearX(operand: Double): PolygonGraphicSimple
  override def shearY(operand: Double): PolygonGraphicSimple
  override def reflect(lineLike: LineLike): PolygonGraphicSimple
  override def negY: PolygonGraphicSimple
  override def negX: PolygonGraphicSimple
  override def prolign(matrix: AxlignMatrix): PolygonGraphicSimple
  override def rotate90: PolygonGraphicSimple
  override def rotate180: PolygonGraphicSimple
  override def rotate270: PolygonGraphicSimple
  override def rotate(rotation: AngleVec): PolygonGraphicSimple
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonGraphicSimple
}

/** Companion object for the PolygonGraphicSimple trait, contains implicit instances for the 2D geometric transformation classes. */
object PolygonGraphicSimple
{ /** Implicit [[Slate2]] type class instance / evidence for [[PolygonGraphicSimple]]. */
  given slateEv: Slate2[PolygonGraphicSimple] = new Slate2[PolygonGraphicSimple]
  { override def slate(obj: PolygonGraphicSimple, operand: VecPt2): PolygonGraphicSimple = obj.slate(operand)
    override def slateXY(obj: PolygonGraphicSimple, xOperand: Double, yOperand: Double): PolygonGraphicSimple = obj.slate(xOperand, yOperand)
    override def slateX(obj: PolygonGraphicSimple, xOperand: Double): PolygonGraphicSimple = obj.slateX(xOperand)
    override def slateY(obj: PolygonGraphicSimple, yOperand: Double): PolygonGraphicSimple = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[PolygonGraphicSimple]]. */
  given scaleEv: Scale[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, operand: Double) => obj.scale(operand)

  /** Implicit [[TransAxes]] type class instance / evidence for [[PolygonGraphicSimple]]. */
  given transAxesEv: TransAxes[PolygonGraphicSimple] = new TransAxes[PolygonGraphicSimple]
  { override def negYT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negY
    override def negXT(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.negX
    override def rotate90(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.rotate90
    override def rotate180(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.rotate180
    override def rotate270(obj: PolygonGraphicSimple): PolygonGraphicSimple = obj.rotate270
  }

  /** Implicit [[Prolign]] type class instance / evidence for [[PolygonGraphicSimple]]. */
  given prolignEv: Prolign[PolygonGraphicSimple] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Rotate]] type class instance / evidence for [[PolygonGraphicSimple]]. */
  given rotateEv: Rotate[PolygonGraphicSimple] = (obj: PolygonGraphicSimple, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[PolygonGraphicSimple]]. */
  given scaleXYEv: ScaleXY[PolygonGraphicSimple] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
}

/** Immutable Graphic element that defines and draws a Polygon. */
trait PolygonDraw extends PolygonGraphicSimple with CanvShapeDraw
{ override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  override def slate(operand: VecPt2): PolygonDraw = PolygonDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): PolygonDraw = PolygonDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateX(xOperand: Double): PolygonDraw = PolygonDraw(shape.slateX(xOperand), lineWidth, lineColour)
  override def slateY(yOperand: Double): PolygonDraw = PolygonDraw(shape.slateY(yOperand), lineWidth, lineColour)
  override def scale(operand: Double): PolygonDraw = PolygonDraw(shape.scale(operand), lineWidth, lineColour)
  override def negY: PolygonDraw = PolygonDraw(shape.negY, lineWidth, lineColour)
  override def negX: PolygonDraw = PolygonDraw(shape.negX, lineWidth, lineColour)
  override def rotate90: PolygonDraw = PolygonDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: PolygonDraw = PolygonDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: PolygonDraw = PolygonDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): PolygonDraw = PolygonDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(rotation: AngleVec): PolygonDraw = PolygonDraw(shape.rotate(rotation), lineWidth, lineColour)
  override def reflect(lineLike: LineLike): PolygonDraw = PolygonDraw(shape.reflect(lineLike), lineWidth, lineColour)
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonDraw = PolygonDraw(shape.scaleXY(xOperand, yOperand), lineWidth, lineColour)
  override def shearX(operand: Double): PolygonDraw = PolygonDraw(shape.shearX(operand), lineWidth, lineColour)
  override def shearY(operand: Double): PolygonDraw = PolygonDraw(shape.shearY(operand), lineWidth, lineColour)
}

object PolygonDraw
{
  def apply(shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black): PolygonDraw = PolygonDrawGen(shape, lineWidth, lineColour)

  /** Implicit [[Slate2]] type class instance / evidence for [[PolygonDraw]]. */
  given slate2Ev: Slate2[PolygonDraw] = new Slate2[PolygonDraw]
  { override def slate(obj: PolygonDraw, operand: VecPt2): PolygonDraw = obj.slate(operand)
    override def slateXY(obj: PolygonDraw, xOperand: Double, yOperand: Double): PolygonDraw = obj.slate(xOperand, yOperand)
    override def slateX(obj: PolygonDraw, xOperand: Double): PolygonDraw = obj.slateX(xOperand)
    override def slateY(obj: PolygonDraw, yOperand: Double): PolygonDraw = obj.slateY(yOperand)
  }  

  /** Implicit [[Scale]] type class instance / evidence for [[PolygonDraw]]. */
  given scaleEv: Scale[PolygonDraw] = (obj: PolygonDraw, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolgn]] type class instance / evidence for [[PolygonDraw]]. */
  given prolignEv: Prolign[PolygonDraw] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Rotate]] type class instance / evidence for [[PolygonDraw]]. */
  given rotateEv: Rotate[PolygonDraw] = (obj: PolygonDraw, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[PolygonDraw]]. */
  given scaleXYEv: ScaleXY[PolygonDraw] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[TransAxes]] type class instance / evidence for [[PolygonDraw]]. */
  given transAxesEv: TransAxes[PolygonDraw] = new TransAxes[PolygonDraw]
  { override def negXT(obj: PolygonDraw): PolygonDraw = obj.negX
    override def negYT(obj: PolygonDraw): PolygonDraw = obj.negY
    override def rotate90(obj: PolygonDraw): PolygonDraw = obj.rotate90
    override def rotate180(obj: PolygonDraw): PolygonDraw = obj.rotate180
    override def rotate270(obj: PolygonDraw): PolygonDraw = obj.rotate270
  }

  /*given persistEv: Persist3[Polygon, Double, Colour, PolygonDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)*/

  /** class for creating a [[DrawFacet]] graphic for the general case of [[Polygon]]. */
  case class PolygonDrawGen(shape: Polygon, lineWidth: Double = 2, lineColour: Colour = Black) extends PolygonDraw
  { override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonDraw(this)
  }
}

/** Immutable Graphic element that defines and fills a Polygon. This element can be trnsformed through all the Affine transformations and a PolygonFill will be
 * returned. */
trait PolygonFill extends PolygonGraphicSimple, CanvShapeFill
{ type ThisT <: PolygonFill
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.polygonFill(this)
  override def toDraw(lineWidth: Double = 2, newColour: Colour): PolygonDraw = shape.draw(lineWidth, newColour)
  override def slate(operand: VecPt2): PolygonFill
  override def slate(xDelta: Double, yDelta: Double): PolygonFill
  override def slateX(xOperand: Double): PolygonFill
  override def slateY(yOperand: Double): PolygonFill
  override def scale(operand: Double): PolygonFill
  override def negX: PolygonFill
  override def negY: PolygonFill
  override def rotate90: PolygonFill
  override def rotate180: PolygonFill
  override def rotate270: PolygonFill
  override def prolign(matrix: AxlignMatrix): PolygonFill
  override def rotate(rotation: AngleVec): PolygonFill
  override def reflect(lineLike: LineLike): PolygonFill
  override def shearX(operand: Double): PolygonFill
  override def shearY(operand: Double): PolygonFill
  override def scaleXY(xOperand: Double, yOperand: Double): PolygonFill
}

/** Companion object for PolygonFill trait. Contains an implementation class [[PolygonFillGen]], a factory method returning the PolygonFill type and implicit
 * instances for the 2D geometric transformation type classes. */
object PolygonFill
{ /** Factory apply method to construct a [[PolygonFill]]. The alternative way is to create the [[Polygon]] and use its fill method. */
  def apply(shape: Polygon, fillFacet: FillFacet): PolygonFill = new PolygonFillGen(shape, fillFacet)
  /*given persistImplicit: Persist2[Polygon, Colour, PolygonFill] = Persist2("PolyFill", "poly", _.shape, "colour", _.colour, apply)*/

  /** Implicit [[Slate2]] type class instance / evidence for [[PolygonFill]]. */
  given slate2Ev: Slate2[PolygonFill] = new Slate2[PolygonFill]
  { override def slate(obj: PolygonFill, operand: VecPt2): PolygonFill = obj.slate(operand)
    override def slateXY(obj: PolygonFill, xOperand: Double, yOperand: Double): PolygonFill = obj.slate(xOperand, yOperand)
    override def slateX(obj: PolygonFill, xOperand: Double): PolygonFill = obj.slateX(xOperand)
    override def slateY(obj: PolygonFill, yOperand: Double): PolygonFill = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[PolygonFill]]. */
  given scaleEv: Scale[PolygonFill] = (obj: PolygonFill, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolign]] type class instance / evidence for [[PolygonFill]]. */
  given prolignEv: Prolign[PolygonFill] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Rotate]] type class instance / evidence for [[PolygonFill]]. */
  given rotateEv: Rotate[PolygonFill] = (obj: PolygonFill, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[PolygonFill]]. */
  given scaleXYEv: ScaleXY[PolygonFill] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[TransAxes]] type class instance / evidence for [[PolygonFill]]. */
  given transAxesEv: TransAxes[PolygonFill] = new TransAxes[PolygonFill]
  { override def negXT(obj: PolygonFill): PolygonFill = obj.negX
    override def negYT(obj: PolygonFill): PolygonFill = obj.negY
    override def rotate90(obj: PolygonFill): PolygonFill = obj.rotate90
    override def rotate180(obj: PolygonFill): PolygonFill = obj.rotate90
    override def rotate270(obj: PolygonFill): PolygonFill = obj.rotate90
  }

  /** Immutable Graphic element that defines and fills a Polygon. This element can be transformed through all the Affine transformations and a [[PolygonFill]]
   * will be returned.
   *
   * @constructor create a new PolygonFill with the underlying polygon and a colour.
   * @param shape  The Polygon shape.
   * @param colour The colour of this graphic. */
  final case class PolygonFillGen(shape: Polygon, fillFacet: FillFacet) extends PolygonFill
  { override def slate(operand: VecPt2): PolygonFill = PolygonFillGen(shape.slate(operand), fillFacet)
    override def slate(xDelta: Double, yDelta: Double): PolygonFillGen = PolygonFillGen(shape.slate(xDelta, yDelta), fillFacet)
    override def slateX(xOperand: Double): PolygonFillGen = PolygonFillGen(shape.slateX(xOperand), fillFacet)
    override def slateY(yOperand: Double): PolygonFillGen = PolygonFillGen(shape.slateY(yOperand), fillFacet)
    override def scale(operand: Double): PolygonFillGen = PolygonFillGen(shape.scale(operand), fillFacet)
    override def negY: PolygonFillGen = PolygonFillGen(shape.negY, fillFacet)
    override def negX: PolygonFillGen = PolygonFillGen(shape.negX, fillFacet)
    override def rotate90: PolygonFillGen = PolygonFillGen(shape.rotate90, fillFacet)
    override def rotate180: PolygonFillGen = PolygonFillGen(shape.rotate180, fillFacet)
    override def rotate270: PolygonFill = PolygonFillGen(shape.rotate270, fillFacet)
    override def prolign(matrix: AxlignMatrix): PolygonFillGen = PolygonFillGen(shape.prolign(matrix), fillFacet)
    override def rotate(rotation: AngleVec): PolygonFillGen = PolygonFillGen(shape.rotate(rotation), fillFacet)
    override def reflect(lineLike: LineLike): PolygonFillGen = PolygonFillGen(shape.reflect(lineLike), fillFacet)
    override def shearX(operand: Double): PolygonFillGen = PolygonFillGen(shape.shearX(operand), fillFacet)
    override def shearY(operand: Double): PolygonFillGen = PolygonFillGen(shape.shearY(operand), fillFacet)
    override def scaleXY(xOperand: Double, yOperand: Double): PolygonFill = PolygonFillGen(shape.scaleXY(xOperand, yOperand), fillFacet)
    override def toDraw(lineWidth: Double = 2, newColour: Colour = Black): PolygonDraw = shape.draw(lineWidth, newColour)
  }
}

/** An interactive element of a [[Polygon]] graphic, that can be identified by a mouse etc, pointable device. */
case class PolygonActive(shape: Polygon, pointerId: Any) extends GraphicAffineElem, GraphicClickable, PolygonGraphicSimple
{ override type ThisT = PolygonActive
  override def ptsTrans(f: Pt2 => Pt2): PolygonActive = PolygonActive(shape.map(f), pointerId)

  /** Renders this functional immutable GraphicElem, using the imperative methods of the abstract [[pCanv.CanvasPlatform]] interface. */
  override def rendToCanvas(cp: CanvasPlatform): Unit = { deb("Not implemented.")}

  //override def slateTo(newCen: Pt2): PolygonActive = ???
  override def ptInside(pt: Pt2): Boolean = shape.ptInside(pt)

  override def nonShapeAttribs: RArr[XAtt] = ???
}

/** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
trait PolygonCompound extends ShapeCompound, PolygonGraphic, Aff2Elem
{
  override def mainSvgElem: SvgElem = SvgPolygon(attribs)

  override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach {
    case c: Colour => cp.polygonFill(shape.fill(c))
    case DrawFacet(c, w) => cp.polygonDraw(shape.draw(w, c))
    case TextFacet(s, rel, col, ta, bl, min) => cp.textGraphic(TextFixed(s, boundingWidth/ rel, cenDefault, col, ta, bl))
    // case fr: FillRadial => cp.circleFillRadial(shape, fr)
    case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
  }

  override def canvElems: RArr[CanvElem] = ???
  override def slate(operand: VecPt2): PolygonCompound = PolygonCompound(shape.slate(operand), facets, children.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): PolygonCompound =
    PolygonCompound(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))


  override def slateX(xOperand: Double): PolygonCompound = PolygonCompound(shape.slateX(xOperand), facets, children.slateX(xOperand))
  override def slateY(yOperand: Double): PolygonCompound = PolygonCompound(shape.slateY(yOperand), facets, children.slateY(yOperand))
  override def scale(operand: Double): PolygonCompound = PolygonCompound(shape.scale(operand), facets, children.scale(operand))
  override def negY: PolygonCompound = PolygonCompound(shape.negY, facets, children.negY)
  override def negX: PolygonCompound = PolygonCompound(shape.negX, facets, children.negX)
  override def prolign(matrix: AxlignMatrix): PolygonCompound = PolygonCompound(shape.prolign(matrix), facets, children.prolign(matrix))
  override def rotate90: PolygonCompound = PolygonCompound(shape.rotate90, facets, children.rotate90)
  override def rotate180: PolygonCompound = PolygonCompound(shape.rotate180, facets, children.rotate180)
  override def rotate270: PolygonCompound = PolygonCompound(shape.rotate270, facets, children.rotate270)
  override def rotate(rotation: AngleVec): PolygonCompound = PolygonCompound(shape.rotate(rotation), facets, children.rotate(rotation))
  override def reflect(lineLike: LineLike): PolygonCompound = PolygonCompound(shape.reflect(lineLike), facets, children.reflect(lineLike))

  override def scaleXY(xOperand: Double, yOperand: Double): PolygonCompound =
    PolygonCompound(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand))

  override def shearX(operand: Double): PolygonCompound
  override def shearY(operand: Double): PolygonCompound

  def addChildren(newChildren: Arr[Graphic2Elem]): PolygonCompound = PolygonCompound(shape, facets, children ++ newChildren)
}

/** Companion object for the PolygonCompound trait contains factory apply method and implicit instances for the 2D geometric transformation type
 * classes. */
object PolygonCompound
{
  def apply(shape: Polygon, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()): PolygonCompound =
    new PolygonCompoundGen(shape, facets, children)

  //given showTImplicit: Show3T[Polygon, Arr[GraphicFacet], Arr[GraphicElem], PolygonCompound] = Show3T[Polygon, Arr[GraphicFacet], Arr[GraphicElem], PolygonCompound]()

  /** Implicit [[Slate2]] type class instance evidence for [[PolygonCompound]]. */
  given slateEv: Slate2[PolygonCompound] = new Slate2[PolygonCompound]
  { override def slate(obj: PolygonCompound, operand: VecPt2): PolygonCompound = obj.slate(operand)
    override def slateXY(obj: PolygonCompound, xOperand: Double, yOperand: Double): PolygonCompound = obj.slate(xOperand, yOperand)
    override def slateX(obj: PolygonCompound, xOperand: Double): PolygonCompound = obj.slateX(xOperand)
    override def slateY(obj: PolygonCompound, yOperand: Double): PolygonCompound = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance evidence for [[PolygonCompound]]. */
  given scaleEv: Scale[PolygonCompound] = (obj: PolygonCompound, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance evidence for [[PolygonCompound]]. */
  given rotateEv: Rotate[PolygonCompound] = (obj: PolygonCompound, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance evidence for [[PolygonCompound]]. */
  given prolignEv: Prolign[PolygonCompound] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance evidence for [[PolygonCompound]]. */
  given scaleXYEv: ScaleXY[PolygonCompound] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Reflect]] type class instance evidence for [[PolygonCompound]]. */
  given reflectEv: Reflect[PolygonCompound] = (obj: PolygonCompound, lineLike: LineLike) => obj.reflect(lineLike)

  /** Implicit [[RefelctAxes]] type class instance evidence for [[PolygonCompound]]. */
  given reflectAxesEv: TransAxes[PolygonCompound] = new TransAxes[PolygonCompound]
  { override def negYT(obj: PolygonCompound): PolygonCompound = obj.negY
    override def negXT(obj: PolygonCompound): PolygonCompound = obj.negX
    override def rotate90(obj: PolygonCompound): PolygonCompound = obj.rotate90
    override def rotate180(obj: PolygonCompound): PolygonCompound = obj.rotate180
    override def rotate270(obj: PolygonCompound): PolygonCompound = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance evidence for [[PolygonCompound]]. */
  given shearEv: Shear[PolygonCompound] = new Shear[PolygonCompound]
  { override def shearXT(obj: PolygonCompound, yFactor: Double): PolygonCompound = obj.shearX(yFactor)
    override def shearYT(obj: PolygonCompound, xFactor: Double): PolygonCompound = obj.shearY(xFactor)
  }

  /** A compound polygon based Graphic. May contain multiple facets and child graphic members. */
  case class PolygonCompoundGen(shape: Polygon, facets: RArr[GraphicFacet], children: RArr[Graphic2Elem] = RArr()) extends PolygonCompound, AxisFree
  {  override type ThisT = PolygonCompoundGen
    override def mainSvgElem: SvgPolygon = SvgPolygon(attribs)

    override def rendToCanvas(cp: pgui.CanvasPlatform): Unit = facets.foreach{
      case c: Colour => cp.polygonFill(shape.fill(c))
      case DrawFacet(c, w) => cp.polygonDraw(shape.draw(w, c))

      case TextFacet(s, ratio, colour, ta, bl, min) =>
      { val size = boundingWidth /ratio
        if (size >= min) cp.textGraphic(TextFixed(s, size, cenDefault, colour, ta, bl))
      }
      // case fr: FillRadial => cp.circleFillRadial(shape, fr)
      case sf => deb("Unrecognised ShapeFacet: " + sf.toString)
    }

    override def slate(operand: VecPt2): PolygonCompoundGen = PolygonCompoundGen(shape.slate(operand), facets, children.slate(operand))
    
    override def slate(xOperand: Double, yOperand: Double): PolygonCompoundGen =
      PolygonCompoundGen(shape.slate(xOperand, yOperand), facets, children.slate(xOperand, yOperand))

    override def scale(operand: Double): PolygonCompoundGen = PolygonCompoundGen(shape.scale(operand), facets, children.scale(operand))
    override def prolign(matrix: AxlignMatrix): PolygonCompoundGen = PolygonCompoundGen(shape.prolign(matrix), facets, children.prolign(matrix))
    override def rotate(rotation: AngleVec): PolygonCompoundGen = PolygonCompoundGen(shape.rotate(rotation), facets, children.rotate(rotation))
    override def reflect(lineLike: LineLike): PolygonCompoundGen = PolygonCompoundGen(shape.reflect(lineLike), facets, children.reflect(lineLike))

    override def scaleXY(xOperand: Double, yOperand: Double): PolygonCompoundGen =
      PolygonCompoundGen(shape.scaleXY(xOperand, yOperand), facets, children.scaleXY(xOperand, yOperand))

    override def shearX(operand: Double): PolygonCompoundGen = PolygonCompoundGen(shape.shearX(operand), facets, children.shearX(operand))
    override def shearY(operand: Double): PolygonCompoundGen = PolygonCompoundGen(shape.shearY(operand), facets, children.shearY(operand))
  }
}