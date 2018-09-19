/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import Colour.Black

trait PolyElem[A] extends Any with PaintElem[A]
{
   def verts: Vec2s
   def xHead: Double = verts.head1
   def yHead: Double = verts.head2
   def vertsLength: Int = verts.length
   def xArray: Array[Double] = verts.elem1sArray
   def yArray: Array[Double] = verts.elem2sArray
}

case class PolyFill(verts: Vec2s, colour: Colour) extends PolyElem[PolyFill]
{ override def fTrans(f: Vec2 => Vec2): PolyFill = PolyFill(verts.fTrans(f), colour) }

case class PolyDraw(verts: Vec2s, lineWidth: Double, colour: Colour = Black) extends PolyElem[PolyDraw]
{ override def fTrans(f: Vec2 => Vec2): PolyDraw = PolyDraw(verts.fTrans(f), lineWidth, colour) }

case class PolyFillDraw(verts: Vec2s, fillColour: Colour, lineWidth: Double, lineColour: Colour = Black) extends PolyElem[PolyFillDraw]
{ override def fTrans(f: Vec2 => Vec2) = PolyFillDraw(verts.fTrans(f), fillColour, lineWidth, lineColour) }

case class PolyOpenDraw(polyOpen: PolyOpen, lineWidth: Double, colour: Colour = Black) extends PaintElem[PolyOpenDraw]
{ override def fTrans(f: Vec2 => Vec2): PolyOpenDraw = PolyOpenDraw(polyOpen.fTrans(f), lineWidth, colour) }