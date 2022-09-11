/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Displays grids on world as well as land masss outlines. */
class GridWorldGui(val canv: CanvasPlatform, scenIn: EScenWarm, viewIn: HGView) extends GlobeGui("Grid World")
{
  val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)
  implicit val gridSys: EGridWarmSys = scenIn.gridSys
  var scale: Length = gridSys.cScale / viewIn.cPScale
  def gScale: Double = gridSys.cScale / scale
  def ifGScale(minScale: Double, elems : => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, Arr[GraphicElem]())
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)
  //def view: HGridView = HGridView()

  val proj = gridSys.projection(mainPanel)
  proj.setView(viewIn)

  val terrs: HCenLayer[WTile] = scenIn.terrs
  val sTerrs: HSideBoolLayer = scenIn.sTerrs

  val g0Str: String = gridSys match {
    case hgm: HGridMulti => s"grid0: ${hgm.grids(0).numSides}"
    case _ => "Single grid"
  }
  val sideError = gridSys.numSides - gridSys.numInnerSides - gridSys.numOuterSides
  deb(s"In: ${gridSys.numInnerSides}, out: ${gridSys.numOuterSides}, total: ${gridSys.numSides}, error: $sideError, $g0Str" )

  def frame: Arr[GraphicElem] =
  {
    def irrFills: GraphicElems = proj match {
      case ep: HSysProjectionEarth => ep.irrFills//Lines2
      case _ => Arr()
    }
    def rcTexts = ifGScale(20.5, optTexts)

    def optTexts = terrs.hcOptFlatMap{ (hc, terr) =>
      proj.transOptCoord(hc).map{ pt =>
        val strs: StringArr = StringArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
      }
    }

    def tiles = gridSys.optMap{ hc => proj.transTile(hc).map(poly => poly.fill(terrs(hc).colour)) }

    def innerSides = proj.innerSidesDraw(2, White) //lines3.map(_.xyLineSeg(scale).draw(White))
    def innerSidesDraw = ifGScale(5, Arr(innerSides))

    def straits: LineSegArr =  proj.transHSides(sTerrs.trueHSides)
    def straitsDraw: GraphicElems = straits.map{ ls  => Rectangle.fromAxisRatio(ls, 0.3).fill(Red) }

    def outerLines = proj.outerSidesDraw(3, Gold)//  ifGScale(4, outers4)

    def seas: GraphicElems = proj match{
      case ep: HSysProjectionEarth => Arr(earth2DEllipse(ep.scale).fill(LightBlue))
      case _ => Arr()
    }

    def irrLines: GraphicElems = proj match{
      case ep: HSysProjectionEarth => ep.irrLines2
      case _ => Arr()
    }

    def irrNames: GraphicElems = proj match {
      case ep: HSysProjectionEarth => ep.irrNames2
      case _ => Arr()
    }
    seas ++ irrFills ++ irrNames ++ tiles ++ innerSidesDraw +% outerLines ++ rcTexts ++ irrLines ++ straitsDraw
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
{ def apply(canv: CanvasPlatform, grid: EScenWarm, view: HGView): GridWorldGui = new GridWorldGui(canv,grid, view)
}