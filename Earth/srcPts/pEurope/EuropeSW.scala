/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, WTile._

/** [[PolygonLL]] graphic depends on [[Iberia]] and [[Frankia]]. */
object FranceSouth extends EArea2("FranceSouth", 44.54 ll 2.53, plain)
{ val montelimar = 44.55 ll 4.71
  val orangeCrossing = 44.07 ll 4.76

  val stRaphael = 43.42 ll 6.76
  val frejus = 43.42 ll 6.74
  val laBastideBlanche = 43.15 ll 6.62
  val capBenat = 43.08 ll 6.36
  val laSeyneSurMer = 43.04 ll 5.85
  val fosSurMer = 43.42 ll 4.94
  val laGrandeMotte = 43.55 ll 4.05
  val narbonne = 43.14 ll 3.08

  override val polygonLL = PolygonLL(Iberia.laNivelleMouth, Frankia.southWest, Frankia.southEast, montelimar, orangeCrossing, stRaphael, frejus,
    laBastideBlanche, capBenat, laSeyneSurMer, fosSurMer, laGrandeMotte, narbonne, Iberia.laMassaneMouth)
}

/** [[PolygonLL]] graphic depends on nothing. */
object Iberia extends EArea2("Iberia", 41 ll -3.5, hills)
{ val laMassaneMouth = 42.54 ll 3.05
  val neSpain = 42.18 ll 3.06
  val begur = 41.95 ll 3.22
  val barcelona = 41.31 ll 2.12
  val southEast = 40 ll 0.03

  val malaga = 36.72 ll -4.41
  val p10 = 36.38 ll -5.22
  val bistroPoint = 36.11 ll -5.34
  val tarifa = 36.01 ll -5.61
  val chipiona = 36.3 ll -6.19
  val heulva = 37 ll -7
  val swPortugal = 37.06 ll -8.34
  val estoril = 38.71 ll -9.48

  val valencia = 39.45 ll -0.32
  val xabia = 38.74 ll 0.22
  val almeriaEast = 36.28 ll -2.06
  val southWest = 40 ll -8.91
  val espinho = 41.02 ll -8.64

  val escaselas = 42.92 ll -9.29
  val malipica = 43.34 ll -8.83
  val carino = 43.76 ll -7.86
  val fozMouth = 43.57 ll -7.24
  val santander = 43.49 ll -3.81
  val p80 = 43.46 ll -2.75
  val laNivelleMouth = 43.39 ll -1.67

  override val polygonLL = PolygonLL(neSpain, begur, barcelona, valencia, xabia, almeriaEast,malaga, p10, bistroPoint, tarifa, chipiona, heulva,
    swPortugal, estoril, espinho, escaselas, malipica, carino, fozMouth, santander, p80, laNivelleMouth, laMassaneMouth)
}