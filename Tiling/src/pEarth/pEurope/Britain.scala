/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

object Scotland extends EArea2("Scotland", 56.82 ll -4.07, hills)
{
   val eggerness = 54.79 ll -4.34
   val sWhithorn = 54.67 ll -4.39
   val dunragit = 54.86 ll -4.84
   val cairngaan = 54.63 ll -4.88
   val northCairn = 55.00 ll -5.15
   val ayr = 55.44 ll -4.64   
   val sKintyre = 55.29 ll -5.77
   
   val portahaven = 55.69 ll -6.25
   
   val snaigmore = 55.85 ll -6.45
   val wScarba = 56.17 ll -5.75
   val kerrera = 56.38 ll -5.58
   val wMull = 56.28 ll -6.38
   val canna = 57.05 ll -6.60
   val wRum = 57.00 ll -6.45
   
   val wSkye = 57.43 ll -6.78
   val nSkye = 57.70 ll -6.29
   val nRona = 57.58 ll -5.96
   val nwScotland = 58.61 ll -4.99            
   val johnOGroats = 58.62 ll -3.08
   val girnigoe = 58.47 ll -3.05
   val inverness = 57.49 ll -4.22
   val lossieMouth = 57.72 ll -3.33
   val aberdeenshire = 57.58 ll -1.85
   
   val firthForth = 56.00 ll -3.39
   val archerfield = 56.06 ll -2.81
   val tantallion = 56.05 ll -2.65
   val stAbbs = 55.91 ll -2.14
   val bamburgh = 55.60 ll -1.68
   val polygonLL = PolygonLL(England.solwayMouth, eggerness, sWhithorn, dunragit, cairngaan, northCairn, ayr, sKintyre, portahaven,
         snaigmore, wScarba, kerrera, wMull, canna, wRum,
         wSkye, nSkye, nRona, nwScotland, johnOGroats, girnigoe,
         inverness, lossieMouth, aberdeenshire, firthForth, archerfield, tantallion, stAbbs, bamburgh, England.tyneMouth)
}

object England extends EArea2("England",  52.73 ll -1.26, plain)
{
   val penzance = 50.06 ll -5.68
   val trevoseHead = 50.55 ll -5.03
   val nwDevon = 51.18 ll -4.19
   val parrettMouth = 51.21 ll -3.01
   val chepstow = 51.61 ll -2.68
   val stDavids = 51.88 ll -5.31   
   
   val anglesey = 53.39 ll -4.54
   val liverpool = 53.44 ll -3.02
   val kentMouth = 54.19 ll -2.86
   val sBarrow = 54.04 ll -3.20
   val stBeesHead = 54.51 ll -3.63
   val solwayMouth = 54.89 ll -3.37      
   
   val tyneMouth = 55.01 ll -1.41
   val scarborough = 54.28 ll -0.39
   val flamborough = 54.11 ll -0.07
   
   val holbeach = 52.89 ll 0.08
   val ouseMouth = 52.80 ll 0.35
   
   val hunstanton = 52.97 ll 0.53
   val cromer = 52.93 ll 1.30
   val horsey = 52.75 ll 1.66
   val lowestoft = 52.48 ll 1.76
   val foulness = 51.61 ll 0.95
   val nwGrain = 51.48 ll 0.48
   val nekent = 51.38 ll 1.43
   val dover = 51.15 ll 1.38
   val ventnor = 50.57 ll -1.29
   val bournemouth = 50.71 ll -1.89
   val swanage = 50.59 ll -1.95
   val ePortland = 50.54 ll -2.41
   val charmouth = 50.73 ll -2.91
   val exeMouth = 50.61 ll -3.42
  
   val startPeninsular = 50.22 ll -3.64
   val stAustell = 50.33 ll -4.75
   val lizard = 49.95 ll -5.20
   val polygonLL = PolygonLL(penzance, trevoseHead, nwDevon, parrettMouth, chepstow, stDavids, anglesey, liverpool,
         kentMouth, sBarrow, stBeesHead, solwayMouth,  tyneMouth, scarborough, flamborough, holbeach, ouseMouth,
         hunstanton, cromer, horsey, lowestoft, foulness, nwGrain, nekent, dover, ventnor,
         bournemouth, swanage, ePortland, charmouth, exeMouth, startPeninsular, stAustell, lizard)         
}