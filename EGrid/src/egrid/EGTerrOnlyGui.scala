/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Displays grids on world as well as land mass outlines. */
class EGTerrOnlyGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGView, isFlat: Boolean) extends EGridBaseGui/*GlobeGui*/("Grid World")
{ val scen: EScenBasic = scenIn
  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)
  implicit val gridSys: EGridSys = scen.gridSys

  var scale: Length = gridSys.cScale / viewIn.pixelsPerC
  def gScale: Double = gridSys.cScale / scale
  def ifGScale(minScale: Double, elems : => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, RArr[GraphicElem]())
  var focus: LatLongDirn = gridSys.hCoordLL(viewIn.hCoord).andDirn(true)
  var sideDrawOn: Boolean = false
  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)
  proj match { case ep: HSysProjectionEarth => ep.irrOn = true; case _ => }

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
    def irrNames: GraphicElems = ifGlobe{ ep => ep.irrNames2 }

    def sideDraws2: RArr[GraphicElem] = ife(sideDrawOn, sideDraws, RArr[GraphicElem]())

    seas ++ irrFills ++ irrNames ++ tileFills ++ tileActives ++ sideFills ++ sideDraws2 ++ sideActives ++ lines2 ++ rcTexts2 ++ irrLines +% outerLines
  }

  override def selectedStr: String = selected match
  { case hc: HCen => hc.rcStr -- terrs(hc).strSemi
    case sc: HSep => sc.rcStr -- sTerrs(sc).strSemi
    case _ => super.selectedStr
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
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
//  def repaint(): Unit = mainRepaint(frame)
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
{ def apply(canv: CanvasPlatform, grid: EScenBasic, view: HGView, isFlat: Boolean): EGTerrOnlyGui = new EGTerrOnlyGui(canv,grid, view, isFlat)
}