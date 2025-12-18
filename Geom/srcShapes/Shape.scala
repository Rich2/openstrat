/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*, Colour.Black

/** A closed shape. It has vertices and the vertices are connected by straight lines or curved lines. Shape does not extend CurvePath, but it does extend
 * [[Fillable]] which extends [[Drawable]].Not sure if [[Shape]] and [[Fillable]] should be separate classes. */
trait Shape extends Any, Aff2Elem, Fillable, BoundedElem
{ /** Determines if the parameter point lies inside this [[Circle]]. */
  def ptInside(pt: Pt2): Boolean

  override def fill(fillfacet: FillFacet): ShapeFill
  override def fillInt(intValue: Int): ShapeFill
  override def draw(lineWidth: Double = 2, lineColour: Colour = Black): ShapeDraw
  override def fillDraw(fillColour: Colour, lineColour: Colour, lineWidth: Double): ShapeCompound
  def fillActive(fillColour: Colour, pointerID: Any): ShapeCompound

  /** [[ShapeCompound]] graphic with a [[FillFacet]], a [[TextFacet]] and a [[ShapeActive]] child. */
  def fillActiveText(fillColour: Colour, pointerEv: Any, str: String, fontRatio: Double, fontColour: Colour = Black, align: TextAlign = CenAlign,
    baseLine: BaseLine = BaseLine.Middle, minSize: Double = 4): ShapeCompound

  def attribs: RArr[XAtt]
  
  /** This canEqual override allow the comparison of [[Shape]]s. */
  def canEqual(that: Any): Boolean = that match
  { case t: Shape => true
    case _ => false
  }

  override def slate(operand: VecPt2): Shape
  override def slate(xOperand: Double, yOperand: Double): Shape
  override def slateFrom(operand: VecPt2): Shape
  override def slateFrom(xOperand: Double, yOperand: Double): Shape
  override def slateX(xOperand: Double): Shape
  override def slateY(yUperand: Double): Shape
  override def scale(operand: Double): Shape
  override def negY: Shape
  override def negX: Shape
  override def prolign(matrix: AxlignMatrix): Shape
  override def rotate90: Shape
  override def rotate180: Shape
  override def rotate270: Shape
  override def rotate(rotation: AngleVec): Shape
  override def mirror(lineLike: LineLike): Shape
  override def scaleXY(xOperand: Double, yOperand: Double): Shape
  override def shearX(operand: Double): Shape
  override def shearY(operand: Double): Shape
}

/** Companion object for the [[Shape]] trait. Contains implicit instances of type TransElem for all the 2d geometric transformation type classes. */
object Shape
{ /** Implicit [[Slate2]] type class instance / evidence for [[Shape]] */
  given slate2Ev: Slate2[Shape] = new Slate2[Shape]
  { override def slate(obj: Shape, operand: VecPt2): Shape = obj.slate(operand)
    override def slateXY(obj: Shape, xOperand: Double, yOperand: Double): Shape = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: Shape, operand: VecPt2): Shape = obj.slateFrom(operand)

    override def slateFromXY(obj: Shape, xOperand: Double, yOperand: Double): Shape = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: Shape, xOperand: Double): Shape = obj.slateX(xOperand)
    override def slateY(obj: Shape, yOperand: Double): Shape = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[Shape]] */
  given scaleEv: Scale[Shape] = (obj: Shape, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Shape]] */
  given rotateEv: Rotate[Shape] = (obj: Shape, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[Shape]] */
  given prolignImplicit: Prolign[Shape] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Shape]] */
  given scaleXYEv: ScaleXY[Shape] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Shape]] */
  given transAxesEv: TransAxes[Shape] = new TransAxes[Shape]
  { override def negYT(obj: Shape): Shape = obj.negY
    override def negXT(obj: Shape): Shape = obj.negX
    override def rotate90(obj: Shape): Shape = obj.rotate90
    override def rotate180(obj: Shape): Shape = obj.rotate180
    override def rotate270(obj: Shape): Shape = obj.rotate270
  }
  /** Implicit [[Shear]] type class instance / evidence for [[Shape]] */
  given shearEv: Shear[Shape] = new Shear[Shape]
  { override def shearXT(obj: Shape, yFactor: Double): Shape = obj.shearX(yFactor)
    override def shearYT(obj: Shape, xFactor: Double): Shape = obj.shearY(xFactor)
  }

  /** Implicit [[Drawing]] type class evidence for [[Shape]]. */
  given drawingEv: Drawing[Shape, ShapeDraw] = (obj, lw, lc) => obj.draw(lw, lc)
  
  /** Implicit [[Filling]] type class evidence for [[Shape]]. */
  given fillingEv: Filling[Shape, ShapeFill] = (obj, fillFactet) => obj.fill(fillFactet)
}

/** A closed shape specified in [[Length]] units. */
trait ShapeLen2 extends Any, FillableLen2
{ override def slate(operand: VecPtLen2): ShapeLen2
  override def slate(xOperand: Length, yOperand: Length): ShapeLen2
  override def slateX(xOperand: Length): ShapeLen2
  override def slateY(yOperand: Length): ShapeLen2
  override def scale(operand: Double): ShapeLen2
  override def mapGeom2(operand: Length): Shape
}