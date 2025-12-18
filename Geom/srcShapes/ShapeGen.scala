/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb.*

/** The new version of ShapeGen. Will prioritise easy and simplicity of functionality over efficiency. A generalised implementation of a [[Shape]]. A closed
 * sequence of curve segments. */
final class ShapeGen(val unsafeArray: Array[CurveSeg]) extends ShapeSegs, AxisFree
{ override type ThisT = ShapeGen
  override def rotate(rotation: AngleVec): ShapeGen = ???
  override def mirror(lineLike: LineLike): ShapeGen = ???
}

/** Companion object of the ShapeGen class contains implicit instances for 2D geometric transformations. */
object ShapeGen
{
  /** Throws on 0 length var args. */
    def apply(curveTails: CurveTailOld*): ShapeGen =
    { val array: Array[CurveSeg] = new Array[CurveSeg](curveTails.length)
      curveTails.iForeach { (ct, i) =>
        ct match {
          //case lt: LineTail =>
          case _ =>
        }
      }
      new ShapeGen(array)

    }

  /** Implicit [[Slate2]] type class instance / evidence for [[ShapeGen]]. */
  given slate2Ev: Slate2[ShapeGen] = new Slate2[ShapeGen]
  { override def slate(obj: ShapeGen, operand: VecPt2): ShapeGen = obj.slate(operand)
    override def slateXY(obj: ShapeGen, xOperand: Double, yOperand: Double): ShapeGen = obj.slate(xOperand, yOperand)
    override def slateFrom(obj: ShapeGen, operand: VecPt2): ShapeGen = obj.slateFrom(operand)
    override def slateFromXY(obj: ShapeGen, xOperand: Double, yOperand: Double): ShapeGen = obj.slateFrom(xOperand, yOperand)
    override def slateX(obj: ShapeGen, xOperand: Double): ShapeGen = obj.slateX(xOperand)
    override def slateY(obj: ShapeGen, yOperand: Double): ShapeGen = obj.slateY(yOperand)
  }

  /** Implicit [[Scale]] type class instance / evidence for [[ShapeGen]]. */
  given scaleEv: Scale[ShapeGen] = (obj: ShapeGen, operand: Double) => obj.scale(operand)
  
  /** Implicit [[Rotate]] type class instance / evidence for [[ShapeGen]]. */
  given rotateEv: Rotate[ShapeGen] = (obj: ShapeGen, angle: AngleVec) => obj.rotate(angle)
  
  /** Implicit [[Prolign]] type class instance / evidence for [[ShapeGen]]. */
  given prolignEv: Prolign[ShapeGen] = (obj, matrix) => obj.prolign(matrix)
  
  /** Implicit [[ScaleXY]] type class instance / evidence for [[ShapeGen]]. */
  given scaleXYEv: ScaleXY[ShapeGen] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  
  /** Implicit [[Mirror]] type class instance / evidence for [[ShapeGen]]. */
  given ReflectEv: Mirror[ShapeGen] = (obj, lineLike) => obj.mirror(lineLike)

  /** Implicit [[TransAxes]] type class instance / evidence for [[ShapeGen]]. */
  given transAxesEv: TransAxes[ShapeGen] = new TransAxes[ShapeGen]
  { override def negXT(obj: ShapeGen): ShapeGen = obj.negX
    override def negYT(obj: ShapeGen): ShapeGen = obj.negY
    override def rotate90(obj: ShapeGen): ShapeGen = obj.rotate90
    override def rotate180(obj: ShapeGen): ShapeGen = obj.rotate180
    override def rotate270(obj: ShapeGen): ShapeGen = obj.rotate270
  }

  /** Implicit [[Shear]] type class instance / evidence for [[ShapeGen]]. */
  given shearEv: Shear[ShapeGen] = new Shear[ShapeGen]
  { override def shearXT(obj: ShapeGen, yFactor: Double): ShapeGen = obj.shearX(yFactor)
    override def shearYT(obj: ShapeGen, xFactor: Double): ShapeGen = obj.shearY(xFactor)
  }
}

class ShapeGenDraw
{

}