/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Displays grids on world as well as land mass outlines. */
class EGTerrOnlyGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGView, isFlat: Boolean, irregsOn: Boolean = true) extends EGridBaseGui("Grid World")
{ deb("Starting EGTerrOnlyGui")
  val scen: EScenBasic = scenIn
  val eas: RArr[EarthPoly] = earthAllRegions.flatMap(_.ePolys)
  implicit val gridSys: EGridSys = scen.gridSys

  var scale: LengthMetric = gridSys.cScale / viewIn.pixelsPerC
  var focus: LatLongDirn = gridSys.hCoordLL(viewIn.hCoord).andDirn(viewIn.nthSth)
  var sideDrawOn: Boolean = false
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)
  proj match { case ep: HSysProjectionEarth => ep.irrOn = irregsOn; case _ => }
  statusText = scen.title

  val terrs: LayerHcRefSys[WTile] = scen.terrs
  val sTerrs: LayerHSOptSys[WSep, WSepSome] = scen.sTerrs
  val corners: HCornerLayer = scen.corners

  val g0Str: String = gridSys match
  { case hgm: EGridMulti => s"grid0: ${hgm.grids(0).numSides}"
    case _ => "Single grid"
  }

  val sideError = gridSys.numSides - gridSys.numInnerSides - gridSys.numOuterSides
  //deb(s"In: ${gridSys.numInnerSides}, out: ${gridSys.numOuterSides}, total: ${gridSys.numSides}, error: $sideError, $g0Str" )

  def frame: RArr[GraphicElem] =
  {
    def outerLines = proj.outerSidesDraw(3, Gold)

    def rcTexts1 = terrs.hcOptFlatMap { (hc, terr) =>
      proj.transOptCoord(hc).map { pt =>
        val strs: StrArr = StrArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
        TextFixed.lines(strs, 12, pt, terr.contrastBW)
      }
    }

    def rcTexts2: GraphicElems = proj.ifTileScale(82, rcTexts1)

    def ifGlobe(f: HSysProjectionEarth => GraphicElems): GraphicElems = proj match
    { case ep: HSysProjectionEarth => f(ep)
      case _ => RArr()
    }

    def seas: GraphicElems = ifGlobe{ep => RArr(earth2DEllipse(ep.metresPerPixel).fill(LightBlue)) }
    def irrFills: GraphicElems = proj match { case ep: HSysProjectionEarth => ep.irrFills; case _ => RArr() }
    def irrLines: GraphicElems = ifGlobe{ ep => ep.irrLines2 }
    def irrActives: GraphicElems = ifGlobe { ep => ep.irrActives2 }

    def sideDraws2: RArr[GraphicElem] = ife(sideDrawOn, sepDraws, RArr[GraphicElem]())

    seas ++ irrFills ++ irrActives ++ tileFills ++ tileActives ++ sepFills ++ sepActives ++ lines2 ++ sideDraws2 ++ rcTexts2 ++ irrLines +% outerLines
  }

  override def selectedStr: String = selectStack.mkStrSemis {
    case hc: HCen => scen.hexNames(hc).emptyMap("Hex") -- hc.rcStr -- terrs(hc).strSemi
    case sc: HSep => "Sep" -- sc.rcStr -- sTerrs(sc).strSemi
    case eai: IslandPoly => eai.strWithGroups
    case ipp: IslandPartPoly => ipp.name -- ipp.area.str
    case obj => obj.toString
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selectStack = cl
      selected = cl.headOrNone
      statusText = selectedStr
      thisTop()
    }
    case (RightButton, _, h) => deb("Right mouse button; " + h.toString)
    case (MiddleButton, _, h) => deb("Middle mouse button; " + h.toString)
    case (_, _, h) => deb("Other; " + h.toString)
  }

  def showSideDraw: PolygonCompound = clickButton("S") { b =>
    sideDrawOn = !sideDrawOn
    repaint()
    statusText = ife(sideDrawOn, "Side Draw on", "Side Draw off")
    thisTop()
  }

  def thisTop(): Unit = reTop(proj.buttons +% showSideDraw)

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  thisTop()
  repaint()
}

object EGTerrOnlyGui
{ def apply(canv: CanvasPlatform, grid: EScenBasic, view: HGView, isFlat: Boolean, irregsOn: Boolean = true): EGTerrOnlyGui =
    new EGTerrOnlyGui(canv,grid, view, isFlat, irregsOn)
}