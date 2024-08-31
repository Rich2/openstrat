/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import Colour.Black, pWeb._

/** The new version of ShapeGen. Will prioritise easy and simplicity of functionality over efficiency. A generalised implementation of a [[Shape]]. A closed
 * sequence of curve segments. */
final class ShapeGen(val unsafeArray: Array[CurveSeg]) extends ShapeSegs with AxisFree {
  override type ThisT = ShapeGen

  override def rotate(angle: AngleVec): ShapeGen = ???

  override def reflect(lineLike: LineLike): ShapeGen = ???
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

  implicit val slateImplicit: Slate[ShapeGen] = (obj: ShapeGen, dx: Double, dy: Double) => obj.slateXY(dx, dy)
  implicit val scaleImplicit: Scale[ShapeGen] = (obj: ShapeGen, operand: Double) => obj.scale(operand)
  implicit val rotateImplicit: Rotate[ShapeGen] = (obj: ShapeGen, angle: AngleVec) => obj.rotate(angle)
  implicit val prolignImplicit: Prolign[ShapeGen] = (obj, matrix) => obj.prolign(matrix)
  implicit val XYScaleImplicit: ScaleXY[ShapeGen] = (obj, xOperand, yOperand) => obj.scaleXY(xOperand, yOperand)
  implicit val ReflectImplicit: Reflect[ShapeGen] = (obj, lineLike) => obj.reflect(lineLike)

  implicit val transAxesImplicit: TransAxes[ShapeGen] = new TransAxes[ShapeGen] {
    override def negYT(obj: ShapeGen): ShapeGen = obj.negY

    override def negXT(obj: ShapeGen): ShapeGen = obj.negX

    override def rotate90(obj: ShapeGen): ShapeGen = obj.rotate90

    override def rotate180(obj: ShapeGen): ShapeGen = obj.rotate180

    override def rotate270(obj: ShapeGen): ShapeGen = obj.rotate270
  }

  implicit val shearImplicit: Shear[ShapeGen] = new Shear[ShapeGen]
  { override def shearXT(obj: ShapeGen, yFactor: Double): ShapeGen = obj.shearX(yFactor)
    override def shearYT(obj: ShapeGen, xFactor: Double): ShapeGen = obj.shearY(xFactor)
  }
}

class ShapeGenDraw
{

}