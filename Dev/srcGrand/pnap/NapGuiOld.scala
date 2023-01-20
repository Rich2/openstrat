/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pnap
import geom._, pglobe._, pEarth._, pgui._, pStrat._, pGrid._

case class NapGuiOld(canv: CanvasPlatform, scen: NapScenld) extends EarthAllGuiOld("1783")
{
  override def saveNamePrefix = "Y1783"
  /** The distance per pixel. This will normally be much greater than than 1 */
  scale = 0.99.km
  focus = 53.17 ll 0.0

  val fHex: OfETile[NapTileOld, ESideOnyAncient] => GraphicElems = etog =>
    {
      import etog._         
      val colour: Colour = tile.colour
      val poly = vertDispVecs.fillActive(colour, tile)

      val textU: GraphicElems = etog.ifScaleCObjs(110, tile.lunits match
        { case ArrHead(head) if tScale > 68 => RArr(UnitCounters.infantry(30, head, head.colour).slate(cen))
          case _ =>
          { val strs: StrArr = StrArr(yxStr, cenLL.degStr)
            TextGraphic.lines(strs, 10, cen, colour.contrastBW)
          }
        })         
        RArr(poly) ++ textU
     }
   
   def fSide: OfESide[NapTileOld, ESideOnyAncient] => GraphicElems = ofs =>
     { import ofs._
       val line = ifScaleCObjs(60, side.terr match
         { case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(t1.colour.contrastBW, 1))
           case Straitsold => RArr(vertDispLine.draw(Colour.Blue, 6))
         })      
       line
     } 
      
  def ls: GraphicElems =
  { val seas = earth2DEllipse(scale).fill(Sea.colour)
    val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this))
    val gs: GraphicElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
    seas %: as ++ gs
  }
 
  mapPanel.mouseUp = (but: MouseButton, clickList, v) => but match
  {
    case LeftButton => {
      selected = clickList
      statusText = selected.toString
      eTop()
    }
        
    case RightButton => (selected, clickList) match
    {
      case (AnyArrHead(c: Corps), AnyArrHead(newTile: NapTileOld)) =>
      { c.tile.lunits = c.tile.lunits.removeFirst (_ == c)
        val newCorps = c.copy (newTile)
        newTile.lunits %:= newCorps
        selected = AnyArr(newCorps)
        repaintMap()
      }

      case (AnyArrHead(c: Corps), clickList) => //deb(clickList.map(_.getClass.toString).toString)
      case _ =>
    }
    case _ =>
  }

  eTop()   
  loadView()
  repaintMap()
}