/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._, Colour.Black

case class CircleFillOld(circle: CircleOld, colour: Colour) extends TransSimerUser with PaintElemOld
{ override type AlignT = CircleFillOld
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillOld = CircleFillOld(transer, colour)
  def shear(xScale: Double, yScale: Double): Transer = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillOld(this)
}

case class CircleFill(circle: Circle, colour: Colour) extends PaintElem//New
{
  override def rendToCanvas(cp: CanvasPlatform): Unit = {}
}


case class CircleDraw(circle: CircleOld, lineWidth: Double, colour: Colour) extends TransSimerUser with PaintElemOld
{ override type AlignT = CircleDraw
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleDraw = CircleDraw(transer, lineWidth, colour)
  def shear(xScale: Double, yScale: Double): Transer = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleDraw(this)

  //override def fTrans(f: Vec2 => Vec2): GeomElem = ???
}

case class CircleFillDraw(circle: CircleOld, fillColour: Colour, lineWidth: Double = 2.0, lineColour: Colour = Black) extends TransSimerUser
  with PaintElemOld
{ override type AlignT = CircleFillDraw
  override type MemT = CircleOld
  override def geomMem: MemT = circle
  override def newThis(transer: CircleOld): CircleFillDraw = CircleFillDraw(transer, fillColour, lineWidth, lineColour)
  override def shear(xScale: Double, yScale: Double): Transer = ???
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.circleFillDraw(this)

  //override def fTrans(f: Vec2 => Vec2): GeomElem = ???
}