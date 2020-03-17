/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

sealed class SFace(val ordNum: Int, val angle: Angle, val xTile: Int, val yTile: Int)
object SFaceUp extends SFace(0, 0.degs, 0, 2)
object SFaceUR extends SFace(1, -45.degs, 1, 1)
object SFaceRt extends SFace(2, -90.degs, 2, 0)
object SFaceDR extends SFace(3, -135.degs, 1, -1)
object SFaceDn extends SFace(4, 180.degs, 0, -2)
object SFaceDL extends SFace(5, 135.degs, -1, -1)
object SFaceLt extends SFace(6, 90.degs, -2, 0)
object SFaceUL extends SFace(7, 45.degs, -1, 1)

object SFace
{
  /** Needs to be renamed. */
  def optFace(orig: Cood, dirn: Cood): Option[SFace] = dirn - orig match
  { case Cood(0, 2) => Some(SFaceUp)
    case Cood(2, 2) => Some(SFaceUR)
    case Cood(2, 0) => Some(SFaceRt)
    case Cood(2, -2) => Some(SFaceDR)
    case Cood(0, -2) => Some(SFaceDn)
    case Cood(-2, -2) => Some(SFaceDL)
    case Cood(-2, 0) => Some(SFaceLt)
    case Cood(-2, 2) => Some(SFaceUL)
    case _ => None
  }
}