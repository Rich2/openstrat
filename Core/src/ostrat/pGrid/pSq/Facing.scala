/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
package pSq
import geom._

sealed class Facing(val angle: Angle, val xTile: Int, yTile: Int)

object FaceUp extends Facing(0.degs, 0, 2)
object FaceUR extends Facing(-45.degs, 1, 1)
object FaceRt extends Facing(-90.degs, 2, 0)
object FaceDR extends Facing(-135.degs, 1, -1)
object FaceDn extends Facing(180.degs, 0, -2)
object FaceDL extends Facing(135.degs, -1, -1)
object FaceLt extends Facing(90.degs, -2, 0)
object FaceUL extends Facing(45.degs, -1, 1)