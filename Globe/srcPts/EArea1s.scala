/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pPts._

/** 20North, 0 East */
object EuropeNW extends EArea1("EuropeNW", 20 ll 0)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(Ireland, England, Scotland, Orkneys, OuterHebrides, Shetland, Faroe, JanMayen, Frankia, BelgLux, Alsace,
    Netherlands, Jutland, Zealand, Funen, Germania, Alpsland, Polandia, Baltland, Ukraine, SwedenSouth, SwedenNorth, Oland)
}

object EuropeEast extends EArea1("EuropeEast", 60 ll 60)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(Balkans, Crete, Finlandia, Gotland, Saaremaa, Hiiumaa, Crimea, MarmaraSea, Anatolia, Caucasus)
}

object EuropeSW extends EArea1("EuropeSw", 20 ll 0)
{ import pEurope._
  //top = 45.59N * 2 - 46.21 = 44.97
  //override def fill = false
  override val a2Arr: RArr[EArea2] = RArr(FranceSouth, Iberia, Sardina, Italy, Corsica)
  //override def iTiles: Seq[ITile] = Seq()//,CBritain, NBritain ,SBritain, Denmark, NSea, BalticSea)
  //override def iSides: Seq[ISide] = iTiles.flatMap(_.ownISides)
}

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaWest  extends EArea1("AsiaWest", 20.18 ll -0.65)
{ import pAsia._
  override val a2Arr = RArr(Arabia, Persia, Iraq, Kazak, RusNorth, India, SriLanka, Himalayas, CentralAsia, SevernyIsland)
}

object AmericasNorth extends EArea1("North America", 49 ll -100)
{
  import pAmericas._
  val ensenada: LatLong = 31.74 ll -116.73

  val lakes: RArr[EArea2] = RArr(LakeSuperior, LakeHuron, LakeMichigan, LakeErie, LakeOntario)
  override val a2Arr: RArr[EArea2] = lakes ++
    RArr(UsaWest, UsaEast, Alaska, NorthWestCanada, SouthWestCanada, CentralCanada, BanksIsland, VictoriaIsland, SouthamptonIsland, EastCanada, BaffinIsland,
      NewFoundland, Baja, CentralAmerica, Cuba)
}