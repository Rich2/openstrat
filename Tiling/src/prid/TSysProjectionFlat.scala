/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid
import geom._, pgui._

trait TSysProjectionFlat extends TSysProjection
{
  /** The number of pixels per tile grid column unit. */
  var pixelsPerC: Double

  /** The number of pixels per tile grid row unit. */
  def pixelsPerR: Double

  var focus: Vec2




  final override val buttons: RArr[PolygonCompound] = RArr(zoomIn, zoomOut, focusLeft, focusRight, focusUp, focusDown)


  def focusAdj(uniStr: String)(f: (Vec2, Double) => Vec2): PolygonCompound = clickButton(uniStr) { butt =>
    val delta = butt(1, 10, 100, 0)
    focus = f(focus, pixelsPerC * delta / 40)
    setGChild
    panel.repaint(getFrame())
    setStatusText(focus.strSemi(2, 2))
  }

  def focusLeft: PolygonCompound = focusAdj("\u2190") { (v, d) =>
    val newX: Double = (v.x - d).max(parent.left)
    Vec2(newX, v.y)
  }

  def focusRight: PolygonCompound = focusAdj("\u2192") { (v, d) =>
    val newX: Double = (v.x + d).min(parent.right)
    Vec2(newX, v.y)
  }

  def focusUp: PolygonCompound = focusAdj("\u2191") { (v, d) =>
    val newY: Double = (v.y + d).min(parent.top)
    Vec2(v.x, newY)
  }

  def focusDown: PolygonCompound = focusAdj("\u2193") { (v, d) =>
    val newY: Double = (v.y - d).max(parent.bottom)
    Vec2(v.x, newY)
  }
}