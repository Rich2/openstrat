/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black
import ostrat.pgui.CanvasPlatform

/** A 4 sided [[Polygon]]. */
trait Quadrilateral extends Polygon4Plus
{ type ThisT <: Quadrilateral
  override def numVerts: Int = 4
  override def numElems: Int = 4
  def diag1: LSeg2 = LSeg2(v2, v0)

  def diag2: LSeg2 = LSeg2(v3, v1)

  @inline def diags: LSeg2Arr = LSeg2Arr(diag1, diag2)

  def vertsTrans(f: Pt2 => Pt2): Quadrilateral = Quadrilateral(f(v0), f(v1), f(v2), f(v3))

  override def slate(operand: VecPt2): Quadrilateral = vertsTrans(_.slate(operand))
  override def slate(xOperand: Double, yOperand: Double): Quadrilateral = vertsTrans(_.slate(xOperand, yOperand))
  override def slateFrom(operand: VecPt2): Quadrilateral = vertsTrans(_.slateFrom(operand))
  override def slateFrom(xOperand: Double, yOperand: Double): Quadrilateral = vertsTrans(_.slateFrom(xOperand, yOperand))
  override def slateX(xOperand: Double): Quadrilateral = vertsTrans(_.slateX(xOperand))
  override def slateY(yOperand: Double): Quadrilateral = vertsTrans(_.slateY(yOperand))
  override def scale(offset: Double): Quadrilateral = vertsTrans(_.scale(offset))
  override def negX: Quadrilateral = vertsTrans(_.negX)
  override def negY: Quadrilateral = vertsTrans(_.negY)
  override def prolign(matrix: AxlignMatrix): Quadrilateral = vertsTrans(_.prolign(matrix))
  override def rotate90: Quadrilateral = vertsTrans(_.rotate90)
  override def rotate180: Quadrilateral = vertsTrans(_.rotate90)
  override def rotate270: Quadrilateral = vertsTrans(_.rotate90)
  override def rotate(rotation: AngleVec): Quadrilateral = vertsTrans(_.rotate(rotation))
  override def mirror(lineLike: LineLike): Quadrilateral = vertsTrans(_.mirror(lineLike))
  override def scaleXY(xOperand: Double, yOperand: Double): Quadrilateral = vertsTrans(_.scaleXY(xOperand, yOperand))
  override def shearX(operand: Double): Quadrilateral = vertsTrans(_.shearX(operand))
  override def shearY(operand: Double): Quadrilateral = vertsTrans(_.shearY(operand))
  final override def sides: LSeg2Arr = LSeg2Arr(side0, side1, side2, side3)
  final override def v0: Pt2 = Pt2(v0x, v0y)
  final override def vLastX: Double = v3x
  final override def vLastY: Double = v3y
  final override def vLast: Pt2 = Pt2(v3x, v3y)
  final override def side0: LSeg2 = LSeg2(v0x, v0y, v1x, v1y)
  final override def sd0CenX: Double = v0x \/ v1x
  final override def sd0CenY: Double = v0y \/ v1y

  final override def sd0Cen: Pt2 = Pt2(sd0CenX, sd0CenY)
  final override def arrayUnsafe: Array[Double] = Array(v0x, v0y, v1x, v1y, v2x, v2y, v3x, v3y)
  final override def xVertsArray: Array[Double] = Array(v0x, v1x, v2x, v3x)
  final override def yVertsArray: Array[Double] = Array(v0y, v1y, v2y, v3y)

  override def draw(lineWidth: Double, lineColour: Colour): QuadDraw = QuadDraw(this, lineWidth, lineColour)
  override def fillDraw(fillColour: Colour, lineColour: Colour = Black, lineWidth: Double = 2): QuadCompound = QuadCompound(this, RArr(fillColour, DrawFacet(lineColour, lineWidth)))


  final override def vertX(index: Int): Double = index %% 4 match
  { case 0 => v0x
    case 1 => v1x
    case 2 => v2x
    case _ => v3x
  }

  final override def vertY(index: Int): Double = index %% 4 match
  { case 0 => v0y
    case 1 => v1y
    case 2 => v2y
    case _ => v3y
  }
}

object Quadrilateral
{ /** Factory apply method to create [[Quadrilateral]] from its 4 vertices. */
  def apply (vt0: Pt2, vt1: Pt2, vt2: Pt2, vt3: Pt2): Quadrilateral = new QuadrilateralGen(vt0.x, vt0.y, vt1.x, vt1.y, vt2.x, vt2.y, vt3.x, vt3.y)

  /** Factory apply method to create [[Quadrilateral]] from the X and Y components of the 4 vertices. */
  def apply(x0: Double, y0: Double, x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double): Quadrilateral =
    new QuadrilateralGen(x0, y0, x1, y1, x2, y2, x3, y3)
    
  /** Implicit [[Slate2]] type class instance for [[Quadrilateral]] */  
  given slate2Ev: Slate2[Quadrilateral] = new Slate2[Quadrilateral]
  { override def slate(obj: Quadrilateral, operand: VecPt2): Quadrilateral = obj.slate(operand)
    override def slateXY(obj: Quadrilateral, xOperand: Double, yOperand: Double): Quadrilateral = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: Quadrilateral, operand: VecPt2): Quadrilateral = obj.slateFrom(operand)
    override def slateFromXY(obj: Quadrilateral, xOperand: Double, yOperand: Double): Quadrilateral = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: Quadrilateral, xOperand: Double): Quadrilateral = obj.slateX(xOperand)
    override def slateY(obj: Quadrilateral, yOperand: Double): Quadrilateral = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance for [[Quadrilateral]] */
  given scaleEv: Scale[Quadrilateral] = (obj, operand) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Quadrilateral]]. */
  given rotateEv: Rotate[Quadrilateral] = (obj: Quadrilateral, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[Quadrilateral]]. */
  given prolignEv: Prolign[Quadrilateral] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Quadrilateral]]. */
  given scaleXYEv: ScaleXY[Quadrilateral] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[Mirror]] type class instance / evidence for [[Quadrilateral]]. */
  given reflectEv: Mirror[Quadrilateral] = (obj: Quadrilateral, lineLike: LineLike) => obj.mirror(lineLike)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Quadrilateral]]. */
  given transAxesEv: TransAxes[Quadrilateral] = new TransAxes[Quadrilateral]
  { override def negYT(obj: Quadrilateral): Quadrilateral = obj.negY
    override def negXT(obj: Quadrilateral): Quadrilateral = obj.negX
    override def rotate90(obj: Quadrilateral): Quadrilateral = obj.rotate90
    override def rotate180(obj: Quadrilateral): Quadrilateral = obj.rotate180
    override def rotate270(obj: Quadrilateral): Quadrilateral = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance / evidence for [[Quadrilateral]]. */
  given shearEv: Shear[Quadrilateral] = new Shear[Quadrilateral] {
    override def shearXT(obj: Quadrilateral, yFactor: Double): Quadrilateral = obj.shearX(yFactor)

    override def shearYT(obj: Quadrilateral, xFactor: Double): Quadrilateral = obj.shearY(xFactor)
  }
  
  /** Implicit [[Drawing]] type class instance / evidence for [[Quadrilateral]]. */
  given drawingEv: Drawing[Quadrilateral, QuadDraw] = (obj, lineWidth, colour) => obj.draw(lineWidth, colour)

  /** Implicit [[Filling]] type class evidence for [[Quadrilateral]]. */
  //given fillingEv: Filling[Quadrilateral, QuadrilateralFill] = (obj, fillFacet) => obj.fill(fillFactet)
}

/** Quadrilateral where the 4 vertices X and Y components are fields */
trait QuadrilateralFields extends Quadrilateral
{
  override def elem(index: Int): Pt2 = index %% 4 match
  { case 0 => Pt2(v0x, v0y)
    case 1 => Pt2(v1x, v1y)
    case 2 => Pt2(v2x, v2y)
    case _ => Pt2(v3x, v3y)
  }
}

/** The general case of a quadrilateral */
class QuadrilateralGen(val v0x: Double, val v0y: Double, val v1x: Double, val v1y: Double, val v2x: Double, val v2y: Double, val v3x: Double, val v3y: Double)
  extends QuadrilateralFields
{ type ThisT = QuadrilateralGen
  override def typeStr: String = "QuadrilateralGen"

  def ptsTrans(f: Pt2 => Pt2): QuadrilateralGen = QuadrilateralGen(f(v0), f(v1), f(v2), f(v3))
}

/** Companion object for [[QuadrilateralGen]], the general case of a [[Quadrilateral]], contains factory methods. */
object QuadrilateralGen
{ /** Apply factory method to construct [[QuadrilateralGen]] from its 4 vertices. */
  def apply(v0: Pt2, v1: Pt2, v2: Pt2, v3: Pt2): QuadrilateralGen =  new QuadrilateralGen(v0.x, v0.y, v1.x, v1.y, v2.x, v2.y, v3.x, v3.y)

  /** Apply factory method to construct [[QuadrilateralGen]] from it's the X and Y components of its 4 vertices. */
  def apply(v0x: Double, v0y: Double, v1x: Double, v1y: Double, v2x: Double, v2y: Double, v3x: Double, v3y: Double): QuadrilateralGen =
    new QuadrilateralGen(v0x, v0y, v1x, v1y, v2x, v2y, v3x, v3y)
}

/** 2-dimensional graphic based ona quadrilateral */
trait QuadGraphic extends PolygonGraphic
{ override def shape: Quadrilateral
}

trait QuadGraphicSimple extends PolygonGraphicSimple, QuadGraphic
{ override def slate(operand: VecPt2): QuadGraphicSimple
  override def slate(xOperand: Double, yOperand: Double): QuadGraphicSimple
  override def scale(operand: Double): QuadGraphicSimple
  override def negX: QuadGraphicSimple
  override def negY: QuadGraphicSimple
  override def rotate90: QuadGraphicSimple
  override def rotate180: QuadGraphicSimple
  override def rotate270: QuadGraphicSimple
}

/** Immutable Graphic element that defines and draws a [[Quadrilateral]]. */
trait QuadDraw extends QuadGraphicSimple, PolygonDraw
{ //override def rendToCanvas(cp: CanvasPlatform): Unit = cp.QuadDraw(this)
  override def slate(operand: VecPt2): QuadDraw = QuadDraw(shape.slate(operand), lineWidth, lineColour)
  override def slate(xOperand: Double, yOperand: Double): QuadDraw = QuadDraw(shape.slate(xOperand, yOperand), lineWidth, lineColour)
  override def slateFrom(operand: VecPt2): QuadDraw = QuadDraw(shape.slateFrom(operand), lineWidth, lineColour)
  override def slateFrom(xOperand: Double, yOperand: Double): QuadDraw = QuadDraw(shape.slateFrom(xOperand, yOperand), lineWidth, lineColour)
  override def slateX(xOperand: Double): QuadDraw = QuadDraw(shape.slateX(xOperand), lineWidth, lineColour)
  override def slateY(yOperand: Double): QuadDraw = QuadDraw(shape.slateY(yOperand), lineWidth, lineColour)
  override def scale(operand: Double): QuadDraw = QuadDraw(shape.scale(operand), lineWidth, lineColour)
  override def negY: QuadDraw = QuadDraw(shape.negY, lineWidth, lineColour)
  override def negX: QuadDraw = QuadDraw(shape.negX, lineWidth, lineColour)
  override def rotate90: QuadDraw = QuadDraw(shape.rotate90, lineWidth, lineColour)
  override def rotate180: QuadDraw = QuadDraw(shape.rotate180, lineWidth, lineColour)
  override def rotate270: QuadDraw = QuadDraw(shape.rotate270, lineWidth, lineColour)
  override def prolign(matrix: AxlignMatrix): QuadDraw = QuadDraw(shape.prolign(matrix), lineWidth, lineColour)
  override def rotate(rotation: AngleVec): QuadDraw = QuadDraw(shape.rotate(rotation), lineWidth, lineColour)
  override def mirror(lineLike: LineLike): QuadDraw = QuadDraw(shape.mirror(lineLike), lineWidth, lineColour)
  override def scaleXY(xOperand: Double, yOperand: Double): QuadDraw = QuadDraw(shape.scaleXY(xOperand, yOperand), lineWidth, lineColour)
  override def shearX(operand: Double): QuadDraw = QuadDraw(shape.shearX(operand), lineWidth, lineColour)
  override def shearY(operand: Double): QuadDraw = QuadDraw(shape.shearY(operand), lineWidth, lineColour)
}

object QuadDraw
{
  def apply(shape: Quadrilateral, lineWidth: Double = 2, lineColour: Colour = Black): QuadDraw = QuadDrawGen(shape, lineWidth, lineColour)

  /** Implicit [[Slate2]] type class instance / evidence for [[QuadDraw]]. */
  given slate2Ev: Slate2[QuadDraw] = new Slate2[QuadDraw]
  { override def slate(obj: QuadDraw, operand: VecPt2): QuadDraw = obj.slate(operand)
    override def slateXY(obj: QuadDraw, xOperand: Double, yOperand: Double): QuadDraw = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: QuadDraw, operand: VecPt2): QuadDraw = obj.slateFrom(operand)
    override def slateFromXY(obj: QuadDraw, xOperand: Double, yOperand: Double): QuadDraw = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: QuadDraw, xOperand: Double): QuadDraw = obj.slateX(xOperand)
    override def slateY(obj: QuadDraw, yOperand: Double): QuadDraw = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[QuadDraw]]. */
  given scaleEv: Scale[QuadDraw] = (obj: QuadDraw, operand: Double) => obj.scale(operand)

  /** Implicit [[Prolgn]] type class instance / evidence for [[QuadDraw]]. */
  given prolignEv: Prolign[QuadDraw] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[Rotate]] type class instance / evidence for [[QuadDraw]]. */
  given rotateEv: Rotate[QuadDraw] = (obj: QuadDraw, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[QuadDraw]]. */
  given scaleXYEv: ScaleXY[QuadDraw] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[TransAxes]] type class instance / evidence for [[QuadDraw]]. */
  given transAxesEv: TransAxes[QuadDraw] = new TransAxes[QuadDraw]
  { override def negXT(obj: QuadDraw): QuadDraw = obj.negX
    override def negYT(obj: QuadDraw): QuadDraw = obj.negY
    override def rotate90(obj: QuadDraw): QuadDraw = obj.rotate90
    override def rotate180(obj: QuadDraw): QuadDraw = obj.rotate180
    override def rotate270(obj: QuadDraw): QuadDraw = obj.rotate270
  }

  /*given persistEv: Persist3[Quad, Double, Colour, QuadDraw] =
    Persist3("PolyFill", "poly", _.shape, "lineWidth", _.lineWidth, "colour", _.lineColour, apply)*/

  /** class for creating a [[DrawFacet]] graphic for the general case of [[Quad]]. */
  case class QuadDrawGen(shape: Quadrilateral, lineWidth: Double = 2, lineColour: Colour = Black) extends QuadDraw
  { //override def rendToCanvas(cp: CanvasPlatform): Unit = cp.QuadDraw(this)
  }
}

/** Compound graphic based on a quadrilateral. */
trait QuadCompound extends PolygonCompound, QuadGraphic
{ /** Graphic child elements of a quadrilateral. */
  def quadChilds: RArr[Quadrilateral => GraphicElems] = RArr()

  /** A sequence of graphics that have been attached to this quadrilateral. */
  def adopted: GraphicElems = RArr()

  override def slate(operand: VecPt2): QuadCompound = QuadCompound(shape.slate(operand), facets, quadChilds, adopted.slate(operand))

  override def slate(xOperand: Double, yOperand: Double): QuadCompound =
    QuadCompound(shape.slate(xOperand, yOperand), facets, quadChilds, adopted.slate(xOperand, yOperand))

  override def slateFrom(operand: VecPt2): QuadCompound = QuadCompound(shape.slateFrom(operand), facets, quadChilds, adopted.slateFrom(operand))

  override def slateFrom(xOperand: Double, yOperand: Double): QuadCompound =
    QuadCompound(shape.slateFrom(xOperand, yOperand), facets, quadChilds, adopted.slateFrom(xOperand, yOperand))

  override def slateX(xOperand: Double): QuadCompound = QuadCompound(shape.slateX(xOperand), facets, quadChilds, adopted.slateX(xOperand))
  override def slateY(yOperand: Double): QuadCompound = QuadCompound(shape.slateY(yOperand), facets, quadChilds, adopted.slateY(yOperand))
  override def scale(operand: Double): QuadCompound = QuadCompound(shape.scale(operand), facets, quadChilds, adopted.scale(operand))
  override def rotate(rotation: AngleVec): QuadCompound = QuadCompound(shape.rotate(rotation), facets, quadChilds, adopted.rotate(rotation))
}

object QuadCompound
{ /** Factory apply method to construct a [[QuadCompound]]. */
  def apply(shape: Quadrilateral, facets: RArr[GraphicFacet], quadChilds: RArr[Quadrilateral => GraphicElems] = RArr(), adopted: RArr[Graphic2Elem] = RArr()):
    QuadCompound = new QuadCompoundGen(shape, facets, quadChilds, adopted)

  /** Implicit [[Slate2]] type class instance / evidence for [[QuadCompound]]. */
  given slate2Ev: Slate2[QuadCompound] = new Slate2[QuadCompound]
  { override def slate(obj: QuadCompound, operand: VecPt2): QuadCompound = obj.slate(operand)    
    override def slateXY(obj: QuadCompound, xOperand: Double, yOperand: Double): QuadCompound = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: QuadCompound, operand: VecPt2): QuadCompound = obj.slateFrom(operand)
    override def slateFromXY(obj: QuadCompound, xOperand: Double, yOperand: Double): QuadCompound = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: QuadCompound, xOperand: Double): QuadCompound = obj.slateX(xOperand)    
    override def slateY(obj: QuadCompound, yOperand: Double): QuadCompound = obj.slateY(yOperand)
  }
}

/** The implementation for the general case of a compound graphic based on a quadrilateral. */
class QuadCompoundGen(val shape: Quadrilateral, val facets: RArr[GraphicFacet], quadChilds: RArr[Quadrilateral => GraphicElems], adopted: RArr[Graphic2Elem])
  extends QuadCompound
{ override def children: RArr[Graphic2Elem] = ???
  override def shearX(operand: Double): QuadCompoundGen = ???
  override def shearY(operand: Double): QuadCompoundGen = ???
}