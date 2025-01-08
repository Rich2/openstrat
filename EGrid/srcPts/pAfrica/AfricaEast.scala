/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for Lake Victoria. Depends on nothing. */
object LakeAlbert extends LakePoly("Lake\nAlbert", 1.673 ll 30.933, Lake)
{ override val area: Kilares = 5590.kilares

  val north: LatLong = 2.46 ll 31.340
  val east: LatLong = 1.937 ll 31.418
  val southEast: LatLong = 1.035 ll 30.583
  val eastCoast: LinePathLL = LinePathLL(north, east, southEast)

  val p60: LatLong = 1.294 ll 30.363
  val p85: LatLong = 1.944 ll 30.967
  val westCoast = LinePathLL(southEast, p60, p85, north)

  override def polygonLL: PolygonLL = eastCoast |-++-| westCoast
}

object Uganda extends EarthPoly("Uganda", 2.024 ll 33.075, ice)
{
  val northWest = 3.705 ll 30.977
  
  override def polygonLL: PolygonLL = PolygonLL(northWest)
}

/** [[PolygonLL]] graphic object for Lake Victoria. Depends on nothing. */
object LakeVictoria extends LakePoly("Lake\nVictoria", -1 ll 32.83, Lake)
{ override val area: Kilares = 59947.kilares

  val southEast: LatLong = -2.23 ll 33.84
  val katongaMouth: LatLong =  -0.14 ll 31.94
  val east: LatLong = -0.39 ll 34.26
  val kusa: LatLong = -0.326 ll 34.819
  val kisuma: LatLong = -0.106 ll 34.723
  val north: LatLong = 0.34 ll 33.34
  val southWest: LatLong = -2.64 ll 31.76
  val southEastAfrica: LinePathLL = LinePathLL(southWest, southEast, east, kusa, kisuma, north, katongaMouth)

  override def polygonLL: PolygonLL = southEastAfrica.reverse.toPolygon
}

/** [[PolygonLL]] graphic object for Lake Tanganyika. Depends on nothing. */
object LakeTanganyika extends LakePoly("Lake\nTanganyika", -6.25 ll 29.57, Lake)
{ override val area: Kilares = 32900.kilares

  val northEast: LatLong = -3.36 ll 29.34
  val mahaleNE: LatLong = -5.91 ll 29.95
  val muhela: LatLong = -6.24 ll 29.72
  val mahaleSE: LatLong = -6.45 ll 30.14
  val south: LatLong = -8.81 ll 31.03
  val eastCoast: LinePathLL = LinePathLL(northEast, mahaleNE, muhela, mahaleSE, south)

  val southWest: LatLong = -8.476 ll 30.450
  val moba: LatLong = -7.04 ll 29.78
  val kalemie: LatLong = -5.91 ll 29.20
  val northWest: LatLong = -3.36 ll 29.16
  val westCoast: LinePathLL = LinePathLL(southWest, moba, kalemie, northWest)

  override def polygonLL: PolygonLL = eastCoast |++| westCoast
}

/** [[PolygonLL]] graphic object for Lake Malawi, depends on nothing. */
object LakeMalawi extends LakePoly("Lake\nMalawi", -12.044 ll 34.461, Lake)
{ override val area: Kilares = 29600.kilares

  val north: LatLong = -9.493 ll 34.039
  val east: LatLong = -11.572 ll 34.960
  val p35: LatLong = -13.721 ll 34.879
  val south: LatLong = -14.418 ll 35.236
  val eastCoast: LinePathLL = LinePathLL(north, east, p35, south)

  val southWest: LatLong = -14.287 ll 34.688
  val west: LatLong = -12.169 ll 34.025
  val zambiaCoast = LinePathLL(southWest, west, north)

  override def polygonLL: PolygonLL = eastCoast |++| zambiaCoast
}

/** [[PolygonLL]] graphic object for Lake Mweru depends on nothing. */
object LakeMweru extends LakePoly("Lake\nMweru", -8.952 ll 28.770, Lake)
{ override val area: Kilares = 5120.kilares

  val north: LatLong = -8.476 ll 28.890
  val northEast: LatLong = -8.651 ll 29.153
  val p20: LatLong = -8.997 ll 29.021
  val southEast: LatLong = -9.509 ll 28.513
  val southWest: LatLong = -9.494 ll 28.344
  val west: LatLong = -9.090 ll 28.342

  override def polygonLL: PolygonLL = PolygonLL(north, northEast, p20, southEast, southWest, west)
}

/** [[PolygonLL]] graphic object for the east of central Africa. Depends on [[AfricaHorn]], [[Mozambique]], [[LakeMalawi]], [[LakeTanganyika]] and
 * [[LakeVictoria]]. */
object CentralAfricaEast extends EarthPoly("Central Africa\neast", -2.17 ll 36.64, oceanic)
{ val mombassa: LatLong = -4.03 ll 39.28
  val saadani: LatLong = -6.042 ll 38.780
  val bagamoyo: LatLong = -6.439 ll 38.909
  val p40: LatLong = -6.865 ll 39.476
  val royumaMouth: LatLong = -10.469 ll 40.436
  val p48: LatLong = -14.413 ll 40.805
  val natiquinde: LatLong = -16.406 ll 39.913
  val p52: LatLong = -17.009 ll 39.070

  override def polygonLL: PolygonLL = LinePathLL(Kenya.equatorEast, mombassa, saadani, bagamoyo, p40, royumaMouth, p48, natiquinde, p52,
    Mozambique.p15) ++< LakeMalawi.eastCoast ++< LakeTanganyika.eastCoast |++| LinePathLL(LakeVictoria.southWest, LakeVictoria.southEast, LakeVictoria.east,
    LakeVictoria.kusa)
}

/** Island grouping of Unguja and Pemba, but not Mafia Island of the Zanzibar Archipelago. */
object UngujaPemba extends IslandPolyGroup("Zanzibar")
{ val pemba: Kilares =988.kilares
  override def area: Kilares = Unguja.area + pemba
  override def elements: RArr[IslandPolyLike] = RArr(Unguja)
}

/** [[PolygonLL]] graphic object for the Unguga island of the Zanzibar archipelago, depends on nothing. */
object Unguja extends IslandPoly("Unguja", -6.112 ll 39.341, tropical)
{ override val area: Kilares = 1666.kilares
  override def groupings: RArr[IslandPolyGroup] = RArr(UngujaPemba)

  val north: LatLong = -5.721 ll 39.302
  val p25: LatLong = -6.126 ll 39.509
  val p40: LatLong = -6.383 ll 39.580
  val south: LatLong = -6.478 ll 39.507
  val capital: LatLong = -6.162 ll 39.185

  override def polygonLL: PolygonLL = PolygonLL(north, p25, p40, south, capital)
}
/** [[PolygonLL]] graphic object for most of Mozambique. Depends on [[CentralAfricaEast]], [[SouthAfricaEast]], and [[LakeMalawi]]. */
object Mozambique extends EarthPoly("Mozambique", -2.17 ll 36.64, savannah)
{ val p15: LatLong = -17.760 ll 37.202
  val beira: LatLong = -19.850 ll 34.883
  val p20: LatLong = -20.940 ll 35.127
  val inhambane: LatLong = -23.38 ll 35.2
  val guinjala: LatLong = -24.106 ll 35.496
  val p35: LatLong = -24.525 ll 35.171

  val mutare: LatLong = -18.977 ll 32.668
  val tete: LatLong = -16.154 ll 33.594

  override def polygonLL: PolygonLL = PolygonLL(p15, beira, p20, inhambane, guinjala, p35, SouthAfricaEast.maputoMouth, SouthAfricaEast.luvuvhuMouth, mutare,
    tete, LakeMalawi.southWest, LakeMalawi.south)
}