/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A Rectangle aligned to the X and Y axes. */
trait Rectanglelign extends Rectangle with Rectangularlign
{ @inline final override def x0: Double = xTopRight
  @inline final override def y0: Double = yTopRight
  @inline final override def v0: Vec2 = x0 vv y0
  @inline final override def x1: Double = xTopLeft
  @inline final override def y1: Double = yTopLeft
  @inline final override def v1: Vec2 = x1 vv y1
  @inline final override def cen: Vec2 = xCen vv yCen
  override def rotation: Angle = 0.degs
}

/** Implementation class for Rectanglelign, a rectangle aligned to the X and Y axes. */
final case class Rectlign(width: Double, height: Double, xCen: Double, yCen: Double) extends Rectanglelign
{ override type ThisT = Rectlign
  override def fTrans(f: Vec2 => Vec2): Rectlign = ???

  override def rotateRadians(radians: Double): Rect = ???

  override def reflect(line: Line): TransElem = ???
  override def reflect(line: LineSeg): TransElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???


}

/** Companion object for the Rectlign class */
object Rectlign
{ def apply(cen: Vec2, width: Double, height: Double): Rectlign = new Rectlign(width, height, cen.x, cen.y)
}