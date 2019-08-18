package ostrat
package pGames.pNew
import pGrid._, pEarth._, pCanv._, Colour._

case class TGui(canv: CanvasPlatform)
{
  val g1: MyGrid = Channel()
  val scale = 48
  def stuff = g1.xyTilesDispAll{(x, y, tile, buff) =>
    val str: String = x.commaAppendStr(y) + "\n" + EGrid80km.coodToLatLong0Off200(x cc y).degStr
    buff.append(g1.tileDisplayPolygonVar(x, y, scale).fillText(tile.colour, str, 15, tile.colour.contrastBW))
    tile match
    {
      case c: Coast => c.fSides(x cc y){(_, poly) =>
        val fill = poly.fTrans(g1.fTrans(scale)).fill(Colour.Blue)
        buff.append(fill)
      }
      case _ =>
    }
  }

  canv.rendElems(stuff)
  canv.rendElems(g1.sideDrawsAll(scale)(2.0))
}