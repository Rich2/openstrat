/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

/** The base trait for all objects on a canvas / panel. The objects are recomposed for each frame. The Canvas objects must be recomposed
 *  each time there is a change within the application state or the user view of that application state. */
trait GraphicElem[A] extends Any with Transable[A]

/* Base trait for all passive objects  on a canvas / panel */
trait PaintElem[A] extends Any with GraphicElem[A]

case class ShapeFill(segs: CurveSegs, fillColour: Colour) extends PaintElem[ShapeFill]
{ override def fTrans(f: Vec2 => Vec2) = ShapeFill(segs.fTrans(f), fillColour) }

case class ShapeDraw(segs: CurveSegs, lineWidth: Double, lineColour: Colour = Black) extends PaintElem[ShapeDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeDraw(segs.fTrans(f), lineWidth, lineColour) }

case class ShapeFillDraw(segs: CurveSegs, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PaintElem[ShapeFillDraw]
{ override def fTrans(f: Vec2 => Vec2) = ShapeFillDraw(segs.fTrans(f), fillColour, lineWidth, lineColour) }



