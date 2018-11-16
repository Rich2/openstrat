/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p305
import geom._, pCanv._, pEarth._

case class BC305Gui(canv: CanvasPlatform, scen: BcScen) extends EarthGui("BC 305")
{
  override def saveNamePrefix = "BC305"
  override def scaleMax: Dist = 14000.km / mapPanelDiameter
  scale = scaleMax
  var lat: Latitude = 20.north
  var long: Longitude = 20.east
  val maxLat = 70.north
  val minLat = 0.north
  //def focus: LatLong = lat * long

  val tops: Seq[Area1] = EarthAreas.oldWorld
//   override def eTop(): Unit = reTop(Seq(bIn, bOut, bLeft, bRight,
//         bDown, bUp, bInv, status))
//   /** 4 methods below are incorrect */
//   def leftCmd: MouseButton => Unit = (mb: MouseButton) =>
//      { long = Longitude((long.radians - distDelta(mb)).min(100)); updateView() }  
//   def rightCmd: MouseButton => Unit = (mb: MouseButton) => 
//      { long = Longitude((long.radians + (distDelta(mb)).max(70.N.radians))); updateView }   
//   def downCmd: MouseButton => Unit = (mb: MouseButton) =>
//      { lat = Latitude((lat.radians - distDelta(mb)).min(0)); updateView() }   
//   def upCmd: MouseButton => Unit = (mb: MouseButton) =>
//      { lat = Latitude((lat.radians + distDelta(mb)).max(0)); updateView() } 
         
  val fHex: OfETile[BcTile, ESideOnly] => GraphicElems = etog =>
    { import etog._         
      val colour: Colour = tile.colour
      val poly = vertDispVecs.fillSubj(tile, colour)
      
      val tileText: GraphicElems = ifScaleCObjs(68,
          { val ls: List[String] = List(yxStr, cenLL.toString)        
            TextGraphic.lines(cen, ls, 10, colour.contrastBW)              
          })         
          poly :: tileText
      }
   def fSide: OfESide[BcTile, ESideOnly] => GraphicElems = ofs => {
      import ofs._
      ifScaleCObjs(60, side.terr match
          {
            case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour,
               (t1, _) => vertDispLine.draw(1, t1.colour.contrastBW))
            case Straits => vertDispLine.draw(6, Colour.Blue) :: Nil
         })        
   }   
         
   def ls: GraphicElems =
   { val gs: GraphicElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
     val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this) )
     gs ::: as   
   }   
   
   eTop()
   loadView 
   repaintMap
}