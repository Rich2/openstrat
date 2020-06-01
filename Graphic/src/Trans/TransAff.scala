/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import scala.math.Pi

/** An object that can transform itself in 2d geometry. This is a key trait, the object can be transformed in 2 dimensional space. Leaf classes must
 *  implement the single method fTrans(f: Vec2 => Vec2): T. The related trait TransDistable  does the same for fTrans(f: Dist2 => Dist2):  T.  */
trait AffineElem extends TransSimer with TransElem
{ type AlignT <: AffineElem
  def fTrans(f: Vec2 => Vec2): AlignT
  def slate(offset: Vec2): AlignT = fTrans(_ + offset)
  def scale(operand: Double): AlignT = fTrans(_ * operand)

  /** A generalised shear transformation. I think this is correct. */
  def shear(xScale: Double, yScale: Double): AffineElem = ??? // fTrans(v => v.x * yScale vv v.y * xScale)

  def mirrorXOffset(yOffset: Double): AlignT = fTrans(_.mirrorXOffset(yOffset))
  def mirrorYOffset(xOffset: Double): AlignT = fTrans(_.mirrorYOffset(xOffset))
  def rotateRadians(radians: Double): AlignT = fTrans(_.rotateRadians(radians))
  def mirror(v1: Vec2, v2: Vec2): AlignT = fTrans(_.mirror(v1, v2))

  override def mirror(line: Line2): AlignT = fTrans((_.mirror(line)))

  override def mirrorX: AlignT = fTrans(_.mirrorX)

  override def mirrorY: AlignT = fTrans(_.mirrorY)

  override def prolign(matrix: ProlignMatrix): AlignT = fTrans(_.prolignTrans(matrix))

  /** Rotates 90 degrees roate-clockwise or + Pi/2 */
  override def rotate90: AlignT = fTrans(_.rotateRadians(Pi/2))
}

/** Affine Transformation */
trait TransAff[T] extends TransSim[T]
{ def shear(obj: T, xScale: Double, yScale: Double): T
}

object TransAff
{
  /*implicit def transAfferImplicit[T <: TransAffer]: TransAff[T] = new TransAff[T]
  { override def shear(obj: T, xScale: Double, yScale: Double): T = obj.shear(xScale, yScale).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Vec2): T = obj.slate(offset).asInstanceOf[T]
    override def scale(obj: T, operand: Double): T = obj.scale(operand).asInstanceOf[T]
    override def mirror(obj: T, line: Line2): T = obj.mirror(line).asInstanceOf[T]
  }*/

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransAff[A]): TransAff[AA] = new TransAff[AA]
  { override def shear(obj: AA, xScale: Double, yScale: Double): AA = obj.map(ev.shear(_, xScale, yScale))
    override def scale(obj: AA, operand: Double): AA = obj.map(ev.scale(_, operand))
    override def slate(obj: AA, offset: Vec2): AA = obj.map(ev.slate(_, offset))
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map(ev.rotateRadians(_, radians))
    override def mirror(obj: AA, line: Line2): AA = obj.map(ev.mirror(_, line))
  }
}

class TransAffExtension[T](value: T, ev: TransAff[T])
{

}