/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

trait ShapeElem extends PaintElem
{ def segs: Shape
  def segsLen: Int = segs.length
}

case class ShapeFill(segs: Shape, colour: Colour, zOrder: Int = 0) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeFill(segs.fTrans(f), colour, zOrder)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.shapeFill(this)
}

case class ShapeDraw(segs: Shape, lineWidth: Double, colour: Colour = Black, zOrder: Int = 0) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(segs.fTrans(f), lineWidth, colour, zOrder)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.shapeDraw(this)
}

case class ShapeFillDraw(segs: Shape, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black, zOrder: Int = 0) extends ShapeElem
{ override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(segs.fTrans(f), fillColour, lineWidth, lineColour, zOrder)
  override def rendElem(cp: pCanv.CanvasPlatform): Unit = cp.shapeFillDraw(this)
}
 