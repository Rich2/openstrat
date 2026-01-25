/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

/** A shape based graphic. */
trait ShapeGraphic extends GraphicBounded
{ def shape: Shape
  final override def boundingRect: Rect = shape.boundingRect
  final override def boundingWidth: Double = shape.boundingWidth
  final override def boundingHeight: Double = shape.boundingHeight
  def attribs: RArr[XAtt]
  final def shapeAttribs: RArr[XAtt] = shape.attribs

  def svgInline: SvgSvgRel

  final def svgInlineStr: String = svgInline.out(0, 150)
  
  override def slate(offset: VecPt2): ShapeGraphic
  override def slate(xOperand: Double, yOperand: Double): ShapeGraphic
  override def slateFrom(offset: VecPt2): ShapeGraphic
  override def slateFrom(xOperand: Double, yOperand: Double): ShapeGraphic
  override def slateX(xOperand: Double): ShapeGraphic
  override def slateY(yOperand: Double): ShapeGraphic
  override def scale(operand: Double): ShapeGraphic
  override def negX: ShapeGraphic
  override def negY: ShapeGraphic
  override def rotate90: ShapeGraphic
  override def rotate180: ShapeGraphic
  override def rotate270: ShapeGraphic
  override def prolign(matrix: AxlignMatrix): ShapeGraphic
  override def rotate(rotation: AngleVec): ShapeGraphic
  override def mirror(lineLike: LineLike): ShapeGraphic
  override def scaleXY(xOperand: Double, yOperand: Double): ShapeGraphic
}

/** Companion object for the ShapeGraphic class. */
object ShapeGraphic
{
  implicit class ArrImplicit(val thisArr: RArr[ShapeGraphic])
  {
    def svgInline(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = ???
  }
  
  /** [[Slate2]] type class instance / evidence for [[ShapeGraphic]]. */
  given slate2Ev: Slate2[ShapeGraphic] = new Slate2[ShapeGraphic]
  { override def slateT(obj: ShapeGraphic, operand: VecPt2): ShapeGraphic = obj.slate(operand)
    override def slateXY(obj: ShapeGraphic, xOperand: Double, yOperand: Double): ShapeGraphic = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: ShapeGraphic, operand: VecPt2): ShapeGraphic = obj.slateFrom(operand)
    override def slateFromXY(obj: ShapeGraphic, xOperand: Double, yOperand: Double): ShapeGraphic = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: ShapeGraphic, xOperand: Double): ShapeGraphic = obj.slateX(xOperand)
    override def slateY(obj: ShapeGraphic, yOperand: Double): ShapeGraphic = obj.slateY(yOperand)
  }

  /** [[Scale]] type class instance / evidence for [[ShapeGraphic]]. */
  given scaleEv: Scale[ShapeGraphic] = (obj: ShapeGraphic, operand: Double) => obj.scale(operand)
  
  /** [[Rotate]] type class instance / evidence for [[ShapeGraphic]]. */
  given rotateEv: Rotate[ShapeGraphic] = (obj: ShapeGraphic, angle: AngleVec) => obj.rotate(angle)
  
  /** [[ScaleXY]] type class instance / evidence for [[ShapeGraphic]]. */
  given scaleXYEv: ScaleXY[ShapeGraphic] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  
  /** [[Prolign]] type class instance / evidence for [[ShapeGraphic]]. */
  given prolignEv: Prolign[ShapeGraphic] = (obj, matrix) => obj.prolign(matrix)

  /** [[MirrorAxes]] type class instance / evidence for [[ShapeGraphic]]. */
  given transAxesEv: MirrorAxes[ShapeGraphic] = new MirrorAxes[ShapeGraphic]
  { override def negYT(obj: ShapeGraphic): ShapeGraphic = obj.negY
    override def negXT(obj: ShapeGraphic): ShapeGraphic = obj.negX
    override def rotate90(obj: ShapeGraphic): ShapeGraphic = obj.rotate90
    override def rotate180(obj: ShapeGraphic): ShapeGraphic = obj.rotate180
    override def rotate270(obj: ShapeGraphic): ShapeGraphic = obj.rotate270
  }
}

trait ShapeLen2Graphic extends GraphicLen2Elem
{ def shape: ShapeLen2
}