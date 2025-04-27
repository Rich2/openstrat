/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** GraphicSimple is a non-compound graphic element that can be rendered to a display (or printed) or is an active element in a display, but can't be both that
 * require a [[GraphicCompound]]. */
trait GraphicSimple extends Graphic2Elem
{ override def slate(operand: VecPt2): GraphicSimple
  override def slate(xOperand: Double, yOperand: Double): GraphicSimple
  override def scale(operand: Double): GraphicSimple
  override def negX: GraphicSimple
  override def negY: GraphicSimple
  override def rotate90: GraphicSimple
  override def rotate180: GraphicSimple
  override def rotate270: GraphicSimple
  override def prolign(matrix: AxlignMatrix): GraphicSimple
  override def rotate(rotation: AngleVec): GraphicSimple
  override def reflect(lineLike: LineLike): GraphicSimple
  override def scaleXY(xOperand: Double, yOperand: Double): GraphicSimple
}

/** Companion object for the [[GraphicSimple]] trait. Contains Implicit instances for 2d geometrical transformation type-classes. */
object GraphicSimple
{ /** Implicit [[Slate2]] type class instance / evidence for [[GraphicSimple]]. */
  given slate2Ev: Slate2[GraphicSimple] = new Slate2[GraphicSimple]
  { override def slate(obj: GraphicSimple, operand: VecPt2): GraphicSimple = obj.slate(operand)
    override def slateXY(obj: GraphicSimple, xOperand: Double, yOperand: Double): GraphicSimple = obj.slate(xOperand, yOperand)
  }
  
  /** Implicit [[Scale]] type class instance / evidence for [[GraphicSimple]]. */
  given scaleEv: Scale[GraphicSimple] = (obj: GraphicSimple, operand: Double) => obj.scale(operand)
  
  /** Implicit [[Rotate]] type class instance / evidence for [[GraphicSimple]]. */
  given rotateEv: Rotate[GraphicSimple] = (obj: GraphicSimple, angle: AngleVec) => obj.rotate(angle)

  /** Implicit [[Prolign]] type class instance / evidence for [[GraphicSimple]]. */
  given prolignEv: Prolign[GraphicSimple] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[TransAxes]] type class instance / evidence for [[GraphicSimple]]. */
  given transAxesEv: TransAxes[GraphicSimple] = new TransAxes[GraphicSimple]
  { override def negYT(obj: GraphicSimple): GraphicSimple = obj.negY
    override def negXT(obj: GraphicSimple): GraphicSimple = obj.negX
    override def rotate90(obj: GraphicSimple): GraphicSimple = obj.rotate90
    override def rotate180(obj: GraphicSimple): GraphicSimple = obj.rotate90
    override def rotate270(obj: GraphicSimple): GraphicSimple = obj.rotate90
  }

  /** Implicit [[ScaleXY]] type class instance / evidence for [[GraphicSimple]]. */
  given scaleXYEv: ScaleXY[GraphicSimple] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
}

trait GraphicLen2Simple extends GraphicLen2Elem