/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom

trait TransRigidDister extends Any
{ type ThisT <: TransRigidDister
  def slate(offset: Dist2): ThisT
  def rotateRadians(radians: Double): ThisT
  def rotate(angle: Angle): ThisT = rotateRadians(angle.radians)
  def mirrorYOffset(xOffset: Dist): ThisT
  def mirrorXOffset(yOffset: Dist): ThisT
  def mirrorY: ThisT = mirrorYOffset(0.metres)
  def mirrorX: ThisT = mirrorXOffset(0.metres)
  def ySlate(yDelta: Dist): ThisT = slate(Dist2(0.metres, yDelta))

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Dist, yOffset: Dist): ThisT = slate(Dist2(xOffset, yOffset))
}
/** A Rigid or Euclidean transformations type class. */
trait TransRigidDist[T]
{
  def slate(obj: T, offset: Dist2): T
  def rotateRadians(obj: T, radians: Double): T
  def mirrorYOffset(obj: T, xOffset: Dist): T
  def mirrorXOffset(obj: T, yOffset: Dist): T
}

object TransRigidDist
{
  implicit def transRigiderImplicit[T <: TransRigidDister]: TransRigidDist[T] = new TransRigidDist[T]
  { override def mirrorXOffset(obj: T, yOffset: Dist): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Dist): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Dist2): T = obj.slate(offset).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrBase[A]](implicit build: ArrBuild[A, AA], ev: TransRigid[A]): TransRigid[AA] = new TransRigid[AA]
  { override def slate(obj: AA, offset: Vec2): AA = obj.map{ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Double): AA = obj.map{ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Double): AA = obj.map{ts => ev.mirrorXOffset(ts, yOffset) }
  }
}

class TransRigidDistExtension[T](value: T, ev: TransRigidDist[T])
{
  /** Translate 2 dimensional vectors along the X axis */
  def slateX(xOffset: Dist): T = ev.slate(value, Dist2(xOffset, 0.metres))

  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(yOffset: Dist): T = ev.slate(value, Dist2(0.metres, yOffset))

  /** Translate in 2 dimensional space. */
  def slate(offset: Dist2): T = ev.slate(value, offset)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Dist, yOffset: Dist): T = ev.slate(value, Dist2(xOffset, yOffset))
}