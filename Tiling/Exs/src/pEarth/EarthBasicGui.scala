/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, startScale: Option[Length] = None, startFocus: Option[LatLong] = None) extends
  CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."

  /** Scale in km / pixel */
  var scale: Length = startScale.getOrElse(12.kMetres)
  var focus: LatLong = startFocus.sget
  var northUp: Boolean = true
  def lat: Latitude = focus.lat
  def long: Longitude = focus.long

  def scaleStr = s"scale = ${scale.kMetresNum.str2} km/pixel"

  def repaint(): Unit = {
    val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)

    val afps: Arr[(EArea2, PolygonM)] = eas.map { a =>
      val p3s: PolygonM3 = a.polygonLL.metres3Default.fromLatLongFocus(focus)
      val p3s2: PolygonM3 = ife(northUp, p3s, p3s.rotateZ180)
      val p3s3 = p3s2.earthZPosXYModify
      (a, p3s3)
    }

    val afps2 = afps.filter(_._2.vertsMin3)
    val af0 = afps2.map { pair =>
      val (d, p) = pair
      p.map(_ / scale).fillActive(d.colour, d)
    }

    val af1 = afps2.map { a => a._2.map(_ / scale).draw() }
    val af2 = afps2.map { pair =>
      val (d, _) = pair
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    def seas = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint(seas %: af0 ++ af1 ++ af2)
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

  def goDirn(str: String)(f: Double => Unit): PolygonCompound = clickButton(str){b =>
    val delta: Int = b.apply(1, 10, 60, 0)
    f(delta)
    repaint()
    statusText = s"focus $focus"
    thisTop()
  }

  def goNorth: PolygonCompound = goDirn("\u2191"){ delta =>
    val newLat: Double = focus.latDegs + ife(northUp, delta , -delta)
    focus = ife(northUp, focus.addLat(delta.degs), focus.subLat(delta.degs))
    northUp = ife(newLat > 90 | newLat < -90, !northUp, northUp)
  }
  def goSouth: PolygonCompound = goDirn("\u2193"){ delta =>
    val newLat: Double = focus.latDegs + ife(northUp, -delta, delta)
    focus = ife(northUp, focus.subLat(delta.degs), focus.addLat(delta.degs))
    northUp = ife(newLat > 90 | newLat < -90, !northUp, northUp)
  }
  def goEast: PolygonCompound = goDirn("\u2192"){ delta => focus = ife(northUp, focus.addLong(delta.degs), focus.subLong(delta.degs)) }
  def goWest: PolygonCompound = goDirn("\u2190"){ delta => focus = ife(northUp, focus.subLong(delta.degs), focus.addLong(delta.degs)) }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected").newLinesToSpaces
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))

  repaint()
  thisTop()
}