/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

object SqCode
{
  sealed class Facing(val angle: Angle, val xTile: Int, yTile: Int)

  object FaceUp extends Facing(0.degs, 0, 2)
  object FaceUR extends Facing(-45.degs, 1, 1)
  object FaceRt extends Facing(-90.degs, 2, 0)
  object FaceDR extends Facing(-135.degs, 1, -1)
  object FaceDn extends Facing(180.degs, 0, -2)
  object FaceDL extends Facing(135.degs, -1, -1)
  object FaceLt extends Facing(90.degs, -2, 0)
  object FaceUL extends Facing(45.degs, -1, 1)
  
  def optFace(orig: Cood, dirn: Cood): Option[Facing] = dirn - orig match
  { case Cood(0, 2) => Some(FaceUp)
    case Cood(2, 2) => Some(FaceUR)
    case Cood(2, 0) => Some(FaceRt)
    case Cood(2, -2) => Some(FaceDR)
    case Cood(0, -2) => Some(FaceDn)
    case Cood(-2, -2) => Some(FaceDL)
    case Cood(-2, 0) => Some(FaceLt)
    case Cood(-2, 2) => Some(FaceUL)
    case _ => None
  }
}