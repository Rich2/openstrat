/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package soceans
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Tasmania 68401km². Depends on nothing. */
object Tasmania extends IslandPoly("Tasmania", -24.45 ll 134.47, mtainOceForest)
{ override val area: Kilares = 68401.kilares

  val capePortland: LatLong = -40.738 ll 147.976
  val tasman: LatLong = -43.242 ll 148.005
  val south: LatLong = -43.640 ll 146.828
  val southWest: LatLong = -43.570 ll 146.032
  val hunterNW: LatLong = -40.483 ll 144.712
  val merseyBluff: LatLong = -41.158 ll 146.355

  override val polygonLL: PolygonLL = PolygonLL(capePortland, tasman, south, southWest, hunterNW, merseyBluff)
}

/** [[polygonLL]] graphical representation of Grande Terre, Grande Terr 16372km² + Loyalty Islands 1981km² = New Caledonia 18353km². Depends on nothing. */
object NewCaldedonia extends IslandPoly("New Caledonia", -21.282 ll 165.369, mtainJungle) {
  override val area: Kilares = 18353.kilares

  val tiya: LatLong = -19.973 ll 163.932
  val touho: LatLong = -20.794 ll 165.261
  val p40: LatLong = -22.094 ll 166.943
  val lleOuen: LatLong = -22.465 ll 166.816
  val p70: LatLong = -21.556 ll 165.266

  override val polygonLL: PolygonLL = PolygonLL(tiya, touho, p40, lleOuen, p70)
}

/** [[polygonLL]] graphical representation of the North Island of New Zealand 111583km². Depends on nothing. */
object NZNorthIsland extends IslandPoly("New Zealand\nNorth Island", -38.66 ll 176, hillyOce)
{ override val area: Kilares = 111583.kilares

  val capeReinga: LatLong = -34.42 ll 172.68
  val teHapua: LatLong = -34.41 ll 173.05
  val capeBrett: LatLong = -35.170 ll 174.333
  val aukland: LatLong = -36.83 ll 174.81
  val sugarLoaf: LatLong = -36.470 ll 175.411
  val crayfishBay: LatLong = -36.722 ll 175.820
  val eCape: LatLong = -37.69 ll 178.54
  val tableCape: LatLong = -39.103 ll 178.000
  val capePalliser: LatLong = -41.61 ll 175.29
  val makara: LatLong = -41.29 ll 174.62
  val himtangi: LatLong = -40.36 ll 175.22
  val capeEgmont: LatLong = -39.28 ll 173.75
  val p88: LatLong = -37.864 ll 174.759

  override val polygonLL: PolygonLL = PolygonLL(capeReinga, teHapua, capeBrett, aukland, sugarLoaf, crayfishBay, eCape, tableCape, capePalliser, makara,
    himtangi, capeEgmont, p88)
}

/** [[polygonLL]] graphical representation of the South Island of New Zealand and Stewart Island 145836km² + 1747.72km². Depends on nothing. */
object NZSouthIsland extends IslandPoly("New Zealand\nSouth Island", -43.68 ll 171.00, hillyOceForest)
{ val southArea: Kilares = 145836.kilares
  val stewartArea: Kilares = 1747.72.kilares
  override val area: Kilares = southArea + stewartArea

  val puponga: LatLong = -40.51 ll 172.72
  val dUrvilleNW: LatLong = -40.729 ll 173.869
  val arapaoa: LatLong = -41.094 ll 174.391
  val capeCambell: LatLong = -41.73 ll 174.27
  val p32: LatLong = -43.683 ll 173.086
  val waitakiMouth: LatLong = -44.937 ll 171.144
  val p40: LatLong = -45.878 ll 170.739
  val southEast: LatLong = -46.576 ll 169.584
  val slopePoint: LatLong = -46.67 ll 169.00
  val stewartSE = -47.288 ll 167.539
  val p58: LatLong = -46.315 ll 167.688
  val southWest: LatLong = -45.98 ll 166.47
  val p70: LatLong = -44.008 ll 168.368
  val capeFoulwind: LatLong = -41.751 ll 171.462

  override val polygonLL: PolygonLL = PolygonLL(puponga, dUrvilleNW, arapaoa, capeCambell, p32, waitakiMouth, p40, southEast, slopePoint, stewartSE, p58,
    southWest, p70, capeFoulwind)
}