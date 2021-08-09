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

  val aw0 = AfricaWestPts.westAfricaSouth.polygonLL
  //deb(aw0.toString)
  val eas: Arr[EarthLevel2] =  Arr(Canarias, Sicily, Majorca, SaharaWest, AfricaWestPts.westAfricaSouth)//*Arr(*/AfricaWest/*, AfricaEast, AfricaSouthern).flatM*/.a2Arr
  val af0 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon((p: PtMetre2) => p / scale).fill(a.colour) }
  val af1 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon(_ / scale).draw() }
  val a2: Arr[PolygonLL] = eas.map(_.polygonLL)
  deb(a2(0).toString)
  val a3: Arr[PolygonMetre3] = a2.map(_.metres3Default)
  deb(a3(0).toString)
 // val af2 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon(_ / 10.km).drawT draw() }
  val ca = (27.72 ll -18.15).toMetres3
  debvar(ca)
  mainRepaint(af0 ++ af1 +- lp4.draw())
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("earth", (EarthBasicGui.apply(_), "JavaFx Earth"))