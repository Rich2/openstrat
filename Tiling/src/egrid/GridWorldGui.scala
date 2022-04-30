/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

class GridWorldGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGridView) extends GlobeGui("Grid World")
{
  val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)
  implicit val gridSys: EGridMainSys = scenIn.gridSys
  var scale: Length = gridSys.cScale / viewIn.pxScale
  def gScale: Double = gridSys.cScale / scale
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)
  //def view: HGridView = HGridView()
  val terrs: HCenDGrid[WTile] = scenIn.terrs
  val gls: LatLongArr = gridSys.map{hc => gridSys.hCoordLL(hc)}

  def repaint(): Unit =
  {
    val irr0: Arr[(EArea2, PolygonM)] = eas.map(_.withPolygonM(focus, northUp))
    val irr1 = irr0.filter(_._2.vertsMin3)

    val irrFills = irr1.map { pair =>
      val (d, p) = pair
      val col = d.terr match{
        case w: Water => BlueViolet
        case _ => Colour.LightPink
      }
      p.map(_ / scale).fill(col)
    }

    def rcTexts = ife(gScale >= 17, optTexts, Arr[GraphicElem]())

    def optTexts = terrs.hcFlatMap{ (hc, terr) =>
      val gls: LatLong = gridSys.hCoordLL(hc)
      val g2 = gls.toMetres3.fromLatLongFocus(focus)
      val strs: Strings = Strings(hc.rcStr32, gls.degStr, hc.strComma)
      TextGraphic.lines(strs, 10, g2.xy / scale, terr.contrastBW)
    }

    val hexs1 = gridSys.map{ hc =>
      val col = terrs(hc).colour
      val p = hc.hVertPolygon.map(gridSys.hCoordLL(_)).toMetres3.fromLatLongFocus(focus).map(_.xy)
      (p, col)
    }

    val hexs2 = hexs1.map{ pair =>
      val (p, col) = pair
      p.map(_ / scale).fill(col)
    }

    val lines = gridSys.sideLineLLs// sideLineSegHCs.map(lsh => LineSegLL(gridSys.hCoordLL(lsh.startPt), gridSys.hCoordLL(lsh.endPt)))

    val irrLines = irr1.map { a => a._2.map(_ / scale).draw(White) }

    val irrNames = irr1.map { pair =>
      val (d, _) = pair
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    def seas: EllipseFill = earth2DEllipse(scale).fill(LightBlue)

    mainRepaint(seas %: irrFills ++ irrNames ++ hexs2 ++ rcTexts ++ irrLines)
  }
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))
  thisTop()
  repaint()
}

object GridWorldGui
{ def apply(canv: CanvasPlatform, grid: EScenBasic/* EGrid80Main*/, view: HGridView): GridWorldGui = new GridWorldGui(canv,grid, view)
}