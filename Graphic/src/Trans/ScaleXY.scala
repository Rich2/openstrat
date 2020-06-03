/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** type class for scale transformation where the X and Y components can be scaled independently. */
trait ScaleXY[T]
{ def scaleTXY(obj: T, xOperand: Double, yOperand: Double): T
}
