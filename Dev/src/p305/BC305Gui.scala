/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package p305
import geom._, pglobe._, pgui._, pEarth._, pGrid._

case class BC305Gui(canv: CanvasPlatform, scen: BcScenOld) extends EarthGuiOld("BC 305")
{
  override def saveNamePrefix = "BC305"
  override def scaleMax: Length = 14000.km / mapPanelDiameter
  scale = scaleMax
  var lat: Latitude = 20.north
  var long: Longitude = 20.east
  val maxLat = 70.north
  val minLat = 0.north
  //def focus: LatLong = lat * long

  val tops: RArr[EArea1] = EarthAreas.oldWorld
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
         
  val fHex: OfETile[BcTileAncient, ESideOnyAncient] => GraphicElems = etog =>
    { import etog._         
      val colour: Colour = tile.colour
      val poly = vertDispVecs.fillActive(colour, tile)
      
      val tileText: GraphicElems = ifScaleCObjs(110,
        { val strs: StringArr = StringArr(yxStr, cenLL.degStr)
          TextGraphic.lines(strs, 10, cen, colour.contrastBW)//.toArraySeq
        })
      poly %: tileText
      }
   def fSide: OfESide[BcTileAncient, ESideOnyAncient] => GraphicElems = ofs => {
      import ofs._
      ifScaleCObjs(60, side.terr match
        {
          case SideNone => ifTiles((t1, t2) => t1.colour == t2.colour, (t1, _) => vertDispLine.draw(t1.colour.contrastBW, 1))
          case Straitsold => RArr(vertDispLine.draw(Colour.Blue, 6))
        })
   }   
         
  def ls: GraphicElems =
  { val seas = earth2DEllipse(scale).fill(Ocean.colour)
    val as: GraphicElems = scen.tops.flatMap(a => a.disp2(this))
    val gs: GraphicElems = scen.grids.flatMap(_.eGraphicElems(this, fHex, fSide))
    seas %: as ++ gs
  }
   
  eTop()
  loadView()
  repaintMap()
}