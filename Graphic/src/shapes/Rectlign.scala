/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom


/** Implementation class for Rectanglelign, a rectangle aligned to the X and Y axes. */
final case class Rectlign(width: Double, height: Double, xCen: Double, yCen: Double) extends Rectanglelign
{ override type ThisT = Rectlign
  override def fTrans(f: Vec2 => Vec2): Rectlign = ???

  override def rotateRadians(radians: Double): Rect = ???

  override def reflect(line: Line): TransElem = ???
  override def reflect(line: Sline): TransElem = ???

  override def scaleXY(xOperand: Double, yOperand: Double): TransElem = ???


}

/** Companion object for the Rectlign class */
object Rectlign
{ def apply(cen: Vec2, width: Double, height: Double): Rectlign = new Rectlign(width, height, cen.x, cen.y)
}