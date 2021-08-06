/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pCanv._, geom._

/** Basic map of the Earth using irregular areas. */
case class EarthBasicGui(canv: CanvasPlatform, startScale: Option[Metres] = None, startFocus: Option[LatLong] = None) extends
  CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."
  def thisTop(): Unit = reTop(Arr())
  thisTop()
  var scale: Length = startScale.getOrElse(8.kMetres)

  val lp = LinePathLL(0 ll 0, 5 ll 0, 5 ll 5, 10 ll 10)
  val lp2 = lp.map(_.toMetres3)
  val lp3 = lp2.map(_.xy)
  val lp4 = lp3.map(_ / scale)

  val eas: Arr[EarthLevel2] =  Arr(AfricaWest, AfricaEast, AfricaSouthern).flatMap(_.a2Arr)
  val af0 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon((p: PtMetre2) => p / scale).fill(a.colour) }
  val af1 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon(_ / scale).draw() }
 // val af2 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon(_ / 10.km).drawT draw() }

  mainRepaint(af0 ++ af1 +- lp4.draw())
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("earth", (EarthBasicGui.apply(_), "JavaFx Earth"))