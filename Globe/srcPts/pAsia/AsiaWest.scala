/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pPts.RusNorth, pPts.AsiaWestPts

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaWest  extends EArea1("AsiaWest", 20.18 ll -0.65)
{ import AsiaWestPts._
   override val a2Arr = RArr(arabia, persia, iraq, kazak, RusNorth, india, sriLanka, himalayas, centralAsia, severny)
   //override val gridMaker = E80Empty
   //override val grids = Seq()//AsiaWestGrid())   
}

/** 41.5E to 91.5E limit 67Degs centre approx  East is centre point */
//object AsiaWestGrid 
//{
//   val xDelta: Int = 84
//   val rads: Double = xDelta * HexCood.gridDistV / EarthEquatorialRadius
//   val degsDelta: Double = rads.radiansToDegrees
//   val centralAngle: Double = 17 + degsDelta
//   println("AsiaWest central angle " + centralAngle + ", delta: " + degsDelta)
//   val long: Longitude = Longitude.deg(centralAngle)   
//   val xOffset = 68  + xDelta
//   def apply(): EGridV =      
//   {
//      //implicit val defaultSide: ESide = SideNone 
//      val g = new EGridV("AsiaWest", long, xOffset, 17, 101, 2, 130){}
//      g.setRow(130, 146, TerrIce, sea, taiga * 3)
//      g.setRow(104, 132, sea, desert *4 , hill * 7)
//      g.setRow(102, 130, sea * 2, desert * 4, mtain * 6, hill)
//      g.setRow(100, 128, hill, sea, desert * 4, mtain * 4, desert * 4)
//      g.setRow(98, 126, hill, sea * 2, hill * 5, mtain * 3, desert * 4)
//      g.setRow(96, 124, desert, hill, mtain, hill * 5, mtain * 7)
//      g.setRow(94, 122, desert, hill * 3, desert * 3, hill * 2, mtain * 7)
//      g.setRow(92, 124, desert, hill * 5, desert * 2, hill, mtain * 7)
//      g.setRow(90, 122, desert, Plain, hill * 5, desert * 2, hill * 3, mtain * 4)
//      g.setRow(88, 124, desert, sea, hill * 7, desert * 4, mtain *3)
//      g.setRow(86, 122, desert * 2, sea * 2, hill * 4, desert * 3, jungle * 5, hill)
//      g.setRow(84, 124, desert * 2, sea, desert, sea * 4, desert * 2, jungle * 7)
//      g.setRow(82, 126, desert * 4, sea * 4, desert, jungle * 7)
//      g.setRow(80, 128, desert * 3, sea * 6, jungle * 5, sea * 2)
//      g.setRow(78, 126, desert * 3, sea * 7, jungle * 4, sea * 2)
//      g.setRow(76, 128, desert, sea * 8, jungle * 4, sea * 2)
//      g.setRow(74, 130, sea * 9, jungle * 3, sea * 3)
//      g.setRow(72, 132, sea * 9, jungle * 2, sea * 3)
//      g.setRow(70, 134, sea * 9, jungle, sea * 3)
//      g.setRow(68, 136, sea * 10, jungle, sea * 2)
//      g.setRow(66, 134, sea * 14)
//      g.setRow(64, 132, sea * 15)
//      g.setRow(62, 130, sea * 15)
//      g.setRow(60, 124, sea * 18)
//      g
//   }
//}

//   import HexE._   
//   val hexs: Seq[HexE] =
//      rowFrom(24, 112, plain *9)) ++
//      rowFrom(22, 110, plain *10)) ++
//      rowFrom(24, 108, plain *10)) ++
//      rowFrom(18, 106, land, land, hill *10)) ++
//      rowFrom(20, 104, land, desert *4), hill *4), mtn(2)) ++
//      rowFrom(14, 102, sea *2), land, desert *2), hill, mtn(7)) ++
//      rowFrom(16, 100, sea, land, desert *2), mtn(5), desert *4)) ++
//      rowFrom(10, 98, hill, hill, sea, hill *4), mtn(3), desert *5)) ++
//      rowFrom(12, 96, hill *7), mtn(6), desert *1)) ++
//      rowFrom(6, 94, desert, hill *2)) ++
//      rowFrom(8, 92, land, hill) ++
//      rowFrom(6, 90, desert, land, hill) ++
//      rowFrom(4, 88, desert, desert, sea) ++
//      rowFrom(6, 86, desert *2), sea) ++
//      rowFrom(4, 84, desert *2))
//   
//   override val specialSides: Seq[HSideE] = Seq()
//}

//abstract class AsiaWestLongGrid(name: String) extends EGridV(name, 66.east, 36, 0, 100, 0, 100)