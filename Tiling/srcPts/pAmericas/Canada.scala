/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pPts
import geom._, pglobe._, LatLong._, WTile._

object Alaska extends EArea2("Alaska", 66.33 ll -151.16, taiga)
{  val northEast = 69.45 ll -141
  val yakut10: LatLong = 59.93 ll -141.03
  val southWest: LatLong = 60.50 ll -164.55
  val p33 = 61.60 ll -166.18
  val koyuk = 64.93 ll -161.19
  val p40 = 64.65 ll -166.39
  val capeDouglas = 65.0 ll -166.70
  val teller = 65.26 ll -166.36
  val imurukMouth = 65.23 ll -166.04
  val brevig = 65.34 ll -166.50
  val west = 65.66 ll -168.11
  val northWest: LatLong = 70.11 ll -161.87
  val p10 = 70.11 ll -143.20

  override def polygonLL: PolygonLL = PolygonLL(northEast, yakut10, southWest, p33, koyuk, p40, capeDouglas, teller, imurukMouth, brevig, west, northWest, p10)
}

object NorthWestCanada extends EArea2("NorthWest Canada", 64.051 ll-129.98, taiga)
{ val yakut50 = 60 ll -139.60
  val yakut10 = 68.90 ll -136.53
  val inuvik10 = 70.56 ll -128.00
  val nunavut10 = 69.00 ll -115.80
  val raeMouth = 67.92 ll -115.34
  val nunavut15 = 67.68 ll -101.53
  val nunavut17 = 68.55 ll -97.80
  val nunavut25 = 68.05 ll -94.76
  val nunavut30 = 69.32 ll -94.23
  val nunavut40 = 71.39 ll -96.17
  val somersetNW = 73.99 ll -95.28
  val somersetNE = 73.91 ll -90.18
  val somersetSE = 72.73 ll -92.27
  val nunavut45 = 72.72 ll -93.79
  val nunavut47 = 71.76 ll -93.69
  val nunavut49 = 71.35 ll -92.97
  val nunavut54 = 70.17 ll -91.57
  val nunavut56 = 69.70 ll -92.48
  val nunavut60 = 69.50 ll -91.83
  val nunavut64 = 68.45 ll -90.59
  val nunavut65 = 68.24 ll -90.23
  val nunavut67 = 69.26 ll -89.13
  val nunavut70 = 68.80 ll -88.02
  val nunavut80 = 67.21 ll -87.19
  val nunavut83 = 68.72 ll -85.59
  val nunavut85 = 69.85 ll -85.55
  val nunavut87 = 69.65 ll -82.13
  val nunavut88: LatLong = 69.20 ll -81.35
  val navut20 = 67.45 ll -81.21
  val naujaat10 = 66.35 ll - 83.41
  val naujaat12 = 66.18 ll -84.44
  val nunavut90 = 66.27 ll -85.23
  val naujaat20 = 66.16 ll - 85.86
  val nunavut92 = 66.54 ll -86.76
  val naujaat15 = 66.20 ll -85.90
  val naujaat17 = 64.00 ll -88.76
  val hudsonBay60W = 60 ll -94.82

  override def polygonLL: PolygonLL = PolygonLL(yakut50, Alaska.yakut10, Alaska.northEast, yakut10, inuvik10, nunavut10, raeMouth, nunavut15,
    nunavut17, nunavut25, nunavut30, nunavut40, somersetNW, somersetNE, somersetSE, nunavut45, nunavut47, nunavut49, nunavut54, nunavut56, nunavut60,
    nunavut65, nunavut67, nunavut70, nunavut80, nunavut83, nunavut85, nunavut87, nunavut88, navut20, naujaat10, naujaat12, nunavut90, naujaat20, nunavut92, naujaat15, naujaat17, hudsonBay60W)
}

object SouthWestCanada extends EArea2("SouthWest Canada", 55 ll-110, taiga)
{ val wUsaNE = 50 ll -98
  val w49th: LatLong = 49 ll -125.66
  val vancouverNW = 50.77 ll -128.38
  val p50 = 54.71 ll -132.81
  val eggIsland : LatLong= 59.91 ll -94.85

  val churchillMouth = 58.79 ll -94.20
  val manitoba10 = 58.75 ll -93.24
  val nelsonMouth = 57.09 ll -92.47
  override def polygonLL: PolygonLL = PolygonLL(wUsaNE, w49th, vancouverNW, p50, NorthWestCanada.yakut50, NorthWestCanada.hudsonBay60W, eggIsland, churchillMouth, manitoba10, nelsonMouth)
}

object BanksIsland extends EArea2("Banks Island", 73.12 ll -121.13, tundra)
{
  val p0 = 74.28 ll -118
  val p10 = 73.54 ll -115.34
  val p18 = 72.66 ll -119.15
  val p30 = 71.58 ll -120.50
  val south = 71.11 ll -123.06
  val southWest = 71.96 ll -125.81
  val northWest = 74.35 ll -124.70
  val north = 74.56 ll -121.49
  override def polygonLL: PolygonLL = PolygonLL(p0, p10, p18, p30, south, southWest, northWest, north)
}

object VictoriaIsland extends EArea2("Victoria Island", 70.65 ll -109.36, tundra)
{
  val stefanssonN = 73.75 ll -105.29
  val vic5 = 71.12 ll -104.60
  val vic10 = 70.21 ll -101.34
  val southEast = 69.00 ll -101.79
  val southWest = 68.46 ll -113.21
  val vic30 = 69.22 ll -113.69
  val pointCaen = 69.30 ll -115.95
  val vic40 = 71.60 ll -118.90
  val northWest = 73.36 ll -114.57
  override def polygonLL: PolygonLL = PolygonLL(stefanssonN, vic5, vic10, southEast, southWest, vic30, pointCaen, vic40, northWest)
}

object SouthamptonIsland extends EArea2("Southampton Island", 64.5 ll -84.35, tundra)
{
  val north = 66.02 ll -85.08
  val p05 = 65.27 ll -84.26
  val east = 63.78 ll -80.16
  val p30 = 63.45 ll -81.00
  val p32 = 63.44 ll -80.97
  val south = 63.11 ll -85.46
  val southEast = 63.56 ll -87.13
  val p52 = 64.09 ll -86.18
  val p80 = 65.73 ll -85.96
  override def polygonLL: PolygonLL = PolygonLL(north, p05, east, p30, p32, south, southEast, p52, p80)
}

object CentralCanada extends EArea2("Central Canada", 52.37 ll -86.94, taiga)
{ val manitoba20 = 57.26 ll -90.89
  val jamesBayNW: LatLong = 55.07 ll -82.31
  val attapiskatMouth = 52.97 ll -82.26
  val moosoneeMouth = 51.36 ll -80.40
  val jamesBayS: LatLong = 51.14 ll -79.79

  override def polygonLL: PolygonLL = LinePathLL(SouthWestCanada.wUsaNE, SouthWestCanada.nelsonMouth, manitoba20, jamesBayNW, attapiskatMouth, moosoneeMouth, jamesBayS) ++
    LakeHuron.centralCanadaCoast ++! LakeSuperior.canadaCoast
}

object BaffinIsland extends EArea2("Baffin Island", 69.55 ll -72.64, tundra)
{
  val bylotNE = 73.67 ll -78.13
  val p20 = 70.54 ll -68.31
  val east = 66.67 ll -61.29
  val southEast = 61.88 ll -65.96
  val p35 = 64.37 ll -74.67
  val p40 = 64.43 ll -78.02
  val p47 = 66.17 ll -74.43
  val p60 = 69.76 ll -77.61
  val p70 = 70.50 ll -88.72
  val p80 = 73.58 ll -88.21
  override def polygonLL: PolygonLL = PolygonLL(bylotNE, p20, east, southEast, p35, p40, p47, p60, p70, p80)
}

object EastCanada extends EArea2("East Canada", 53.71 ll-70, taiga)
{ val eastMainMouth = 52.24 ll -78.56
  val jamesBayMouthEast = 54.63 ll -79.74
  val hudsonBayEast = 56.46 ll -76.52
  val nunavut120 = 58.68 ll -78.69
  val hudsonBayMouthE: LatLong = 62.57 ll -77.99
  val ungavaW: LatLong = 61.04 ll -69.56
  val koksoakMouth = 58.90 ll -69.38
  val ungavaS: LatLong = 58.26 ll -67.45
  val katavik50 = 58.82 ll -66.44
  val ungavaE: LatLong = 60.49 ll -64.74
  val labrador50 = 54.54 ll -57.30
  val labrador60 = 52.10 ll -55.72
  val labradorE: LatLong = 52.42 ll -56.05
  val labrador70 = 50.27 ll -59.91
  val madeleine = 49.25 ll -65.36
  val septlles = 50.23 ll -66.37
  val pointeMonts = 49.32 ll -67.38
  val capRosiers = 48.86 ll -64.20
  val gasconsEst = 48.21 ll -64.78
  val scoudoucMouth = 46.22 ll -64.55
  val eNovaScotia: LatLong = 46.16 ll -59.86
  val novaScotiaS = 43.43 ll -65.61

  //val e49th = deg(49, -64.41) Removed as Newfoundland is the close
  val maineE = degs(44.87, -66.93)

  val eCanadaCoast = LinePathLL(ungavaE, labrador50, labrador60, labradorE, labrador70, septlles, pointeMonts, madeleine, capRosiers, gasconsEst,
    scoudoucMouth, eNovaScotia, novaScotiaS)

  override val polygonLL: PolygonLL = LakeHuron.eastCanadaCoast ++ LinePathLL(CentralCanada.jamesBayS, eastMainMouth, jamesBayMouthEast,
    hudsonBayEast, nunavut120, hudsonBayMouthE, ungavaW, koksoakMouth, ungavaS, katavik50) ++  eCanadaCoast +% maineE ++
    LakeOntario.canadaCoast ++! LakeErie.eastCanadaCoast
}

object NewFoundland extends EArea2("Newfoundland", 48.72 ll -56.16, taiga)
{
  val north = 51.63 ll -55.43
  val pollardsPoint = 49.75 ll -56.92
  val p10 = 50.15 ll -56.16
  val p20 = 49.25 ll -53.47
  val east: LatLong = 47.52 ll -52.64
  val southWest = 47.62 ll -59.30
  val capGeorge = 48.46 ll -59.27
  val savageCove = 51.33 ll -56.70
  override def polygonLL: PolygonLL = PolygonLL(north, pollardsPoint, p10, p20, east, southWest, capGeorge, savageCove)
}


object LakeSuperior extends EArea2("Lake Superior", 47.5 ll -88, lake)
{ val east: LatLong = 46.52 ll -84.61
  val michipicoten: LatLong = 47.96 ll -84.86
  val north: LatLong = 48.80 ll -87.31
  val west48: LatLong = 48.00 ll -89.57
  val canadaCoast = LinePathLL(east, michipicoten, north, west48)

  val west: LatLong = 46.77 ll -92.11
  val montrealMouth: LatLong = 44.57 ll -90.42
  val highRock : LatLong = 47.42 ll -87.71
  val chocolayMouth: LatLong = 46.50 ll -87.35

  val usCoast = LinePathLL(west48, west, montrealMouth, highRock, chocolayMouth, east)

  override def polygonLL: PolygonLL = canadaCoast.reverse +/--! usCoast
}

object LakeHuron extends EArea2("Lake Huron", 44.80 ll -82.4, lake)
{ val northEast: LatLong = 45.89 ll -80.75
  val killarney: LatLong = 45.99 ll -81.43
  val fitzwilliam: LatLong = 45.45 ll -81.79
  val borderNorth: LatLong = 45.91 ll -83.50
  val pineMouth: LatLong = 46.05 ll -84.66

  val centralCanadaCoast: LinePathLL = LinePathLL(northEast, killarney, fitzwilliam, borderNorth, pineMouth)

  val south: LatLong = 43.00 ll -82.42
  val saubleBeach: LatLong = 44.63 ll -81.27
  val tobermory: LatLong = 45.26 ll -81.69
  val owenSound: LatLong = 44.58 ll -80.94
  val geogianSouth: LatLong = 44.47 ll -80.11
  val east: LatLong = 44.86 ll -79.89

  val eastCanadaCoast: LinePathLL = LinePathLL(south, saubleBeach, tobermory, owenSound, geogianSouth, east, northEast)

  val pesqueIsle: LatLong = 45.27 ll -83.38
  val tobicoLagoon: LatLong = 43.67 ll -83.90
  val turnipRock: LatLong = 44.07 ll -82.96

  val usCoastSouth: LinePathLL = LinePathLL(pesqueIsle, tobicoLagoon, turnipRock, south)

  override def polygonLL: PolygonLL = usCoastSouth.reverse ++ LinePathLL(LakeMichigan.mouthSouth, LakeMichigan.mouthNorth) ++/
    centralCanadaCoast +/--! eastCanadaCoast
}

object LakeErie extends EArea2("Lake Erie", 42.24 ll -81.03, lake)
{ val niagraMouth: LatLong = 42.89 ll -78.91
  val longPoint: LatLong = 42.58 ll -80.44
  val portStanley: LatLong = 42.66 ll -81.24
  val theTip: LatLong = 41.90 ll -82.50
  val detroitMouth: LatLong = 42.05 ll -83.15

  val eastCanadaCoast: LinePathLL = LinePathLL(niagraMouth, longPoint, portStanley, theTip, detroitMouth)

  val maumeeMouth: LatLong = 41.70 ll -83.47
  val south: LatLong = 41.38 ll -82.49
  val east: LatLong = 42.78 ll -78.85

  val usCoast: LinePathLL = LinePathLL(detroitMouth, maumeeMouth, south, east, niagraMouth)

  override def polygonLL: PolygonLL = eastCanadaCoast.reverse +/--! usCoast
}

object LakeOntario extends EArea2("Lake Ontario", 43.65 ll -77.84, lake)
{ val wolfeSW: LatLong = 44.10 ll -76.44
  val northEast: LatLong = 44.20 ll -76.51
  val frenchmansBay: LatLong = 43.81 ll -79.09
  val southWest: LatLong = 43.30 ll -79.82
  val niagraMouth: LatLong = 43.26 ll -79.07

  val canadaCoast: LinePathLL = LinePathLL(wolfeSW, northEast, frenchmansBay, southWest, niagraMouth)

  val southEast: LatLong = 43.53 ll -76.22
  val tibbettsPoint =  44.10 ll -76.37

  val usCoast: LinePathLL = LinePathLL(niagraMouth, southEast, tibbettsPoint, wolfeSW)

  override def polygonLL: PolygonLL = canadaCoast.reverse +/--! usCoast
}