/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pEarth
//import Terrain._
//import pGrid._
//
///** An irreguler tile */ 
//abstract class ITile(val sym: Symbol, val cen: LatLong, val terr: ETile) extends GeographicSymbolKey
//{
//   override def toString = "ITile: " + sym.toString()
//   def sidesIn: Seq[SideLike]
//   def sides: Seq[SideLike] = sidesIn.map(sl => sl match 
//         {
//      case s1: ISide1 if s1.owner != this => ISide2(this, s1)
//      case s => s
//         })
//   def side2s: Seq[ISide2] = ???// sides.filterTo[ISide2](ISide2.ISide2IsTypeImplicit)
//   val island: Boolean = false
//   def textScale: Dist = 15.km
//   def aStrs = Seq(name)
//   def backColour = terr.colour
//   def textColour = ife(island, backColour.colourContrast2(sea.colour), backColour.blackOrWhite)
//   def ownISides: Seq[ISide] = sides.filter(_.isInstanceOf[ISide]).asInstanceOf[Seq[ISide]].filter(_.owner == this)
//   def otherISides: Seq[ISide] = sides.filter(_.isInstanceOf[ISide]).asInstanceOf[Seq[ISide]].filter(_.owner != this)
//   def latLongs: Seq[LatLong] =
//   {
//      val side1: Seq[LatLong] = sides.head match
//      {
//         case hc: HexChain => hc.latLongs         
//         case iSide: ISide /*if this == iSide.owner */ => iSide.pts
//         case hsc: HSideChain => hsc.latLongs
//      }
//      sides.tail.foldLeft(side1)((acc, n) => n match
//         {
//            case hc: HexChain => acc ++ hc.latLongs
//            case iSide: ISide => acc ++ iSide.pts.tail
//            case hsc: HSideChain => acc ++ hsc.latLongs.tail
//           // case iSide: ISide => acc ++ iSide.pts.reverse.tail //init.reverse would be equivlent
//         })         
//   }
//   def display(eg: EarthGui): DisplaySeqs = 
//   {  
//      eg.polyToGlobedArea(latLongs) match
//      {
//         case GlobedAll(d2s) =>
//         { 
//            val v2s = eg.transSeq(d2s)
//            val cenXY: Vec2 = eg.latLongToXY(cen)
//            val s1: CanvObjs = Seq(v2s.fillSubj(this, backColour))
//            val s2: CanvObjs = (eg.scale < textScale).ifSeq(FillText.lines(cenXY, aStrs, 10, textColour))
//            DisplaySeqs(s1, s2) 
//         }
//         case GlobedSome(s) =>
//         {
//            val cenXY: Vec2 = eg.latLongToXY(cen)
//            DisplaySeqs.f1(ShapeSubj.fill(cenXY, s.map(_.toVec2s(eg.trans)), this, backColour))
//         }
//         case GlobedNone => DisplaySeqs.empty
//      }
//      
//   }
//   def side1(pts: LatLong*): ISide1 = ISide1(ITile.this, pts)
//     // override def size: Int = 1      
//     // override def pts: Seq[LatLong] = ptsI       
//   //}
//   //def init(): Unit = otherISides.foreach(_.owner2 = this)
//   //def side2(iSide1: ISide1): ISide2 = ISide2(this, iSide1)
//}
//
//abstract class ITileIsland(sym: Symbol, cen: LatLong, terr: ETile) extends ITile(sym, cen, terr)
//{ override val island = true }
//
////object ITileNone extends ITile('ITileNone, LatLong.deg(0, 0), TerrNone)
////{
////   override def sidesIn: Seq[SideLike] = Seq()
////}
//
//sealed trait SideLike
//
////case class HexChain(grid: EGrid, x: Int, y: Int, vNum: Int, others: SideVec*) extends SideLike
////{   
////   def latLongs: Seq[LatLong] = others.foldLeft(Seq(HexVert(x, y)))(_ + _).map(grid.vertLL)
////}
////
////case class HSideChain(grid: EGrid, hSides: Seq[HSideV]) extends SideLike
////{
////   def gridVerts: Seq[Vec2] = hSides.map(_.pt2).+:(hSides.head.pt1)
////   def latLongs: Seq[LatLong] = gridVerts.map(grid.vec2ToLL)
////   
////}
//
//trait ISide extends SideLike
//{
//   def owner: ITile   
//   def size: Int = 1   
//   def pts: Seq[LatLong]  
//}
//
//case class ISide1(owner: ITile, pts: Seq[LatLong]) extends ISide
//{
//   override def toString = "ISide".appendParenth(owner.name)
//}
//
//case class ISide2(owner2: ITile, iSide1: ISide1) extends ISide// with IsType[ISide2]
//{
//   override def toString = "ISide2".appendParenth(owner.name, owner2.name)
//   override def owner: ITile = iSide1.owner
//   override def pts: Seq[LatLong] = iSide1.pts.reverse
//   def display(eg: EarthGui): CanvObjs =
//   {
//      val l1: Seq[LatLongLine] = pts.toLatLongLines
//      val l2: Seq[LineDist3] = l1 .map(eg.latLongLineToDist3)
//      l2.flatMap(l3 => (l3.zsPos && owner.terr == owner2.terr ).toOption(LineDraw(l3.toXY.toLine2(eg.trans), 1, owner.terr.colour.blackOrWhite)))      
//   }
//}
//
//object ISide2
//{
////   implicit object ISide2IsTypeImplicit extends IsType[ISide2]
////   {
////      override def isType(obj: Any): Boolean = obj.isInstanceOf[ISide2]
////   }
//}
//
