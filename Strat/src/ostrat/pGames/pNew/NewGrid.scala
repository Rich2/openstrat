package ostrat
package pGames.pNew
import pGrid._, pEarth._, pCanv._, Colour._

case class TGui(canv: CanvasPlatform)
{
  val g1: MyGrid = Channel()
  val scale = 48
  def stuff = g1.xyTilesDispAll {(x, y, tile, buff) => tile match {
    case c: HVertOffsTr =>
    { buff.append(g1.tileDisplayPolygonReduce(x, y, scale, c.vertOffs).fill(tile.colour))
      c match
      { case ct: Coast => ct.vertOffs.dnRt match
        { case s: StraitsDnLt =>
          { debb()
            buff.append(s.sidePoly(x cc y, ct.vertOffs).fTrans(g1.fTrans(scale)).fill(Blue))
          }
          case _ =>
        }
        case _ =>
      }
    }
    case t => buff.append(g1.tileFill(x, y, scale)(_.colour))
    }
  }

  canv.rendElems(stuff)
  canv.rendElems(g1.sideDrawsAll(scale)(2.0))
}