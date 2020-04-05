/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package p1783
import geom._, pEarth._, pCanv._, pStrat._

case class Y1783Gui(canv: CanvasPlatform, scen: NapScen) extends EarthAllGui("1783")
{
  override def saveNamePrefix = "Y1783"
  /** The distance per pixel. This will normally be much greater than than 1 */
  scale = 0.99.km   
  focus = 53.17 ll 0.0
  val fHex: OfETile[NTileOld, ESideOldOnly] => GraphicElemsOld = etog =>
    {
      import etog._         
      val colour: Colour = tile.colour
      val poly = vertDispVecs.fillActive(colour, tile)       

      val textU: GraphicElemsOld = etog.ifScaleCObjsOld(68, tile.lunits match
        { case RefsHead(head) if tScale > 68 => ArrOld(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(cen))
          case _ =>
          { val strs: ArrOld[String] = ArrOld(yxStr, cenLL.degStr)
            TextGraphic.lines(strs.toRefs, 10, cen, colour.contrastBW).toArraySeq
          }
        })         
        Refs(poly).toArraySeq ++ textU
     }
   
   def fSide: OfESide[NTileOld, ESideOldOnly] => GraphicElemsOld = ofs =>
     { import ofs._
       val line = ifScaleCObjsOld(60, side.terr match
         { case SideNone => ifTilesOld((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
           case Straitsold => ArrOld(vertDispLine.draw(6, Colour.Blue))
         })      
       line
     } 
      
  def ls: GraphicElems =
  { val gs: GraphicElemsOld = scen.grids.toArraySeq.flatMap(_.eGraphicElems(this, fHex, fSide))
    val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this))
    gs.toRefs ++ as
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
       repaintMap
      }
      case (List(c: Corps), clickList) => //deb(clickList.map(_.getClass.toString).toString)
      case _ =>
    }
    case _ =>
  }

  eTop()   
  loadView   
  repaintMap   
}