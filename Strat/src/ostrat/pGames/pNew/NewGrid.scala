package ostrat
package pGames.pNew
import pGrid._, pEarth._,geom._,  pCanv._, Colour._

case class TGui(canv: CanvasPlatform)
{
  val g1: MyGrid = Channel()
  val scale = 48
  def stuff = g1.xyTilesDispAll{(x, y, tile, buff) =>
    val str: String = x.commaAppendStr(y) + "\n" + EGrid80km.coodToLatLong0Off200(x cc y).degStr
    buff.append(g1.tileDisplayPolygonVar(x, y, scale).fillText(tile.colour, str, 15, tile.colour.contrastBW))
    tile match
    {
      case c: Land => c.fSides(x cc y){(_, poly) =>
        val fill = poly.fTrans(g1.fTrans(scale)).fill(Blue)
        buff.append(fill)
      }
      case _ =>
    }
  }
  val b1 = BadTranser(1)
  val b2 = BadTranser(2)
  val l = List(b1, b2).scale(5)
  val ls = l.scale(3)
  debvar(ls)
  canv.rendElems(stuff)
  canv.rendElems(g1.sideDrawsAll(scale)(2.0))
}