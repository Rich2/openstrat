/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth; package pEurope
import geom._, pglobe._, egrid._, WTiles._

/** [[PolygonLL]] graphic depends on [[IberiaNorth]] and [[Frankia]]. */
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

  override val polygonLL = PolygonLL(IberiaNorth.laNivelleMouth, Frankia.southWest, Frankia.southEast, montelimar, orangeCrossing, stRaphael, frejus,
    laBastideBlanche, capBenat, laSeyneSurMer, fosSurMer, laGrandeMotte, narbonne, IberiaNorth.laMassaneMouth)
}

/** [[PolygonLL]] graphic depends on nothing. */
object IberiaSouth extends EArea2("IberiaSouth", 38.48 ll -4.55, plain)
{ val valencia = 39.45 ll -0.32
  val xabia = 38.74 ll 0.22
  val p15 = 38.43 ll -0.39
  val capePalos = 37.63 ll -0.70
  val p20 = 36.93 ll -1.90
  val southEast = 36.74 ll -2.13
  val malaga = 36.72 ll -4.41
  val p10 = 36.38 ll -5.22
  val bistroPoint = 36.11 ll -5.34
  val tarifa = 36.01 ll -5.61

  val chipiona = 36.3 ll -6.19
  val heulva = 37 ll -7
  val swPortugal = 37.06 ll -8.34
  val comporta = 38.38 ll -8.80
  val estoril = 38.71 ll -9.48
  val p85 = 38.75 ll -9.47
  val p87 = 38.77 ll -9.50
  val mondegoMouth = 40.15 ll -8.87
  override val polygonLL = PolygonLL(valencia, xabia, p15, capePalos, p20, southEast, malaga, p10, bistroPoint, tarifa, chipiona, heulva, swPortugal,
    comporta, estoril, p85, p87, mondegoMouth)
}

/** [[PolygonLL]] graphic depends on [[IberiaSouth]]. */
object IberiaNorth extends EArea2("Iberia", 41 ll -3.5, hills)
{ val laMassaneMouth = 42.54 ll 3.05
  val neSpain = 42.18 ll 3.06
  val begur = 41.95 ll 3.22

  val barcelona = 41.31 ll 2.12
  val cambrills = 41.07 ll 1.06
  val ebroMouth = 40.72 ll 0.87
  val p37 = 40.80 ll 0.70
  val p40 = 40.55 ll 0.62
  val p42 = 40.55 ll 0.53

  val p50 = 40.19 ll -8.91
  val espinho = 41.02 ll -8.64
  val p55 = 42.11 ll -8.90
  val p62 = 42.52 ll -9.04
  val escaselas = 42.92 ll -9.29
  val malipica = 43.34 ll -8.83
  val carino = 43.76 ll -7.86
  val fozMouth = 43.57 ll -7.24
  val santander = 43.49 ll -3.81
  val p80 = 43.46 ll -2.75
  val laNivelleMouth = 43.39 ll -1.67

  override val polygonLL = PolygonLL(neSpain, begur, barcelona, cambrills, ebroMouth, p37, p40, p42, IberiaSouth.valencia, IberiaSouth.mondegoMouth,
    p50, espinho, p55, p62, escaselas, malipica, carino, fozMouth, santander, p80, laNivelleMouth, laMassaneMouth)
}

/** [[PolygonLL]] graphic for Italy depends on [[ItalySouth]]. */
object Italy extends EArea2("ItalyNorth", 43.61 ll 11.82, hills)
{ val venice = 45.42 ll 12.21
  val ven1 = 44.96 ll 12.55
  val cervia = 44.25 ll 12.35
  val ancona = 43.62 ll 13.51
  val guilianova = 42.76 ll 13.96
  val vasto = 42.10 ll 14.71
  val campomarina = 41.92 ll 15.13
  val vieste = 41.88 ll 16.17


  val gaeta = 41.20 ll 13.57
  val p55 = 41.23 ll 13.04
  val anzio = 41.45 ll 12.62
  val santaMarinella = 42.03 ll 11.83
  val puntaAla = 42.80 ll 10.73
  val livorno = 43.54 ll 10.29
  val forteDeiMarmi = 43.96 ll 10.16
  val palmaria = 44.03 ll 9.84
  val recco = 44.35 ll 9.14
  val genoa = 44.39 ll 8.94
  val voltri = 44.42 ll 8.75

  override val polygonLL = PolygonLL(venice, ven1, cervia, ancona, guilianova, vasto, campomarina, vieste, ItalySouth.siponto, ItalySouth.diProcida,
    gaeta, p55, anzio, santaMarinella, puntaAla, livorno, forteDeiMarmi, palmaria, recco, genoa, voltri)
}

/** [[PolygonLL]] graphic for Italy depends on nothing. */
object ItalySouth extends EArea2("ItalySouth", 40.81 ll 15.86, hills)
{ val siponto = 41.60 ll 15.89
  val barletta = 41.32 ll 16.28
  val brindisi = 40.65 ll 17.97
  val otranto = 40.12 ll 18.45
  val leuca = 39.79 ll 18.34
  val puntaPizzo = 40.00 ll 17.99
  val p40 = 40.05 ll 17.97
  val p42 = 40.21 ll 17.86

  val taranto = 40.52 ll 17.09
  val bruscata = 39.76 ll 16.48
  val mirto = 39.62 ll 16.77
  val capoColonna = 39.02 ll 17.20
  val stilaroMouth = 38.43 ll 16.57

  val palizzi = 37.95 ll 16.03
  val riaciCapo = 37.95 ll 15.67
  val giovanni = 38.23 ll 15.63
  val bagnaraCalabra = 38.28 ll 15.79
  val lamezia = 38.89 ll 16.22
  val p70 = 39.99 ll 15.42
  val licosa = 40.25 ll 14.91
  val diProcida = 40.79 ll 14.04

  val polygonLL = PolygonLL(siponto, barletta, brindisi, otranto, leuca, puntaPizzo, p40, p42, taranto, bruscata, mirto, capoColonna, stilaroMouth, palizzi, riaciCapo,
    giovanni, bagnaraCalabra, lamezia, p70, licosa, diProcida)
}