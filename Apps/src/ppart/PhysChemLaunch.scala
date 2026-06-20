/* Copyright 2025-6 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package ppart
import geom.pgui.*, pParse.*

object PhysChemLaunch  extends GuiLaunchMore
{ override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) = (ParticleGui(_), "JavaFx PhysChem")
  override def settingStr: String = "PhysChem"
  override def default: (CanvasPlatform => Any, String) = (ParticleGui(_), "JavaFx PhysChem")
}