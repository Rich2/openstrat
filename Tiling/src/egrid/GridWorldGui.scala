/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, eg80._, prid._, phex._, pEarth._, pglobe._

class GridWorldGui(val canv: CanvasPlatform, gridIn: EGrid80KmMain, viewIn: HGridView) extends GlobeGui("Grid World")
{
  var scale: Length = 1.kMetres
  var focus: LatLong = 58 ll 0
  val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)
  implicit val grid: EGrid80KmMain = gridIn
  var view: HGridView = viewIn

  val terrs = EuropeNW80Terr()
  val gls: LatLongArr = grid.map(hc => grid.hCoordLL(hc))

  def repaint(): Unit =
  {
    val afps: Arr[(EArea2, PolygonM)] = eas.map(_.withPolygonM(focus, northUp))

    val afps2 = afps.filter(_._2.vertsMin3)
    val af0 = afps2.map { pair =>
      val (d, p) = pair
      val col = d.terr match{
        case w: Water => Colour.Blue
        case _ => Colour.White
      }
      p.map(_ / scale).fill(col)//, d)
    }

    val cens = gls.map(_.toMetres3.fromLatLongFocus(focus))
    val cens2 = cens.map(v => TextGraphic("Hex", 8, v.xy / scale))
    val polys = grid.map{hc =>
      val col = terrs(hc).colour
      val p = hc.hVertPolygon.map(grid.hCoordLL(_)).toMetres3.fromLatLongFocus(focus).map(_.xy)
      (p, col)
    }
    val polys2 = polys.map{ pair =>
      val (p, col) = pair
      p.map(_ / scale).fill(col)
    }

    val af1 = afps2.map { a => a._2.map(_ / scale).draw() }
    val af2 = afps2.map { pair =>
      val (d, _) = pair
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    def seas: EllipseFill = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint(seas %: af0 ++ polys2 ++ cens2)// ++ af1)// ++ af2)
  }
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))
  thisTop()
  repaint()
}

object GridWorldGui
{ def apply(canv: CanvasPlatform, grid: EGrid80KmMain, view: HGridView): GridWorldGui = new GridWorldGui(canv,grid, view)
}