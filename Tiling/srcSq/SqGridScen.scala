/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import geom._, pgui._

abstract class SqSysGui(title: String) extends TGridSysGui(title)
{ override def pixPerTile: Double = pixPerC * 2

  def zoomIn: PolygonCompound = clickButton("+") { _ =>
    pixPerC *= 1.1
    repaint()
    statusText = tilePScaleStr
    thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-") { _ =>
    pixPerC /= 1.1
    repaint()
    statusText = tilePScaleStr
    thisTop()
  }

  def focusAdj(uniStr: String)(f: (Vec2, Double) => Vec2): PolygonCompound = clickButton(uniStr) { butt =>
    val delta = butt(1, 10, 100, 0)
    focus = f(focus, pixPerC * delta / 40)
    repaint()
    statusText = focus.strSemi(2, 2)
    thisTop()
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

  def navButtons: RArr[PolygonCompound] = RArr(zoomIn, zoomOut, focusLeft, focusRight, focusUp, focusDown)
}

trait SqGridScen extends GridTurnScen
{
  /** This gives the structure of the square grid. It contains no data about the elements of the grid. But it allows the scenario to create and
   *  operate on flat arrays of data. */
  implicit val gridSys: SqGridSys

  def defaultView(pxScale: Double = 50): SGView = gridSys.defaultView(pxScale)
}