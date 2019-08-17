package ostrat
package pGames.pNew
import pGrid._, pEarth._, pCanv._, Colour._

case class TGui(canv: CanvasPlatform)
{
  val g1: MyGrid = Channel()
  val scale = 48
  def stuff = g1.xyTilesDispAll {(x, y, tile, buff) =>
  {
    val str: String = x.commaAppendStr(y) + "\n" + EGrid80km.coodToLatLong0Off200(x cc y).degStr
    buff.append(g1.tileDisplayPolygonVar(x, y, scale).fillText(tile.colour, str, 15, tile.colour.contrastBW))
    tile match
    { case ct: Coast => ct.vertOffs.down match
      { case s: StraitsDnLt =>
        { deb("Straits")
          buff.append(geom.TextGraphic("Channel", 16, g1.coodToVec2(205 cc 463)).fTrans(g1.fTrans(scale)))
          buff.append(s.sidePoly(x cc y - 1, ct.vertOffs).fTrans(g1.fTrans(scale)).fill(Blue))
        }
        case _ =>
      }
      case _ =>
    }
  }

  }

  canv.rendElems(stuff)
  canv.rendElems(g1.sideDrawsAll(scale)(2.0))
}