/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import geom._
import pGrid._

/** Not sure whether the "fTile: (Int, Int, Terrain) => TileT" should be implicit. Will change with multiple implicit parameter lists */
trait EGridMaker
{
   def apply[TileT <: Tile, SideT <: GridElem](fTile: (Int, Int, Terrain) => TileT, fSide: (Int, Int, SideTerr) => SideT)(implicit evTile: IsType[TileT], evSide: IsType[SideT]):
   EGrid[TileT, SideT]
}

/** A Hex Grid for an area of the earth. It is irregular because as you move towards the poles the row length decreases. The x dirn 
 *  follows lines of longitude. The y Axis at the cenLong moves along a line of longitude. */
class EGrid[TileT <: Tile, SideT <: GridElem](bounds: Array[Int], val name: String, val cenLong: Longitude, val scale: Dist, val xOffset: Int,
  val yOffset: Int, xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)(implicit evTile: IsType[TileT], evSide: IsType[SideT]) extends
      HexGridIrr[TileT, SideT](bounds, xTileMin, xTileMax, yTileMin, yTileMax)
{
   thisEGrid =>
   type GridT <: EGrid[TileT, SideT]
   val vec2ToLL: Vec2 => LatLong = fVec2ToLatLongReg(cenLong, scale, xOffset, yOffset)   
   
//   def edgeChain(x1: Int, y1: Int, up: Boolean, x2: Int, y2: Int, maxLen: Int = 100): HSideChain =
//   {      
//       Unit match
//       {
//          //case _ if !outerSides.coodContains(x1, y1) => excep(x1.toString -- y1.toString --"Start side in edgechain method not an outer side")
//          //case _ if !outerSides.coodContains(x2, y2) => excep(x2.toString -- y2.toString --"End side in edgechain method not an outer side")
//          case _ => 
//       }
//       val h1 = HSideV(x1, y1, up)
//       HSideChain(this, Seq(h1))
//   }
   //def chain(x: Int, y: Int, vNum: Int, others: SideVec*): HexChain = HexChain(this, x, y, vNum, others:_*)
   //def chainLL(x: Int, y: Int, vNum: Int, others: SideVec*): Seq[LatLong] = HexChain(this, x, y, vNum, others:_*).latLongs
  // def vertsToLLs(inp: Seq[HexVert]): Seq[LatLong] = inp.map(v => vec2ToLL(v.toVec2))
   /** Throws Exception for the time being */
   //def hexsFromInnerSide(sideCood: HSideCood): (HexE, HexE) = sideCood.adjTileCoods.bimap(hexs.getHex(_), hexs.getHex(_))   
   
  // @deprecated def vertToLL(x: Int, y: Int): LatLong = vec2ToLL(HexVert(x, y).toVec2)
   def vToLL(vIn: Vec2) : LatLong = vec2ToLL(vIn)
   //def sides
//   def sideIfCens(sideCood: HSideCood, f: (HexE, HexE) => Boolean): Boolean =
//   {
//      val optHexs = getHexsFromSide(sideCood)
//      ife (optHexs.length == 2, f(optHexs(0), optHexs(1)), false)
//   }
   override def toString: String = "Grid " + name
   
   def vertLL(x: Int, y: Int, ptNum: Int): LatLong = ???
//   {
//      val xy: Vec2 = HG.xyToVec2(x, y) + verts(ptNum)
//      vec2ToLL(xy)
//   }
   /** name needs modifying */
   //def coodToVec2(cood: Cood): Vec2 = HexGrid.coodToVec2(cood)
   def llXLen =  (xTileMax - xTileMin + 5) * 2    
   def llYLen = yTileMax - yTileMin + 3
   val vArr: Array[Double] = new Array[Double](llYLen * llXLen)
   def llXInd(x: Int): Int = (x - xTileMin + 2) * 2
   def llYInd(y: Int): Int = (y - yTileMin + 1) * llXLen
   def llInd(x: Int, y: Int): Int = llYInd(y) + llXInd(x)
   def getLL(x: Int, y: Int): LatLong =
   {
      val index: Int = llInd(x, y)
      LatLong(vArr(index), vArr(index + 1))
   }
   def getLL(cood: Cood): LatLong = getLL(cood.x ,cood.y)
   
   def setLL(x: Int, y: Int, llValue: LatLong): Unit =
   {
      val index: Int = llInd(x, y)
      vArr(index) = llValue.lat
      vArr(index + 1) = llValue.long
   }
   def setLL(cood: Cood, llValue: LatLong): Unit = setLL(cood.x, cood.y, llValue)
   final def setLongitude(cood: Cood, radians: Double): Unit = setLongitude(cood.x, cood.y, radians)
   def setLongitude(x: Int, y: Int, radians: Double): Unit = vArr(llInd(x, y) + 1) = radians
   /** These 2 methods may be redundant */
   def coodToLL(cood: Cood): LatLong = coodToLL(cood.x, cood.y) //vec2ToLatLongReg(HG.coodToVec2(hc), cenLong, scale, xOffset, yOffset)
   def coodToLL(x: Int, y: Int): LatLong = getLL(x, y)//vec2ToLL(coodToVec2(cood))
   
   tileCoodForeach{cood =>
      setLL(cood, vec2ToLL(HexGrid.coodToVec2(cood)))
      sideCoodsOfTile(cood).foreach(vc => setLL(vc, vec2ToLL(HexGrid.coodToVec2(vc))))
      vertCoodsOfTile(cood).foreach(vc => setLL(vc, vec2ToLL(HexGrid.coodToVec2(vc))))
         }   
   
  // def vertLL(hv: HexVert): LatLong = vec2ToLL(hv.toVec2)

   def ofETilesFold[R](eg: EarthGui, f: OfETile[TileT, SideT] => R, fSum: (R, R) => R)(emptyVal: R) =
   {
      var acc: R = emptyVal
      tileCoodForeach{ tileCood =>         
         val newRes: R = f(new OfETile[TileT, SideT](eg, thisEGrid ,getTile(tileCood)))
         acc = fSum(acc, newRes)
      }
      acc
   }   
   
   def eDisp(eg: EarthGui, fDisp: (OfETile[TileT, SideT]) => GraphicElems): GraphicElems = 
   {
      var acc: GraphicElems = Nil
      tileCoodForeach { tileCood =>
         val tog = new OfETile[TileT, SideT](eg, thisEGrid, getTile(tileCood))
         val newRes: GraphicElems = ife(tog.cenFacing, fDisp(tog), Nil) 
         acc = acc ++ newRes
      }
      acc
   }
   
   def eGraphicElems(eg: EarthGui, fDisp: (OfETile[TileT, SideT]) => GraphicElems, sDisp: (OfESide[TileT, SideT]) => GraphicElems): GraphicElems = 
   {
      var acc: GraphicElems = Nil
      tileCoodForeach { tileCood =>
         val tog = new OfETile[TileT, SideT](eg, thisEGrid, getTile(tileCood))
         val newRes: GraphicElems = ife(tog.cenFacing, fDisp(tog), Nil) 
         acc = acc ++ newRes
      }
      var sideAcc: GraphicElems = Nil
      sideCoodForeach { sideCood =>
         val tog = new OfESide[TileT, SideT](eg, thisEGrid, getSide(sideCood))
         val newRes: GraphicElems = ife(tog.sideCenFacing, sDisp(tog), Nil) 
         sideAcc ++= newRes
      }      
      acc ++ sideAcc
   }
   
      
   def disp(eg: EarthGui, fDisp: (EGrid[TileT, SideT], Cood) => GraphicElems): GraphicElems = tileCoodsDisplayFold(cood => fDisp(this, cood))
   var rightGrid: Option[EGrid[TileT, SideT]] = None
}
      
//   val sideObjs: CanvObjs = sidesFlatMap((cood, st) =>
//        {
//           val ln: Line2 = cood.hexSideLine
//           val lnLL: LatLongLine = ln.toLatLongLine(vec2ToLL)
//           val lnDist3: LineDist3 = eg.latLongLineToDist3(lnLL) 
//           val (lineWidth, colour) = st match
//           {
//              case Straits => (4, Colour.Blue)           
//              case _ => (1, Colour.Black)
//            }
//           (lnDist3.zsPos).toOption(LineDraw(lnDist3.toXY.toLine2(eg.trans),lineWidth, colour)).toSeq 
//        })
         
//      val s3b: CanvObjs = (scale > eg.scale * 8.0).ifSeq(innerSides.flatMap(sd =>
//         {
//            val lnv: Line2 = sd.hexSideLine
//            val lnLL: LatLongLine = lnv.toLatLongLine(vec2ToLL)
//            val lnDist3: LineDist3 = eg.latLongLineToDist3(lnLL)
//            val (h1, h2) = hexsFromInnerSide(sd)
//            (lnDist3.zsPos && (h1.terr == h2.terr)).toOption(LineDraw(lnDist3.toXY.toLine2(eg.trans), 1, h1.terr.colour.blackOrWhite))
//         }))
 


//abstract class EGridU04(name: String, xMin: Int, xMax: Int, yMin: Int, yMax: Int) extends
//   EGridU(name, cenLong = 40.east, xOffset = 100, xMin, xMax, yMin, yMax)

//abstract class EGridU00(name: String, xMin: Int, xMax: Int, yMin: Int, yMax: Int) extends
//   EGridU(name, cenLong = 0.0.east, xOffset = 100, xMin, xMax, yMin, yMax)
//abstract class EGridU(name: String, cenLong: Longitude, xOffset: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int) extends
//   EGrid(name, cenLong, gridDistU, xOffset, yOffset = 200, xMin, xMax, yMin, yMax)

//abstract class EGridV(name: String, cenLong: Longitude, xOffset: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int) extends
//   EGrid(name, cenLong, gridDistV, xOffset, 60, xMin, xMax, yMin, yMax)
//abstract class EGridW(name: String, cenLong: Longitude, xOffset: Int, xMin: Int, xMax: Int, yMin: Int, yMax: Int) extends
//   EGrid(name, cenLong, gridDistW, xOffset, 30, xMin, xMax, yMin, yMax)

//class GVert(grid: EGrid, hv: HexVert)
//{
//   def latLong: LatLong = grid.vec2ToLL(hv.toVec2)
//   def edgeChain(start: SideVec, end: HSideCood): HSideChain =
//   {
//      //val v = hv 
//      ???
//   }
//}
//
//object GVert
//{
//   def apply(grid: EGrid, x: Int, y: Int): GVert = new GVert(grid, HexVert(x, y))
//}


