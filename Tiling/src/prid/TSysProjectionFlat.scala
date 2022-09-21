/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

trait TSysProjectionFlat extends TSysProjection
{
  /** The number of pixels per tile grid column unit. */
  var pixCScale: Double

  /** The number of pixels per tile grid row unit. */
  def pixRScale: Double

  /** The number of pixels per tile from side to opposite side. */
  def pixTileScale: Double

  var focus: Vec2
  def setGChild: Unit

  def pixTileScaleStr = s"scale = ${pixTileScale.str2} pixels per tile"

  final override val buttons: Arr[PolygonCompound] = Arr(zoomIn, zoomOut, focusLeft, focusRight, focusUp, focusDown)

  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    pixCScale *= 1.1
    setGChild
    panel.repaint(getFrame())
    setStatusText(pixTileScaleStr)
  }

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    pixCScale /= 1.1
    setGChild
    panel.repaint(getFrame())
    setStatusText(pixTileScaleStr)
  }

  def focusAdj(uniStr: String)(f: (Vec2, Double) => Vec2): PolygonCompound = clickButton(uniStr) { butt =>
    val delta = butt(1, 10, 100, 0)
    focus = f(focus, pixCScale * delta / 40)
    setGChild
    panel.repaint(getFrame())
    setStatusText(focus.strSemi(2, 2))
  }

  def focusLeft: PolygonCompound = focusAdj("\u2190") { (v, d) =>
    val newX: Double = (v.x - d).max(gridSys.left)
    Vec2(newX, v.y)
  }

  def focusRight: PolygonCompound = focusAdj("\u2192") { (v, d) =>
    val newX: Double = (v.x + d).min(gridSys.right)
    Vec2(newX, v.y)
  }

  def focusUp: PolygonCompound = focusAdj("\u2191") { (v, d) =>
    val newY: Double = (v.y + d).min(gridSys.top)
    Vec2(v.x, newY)
  }

  def focusDown: PolygonCompound = focusAdj("\u2193") { (v, d) =>
    val newY: Double = (v.y - d).max(gridSys.bottom)
    Vec2(v.x, newY)
  }
}