/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pgui._, geom._, prid._, phex._, pEarth._, pglobe._, Colour._

/** Expwrimental Gui. Displays grids on world as well as land mass outlines. */
class ExpWorldGui(val canv: CanvasPlatform, scenIn: EScenBasic, viewIn: HGView, isFlat: Boolean) extends GlobeGui("Grid World")
{
  val scen: EScenBasic = scenIn
  deb(scen.title)
  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)
  implicit val gridSys: EGridSys = scen.gridSys

  var scale: Length = gridSys.cScale / viewIn.cPScale
  def gScale: Double = gridSys.cScale / scale
  def ifGScale(minScale: Double, elems : => GraphicElems): GraphicElems = ife(gScale >= minScale, elems, RArr[GraphicElem]())
  var focus: LatLong = gridSys.hCoordLL(viewIn.hCoord)

  implicit val proj: HSysProjection = ife(isFlat, HSysProjectionFlat(gridSys, mainPanel), gridSys.projection(mainPanel))
  proj.setView(viewIn)

  val terrs: HCenLayer[WTile] = scen.terrs
  val sTerrs: HSideBoolLayer = scen.sTerrs
  val corners: HCornerLayer = scen.corners

  val g0Str: String = gridSys match
  { case hgm: HGridMulti => s"grid0: ${hgm.grids(0).numSides}"
    case _ => "Single grid"
  }

  val sideError = gridSys.numSides - gridSys.numInnerSides - gridSys.numOuterSides
  deb(s"In: ${gridSys.numInnerSides}, out: ${gridSys.numOuterSides}, total: ${gridSys.numSides}, error: $sideError, $g0Str" )

  def frame: RArr[GraphicElem] =
  {
    def irrFills: GraphicElems = proj match {
      case ep: HSysProjectionEarth => ep.irrFills
      case _ => RArr()
    }

    def rcTexts1 = terrs.hcOptFlatMap{ (hc, terr) =>
      proj.transOptCoord(hc).map{ pt =>
        val strs: StrArr = StrArr(hc.rcStr32).appendOption(proj.hCoordOptStr(hc)) +% hc.strComma
        TextGraphic.lines(strs, 12, pt, terr.contrastBW)
      }
    }

    def rcTexts2: GraphicElems = proj.ifTileScale(82, rcTexts1)

    def tiles = gridSys.optMap{ hc => proj.transTile(hc).map(poly => poly.fill(terrs(hc).colour)) }
    def tiles2 = gridSys.map{hc =>
      corners.tilePoly(hc).map{ hvo => hvo.toPt2Reg(proj.transCoord(_)) }.fill(terrs(hc).colour)
    }

    def sides1: GraphicElems = sTerrs.projTruesLineSegMap{ls => Rectangle.fromAxisRatio(ls, 0.3).fill(Brown) }

    def sides2 = proj.linksOptMap{ (hs: HSide) =>
      val h1 = hs.tile1Opt

      Some(4)
    }

    val hs = HSide(143, 507)

    def strait1 = {
      //val hs =
      val t1 = hs.tile1
      val t2 = hs.tile2

      val p1 = corners.cornerV1(t2, 4)
      val p2 = corners.cornerV1(t2, 3)
      val p3 = corners.cornerV1(t1, 1)
      val p4 = corners.cornerV1(t1, 0)
      PolygonHVAndOffset(p1, p2, p3, p4).project(proj).fill(Violet)
    }

    def strait2: GraphicElems = ife(gridSys.ifSideExists(hs), RArr(strait1), RArr())

    def lines1: RArr[LineSegDraw] = sTerrs.projFalseLinksHsLineSegOptMap { (hs, ls) =>
      val t1 = terrs(hs.tile1)
      val t2 = terrs(hs.tile2)
      ife(t1 == t2, Some(ls.draw(t1.contrastBW)), None)
    }

    def lines2: GraphicElems = proj.ifTileScale(50, lines1)

    def lines3: GraphicElems = proj.linksOptMap{hs =>
      val hc1 = hs.tile1Reg
      val t1 = terrs(hc1)
      def t2 = terrs(hs.tile2Reg)
      sTerrs(hs) match{
        case true => None
        case _ if t1 != t2 => None
        case _ => {
          val cs = hs.corners
          val ls1 = corners.sideLine(cs._1, cs._2, cs._3)
          val ls2 = ls1.map(hva => hva.toPt2Reg(proj.transCoord(_)))
          Some(ls2.draw(t1.contrastBW))
        }
      }
    }

    def lines4: GraphicElems = proj.ifTileScale(50, lines3)





    def outerLines = proj.outerSidesDraw(3, Gold)

    def ifGlobe(f: HSysProjectionEarth => GraphicElems): GraphicElems = proj match
    { case ep: HSysProjectionEarth => f(ep)
      case _ => RArr()
    }

    def seas: GraphicElems = ifGlobe{ep => RArr(earth2DEllipse(ep.scale).fill(LightBlue)) }
    def irrLines: GraphicElems = ifGlobe{ ep => ep.irrLines2 }
    def irrNames: GraphicElems = ifGlobe{ ep => ep.irrNames2 }

    seas ++ irrFills ++ irrNames ++ tiles2 ++ sides1 ++ lines4 +% outerLines ++ rcTexts2 ++ irrLines ++ strait2
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