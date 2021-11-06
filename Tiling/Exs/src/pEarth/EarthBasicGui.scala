/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, startScale: Option[Metres] = None, startFocus: Option[LatLong] = None) extends
  CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."

  /** Scale in km / pixel */
  var scale: Length = startScale.getOrElse(12.kMetres)
  var long: Longitude = Longitude.degs(0)

  def scaleStr = s"scale = ${scale.kMetresNum.str2} km/pixel"

  def repaint(): Unit = {
    val eas: Arr[EarthLevel2] = EarthAreas.allTops.flatMap(_.a2Arr)
    val afps: Arr[(EarthLevel2, PolygonMetre)] = eas.map { a => (a, a.polygonLL.subLong(long).metres3Default.earthZPosXYModify) }
    val af0 = afps.map { p => p._2.map(_ / scale).fill(p._1.colour) }
    val af1 = afps.map { a => a._2.map(_ / scale).draw() }

    def seas = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint((seas +: af0) ++ af1)
  }

  def zoomIn: PolygonCompound = clickButton("+"){_ =>
    scale /= 1.1
    repaint()
    statusText = scaleStr
    thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-"){_ =>
    scale *= 1.1
    repaint()
    statusText = scaleStr
    thisTop()
  }

  def goEast: PolygonCompound = clickButton("\u2192"){b =>
    def delta = b.apply(1, 10, 90, 0)
    long += Longitude.degs(delta)
    repaint()
    statusText = s"Longitude $long"
    thisTop()
  }

  def goWest: PolygonCompound = clickButton("\u2190"){b =>
    def delta = b.apply(1, 10, 90, 0)
    long -= Longitude.degs(delta)
    repaint()
    statusText = s"Longitude $long"
    thisTop()
  }

  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goWest, goEast))

  repaint()
  thisTop()
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("Earth", (EarthBasicGui.apply(_), "JavaFx Earth"))