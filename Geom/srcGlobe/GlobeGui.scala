/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import ostrat.pgui._

abstract class GlobeGui(title: String) extends CmdBarGui(title)
{
  var focus: LatLong
  var scale: Length
  def scaleStr = s"scale = ${scale.kMetresNum.str2} km/pixel"
  def repaint(): Unit
  var northUp: Boolean = true
  def lat: Latitude = focus.lat
  def long: Longitude = focus.long
  def thisTop(): Unit

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
  def goEast: PolygonCompound = goDirn("\u2192"){ delta => focus = ife(northUp, focus.addLongVec(delta.degs), focus.subLong(delta.degs)) }
  def goWest: PolygonCompound = goDirn("\u2190"){ delta => focus = ife(northUp, focus.subLong(delta.degs), focus.addLongVec(delta.degs)) }
}