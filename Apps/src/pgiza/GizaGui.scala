/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pgiza
import geom.*, pgui.*

class GizaGui(val canv: CanvasPlatform) extends CmdBarGui
{
  override def title: String = "Giza Pyramids"
  def frame: RArr[Graphic2Elem] = Giza.pyramids.flatMap(_.baseGraphics).mapGeom2(1.metres)
  def repaint(): Unit = mainRepaint(frame)
  repaint()
}