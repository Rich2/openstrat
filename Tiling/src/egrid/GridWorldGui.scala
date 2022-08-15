/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Displays grids on world as well as land masss outlines. */
class GridWorldGui(val canv: CanvasPlatform, scenIn: EScenWarm, viewIn: HGView) extends GlobeGui("Grid World")
{
  val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)
  implicit val gridSys: EGridWarmSys = scenIn.gridSys
  var scale: Length = gridSys.cScale / viewIn.pxScale
  def gScale: Double = gridSys.cScale / scale
  def ifGScale(minScale: Double, elems : => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, Arr[GraphicElem]())
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)
  //def view: HGridView = HGridView()

  val proj = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  val terrs: HCenDGrid[WTile] = scenIn.terrs
  val sTerrs: HSideBoolDGrid = scenIn.sTerrs
  val gls: LatLongArr = gridSys.map{hc => gridSys.hCoordLL(hc) }

  val g0Str: String = gridSys match {
    case hgm: HGridMulti => s"grid0: ${hgm.grids(0).numSides}"
    case _ => "Single grid"
  }
  val sideError = gridSys.numSides - gridSys.numInnerSides - gridSys.numOuterSides
  deb(s"In: ${gridSys.numInnerSides}, out: ${gridSys.numOuterSides}, total: ${gridSys.numSides}, error: $sideError, $g0Str" )

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

    def rcTexts = ifGScale(20.5, optTexts)

    def optTexts = terrs.hcFlatMap{ (hc, terr) =>
      proj.transCoord(hc).foldToGraphics{ pt =>
        val strs: StringArr = StringArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
      }
    }

    val hexs1 = gridSys.map{ hc =>
      val col = terrs(hc).colour
      val p = hc.hVertPolygon.map(gridSys.hCoordLL(_)).toMetres3.fromLatLongFocus(focus)//.map(_.xy)
      (p, col)
    }

    val hexs2 = hexs1.map{ (p, col: Colour) => p.map(_.xy / scale).fill(col) }

    def innerSides = proj.innerSidesDraw(2, White) //lines3.map(_.xyLineSeg(scale).draw(White))
    def innerSidesDraw = ifGScale(5, Arr(innerSides))

    def straits: LineSegArr =  proj.transHSides(sTerrs.trueHSides)
    def straitsDraw: GraphicElems = straits.map{ ls  => Rectangle.fromAxisRatio(ls, 0.3).fill(Red) }

    def outerLines = proj.outerSidesDraw(3, Gold)//  ifGScale(4, outers4)

    val irrLines = irr1.map { a => a._2.map(_ / scale).draw(White) }
    def irrLines2 = ifGScale(2, irrLines)

    val irrNames = irr1.map { pair =>
      val (d, _) = pair
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 12, posn, d.colour.contrastBW)
    }
    def irrNames2 = ifGScale(4, irrNames)

    def seas: EllipseFill = earth2DEllipse(scale).fill(LightBlue)

    mainRepaint(seas %: irrFills ++ irrNames2 ++ hexs2 ++ innerSidesDraw +% outerLines ++ rcTexts ++ irrLines2 ++ straitsDraw)
  }
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))
  thisTop()
  repaint()
}

object GridWorldGui
{ def apply(canv: CanvasPlatform, grid: EScenWarm, view: HGView): GridWorldGui = new GridWorldGui(canv,grid, view)
}