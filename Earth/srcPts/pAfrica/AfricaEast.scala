/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, LatLong._, WTile._

object AfricaEast extends EArea1("AfricaEast", 18.19 ll 24.46)
{ //override val gridMaker = E80Empty// AfricaEastGrid
  override val a2Arr: RArr[EArea2] = RArr(SaharaEast, EastAfricaSouth ,Peloponnese)
}

//object AfricaEastGrid extends EGridMaker
//{
//   def apply[TileT <: AnyRef, SideT <: AnyRef](fTile: Terrain => TileT)(implicit evTile: IsType[TileT], evSide: IsType[SideT]):
//      EGrid[TileT, SideT] =
//   {
//      val grid = new EGNorth[TileT, SideT]("AfricaEast", 40.east, xOffset = 600, 400, 600)
//      grid.fSetAll(fTile, Ocean)
////      val gs: (Int, Int, Multiple[Terrain]*) => Unit = grid.setRow[Terrain](fTile) _
////      gs(428, 516, hills * 4)
////      gs(426, 514, hills * 4)
////      gs(424, 516, hills *4)
////      gs(422, 514, sea * 3, hills * 2, sea * 3)
////      gs(420, 516, sea, hills * 2, sea * 4)
////      gs(418, 514, sea * 2, hills, sea * 5)
// //     gs(300, 492, jungle * 17, sea * 2, plain * 12, sea * 24)
//      grid
//   }
//}

object Peloponnese extends EArea2("Peloponnese", 37.56 ll 23.18, hills)
{ val ePeninsular = 38.04 ll 23.56
  val kechries = 37.88 ll 22.99
  val p1 = 37.44 ll 23.51
  val neaKios = 37.58 ll 22.74
  val voia =degs(36.44, 23.17)
  val eElos = 36.79 ll 22.78
  val wElos = 36.80 ll 22.61
  val sGreece = 36.38 ll 22.48
  val koroni = degs(36.73, 21.87)
  val kyllini = 37.93 ll 21.13
  val rioPio = 38.30 ll 21.77
  val corinth = 37.94 ll 22.93
  val nPeninsular = 38.15 ll 23.22
  val polygonLL: PolygonLL = PolygonLL(ePeninsular, kechries, p1, neaKios, voia, eElos, wElos, sGreece, koroni, kyllini, rioPio, corinth, nPeninsular)
}

object SaharaEast extends EArea2("Sahara\neast", 24 ll 25, desert)
{ val elAgheila = 30.12 ll 19.08
  val benghazi = degs(32.12, 20.05)
  val derna = degs(32.93, 22.15)
  val alamein = 30.3 ll 29.4
  val portSaid = 31.09 ll 32.12
  val suez = 29.38 ll 32.22
  val southEast = 17 ll 39.4

  val polygonLL: PolygonLL = PolygonLL(SaharaWest.southEast, SaharaWest.northEast, elAgheila, benghazi, derna, alamein, portSaid, suez, southEast)
}

object EastAfricaSouth extends EArea2("East Africa\nsouth", 10 ll 32, plain)
{ val dankalia = 14 ll 41.66// eAfricaN
  val berbera = degs(10, 44)
  val hornAfrica = degs(12, 51)
  val iskushuban1 = 10.44 ll 51.41
  val iskushuban2 = 10.31 ll 50.90
  val rasMacbar = 9.47 ll 50.85

  val southEast = AfricaWestPts.cAfricaN * 48.east
  val cAfricaNE = AfricaWestPts.cAfricaN * 32.east
    
  val polygonLL: PolygonLL = PolygonLL(AfricaWestPts.westAfricaPtSE, SaharaWest.southEast, SaharaEast.southEast, dankalia, berbera, hornAfrica, iskushuban1,
    iskushuban2, rasMacbar, southEast, cAfricaNE)
}