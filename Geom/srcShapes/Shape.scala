/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb._, Colour.Black

/** A closed shape. It has vertices and the vertices are connected by straight lines or curved lines. Shape does not extend CurvePath but it does
 *  extend [[Fillable]] which extends [[Drawable]].Not sure if [[Shape]] and [[Fillable]] should be separate classes. */
trait Shape extends Any with Fillable with BoundedElem
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

  def attribs: RArr[XmlAtt]
  
  /** This canEqual override allow the comparison of [[Shape]]s. */
  def canEqual(that: Any): Boolean = that match
  { case t: Shape => true
    case _ => false
  }

  override def slate(xDelta: Double, yDelta: Double): Shape
  override def scale(operand: Double): Shape
  override def negY: Shape
  override def negX: Shape
  override def prolign(matrix: ProlignMatrix): Shape
  override def rotate90: Shape
  override def rotate180: Shape
  override def rotate270: Shape
  override def rotate(rotation: AngleVec): Shape
  override def reflect(lineLike: LineLike): Shape
  override def scaleXY(xOperand: Double, yOperand: Double): Shape
  override def shearX(operand: Double): Shape
  override def shearY(operand: Double): Shape
}

/** Companion object for the [[Shape]] trait. Contains implicit instances of type TransElem for all the 2d geometric transformation type classes. */
object Shape
{ /** Implicit [[SlateXY]] type class instance / evidence for [[Shape]] */
  implicit val slateEv: SlateXY[Shape] = (obj: Shape, dx: Double, dy: Double) => obj.slate(dx, dy)

  /** Implicit [[Scale]] type class instance / evidence for [[Shape]] */
  implicit val scaleEv: Scale[Shape] = (obj: Shape, operand: Double) => obj.scale(operand)

  /** Implicit [[Rotate]] type class instance / evidence for [[Shape]] */
  implicit val rotateEv: Rotate[Shape] = (obj: Shape, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[Shape]] */
  implicit val prolignImplicit: Prolign[Shape] = (obj, matrix) => obj.prolign(matrix)

  /** Implicit [[ScaleXY]] type class instance / evidence for [[Shape]] */
  implicit val scaleXYEv: ScaleXY[Shape] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)

  /** Implicit [[TransAxes]] type class instance / evidence for [[Shape]] */
  implicit val transAxesEv: TransAxes[Shape] = new TransAxes[Shape]
  { override def negYT(obj: Shape): Shape = obj.negY
    override def negXT(obj: Shape): Shape = obj.negX
    override def rotate90(obj: Shape): Shape = obj.rotate90
    override def rotate180(obj: Shape): Shape = obj.rotate180
    override def rotate270(obj: Shape): Shape = obj.rotate270
  }
  /** Implicit [[Shear]] type class instance / evidence for [[Shape]] */
  implicit val shearImplicit: Shear[Shape] = new Shear[Shape]
  { override def shearXT(obj: Shape, yFactor: Double): Shape = obj.shearX(yFactor)
    override def shearYT(obj: Shape, xFactor: Double): Shape = obj.shearY(xFactor)
  }
}

/** A closed shape specified in [[Length]] units. */
trait ShapeLen2 extends Any, GeomLen2Elem
{ override def slate(operand: VecPtLen2): ShapeLen2
  override def slate(xOperand: Length, yOperand: Length): ShapeLen2
  override def slateX(xOperand: Length): ShapeLen2
  override def slateY(yOperand: Length): ShapeLen2
  override def scale(operand: Double): ShapeLen2
  override def mapGeom2(operand: Length): Shape
}