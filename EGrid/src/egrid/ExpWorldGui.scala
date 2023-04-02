/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Experimental Gui. Displays grids on world as well as land mass outlines. */
class ExpWorldGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGView, isFlat: Boolean) extends GlobeGui("Grid World")
{
  val scen: EScenBasic = scenIn
  deb(scen.title)
  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)
  implicit val gridSys: EGridSys = scen.gridSys

  var scale: Length = gridSys.cScale / viewIn.pixelsPerC
  def gScale: Double = gridSys.cScale / scale
  def ifGScale(minScale: Double, elems : => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, RArr[GraphicElem]())
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)

  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideLayer[WSide] = scen.sTerrs
  val corners: HCornerLayer = scen.corners

  val g0Str: String = gridSys match
  { case hgm: EGridMulti => s"grid0: ${hgm.grids(0).numSides}"
    case _ => "Single grid"
  }

  val sideError = gridSys.numSides - gridSys.numInnerSides - gridSys.numOuterSides
  deb(s"In: ${gridSys.numInnerSides}, out: ${gridSys.numOuterSides}, total: ${gridSys.numSides}, error: $sideError, $g0Str" )

  def frame: RArr[GraphicElem] =
  { def tilePolys: HCenPairArr[Polygon] = proj.hCenPolygons(corners)

    /*def tileBackFills: GraphicElems = terrs.hcOptMap { (tile, hc) =>
      tile match
      {
        /*case li: Coastal =>
        { val res = hc.hVertPolygon.toPolygon(proj.transCoord).fill(li.sideTerrs.colour)
          Some(res)
        }*/
        case _ => None
      }
    }*/

    def tileFrontFills: RArr[PolygonFill] = tilePolys.pairMap{ (hc, poly) => poly.fill(terrs(hc)(gridSys).colour) }

    def tileActives: RArr[PolygonActive] = tilePolys.pairMap{ (hc, poly) => poly.active(hc) }

    def sideFills: GraphicElems = sTerrs.somesPolyMapAlt(proj, corners){ (hs, poly, st) => poly.fill(st.colour) }

    def sideActives: GraphicElems = sTerrs.somesPolyMap(proj, corners){ (hs, poly) => poly.active(hs) }

    def lines1: GraphicElems = proj.linksOptMap { hs =>
      def t1: WTile = terrs(hs.tileLt)
      def t2: WTile = terrs(hs.tileRt)
      sTerrs(hs) match {
        case WSideNone if t1.colour == t2.colour => {
          val cs: (HCen, Int, Int) = hs.corners
          val ls1: LineSeg = corners.sideLine(cs._1, cs._2, cs._3)
          Some(ls1.draw(t1.contrastBW))
        }
        //case vs: WSideMid if vs.terr.colour == t2.colour => Some(hs.lineSegHC.lineSeg.draw(t2.contrastBW))
        //case vs: WSideMid if vs.terr.colour == t1.colour => Some(hs.lineSegHC.lineSeg.draw(t1.contrastBW))
        //case vs: WSideMid if vs.terr.colour == t2.colour => Some(hs.lineSegHC.lineSeg.draw(t2.contrastBW))
        case _ => None
      }
    }

    def lines2: GraphicElems = proj.ifTileScale(50, lines1)

    /*def lines3: GraphicElems = terrs.projHCenFlatMap { (hc, tile) =>
      tile match {
        case cst: Coastal => cst.indentedVertexIndexMap { i =>
          val p1: HVAndOffset = corners.cornerV1(hc, i)
          val p2 = hc.vExact(i)
          LineSegHVAndOffset(p1, p2).map(proj.transHVAndOffset).draw(cst.sideTerrs.contrastBW)
        }
        case _ => RArr()
      }
    }

    def lines4: GraphicElems = proj.ifTileScale(50, lines3)*/

    def outerLines = proj.outerSidesDraw(3, Gold)

    def rcTexts1 = terrs.hcOptFlatMap { (hc, terr) =>
      proj.transOptCoord(hc).map { pt =>
        val strs: StrArr = StrArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
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

    seas ++ irrFills ++ irrNames ++ /* tileBackFills ++ */ tileFrontFills ++ tileActives ++ sideFills ++ sideActives ++ lines2/* ++ lines4*//* +% outerLines&*/ ++ rcTexts2 ++ irrLines
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) =>
    { selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  def repaint(): Unit = mainRepaint(frame)
  def thisTop(): Unit = reTop(proj.buttons)

  proj.getFrame = () => frame
  proj.setStatusText = { str =>
    statusText = str
    thisTop()
  }
  thisTop()
  repaint()
}

object ExpWorldGui
{ def apply(canv: CanvasPlatform, grid: EScenBasic, view: HGView, isFlat: Boolean): ExpWorldGui = new ExpWorldGui(canv,grid, view, isFlat)
}