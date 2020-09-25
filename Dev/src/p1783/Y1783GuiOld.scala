/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package p1783
import geom._, pEarth._, pCanv._, pStrat._

case class Y1783GuiOld(canv: CanvasPlatform, scen: NapScen) extends EarthAllGuiOld("1783")
{
  override def saveNamePrefix = "Y1783"
  /** The distance per pixel. This will normally be much greater than than 1 */
  scale = 0.99.km   
  focus = 53.17 ll 0.0

  val fHex: OfETile[NTileOld, ESideOldOnly] => DisplayElems = etog =>
    {
      import etog._         
      val colour: Colour = tile.colour
      val poly = vertDispVecs.fillActiveOld(colour, tile)

      val textU: DisplayElems = etog.ifScaleCObjs(68, tile.lunits match
        { case ArrHead(head) if tScale > 68 => Arr(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(cen))
          case _ =>
          { val strs: Arr[String] = Arr(yxStr, cenLL.degStr)
            TextGraphic.lines(strs, 10, cen, colour.contrastBW)
          }
        })         
        Arr(poly) ++ textU
     }
   
   def fSide: OfESide[NTileOld, ESideOldOnly] => DisplayElems = ofs =>
     { import ofs._
       val line = ifScaleCObjs(60, side.terr match
         { case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
           case Straitsold => Arr(vertDispLine.draw(6, Colour.Blue))
         })      
       line
     } 
      
  def ls: DisplayElems =
  { val gs: DisplayElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
    val as: DisplayElems = scen.tops.flatMap(a => a.disp2(this))
    gs ++ as
  }
 
  mapPanel.mouseUp = (but: MouseButton, clickList, v) => but match
  {
    case LeftButton => selected = clickList //.fHead(Arr(), Arr(_))
        
    case RightButton => (selected, clickList) match
    { case (List(c: Corps), List(newTile: NTileOld)) =>
      {
       c.tile.lunits = c.tile.lunits.removeFirst (_ == c)
       val newCorps = c.copy (newTile)
       newTile.lunits +:= newCorps
       selected = List(newCorps)
       repaintMap()
      }
      case (List(c: Corps), clickList) => //deb(clickList.map(_.getClass.toString).toString)
      case _ =>
    }
    case _ =>
  }

  eTop()   
  loadView()
  repaintMap()
}