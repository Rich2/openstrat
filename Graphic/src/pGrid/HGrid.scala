package ostrat
package pGrid
import geom._

/*
trait HGrid[TileT] extends TGrid[TileT]
{
  type GridT[A] = HGrid[A]
  @inline override def xStep: Int = 4
  override def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
  override def coodIsTile(x: Int, y: Int): Unit = ifNotExcep(
    x %% 4 == 0 & y %% 4 == 0 | x %% 4 == 2 & y %% 4 == 2,
    x.commaInts((y)) -- "is an invalid Hex tile coordinate")
  override def vertCoodsOfTile(tileCood: Cood): Coods = HexGrid.vertCoodsOfTile(tileCood)
  def cen: Vec2 = (xMax + xMin) / 2 vv (yMin + yMax) * HexGrid.yRatio / 2
  override def sideCoodLine(x: Int, y: Int): CoodLine = HexGrid.vertCoodsOfSide(x, y)

  def tilePolygonReduce(xc: Int, yc: Int, vs: HVertOffs): Polygon =
  {
    val acc: Buff[Double] = Buff(24)

    def vAdj(hv: HVert, vert: Cood): Unit = (hv) match
    { case hv: HVertSingle => acc.app2(hv.vec2(vert))

      case hv: HVertDirn2 if (vert + hv.dirn) == Cood(xc, yc) =>
      { acc.app2(hv.ltVec2(vert))
        acc.app2(hv.rtVec2(vert))
      }

      case hv: HVertDirn2 if (vert + hv.ltDirn) == Cood(xc, yc) => acc.app2(hv.ltVec2(vert))
      case hv: HVertDirn2 => acc.app2(hv.rtVec2(vert))
    }

    vAdj(vs.up, Cood(xc, yc + 1))
    vAdj(vs.upRt, Cood(xc + 2, yc + 1))
    vAdj(vs.dnRt, Cood(xc + 2, yc - 1))
    vAdj(vs.down, Cood(xc, yc - 1))
    vAdj(vs.dnLt, Cood(xc - 2, yc - 1))
    vAdj(vs.upLt, Cood(xc -2, yc + 1))
    new Polygon(acc.toArray)
  }

  def tilePolygonVar(x: Int, y: Int): Polygon = getTile(x, y) match
  { case t: HVertOffsTr => tilePolygonReduce(x, y, t.vertOffs)
    case t =>  vertCoodsOfTile(x, y).pMap(coodToVec2)
  }

  def tileDisplayPolygonReduce(x: Int, y: Int, scale: Double, vertOffs: HVertOffs, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z): Polygon =
    tilePolygonReduce(x, y, vertOffs).fTrans(v => (v - mapOffset) * scale - displayOffset)

  def tileDisplayPolygonVar(x: Int, y: Int, scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z): Polygon =
    tilePolygonVar(x, y).fTrans(v => (v - mapOffset) * scale - displayOffset)

  def tileFillVar(x: Int, y: Int, scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z)(f: TileT => Colour): PolyFill =
    tileDisplayPolygonVar(x, y, scale, mapOffset, displayOffset).fill(f(getTile(x, y)))
}
*/
