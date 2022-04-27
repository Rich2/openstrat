/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._

class GridWorldGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGridView) extends GlobeGui("Grid World")
{
  //def view: HGridView = HGridView()


  val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)
  implicit val gridSys: EGridMainSys = scenIn.eGrid
  var scale: Length = gridSys.cScale / viewIn.pxScale
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)

  val terrs: HCenDGrid[WTile] = scenIn.terrs
  val gls: LatLongArr = gridSys.map{hc => gridSys.hCoordLL(hc)}

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

    def cenTexts = ife(scale <= 1.km, optTexts, Arr[GraphicElem]())

    def optTexts = gridSys.flatMap { hc =>
      val gls: LatLong = gridSys.hCoordLL(hc)
      val g2 = gls.toMetres3.fromLatLongFocus(focus)
      val strs: Strings = Strings(hc.rcStr32, gls.degStr, hc.strComma)
      TextGraphic.lines(strs, 10, g2.xy / scale, Colour.White)
    }

    val polys = gridSys.map{ hc =>
      val col = terrs(hc).colour
      val p = hc.hVertPolygon.map(gridSys.hCoordLL(_)).toMetres3.fromLatLongFocus(focus).map(_.xy)
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

    mainRepaint(seas %: af0 ++ polys2 ++ cenTexts)// ++ af1)// ++ af2)
  }
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))
  thisTop()
  repaint()
}

object GridWorldGui
{ def apply(canv: CanvasPlatform, grid: EScenBasic/* EGrid80Main*/, view: HGridView): GridWorldGui = new GridWorldGui(canv,grid, view)
}