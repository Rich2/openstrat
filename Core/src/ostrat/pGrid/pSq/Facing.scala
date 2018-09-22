/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
package pSq
import geom._

sealed class Facing(val angle: Angle)

object FaceUp extends Facing(0.degs)
object FaceUR extends Facing(-45.degs)
object FaceRt extends Facing(-90.degs)
object FaceDR extends Facing(-135.degs)
object FaceDn extends Facing(180.degs)
object FaceDL extends Facing(135.degs)
object FaceLt extends Facing(90.degs)
object FaceUL extends Facing(45.degs)