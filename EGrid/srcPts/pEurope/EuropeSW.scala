/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic for the south of France depends on [[IberiaNorth]] and [[FranceNorth]]. */
object FranceSouth extends EArea2("FranceSouth", 44.54 ll 2.53, oceanic)
{ val montelimar: LatLong = 44.55 ll 4.71
  val orangeCrossing: LatLong = 44.07 ll 4.76

  val stRaphael: LatLong = 43.42 ll 6.76
  val frejus: LatLong = 43.42 ll 6.74
  val laBastideBlanche: LatLong = 43.15 ll 6.62
  val capBenat: LatLong = 43.08 ll 6.36
  val laSeyneSurMer: LatLong = 43.04 ll 5.85
  val fosSurMer: LatLong = 43.42 ll 4.94
  val laGrandeMotte: LatLong = 43.55 ll 4.05
  val narbonne: LatLong = 43.14 ll 3.08

  val capBreton = 43.656 ll -1.446

  override val polygonLL = PolygonLL(FranceNorth.southWest, FranceNorth.southEast, montelimar, orangeCrossing, stRaphael, frejus,
    laBastideBlanche, capBenat, laSeyneSurMer, fosSurMer, laGrandeMotte, narbonne, IberiaNorth.laMassaneMouth, IberiaNorth.laNivelleMouth, capBreton)
}

/** [[PolygonLL]] graphic depends on nothing. */
object IberiaSouth extends EArea2("IberiaSouth", 38.48 ll -4.55, hillySub)
{ val valencia: LatLong = 39.45 ll -0.32
  val xabia: LatLong = 38.74 ll 0.22
  val p15: LatLong = 38.43 ll -0.39
  val capePalos: LatLong = 37.63 ll -0.70
  val p20: LatLong = 36.93 ll -1.90
  val southEast: LatLong = 36.74 ll -2.13
  val p50: LatLong = 36.703 ll -2.646
  val culoDePerros: LatLong = 36.695 ll -2.848
  val caboSacratif: LatLong = 36.683 ll -3.468
  val malaga: LatLong = 36.72 ll -4.41
  val p60: LatLong = 36.38 ll -5.22
  val bistroPoint: LatLong = 36.11 ll -5.34
  val tarifa: LatLong = 36.01 ll -5.61

  val chipiona: LatLong = 36.3 ll -6.19
  val matalascanas: LatLong = 36.966 ll -6.552
  val neuvaUmbria: LatLong = 37.205 ll -7.057
  val daBarreta = 36.960 ll -7.887
  val swPortugal: LatLong = 37.06 ll -8.34
  val comporta: LatLong = 38.38 ll -8.80
  val estoril: LatLong = 38.71 ll -9.48
  val p85: LatLong = 38.75 ll -9.47
  val p87: LatLong = 38.77 ll -9.50
  val mondegoMouth: LatLong = 40.15 ll -8.87

  override val polygonLL = PolygonLL(valencia, xabia, p15, capePalos, p20, southEast, p50, culoDePerros, caboSacratif, malaga, p60, bistroPoint, tarifa,
    chipiona, matalascanas, neuvaUmbria, daBarreta, swPortugal, comporta, estoril, p85, p87, mondegoMouth)
}

/** [[PolygonLL]] graphic depends on [[IberiaSouth]]. */
object IberiaNorth extends EArea2("Iberia north", 41 ll -3.5, hillySub)
{ val laMassaneMouth: LatLong = 42.54 ll 3.05
  val neSpain: LatLong = 42.18 ll 3.06
  val begur: LatLong = 41.95 ll 3.22

  val barcelona: LatLong = 41.31 ll 2.12
  val cambrills: LatLong = 41.07 ll 1.06
  val ebroMouth: LatLong = 40.72 ll 0.87
  val p37: LatLong = 40.80 ll 0.70
  val p40: LatLong = 40.55 ll 0.62
  val p42: LatLong = 40.55 ll 0.53

  val p50: LatLong = 40.19 ll -8.91
  val espinho: LatLong = 41.02 ll -8.64
  val p53: LatLong = 41.412 ll -8.788
  val p55: LatLong = 42.11 ll -8.90
  val p62: LatLong = 42.52 ll -9.04
  val escaselas: LatLong = 42.92 ll -9.29
  val malipica: LatLong = 43.34 ll -8.83
  val carino: LatLong = 43.76 ll -7.86
  val fozMouth: LatLong = 43.57 ll -7.24
  val caboPenas: LatLong = 43.658 ll -5.844
  val santander: LatLong = 43.49 ll -3.81
  val p80: LatLong = 43.46 ll -2.75
  val p90: LatLong = 43.310 ll -2.227
  val laNivelleMouth: LatLong = 43.39 ll -1.67

  override val polygonLL = PolygonLL(neSpain, begur, barcelona, cambrills, ebroMouth, p37, p40, p42, IberiaSouth.valencia, IberiaSouth.mondegoMouth, p50,
    espinho, p53, p55, p62, escaselas, malipica, carino, fozMouth, caboPenas, santander, p80, p90, laNivelleMouth, laMassaneMouth)
}
