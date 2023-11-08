/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import geom._, pgui._, collection.mutable.ArrayBuffer

/** Projects [[HGridSys]] on to a flat surface for 2D graphics. Like all projections attempts to remove tiles that can't be seen. */
final case class HSysProjectionFlat(parent: HGridSys, panel: Panel) extends HSysProjection with TSysProjectionFlat
{ type SysT = HGridSys
  pixelsPerC = parent.fullDisplayScale(panel.width, panel.height)
  override def pixelsPerR: Double = pixelsPerC * Sqrt3
  override def pixelsPerTile: Double = pixelsPerC * 4

  var focus: Vec2 = parent.defaultView(pixelsPerC).vec
  override def ifTileScale(minScale: Double, elems: => GraphicElems): GraphicElems = ife(pixelsPerTile >= minScale, elems, RArr[GraphicElem]())

  override def setView(view: Any): Unit = view match
  {
    case hv: HGView =>
    { pixelsPerC = hv.pixelsPerC
      focus = hv.vec
      gChild = getGChild
    }
    case d: Double => pixelsPerC = d
    case _ =>
  }

  /** This is child grid the subset of the grid that should actually be sent to the display to be rendered. */
  var gChild: HGridSys = getGChild

  def setGChild: Unit = gChild = getGChild

  /** This is the method to recreate the grid system subset that should be displayed. */
  def getGChild : HGridSys =
  { def newBottom: Int = (focus.y / Sqrt3 + panel.bottom / pixelsPerR - 1).round.toInt.roundUpToEven
    val newTop: Int = (focus.y / Sqrt3 + panel.top / pixelsPerR + 1).round.toInt.roundDownToEven
    val newLeft: Int = (focus.x + panel.left / pixelsPerC - 2).round.toInt.roundUpToEven
    val newRight: Int = (focus.x + panel.right / pixelsPerC + 2).round.toInt.roundDownToEven

    parent match
    {
      case hg: HGridReg =>
      { val bt = hg.bottomCenR.max(newBottom)
        val tp = hg.topCenR.min(newTop)
        val lt = hg.gridLeftCenC.max(newLeft)
        val rt = hg.gridRightCenC.min(newRight)
        //deb(s"bt: $bt, tp: $tp, lt: $lt, rt: $rt")
        HGridReg(bt, tp, lt, rt)
      }
      case hgi: HGridIrr => hgi.numTileRows match
      { case n if n <= 0 => hgi
        case _ if newTop < hgi.bottomCenR | newBottom > hgi.topCenR | newLeft > hgi.gridRightCenC | newRight < hgi.gridLeftCenC => HGridIrr.fromTop(hgi.topCenR)
        case _ => {
          val bottom = hgi.bottomCenR.max(newBottom)
          val top = hgi.topCenR.min(newTop)
          val numRows = (top - bottom + 2) / 2
          val newArray = new Array[Int](numRows * 2)
          var i = 0
          iToForeach(bottom, top, 2){ r =>
            val rowLeft0 = hgi.rowLeftCenC(r).max(newLeft)
            val rowLeft = ife(r.div4Rem0, rowLeft0.roundUpTo(_.div4Rem0), rowLeft0.roundUpTo(_.div4Rem2))
            val rowRight0 = hgi.rowRightCenC(r).min(newRight)
            val rowRight = ife(r.div4Rem0, rowRight0.roundDownTo(_.div4Rem0), rowRight0.roundDownTo(_.div4Rem2))
            val rowLen = ((rowRight - rowLeft) / 4 + 1).max(0)
            newArray(i) = rowLeft
            i += 1
            newArray(i) = rowRight
            i += 1
          }
          val newGrid = new HGridIrr(bottom, newArray)
          newGrid
        }
      }
      case hs => hs
    }
  }

  override def tilePolygons: PolygonGenArr = gChild.map(_.hVertPolygon.map(transCoord(_)))

  override def tileActives: RArr[PolygonActive] =
    gChild.map(hc => hc.hVertPolygon.map(parent.flatHCoordToPt2(_)).slate(-focus).scale(pixelsPerC).active(hc))

  override def hCenPtMap(f: (HCen, Pt2) => GraphicElem): GraphicElems =
  { val buff = new ArrayBuffer[GraphicElem]
    gChild.foreach{hc => transOptCoord(hc).foreach(pt => buff.append(f(hc, pt))) }
    new RArr[GraphicElem](buff.toArray)
  }

  override def hCenSizedMap(hexScale: Double)(f: (HCen, Pt2) => GraphicElem): GraphicElems = ifTileScale(hexScale, hCenPtMap(f))

  override def sideLines: LineSegArr = gChild.sideLineSegHCs.map(_.map(parent.flatHCoordToPt2(_))).slate(-focus).scale(pixelsPerC)
  override def innerSideLines: LineSegArr = gChild.innerSideLineSegHCs.map(_.map(parent.flatHCoordToPt2(_))).slate(-focus).scale(pixelsPerC)
  override def outerSideLines: LineSegArr = gChild.outerSideLineSegHCs.map(_.map(parent.flatHCoordToPt2(_))).slate(-focus).scale(pixelsPerC)

  override def transOptCoord(hc: HCoord): Option[Pt2] = Some(parent.flatHCoordToPt2(hc).slate(-focus).scale(pixelsPerC))
  override def transCoord(hc: HCoord): Pt2 = parent.flatHCoordToPt2(hc).slate(-focus).scale(pixelsPerC)

  override def transOptHVOffset(hvo: HVOffset): Option[Pt2] = Some(transHVOffset(hvo))

  override def transTile(hc: HCen): Option[Polygon] = Some(hc.hVertPolygon.map(transCoord(_)))

  override def transOptLineSeg(seg: LineSegHC): Option[LineSeg] =
    transOptCoord(seg.startPt).map2(transOptCoord(seg.endPt)){ (p1, p2) => LineSeg(p1, p2) }

  override def transLineSeg(seg: LineSegHC): LineSeg = seg.map(transCoord)

  override def transHSides(inp: HSideArr): LineSegArr = LineSegArr()// ???//.slate(-focus).scale(cPScale)
}