/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Displays grids on world as well as land masss outlines. */
class GridWorldGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGView, isFlat: Boolean) extends GlobeGui("Grid World")
{
  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)
  implicit val gridSys: EGridSys = scenIn.gridSys
  var scale: Length = gridSys.cScale / viewIn.cPScale
  def gScale: Double = gridSys.cScale / scale
  def ifGScale(minScale: Double, elems : => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, RArr[GraphicElem]())
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)
  //def view: HGridView = HGridView()

  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))// gridSys.projection(mainPanel)
  proj.setView(viewIn)

  val terrs: HCenLayer[WTile] = scenIn.terrs
  val sTerrs: HSideBoolLayer = scenIn.sTerrs

  val g0Str: String = gridSys match
  { case hgm: HGridMulti => s"grid0: ${hgm.grids(0).numSides}"
    case _ => "Single grid"
  }

  val sideError = gridSys.numSides - gridSys.numInnerSides - gridSys.numOuterSides
  deb(s"In: ${gridSys.numInnerSides}, out: ${gridSys.numOuterSides}, total: ${gridSys.numSides}, error: $sideError, $g0Str" )

  def frame: RArr[GraphicElem] =
  {
    def irrFills: GraphicElems = proj match {
      case ep: HSysProjectionEarth => ep.irrFills//Lines2
      case _ => RArr()
    }

    def rcTexts = proj.ifTileScale(82, optTexts)

    def optTexts = terrs.hcOptFlatMap{ (hc, terr) =>
      proj.transOptCoord(hc).map{ pt =>
        val strs: StrArr = StrArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
      }
    }
    def optTexts2 = ifGScale(5, optTexts)

    def tiles = gridSys.optMap{ hc => proj.transTile(hc).map(poly => poly.fill(terrs(hc).colour)) }
    def sides1: GraphicElems = sTerrs.projTruesLineSegMap{ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Colour.DarkBlue) }
    def innerSidesDraw = proj.innerSidesDraw(2, White)
    def innerSidesDraw2 = ifGScale(5, RArr(innerSidesDraw))

    def straits: LineSegArr =  proj.transHSides(sTerrs.trueHSides)
    def straitsDraw: GraphicElems = straits.map{ ls  => Rectangle.fromAxisRatio(ls, 0.3).fill(Red) }

    def outerLines = proj.outerSidesDraw(3, Gold)//  ifGScale(4, outers4)

    def seas: GraphicElems = proj match{
      case ep: HSysProjectionEarth => RArr(earth2DEllipse(ep.scale).fill(LightBlue))
      case _ => RArr()
    }

    def irrLines: GraphicElems = proj match{
      case ep: HSysProjectionEarth => ep.irrLines2
      case _ => RArr()
    }

    def irrNames: GraphicElems = proj match {
      case ep: HSysProjectionEarth => ep.irrNames2
      case _ => RArr()
    }
    seas ++ irrFills ++ irrNames ++ tiles ++ sides1 ++ innerSidesDraw2 +% outerLines ++ rcTexts ++ irrLines ++ straitsDraw
  }
  def repaint(): Unit = mainRepaint(frame)
  def thisTop(): Unit = reTop(proj.buttons)

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
   // statusText = str
    thisTop()
  }
  thisTop()
  repaint()
}

object GridWorldGui
{ def apply(canv: CanvasPlatform, grid: EScenBasic, view: HGView, isFlat: Boolean): GridWorldGui = new GridWorldGui(canv,grid, view, isFlat)
}