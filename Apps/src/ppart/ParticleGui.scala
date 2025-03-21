/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package ppart
import geom.*, pgui.*

class ParticleGui(val canv: CanvasPlatform) extends CanvasNoPanels("Canv1")
{ val scale = 1.picometres
  val output: GraphicElems = CO2Mc.linesCircles.map(_.mapGeom2(scale)).slateX(-400) ++ Water.linesCircles.map(_.mapGeom2(scale)).slateX(400)

  repaint(output)
}
