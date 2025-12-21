/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pglobe
import pgui.*

abstract class GlobeGui(val title: String) extends CmdBarGui
{ var focus: LatLongDirn
  def nthSth: NorthSouthUp = focus.dirn
  def northUp: Boolean = focus.dirn.northUp  

  /** The length normally shown in kms per pixel. */
  var scale: LengthMetric

  def ifScale(minScale: LengthMetric, inp: => GraphicElems): GraphicElems = ife(scale < minScale, inp, RArr[Graphic2Elem]())

  def scaleStr = s"scale = ${scale.kilometresNum.str2} km/pixel"
  def repaint(): Unit

  def lat: Latitude = focus.lat
  def long: Longitude = focus.long
  def thisTop(): Unit

  def zoomIn: PolygonCompound = clickButton("+"){b =>
    scale /= b.apply(1.1, 1.3, 2, 1)
    repaint()
    statusText = scaleStr
    thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-"){b =>
    scale *= b.apply(1.1, 1.3, 2, 1)
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
    val newLat: Double = focus.latDegs + nthSth.fld(delta , -delta)
    focus = nthSth.fld(focus.addLat(delta.degsVec), focus.subLat(delta.degsVec))
  }
  def goSouth: PolygonCompound = goDirn("\u2193"){ delta =>
    val newLat: Double = focus.latDegs + nthSth.fld(-delta, delta)
    focus = nthSth.fld(focus.subLat(delta.degsVec), focus.addLat(delta.degsVec))
  }
  def goEast: PolygonCompound = goDirn("\u2192"){ delta => focus = nthSth.fld(focus.addLongVec(delta.degsVec), focus.subLong(delta.degsVec)) }
  def goWest: PolygonCompound = goDirn("\u2190"){ delta => focus = nthSth.fld(focus.subLong(delta.degsVec), focus.addLongVec(delta.degsVec)) }
}