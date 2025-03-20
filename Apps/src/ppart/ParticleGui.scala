/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package ppart
import geom.*, Colour.*, pgui.*

class ParticleGui(val canv: CanvasPlatform) extends CanvasNoPanels("Canv1")
{ val scale = 0.01.angstroms
  val output = Water.fillDrawsOld(scale) ++ Dihydrogen.fillDrawsOld(scale, 0.picometres, 200.picometres) ++
    CO2Mc.fillDrawsOld(scale, 300.picometres, 200.picometres) ++ CO2Mc.circles.map(_.mapScalar2(scale)).slateX(-400) ++
    Water.circles.map(_.mapScalar2(scale)).slateX(400)

  repaint(output)
}
