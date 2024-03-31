/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of England north. Depends on [[ScotlandLow]]. */
object EnglandNorth extends EArea2("England north",  53.632 ll -1.581, hillyOce)
{ val p20: LatLong = 54.483 ll -0.577
  val scarborough: LatLong = 54.28 ll -0.39
  val flamborough: LatLong = 54.11 ll -0.07
  val p30: LatLong = 53.621 ll 0.142
  val humberMouth: LatLong = 53.552 ll 0.077

  val merseyMouth: LatLong = 53.448 ll -3.033
  val formby: LatLong = 53.554 ll -3.105
  val fleetwood: LatLong = 53.920 ll -3.049
  val kentMouth: LatLong = 54.19 ll -2.86
  val humphreyHead: LatLong = 54.151 ll -2.831
  val sBarrow: LatLong = 54.04 ll -3.20
  val stBeesHead: LatLong = 54.51 ll -3.63

  override val polygonLL: PolygonLL = PolygonLL(ScotlandLow.tyneMouth, p20, scarborough, flamborough, p30,humberMouth, merseyMouth,formby, fleetwood, kentMouth,
    humphreyHead, sBarrow, stBeesHead, ScotlandLow.solwayMouth)

   val london: LocationLL = LocationLL("London", 51.51, - 0.13, 1)
   override val places: LocationLLArr = LocationLLArr(london)
}

/** [[polygonLL]] Graphical representation of Wales. Depends on nothing. */
object Wales extends EArea2("Wales", 52.40 ll -3.50, hillyOce)
{ val talacre: LatLong = 53.355 ll -3.315
  val deeMouth: LatLong = 53.246 ll -3.104
  val p5: LatLong = 52.75 ll -3
  val chepstow: LatLong = 51.61 ll -2.68
  val lavernock: LatLong = 51.405 ll -3.169
  val nashPoint: LatLong = 51.40 ll -3.56
  val p25: LatLong = 51.535 ll -4.205
  val p35: LatLong = 51.598 ll -4.926
  val skomer: LatLong = 51.739 ll -5.313
  val stDavids: LatLong = 51.88 ll -5.31
  val p50: LatLong = 52.133 ll -4.688
  val aberaeron: LatLong = 52.25 ll -4.26
  val tonfanu: LatLong = 52.612 ll -4.128
  val criccieth: LatLong = 52.92 ll -4.23
  val mynydd: LatLong = 52.79 ll -4.77
  val southStack: LatLong = 53.307 ll -4.700
  val carmelHead: LatLong = 53.404 ll -4.572
  val angleseaNE: LatLong = 53.417 ll -4.287
  val angleseaEast: LatLong = 53.310 ll -4.040
  val p80: LatLong = 53.342 ll -3.868

  override val polygonLL: PolygonLL = PolygonLL(talacre, deeMouth, p5, chepstow, lavernock, nashPoint, p25, p35, skomer, stDavids, p50, aberaeron, tonfanu,
    criccieth, mynydd, southStack, carmelHead,angleseaNE, angleseaEast, p80)
}

/** [[polygonLL]] graphical representation of England middle. Depends on [[EnglandNorth]] and [[Wales]]. */
object EnglandMiddle extends EArea2("England middle",  53.632 ll -1.581, deshot)
{ val p2: LatLong = 53.520 ll 0.076
  val donnaNook: LatLong = 53.467 ll 0.182
  val holbeach: LatLong = 52.89 ll 0.08
  val neneMouth: LatLong = 52.825 ll 0.216
  val ouseMouth: LatLong = 52.80 ll 0.35
  val hunstanton: LatLong = 52.97 ll 0.53
  val cromer: LatLong = 52.93 ll 1.30
  val horsey: LatLong = 52.75 ll 1.66
  val lowestoft: LatLong = 52.48 ll 1.76
  val p25: LatLong = 52.087 ll 1.579
  val p30: LatLong = 51.777 ll 1.132
  val p32: LatLong = 51.739 ll 0.947
  val foulness: LatLong = 51.61 ll 0.95

  val wirralNW = 53.386 ll -3.198

  override val polygonLL: PolygonLL = PolygonLL(EnglandNorth.humberMouth, p2, donnaNook, holbeach, neneMouth, ouseMouth, hunstanton, cromer, horsey, lowestoft,
    p25, p30, p32, foulness, EnglandSouth.northEast, Wales.chepstow, Wales.p5, Wales.deeMouth, wirralNW, EnglandNorth.merseyMouth)
}

/** [[polygonLL]] graphical representation of England. Depends on [[ScotlandHigh]] and [[Wales]]. */
object EnglandSouth extends EArea2("England south", 51.632 ll -0.679, oceanic)
{ val northEast: LatLong = 51.494 ll 0.474
  val nwGrain: LatLong = 51.48 ll 0.48
  val nekent: LatLong = 51.38 ll 1.43
  val dover: LatLong = 51.15 ll 1.38

  val dungeness: LatLong = 50.91 ll 0.98
  val beachyHead: LatLong = 50.73 ll 0.24
  val selsey: LatLong = 50.722 ll -0.788
  val bembridgePoint = 50.684 ll -1.069
  val ventnor: LatLong = 50.57 ll -1.29
  val bournemouth: LatLong = 50.71 ll -1.89
  val swanage: LatLong = 50.59 ll -1.95
  val ePortland: LatLong = 50.54 ll -2.41
  val charmouth: LatLong = 50.73 ll -2.91
  val exeMouth: LatLong = 50.61 ll -3.42
  val startPeninsular: LatLong = 50.22 ll -3.64
  val stAustell: LatLong = 50.33 ll -4.75
  val lizard: LatLong = 49.95 ll -5.20
  val penzance: LatLong = 50.06 ll -5.68
  val trevoseHead: LatLong = 50.55 ll -5.03
  val hartlandPoint: LatLong = 51.022 ll -4.525
  val peppercombe: LatLong = 50.995 ll -4.308
  val nwDevon: LatLong = 51.18 ll -4.19
  val parrettMouth: LatLong = 51.21 ll -3.01

  override val polygonLL: PolygonLL = PolygonLL(northEast, nwGrain, nekent, dover, dungeness, beachyHead, selsey, bembridgePoint, ventnor, bournemouth, swanage,
    ePortland, charmouth, exeMouth, startPeninsular, stAustell, lizard, penzance, trevoseHead, hartlandPoint, peppercombe, nwDevon, parrettMouth,
    Wales.chepstow)
}