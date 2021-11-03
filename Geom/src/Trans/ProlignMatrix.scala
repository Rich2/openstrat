/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import reflect.ClassTag

/** A matrix for proportionate and aligned to X and Y axes transformations. This transformation set preserves Circles and Squares. It also
 * preserves the alignment of Squares and Rectangle to the axes. */
final case class ProlignMatrix(vFactor: Double, negY: Boolean, negX: Boolean, xDelta: Double, yDelta: Double)
{ def vDelta: Pt2 = Pt2(xDelta, yDelta)
  def scale(operand: Double): ProlignMatrix = ProlignMatrix(vFactor * operand, negY, negX, xDelta * operand, yDelta * operand)
  def slate(operand: Pt2): ProlignMatrix = ProlignMatrix(vFactor, negY, negX, xDelta + operand.x, yDelta + operand.y)
  def slate(xOperand: Double, yOperand: Double): ProlignMatrix = ProlignMatrix(vFactor, negY, negX, xDelta + xOperand, yDelta + yOperand)

  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorXOffset(yOffset: Double): ProlignMatrix = ProlignMatrix(vFactor, !negY, negX, xDelta, yDelta + 2 * yOffset)

  /** Reflect, mirror across a line parallel to the X axis. */
  def mirrorYOffset(xOffset: Double): ProlignMatrix = ProlignMatrix(vFactor, negY, !negX, xDelta + 2 * xOffset, yDelta)

  def mirrorX: ProlignMatrix = ProlignMatrix(vFactor, !negY, negX, xDelta, yDelta)

  def mirrorY: ProlignMatrix = ProlignMatrix(vFactor, negY, !negX, xDelta, yDelta)

  def vecTrans(inp: Pt2): Pt2 =
  { val xa = ife(negX, -inp.x, inp.x)
    val ya = ife(negY, -inp.y, inp.y)
    Pt2(xa + xDelta, ya + yDelta)
  }
}

/** Companion object for Prolign Matrices. */
object ProlignMatrix
{
  def mirrorY: ProlignMatrix = ProlignMatrix(1, true, false, 0, 0)
}

/** Type class for Prolign transformations. These are proportionate and aligned to X and Y axes transformations. This transformation set preserves
 *  Circles and Squares. It also preserves the alignment of Squares and Rectangle to the axes.*/
trait Prolign[A]
{ def prolignObj(obj: A, prolignMatrix: ProlignMatrix): A
}

/** Companion object for the Prolign type class. */
object Prolign
{
  implicit def transAlignerImplicit[T <: SimilarPreserve]: Prolign[T] = (obj, offset) => obj.prolign(offset).asInstanceOf[T]

  implicit def arrImplicit[A](implicit ct: ClassTag[A], ev: Prolign[A]): Prolign[Arr[A]] =
    (arrA : Arr[A], prolignMatrix: ProlignMatrix) => arrA.map(a => ev.prolignObj(a, prolignMatrix))

  implicit def functorImplicit[A, F[_]](implicit evF: Functor[F], evA: Prolign[A]): Prolign[F[A]] =
    (fa, offset) => evF.mapT(fa, a => evA.prolignObj(a, offset))

  implicit def arrayImplicit[A](implicit ct: ClassTag[A], ev: Prolign[A]): Prolign[Array[A]] = (obj, offset) => obj.map(ev.prolignObj(_, offset))
}