/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A matrix for proportionate and aligned to X and Y axes transformations. This transformation set preserves Circles and Squares. It also preserves the
 * alignment of Squares and Rectangle to the axes. */
final case class AxlignMatrix(vFactor: Double, negY: Boolean, negX: Boolean, xDelta: Double, yDelta: Double)
{ def vDelta: Pt2 = Pt2(xDelta, yDelta)
  def scale(operand: Double): AxlignMatrix = AxlignMatrix(vFactor * operand, negY, negX, xDelta * operand, yDelta * operand)
  def slate(operand: Pt2): AxlignMatrix = AxlignMatrix(vFactor, negY, negX, xDelta + operand.x, yDelta + operand.y)
  def slate(xOperand: Double, yOperand: Double): AxlignMatrix = AxlignMatrix(vFactor, negY, negX, xDelta + xOperand, yDelta + yOperand)

  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): AxlignMatrix = AxlignMatrix(vFactor, !negY, negX, xDelta, yDelta + 2 * yOffset)

  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): AxlignMatrix = AxlignMatrix(vFactor, negY, !negX, xDelta + 2 * xOffset, yDelta)

  def mirrorX: AxlignMatrix = AxlignMatrix(vFactor, !negY, negX, xDelta, yDelta)

  def mirrorY: AxlignMatrix = AxlignMatrix(vFactor, negY, !negX, xDelta, yDelta)

  def vecTrans(inp: Pt2): Pt2 =
  { val xa = ife(negX, -inp.x, inp.x)
    val ya = ife(negY, -inp.y, inp.y)
    Pt2(xa + xDelta, ya + yDelta)
  }
}

/** Companion object for [[Prolign]] Matrices. */
object AxlignMatrix
{
  def mirrorY: AxlignMatrix = AxlignMatrix(1, true, false, 0, 0)
}

/** Type class for Prolign transformations. These are proportionate and aligned to X and Y axes transformations. This transformation set preserves [[Circle]]s
 * and [[Square]]s. It also preserves the alignment of Squares and Rectangle to the axes.*/
trait Prolign[A]
{ def prolignObj(obj: A, prolignMatrix: AxlignMatrix): A
}

/** Companion object for the Prolign type class. */
object Prolign
{
  //given transAlignerEv[T <: SimilarPreserve]: Prolign[T] = (obj, offset) => obj.prolign(offset).asInstanceOf[T]

  given arrEv[A](using ct: ClassTag[A], ev: Prolign[A]): Prolign[RArr[A]] =
    (arrA : RArr[A], prolignMatrix: AxlignMatrix) => arrA.map(a => ev.prolignObj(a, prolignMatrix))

  given functorEv[A, F[_]](using evF: Functor[F], evA: Prolign[A]): Prolign[F[A]] =
    (fa, offset) => evF.mapT(fa, a => evA.prolignObj(a, offset))

  given arrayEv[A](using ct: ClassTag[A], ev: Prolign[A]): Prolign[Array[A]] = (obj, offset) => obj.map(ev.prolignObj(_, offset))
}

extension[T](value: T)(using ev: Prolign[T])
{ def prolign(matrix: AxlignMatrix): T = ev.prolignObj(value, matrix)
}