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
      val gls: LatLong = gridSys.hCoordLL(hc)
      val g2 = gls.toMetres3.fromLatLongFocus(focus)
      val strs: StringArr = StringArr(hc.rcStr32, gls.degStr, hc.strComma)
      TextGraphic.lines(strs, 12, g2.xy / scale, terr.contrastBW)
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

    def lines = gridSys.innerSideLineM3s
    def lines2 = lines.fromLatLongFocus(focus)
    def lines3 = lines2.filter(_.zsPos)
    //def lines4: LineSegArr = lines3.map(_.xyLineSeg(scale))
    def lines5 = lines3.map(_.xyLineSeg(scale).draw(White))
    def lines6 = ifGScale(5, lines5)

//    def sides0: Arr[(HSide, LineSegLL)] = gridSys.sidesMap{ sd => (sd, sd.lineSegHC.map(gridSys.hCoordLL(_))) }
//    def sides1: Arr[(HSide, LineSegM3)] = sides0.map{ (sc, ls) => (sc, ls.map(_.toMetres3)) }
//    def sides2: Arr[(HSide, LineSegM3)] = sides1.map{ (sc, ls) => (sc, ls.map(_.fromLatLongFocus(focus))) }
//    def sides3: Arr[(HSide, LineSegM3)] = sides2.filter((sc, ls) => ls.zsPos)
//    def sides4: Arr[(HSide, LineSeg)] = sides3.map{ (sc, ls) => (sc, ls.map(_.xy / scale)) }

    val sides0 = sTerrs.truesMap(_.lineSegHC.map(gridSys.hCoordLL(_)))
    def sides1: LineSegM3Arr = sides0.map{ _.map(_.toMetres3) }
    def sides2: LineSegM3Arr = sides1.map{ _.map(_.fromLatLongFocus(focus)) }
    def sides3: LineSegM3Arr = sides2.filter(_.zsPos)
    def sides4: LineSegArr = sides3.map{ _.map(_.xy / scale) }
    def sides: GraphicElems = sides4.map{ ls  => Rectangle.fromAxisRatio(ls, 0.3).fill(Red) }

    val outers = gridSys.outerSideLineM3s
    val outers2 = outers.fromLatLongFocus(focus)
    val outers3 = outers2.filter(_.zsPos)
    val outers4 = outers3.map(_.xyLineSeg(scale).draw(Gold, 3))
    def outers5 = Arr(proj.outerSidesDraw(3, Gold))//  ifGScale(4, outers4)

    val irrLines = irr1.map { a => a._2.map(_ / scale).draw(White) }
    def irrLines2 = ifGScale(2, irrLines)

    val irrNames = irr1.map { pair =>
      val (d, _) = pair
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 12, posn, d.colour.contrastBW)
    }
    def irrNames2 = ifGScale(4, irrNames)

    def seas: EllipseFill = earth2DEllipse(scale).fill(LightBlue)

    mainRepaint(seas %: irrFills ++ irrNames2 ++ hexs2 ++ lines6 ++ outers5 ++ rcTexts ++ irrLines2 ++ sides)
  }
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))
  thisTop()
  repaint()
}

object GridWorldGui
{ def apply(canv: CanvasPlatform, grid: EScenWarm, view: HGView): GridWorldGui = new GridWorldGui(canv,grid, view)
}