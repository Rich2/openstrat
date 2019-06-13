/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p1783
import geom._, pEarth._, pCanv._, pStrat._

case class Y1783Gui(canv: CanvasPlatform, scen: NapScen) extends EarthAllGui("1783")
{
  override def saveNamePrefix = "Y1783"
  /** The distance per pixel. This will normally be much greater than than 1 */
  scale = 0.99.km   
  focus = 53.17 ll 0.0
  val fHex: OfETile[NTile, ESideOnly] => GraphicElems = etog =>
    {
      import etog._         
      val colour: Colour = tile.colour
      val poly = vertDispVecs.fillActive(colour, tile)       
      val textU: GraphicElems = etog.ifScaleCObjs(68, tile.lunits match
        {
          case Arr1(head, _) if tScale > 68 => List(UnitCounters.infantry(30, head, head.colour,tile.colour).slate(cen))
          case _ =>
          {
            val strs: List[String] = List(yxStr, cenLL.degStr)                   
            TextGraphic.lines(strs, 10, cen, colour.contrastBW)
          }
        })         
        poly ::: textU
     }
   
   def fSide: OfESide[NTile, ESideOnly] => GraphicElems = ofs =>
     {
       import ofs._
       val line = ifScaleCObjs(60, side.terr match
         {
           case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
           case Straits => vertDispLine.draw(6, Colour.Blue) :: Nil
         })      
       line
     } 
      
  def ls: GraphicElems =
  {
    val gs: GraphicElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
    val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this) )
    gs ++ as
  }
 
  mapPanel.mouseUp = (v, but: MouseButton, clickList) => (but, selected, clickList) match
  {
    case (LeftButton, _, _) => selected = clickList.fHead(Arr(), Arr(_))
        
    case (RightButton, Arr(c : Corps), Arr(newTile: NTile)) =>
    {
      c.tile.lunits = c.tile.lunits.removeFirst(_ == c)
      val newCorps = c.copy(newTile) 
      newTile.lunits +:= newCorps
      selected = Arr(newCorps)
      repaintMap  
    }
    
    case (RightButton, Arr(c : Corps), clickList) => //deb(clickList.map(_.getClass.toString).toString)
    case _ => 
  }
  
  eTop()   
  loadView   
  repaintMap   
}