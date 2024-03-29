/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** Top level grouping for north west European areas. */
object EuropeNW extends EArea1("EuropeNW", 20 ll 0)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(IrelandNorth, IrelandSouth, England, ScotlandLow, ScotlandHigh, Wales, Orkneys, IslayJura, Uist, IsleLewis, Shetland,
    Faroe, JanMayen, Frankia, BelgLux, Alsace, Netherlands, Jutland, Zealand, Funen, Germania, Alpsland, Polandia, Baltland, Ukraine, SwedenSouth, SwedenNorth,
    Oland)
}

/** Top level grouping for north east European areas. */
object EuropeEast extends EArea1("EuropeEast", 60 ll 60)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(RussiaSouth, VolgaRegion, Caspian, AzovSea, Greece, BalkansWest, BalkansEast, Finlandia, KolaPeninsula, Gotland,
    Saaremaa, Hiiumaa, Crimea, MarmaraSea, Peloponnese)
}

/** Top level grouping for south west European areas. */
object EuropeSW extends EArea1("EuropeSw", 20 ll 0)
{ import pEurope._
  override val a2Arr: RArr[EArea2] = RArr(FranceSouth, IberiaNorth, IberiaSouth, ItalyNorth, ItalySouth, ItalyHeel, ItalyToe)
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
  override val a2Arr: RArr[EArea2] = RArr(SaharaWest, AfricaCentral, WestAfricaSouth, AfricaHorn)
}

/** The southern part of Africa below approximately 5 degrees north, roughly triangular in shape. */
object AfricaSouth extends EArea1("AfricanSouth", -16.14 ll 24.36)
{ import pAfrica. _
  override val a2Arr: RArr[EArea2] = RArr(LakeVictoria, LakeTanganyika, LakeMalawi, LakeMweru, SouthAfrica, Congo, AngloaZambia, centralAfricaEast,
    ZimMoz, Zanzibar, Madagascar)
}

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object MiddleEast  extends EArea1("MiddleEast", 20.18 ll -0.65)
{ import middleEast._
  override val a2Arr = RArr(Anatolia, Kurdistan, LakeVan, Armenia, Levant, Arabia, Persia, Iraq, LakeTuz)
}

object Alaska extends EArea1("Alaska", 66.276 ll -151.410)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(StLawrenceIsland, AlaskaNorth, AlaskaSouth, Nunivak, AleutPenisula, KodiakIsland)
}

object CanadaIslands extends EArea1("Canada Islands", 75.16 ll -94.87)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(EllesmereIsland, RingnesIslands, MackenzieIslands, BanksIsland, MelvilleIsland, VictoriaIsland, PrinceWalesIsland,
    SouthamptonIsland,  DevonIsland, BaffinIsland)
}

object Canada extends EArea1("Canada", 61 ll -109)
{ import pAmericas._
  val ensenada: LatLong = 31.74 ll -116.73
  val greatLakes: RArr[EArea2] = RArr(LakeSuperior, LakeHuron, LakeMichigan, LakeErie, LakeOntario)

  override val a2Arr: RArr[EArea2] = greatLakes ++ RArr(CanadaNorthWest, CanadaNorthWest, GreatBearLake, GreatSlaveLake, LakeWinnipeg, Nunavut, CanadaSouthWest,
    ReindeerLake, CanadaCentral, CanadaSouthEast, NewBrunswick, NovaScotia,  Quebecia,NewFoundland, Ungava)
}

object AmericasNearNorth extends EArea1("North America", 49 ll -100)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(UsaSouthWest, UsaNorthWest, UsaMidWest, UsaNorthEast, MicheganLower, Delmarva, UsaSouth, UsaPrariesSouth, Florida)
}

object AmericasCentral extends EArea1("Americas Central", 19.563 ll -89.167)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(Baja, Mexico, MexicoEast, ElSalPanama, Cuba, Hispaniola, Jamaica)
}

object AmericasSouth extends EArea1("SAmericas", -6.52 ll -62.28)
{ import pAmericas._
  override val a2Arr: RArr[EArea2] = RArr(SouthAmericaSouth, SouthAmericaMiddle, SouthAmericaWest, ColomVenez, SouthAmericaEast)
}

object AsiaMain extends EArea1("Asia", 60 ll 65)
{ import pAsia._
  override val a2Arr: RArr[EArea2] = RArr(RusNorth, SiberiaWest, SiberiaNorth, SevernyIsland, Pakistan, India, SriLanka, Himalayas, Kyrgyyzstan, SiberiaSouth)
}

/** Asia East. North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaEast extends EArea1("Asia", 60 ll 100)
{ import pAsia._
  override val a2Arr: RArr[EArea2] = RArr(Manchuria, Mongolia, Xinjiang, China, Hainan, IndoChina, Korea, Yakutia, FeAsia, Kamchatka, sakhalin, Hokkaido,
    Honshu, Kyshu, Taiwan, Kazak, MalayPeninsula, LakeBaikal)
}

/** Polar regions. */
object PolarNorth extends EArea1("NPole", 89.5 ll 0)
{ import noceans._
  override val a2Arr: RArr[EArea2] = RArr(Greenland, ArticNear, ArticWest, ArticEast, ArticFar, Svalbard, Nordauslandet, SevernayaZemyla)
}

object Australasia extends EArea1("Australasia", -23 ll 130)
{ import soceans._
  override val a2Arr: RArr[EArea2] = RArr(WesternAustralia, Australia, Tasmania, NZNorthIsland, NZSouthIsland)
}

object MalayArch extends EArea1("MalayArchAustralasia", 0.762 ll 123.068)
{ import pMalay._
  override val a2Arr: RArr[EArea2] = RArr(Sumatra, Borneo, Sulawesi, javaIsland, LesserSunda, NewGuinea, Luzon, Palawan, Visayas, Samar,
    Mindano)
}

/** The North Atlantic. The seas and the land of the north Atlantic. */
object NorthAtantic extends EArea1("NAtlantic", 60 ll -30)
{ override val a2Arr: RArr[EArea2] = RArr(noceans.Iceland)
}

object PacificTop extends EArea1("Pacific", 0 ll 175)
{ import noceans._
  override val a2Arr: RArr[EArea2] = RArr(Hawaii)
}

object PolarSouth extends EArea1("PolarSouth", -89.9 ll 0)
{ import soceans._
  override val a2Arr: RArr[EArea2] = RArr(AntarticaEast, AntarticaWest, RossSeaIce, WeddelSeaIce)
}