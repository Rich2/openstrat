/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[polygonLL]] graphical representation of Scotland. Depends on nothing. */
object Scotland extends EArea2("Scotland", 56.82 ll -4.07, hills)
{ val johnOGroats: LatLong = 58.62 ll -3.08
  val girnigoe: LatLong = 58.47 ll -3.05
  val inverness: LatLong = 57.49 ll -4.22
  val lossieMouth: LatLong = 57.72 ll -3.33
  val aberdeenshire: LatLong = 57.58 ll -1.85
  val firthForth: LatLong = 56.00 ll -3.39
  val archerfield: LatLong = 56.06 ll -2.81
  val tantallion: LatLong = 56.05 ll -2.65
  val stAbbs: LatLong = 55.91 ll -2.14
  val bamburgh: LatLong = 55.60 ll -1.68
  val tyneMouth: LatLong = 55.01 ll -1.41

  val solwayMouth: LatLong = 54.89 ll -3.37
  val eggerness: LatLong = 54.79 ll -4.34
  val sWhithorn: LatLong = 54.67 ll -4.39
  val dunragit: LatLong = 54.86 ll -4.84
  val cairngaan: LatLong = 54.63 ll -4.88
  val northCairn: LatLong = 55.00 ll -5.15
  val ayr: LatLong = 55.44 ll -4.64
  val sKintyre: LatLong = 55.29 ll -5.77
  val portahaven: LatLong = 55.69 ll -6.25
  val snaigmore: LatLong = 55.85 ll -6.45
  val wScarba: LatLong = 56.17 ll -5.75
  val kerrera: LatLong = 56.38 ll -5.58
  val wMull: LatLong = 56.28 ll -6.38
  val canna: LatLong = 57.05 ll -6.60
  val wRum: LatLong = 57.00 ll -6.45
  val wSkye: LatLong = 57.43 ll -6.78
  val nSkye: LatLong = 57.70 ll -6.29
  val nRona: LatLong = 57.58 ll -5.96
  val nwScotland: LatLong = 58.61 ll -4.99

  override val polygonLL: PolygonLL = PolygonLL( johnOGroats, girnigoe, inverness, lossieMouth, aberdeenshire, firthForth, archerfield, tantallion,
     stAbbs, bamburgh, tyneMouth,
     solwayMouth, eggerness, sWhithorn, dunragit, cairngaan, northCairn, ayr, sKintyre, portahaven,snaigmore, wScarba, kerrera, wMull, canna, wRum,
     wSkye, nSkye, nRona, nwScotland,
  )
}

/** [[polygonLL]] Graphical representation of Wales. Depends on nothing. */
object Wales extends EArea2("Wales", 52.40 ll -3.50, hills)
{ val liverpool: LatLong = 53.44 ll -3.02
  val chepstow: LatLong = 51.61 ll -2.68
  val nashPoint: LatLong = 51.40 ll -3.56
  val stDavids: LatLong = 51.88 ll -5.31
  val aberaeron: LatLong = 52.25 ll -4.26
  val criccieth: LatLong = 52.92 ll -4.23
  val mynydd: LatLong = 52.79 ll -4.77
  val anglesey: LatLong = 53.39 ll -4.54

  override val polygonLL: PolygonLL = PolygonLL(liverpool, chepstow, nashPoint, stDavids, aberaeron, criccieth, mynydd, anglesey)
}

/** [[polygonLL]] graphical representation of England. Depends on [[Scotland]] and [[Wales]]. */
object England extends EArea2("England", 52.73 ll -1.26, land)
{ val scarborough: LatLong = 54.28 ll -0.39
  val flamborough: LatLong = 54.11 ll -0.07
  val holbeach: LatLong = 52.89 ll 0.08
  val ouseMouth: LatLong = 52.80 ll 0.35
  val hunstanton: LatLong = 52.97 ll 0.53
  val cromer: LatLong = 52.93 ll 1.30
  val horsey: LatLong = 52.75 ll 1.66
  val lowestoft: LatLong = 52.48 ll 1.76
  val foulness: LatLong = 51.61 ll 0.95
  val nwGrain: LatLong = 51.48 ll 0.48
  val nekent: LatLong = 51.38 ll 1.43
  val dover: LatLong = 51.15 ll 1.38

  val dungeness: LatLong = 50.91 ll 0.98
  val beachyHead: LatLong = 50.73 ll 0.24
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
  val nwDevon: LatLong = 51.18 ll -4.19
  val parrettMouth: LatLong = 51.21 ll -3.01

  val kentMouth: LatLong = 54.19 ll -2.86
  val sBarrow: LatLong = 54.04 ll -3.20
  val stBeesHead: LatLong = 54.51 ll -3.63

  override val polygonLL: PolygonLL = PolygonLL(Scotland.tyneMouth, scarborough, flamborough, holbeach, ouseMouth, hunstanton, cromer, horsey,
    lowestoft, foulness, nwGrain, nekent, dover,
    dungeness, beachyHead, ventnor, bournemouth, swanage, ePortland, charmouth, exeMouth, startPeninsular, stAustell, lizard, penzance, trevoseHead,
    nwDevon, parrettMouth, Wales.chepstow,  Wales.liverpool,  kentMouth, sBarrow, stBeesHead, Scotland.solwayMouth,
  )

   val london: LocationLL = LocationLL("London", 51.51, - 0.13, 1)
   override val places: LocationLLArr = LocationLLArr(london)
}