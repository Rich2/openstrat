/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pAfrica
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic object for Lake Victoria. Depends on nothing. */
object LakeVictoria extends EArea2("Lake\nVictoria", -1 ll 32.83, Lake)
{ val southEast: LatLong = -2.23 ll 33.84
  val katongaMouth: LatLong =  -0.14 ll 31.94
  val east: LatLong = -0.39 ll 34.26
  val kusa = -0.326 ll 34.819
  val kisuma = -0.106 ll 34.723
  val north: LatLong = 0.34 ll 33.34
  val southWest: LatLong = -2.64 ll 31.76
  val southEastAfrica: LinePathLL = LinePathLL(southWest, southEast, east, kusa, kisuma, north, katongaMouth)

  override def polygonLL: PolygonLL = southEastAfrica.reverse.toPolygon
}

/** [[PolygonLL]] graphic object for Lake Tanganyika depends on nothing. */
object LakeTanganyika extends EArea2("Lake\nTanganyika", -6.25 ll 29.57, Lake)
{ val northEast: LatLong = -3.36 ll 29.34
  val northWest: LatLong = -3.36 ll 29.16
  val kalemie: LatLong = -5.91 ll 29.20
  val moba: LatLong = -7.04 ll 29.78
  val southWest = -8.476 ll 30.450
  val south: LatLong = -8.81 ll 31.03
  val westCoast: LinePathLL = LinePathLL(northEast, northWest, kalemie, moba, southWest)

  val mahaleSE: LatLong = -6.45 ll 30.14
  val muhela: LatLong = -6.24 ll 29.72
  val mahaleNE: LatLong = -5.91 ll 29.95

  val eastCoast: LinePathLL = LinePathLL(south, mahaleSE, muhela, mahaleNE, northEast)

  override def polygonLL: PolygonLL = south %: westCoast.reverse |++<| eastCoast.inner
}

/** [[PolygonLL]] graphic object for Lake Tanganyika depends on nothing. */
object LakeMalawi extends EArea2("Lake\nMalawi", -12.044 ll 34.461, Lake)
{ val north: LatLong = -9.493 ll 34.039
  val east: LatLong = -11.572 ll 34.960
  val south: LatLong = -14.418 ll 35.236
  val southWest: LatLong = -14.287 ll 34.688
  val west: LatLong = -12.169 ll 34.025

  override def polygonLL: PolygonLL = PolygonLL(north, east, south, southWest, west)
}

/** [[PolygonLL]] graphic object for Lake Mweru depends on nothing. */
object LakeMweru extends EArea2("Lake\nMweru", -8.952 ll 28.770, Lake)
{ val north: LatLong = -8.476 ll 28.890
  val northEast: LatLong = -8.651 ll 29.153
  val p20: LatLong = -8.997 ll 29.021
  val southEast: LatLong = -9.509 ll 28.513
  val southWest: LatLong = -9.494 ll 28.344
  val west: LatLong = -9.090 ll 28.342

  override def polygonLL: PolygonLL = PolygonLL(north, northEast, p20, southEast, southWest, west)
}

/** [[PolygonLL]] graphic object for the east of central Africa. Depends on [[SouthAfrica]], [[LakeTanganyika]] and  [[LakeVictoria]]. */
object centralAfricaEast extends EArea2("Central Africa\neast", -2.17 ll 36.64, plain)
{
  val mombassa: LatLong = -4.03 ll 39.28
  val saadani: LatLong = -6.042 ll 38.780
  val bagamoyo: LatLong = -6.439 ll 38.909
  val p40: LatLong = -6.865 ll 39.476
  val royumaMouth: LatLong = -10.469 ll 40.436
  //val seNacala: LatLong = -14.4 ll 40.3

  override def polygonLL: PolygonLL = LinePathLL(AfricaHorn.equatorEast, mombassa, saadani, bagamoyo, p40, royumaMouth, ZimMoz.northEast,
    LakeMalawi.south, LakeMalawi.east, LakeMalawi.north) ++
    LakeTanganyika.eastCoast |++| LinePathLL(LakeVictoria.southWest, LakeVictoria.southEast, LakeVictoria.east, LakeVictoria.kusa)
}

/** [[PolygonLL]] graphic object for the east of central Africa. Depends on [[SouthAfrica]], [[LakeTanganyika]] and  [[LakeVictoria]]. */
object ZimMoz extends EArea2("Mozambique", -2.17 ll 36.64, savannah)
{
  val northEast = -14.413 ll 40.805
  val p15: LatLong = -17 ll 39.06

  override def polygonLL: PolygonLL = PolygonLL(northEast, p15, SouthAfrica.beira, SouthAfrica.p95, LakeMalawi.southWest, LakeMalawi.south)
}

/** [[PolygonLL]] graphic object for Madagascar depends on nothing. */
object Zanzibar extends EArea2("Zanzibar", -6.112 ll 39.341, plain)
{ val north: LatLong = -5.721 ll 39.302
  val p25: LatLong = -6.126 ll 39.509
  val p40: LatLong = -6.383 ll 39.580
  val south: LatLong = -6.478 ll 39.507
  val capital: LatLong = -6.162 ll 39.185

  override def polygonLL: PolygonLL = PolygonLL(north, p25, p40, south, capital)
}