/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

trait TransRigidDister extends Any
{ type ThisT <: TransRigidDister
  def slate(offset: Pt2M): ThisT
  def rotateRadians(radians: Double): ThisT
  def rotate(angle: Angle): ThisT = rotateRadians(angle.radians)
  def mirrorYOffset(xOffset: Metre): ThisT
  def mirrorXOffset(yOffset: Metre): ThisT
  def mirrorY: ThisT = mirrorYOffset(0.metres)
  def mirrorX: ThisT = mirrorXOffset(0.metres)
  def ySlate(yDelta: Metre): ThisT = slate(Pt2M(0.metres, yDelta))

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Metre, yOffset: Metre): ThisT = slate(Pt2M(xOffset, yOffset))
}
/** A Rigid or Euclidean transformations type class. */
trait TransRigidDist[T]
{
  def slate(obj: T, offset: Pt2M): T
  def rotateRadians(obj: T, radians: Double): T
  def mirrorYOffset(obj: T, xOffset: Metre): T
  def mirrorXOffset(obj: T, yOffset: Metre): T
  def rotate(obj: T, angle: Angle): T = rotateRadians(obj, angle.radians)
}

object TransRigidDist
{
  implicit def transRigiderImplicit[T <: TransRigidDister]: TransRigidDist[T] = new TransRigidDist[T]
  { override def mirrorXOffset(obj: T, yOffset: Metre): T = obj.mirrorXOffset(yOffset).asInstanceOf[T]
    override def mirrorYOffset(obj: T, xOffset: Metre): T = obj.mirrorYOffset(xOffset).asInstanceOf[T]
    override def rotateRadians(obj: T, radians: Double): T = obj.rotateRadians(radians).asInstanceOf[T]
    override def slate(obj: T, offset: Pt2M): T = obj.slate(offset).asInstanceOf[T]
  }

  implicit def arrImplicit[A, AA <: ArrImut[A]](implicit build: ArrTBuilder[A, AA], ev: TransRigidDist[A]): TransRigidDist[AA] = new TransRigidDist[AA]
  { override def slate(obj: AA, offset: Pt2M): AA = obj.map{ ts => ev.slate(ts, offset)}
    override def rotateRadians(obj: AA, radians: Double): AA = obj.map{ts => ev.rotateRadians(ts, radians) }
    override def mirrorYOffset(obj: AA, xOffset: Metre): AA = obj.map{ ts => ev.mirrorYOffset(ts, xOffset) }
    override def mirrorXOffset(obj: AA, yOffset: Metre): AA = obj.map{ ts => ev.mirrorXOffset(ts, yOffset) }
  }
}

class TransAlignDistExtension[T](value: T, ev: TransRigidDist[T])
{ /** Translate 2 dimensional vectors along the X axis */
  def slateX(xOffset: Metre): T = ev.slate(value, Pt2M(xOffset, 0.metres))

  /** Translate 2 dimensional vectors along the Y axis */
  def slateY(yOffset: Metre): T = ev.slate(value, Pt2M(0.metres, yOffset))

  /** Translate in 2 dimensional space. */
  def slate(offset: Pt2M): T = ev.slate(value, offset)

  /** Translate in 2 dimensional space. */
  def slate(xOffset: Metre, yOffset: Metre): T = ev.slate(value, Pt2M(xOffset, yOffset))
}