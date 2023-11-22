/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** 20North, 0 East */
object EuropeNW extends EArea1("EuropeNW", 20 ll 0)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(Ireland, England, Scotland, Wales, Orkneys, OuterHebrides, Shetland, Faroe, JanMayen, Frankia, BelgLux, Alsace,
    Netherlands, Jutland, Zealand, Funen, Germania, Alpsland, Polandia, Baltland, Ukraine, SwedenSouth, SwedenNorth, Oland)
}

object EuropeEast extends EArea1("EuropeEast", 60 ll 60)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(RussiaSouth, AzovSea, Greece, BalkansWest, BalkansEast, Finlandia, KolaPeninsula, Gotland, Saaremaa,
    Hiiumaa, Crimea, MarmaraSea, Peloponnese)
}

object EuropeSW extends EArea1("EuropeSw", 20 ll 0)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(FranceSouth, IberiaNorth, IberiaSouth, Italy, ItalySouth)
}

object MediterreaneanWest extends EArea1("Mediterranean West", 35.78 ll 18.73)
{ import pMed._
  override val a2Arr: RArr[EArea2] = RArr(Maghreb, SaharaCentral, Canarias, Sicily, Majorca, Sardina, Corsica)
}

object MediterraneanEast extends EArea1("Mediterranean East", 35.78 ll 18.73)
{ import pMed._
  override val a2Arr: RArr[EArea2] = RArr(SaharaEast, Sinai, Crete, Corfu, Cyprus, Rhodes, Cephalonia, Lesbos, Chios)
}

object AfricaNorth extends EArea1("AfricaNorth", 18.19 ll 15)
{ import pAfrica._
  override val a2Arr: RArr[EArea2] = RArr(SaharaWest, EastAfricaSouth, WestAfricaSouth)
}

/** The southern part of Africa below approximately 5 degrees north, roughly triangular in shape. */
object AfricaSouth extends EArea1("AfricanSouth", -16.14 ll 24.36)
{ import pAfrica. _
  override val a2Arr: RArr[EArea2] = RArr(LakeVictoria, LakeTanganyika, SouthAfrica, CentralAfricaWest, centralAfricaEast, Madagascar)
}

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object MiddleEast  extends EArea1("MiddleEast", 20.18 ll -0.65)
{ import middleEast._
  override val a2Arr = RArr(Anatolia, Kurdistan, LakeVan, Armenia, Levant, Arabia, Persia, Iraq, LakeTuz)
}

object AmericasFarNorth extends EArea1("Far North\nAmerica", 61 ll -109)
{
  import pAmericas._
  val ensenada: LatLong = 31.74 ll -116.73

  val lakes: RArr[EArea2] = RArr(LakeSuperior, LakeHuron, LakeMichigan, LakeErie, LakeOntario)
  override val a2Arr: RArr[EArea2] = lakes ++
    RArr(Alaska, CanadaNorthWest, CanadaNorthWest, EllesmereIsland, GreatBearLake, GreatSlaveLake, LakeWinnipeg, Nunavut, CanadaSouthWest,
      CanadaCentral, BanksIsland, MelvilleIsland, VictoriaIsland, PrinceWalesIsland, SouthamptonIsland, CanadaSouthEast, NewBrunswick, NovaScotia,
      CanadaNorthEast, DevonIsland, BaffinIsland, NewFoundland)
}

object AmericasNearNorth extends EArea1("North America", 49 ll -100)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(UsaWest, UsaMid, UsaEast, Florida, Baja, Mexico, MexicoEast, AmericasCentral, Cuba)
}

object AsiaMain extends EArea1("Asia", 60 ll 65)
{ import pAsia._
  override val a2Arr: RArr[EArea2] = RArr(RusNorth, SiberiaWest, SiberiaNorth, SevernyIsland, Pakistan, India, SriLanka, Himalayas, Kyrgyyzstan, SiberiaSouth)
}

/** Asia East. North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaEast extends EArea1("Asia", 60 ll 100)
{ import pAsia._
  override val a2Arr: RArr[EArea2] = RArr(Manchuria, Mongolia, Xinjiang, China, IndoChina, Korea, Yakutia, FeAsia, sakhalin, Hokkaido, Honshu, Kyshu,
    Taiwan, Kazak, MalayPeninsula, LakeBaikal)
}

/** Polar regions. */
object PolarNorth extends EArea1("NPole", 89.5 ll 0)
{ import pOceans._
  override val a2Arr: RArr[EArea2] = RArr(Greenland, Artic, Svalbard, Nordauslandet)
}

object AmericasSouth extends EArea1("SAmericas", -6.52 ll -62.28)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(SouthAmericaSouth, SouthAmericaMiddle, SouthAmericaWest, SouthAmericaEast)
}

object Australasia extends EArea1("Australasia", -23 ll 130)
{ import pOceans._
  override val a2Arr: RArr[EArea2] = RArr(Sumatra, Borneo, Sulawesi, javaIsland, NewGuinea, Australia, NZNorthIsland, NZSouthIsland)
}

/** The North Atlantic. The seas and the land of the north Atlantic. */
object NorthAtantic extends EArea1("NAtlantic", 60 ll -30)
{ override val a2Arr: RArr[EArea2] = RArr(Iceland)
}

object PacificTop extends EArea1("Pacific", 0 ll 175)
{ import pOceans._
  override val a2Arr: RArr[EArea2] = RArr(Hawaii)
}

object PolarSouth extends EArea1("PolarSouth", -89.9 ll 0)
{ import pOceans._
  override val a2Arr: RArr[EArea2] = RArr(AntarticaEast, AntarticaWest, WeddelSeaIce)
}