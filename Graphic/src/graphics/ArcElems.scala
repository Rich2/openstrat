/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import pCanv._, Colour.Black

/** Circular Arc Draw PaintElem. */
final case class CArcDraw(arc: CArc, lineWidth: Double = 2.0, colour: Colour = Black) extends PaintElem with TransSimerUser
{ type ThisT = CArcDraw
  type MemT = CArc
  override def geomMem: CArc = arc
  override def newThis(transer: CArc): CArcDraw = CArcDraw(transer, lineWidth, colour)
  override def rendToCanvas(cp: CanvasPlatform): Unit = cp.cArcDraw(this)
}