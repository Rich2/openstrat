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
  var long: Longitude = Longitude.degs(20)

  def scaleStr = s"scale = ${scale.kMetresNum.str2} km/pixel"

  val lp = LinePathLL(0 ll 0, 5 ll 0, 5 ll 5, 10 ll 10)
  val lp2 = lp.map(_.toMetres3)
  val lp3 = lp2.map(_.xy)
  val lp4 = lp3.map(_ / scale)

  val p1 = SaharaWest.polygonLL
  debvar(p1)
  val p2 = p1.llLongAdd(long)
  debvar(p2)

  def repaint(): Unit = {
    val eas: Arr[EarthLevel2] = EarthAreas.allTops.flatMap(_.a2Arr)
    //val afPairs: Arr[(EarthLevel2, PolygonMetre3)] = eas.map { a => (a, a.polygonLL.metres3Default) }.filter(_._2.zNonNeg)
    val afps: Arr[(EarthLevel2, PolygonMetre)] = eas.map { a => (a, a.polygonLL.metres3Default.earthZPosXYModify) }//.filter(_._2.zNonNeg)
    val af0 = afps.map { p => p._2.map(_ / scale).fill(p._1.colour) }
    val af1 = afps.map { a => a._2.map(_ / scale).draw() }

    def seas = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint((seas +: af0) ++ af1 +- lp4.draw())
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
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut))

  repaint()
  thisTop()
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("Earth", (EarthBasicGui.apply(_), "JavaFx Earth"))