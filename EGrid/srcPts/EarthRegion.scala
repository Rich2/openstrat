/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._

/** A first level area of the Earth, a large area such as North West Europe. */
abstract class EarthRegion(val name: String, val cen: LatLong) extends GeographicSymbolKey
{ def neighbs: RArr[EarthRegion] = RArr()
  def ePolys: RArr[EarthPoly]
  def places: LocationLLArr = ePolys.flatMap(_.places)(LocationLLArr.flatArrBuilderImplicit)
  def lakes: RArr[LakePoly] = ePolys.lakeFilter
}

/** Polar regions. */
object PolarNorth extends EarthRegion("NPole", 89.5 ll 0)
{ import noceans.*
  override val ePolys: RArr[EarthPoly] = RArr(Greenland, ArticNear, ArticWest, ArticEast, ArticFar, Spitsbergen, EdgeIsland, Nordauslandet, SevernayaZemyla)
}

/** The North Atlantic. The seas and the land of the North Atlantic. */
object NorthAtantic extends EarthRegion("NAtlantic", 60 ll -30)
{ import noceans.*
  override val ePolys: RArr[EarthPoly] = RArr(Iceland, JanMayen, Faroe)
}

/** Top level grouping for north-west European areas. */
object EuropeNW extends EarthRegion("EuropeNW", 20 ll 0)
{ import pEurope._
  override val ePolys: RArr[EarthPoly] = RArr(IrelandNorth, IrelandSouth, EnglandNorth, EnglandMiddle, EnglandSouth, ScotlandLow, ScotlandHigh, Wales, Orkneys,
    IslayJura, Uist, IsleLewis, Shetland, Brittany, FranceNorth, BelgLux, Alsace, Netherlands, Jutland, Zealand, Funen, Germania, Alpsland,
    Polandia, Baltland, Ukraine, SwedenSouth, SwedenMid, SwedenNorth, Oland, IsleMan)
}

/** Top level grouping for north-east European areas. */
object EuropeEast extends EarthRegion("EuropeEast", 60 ll 60)
{ import pEurope._
  override val ePolys: RArr[EarthPoly] = RArr(RussiaSouth, VolgaRegion, Caspian, AzovSea, Greece, BalkansWest, BalkansEast, FinlandNorth, FinlandSouth, Karelia,
    LakePeipus, RussiaNE, LakeLagoda, LakeOnega, KolaPeninsula, Gotland, Saaremaa, Hiiumaa, Crimea, MarmaraSea, Peloponnese)
}

/** Top level grouping for south-west European areas. */
object EuropeSW extends EarthRegion("EuropeSw", 20 ll 0)
{ import pEurope._
  override val ePolys: RArr[EarthPoly] = RArr(FranceSouth, IberiaNorth, IberiaSouth, ItalyNorth, ItalySouth, ItalyHeel, ItalyToe)
}

object MediterreaneanWest extends EarthRegion("Mediterranean West", 35.78 ll 18.73)
{ import pMed._
  override val ePolys: RArr[EarthPoly] = RArr(MaghrebWest, MaghrebEast, SaharaCentral, Sicily, Mallorca, Menorca, Ibiza, Sardinia, Corsica)
}

object MediterraneanEast extends EarthRegion("Mediterranean East", 35.78 ll 18.73)
{ import pMed._
  override val ePolys: RArr[EarthPoly] = RArr(SaharaEast, Sinai, Naxos, Andros, Kythira, Crete, Corfu, Thasos, Samos, Cyprus, Rhodes, Cephalonia, Lesbos, Chios)
}

object AfricaNorth extends EarthRegion("AfricaNorth", 18.19 ll 15)
{ import pAfrica._
  override val ePolys: RArr[EarthPoly] = RArr(Canarias, SaharaWest, AfricaCentral, WestAfricaSouth, AfricaHorn)
}

/** Tropical Africa. */
object AfricaTropical extends EarthRegion("African tropical", -16.14 ll 24.36)
{ import pAfrica._
  override val ePolys: RArr[EarthPoly] = RArr(FernandoPo, Congo, CentralAfricaEast, Unguja, Angola, Zambia, LakeVictoria, LakeTanganyika, LakeMweru, LakeMalawi)
}

/** The southern part of Africa below approximately 5° north, roughly triangular. */
object AfricaSouth extends EarthRegion("AfricanSouth", -16.14 ll 24.36)
{ import pAfrica. _
  override val ePolys: RArr[EarthPoly] = RArr(NamibiaBotswana, Zimbabwe, SouthAfricaEast, Mozambique, SouthAfricaWest, Madagascar)
}

/** North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object MiddleEast  extends EarthRegion("MiddleEast", 20.18 ll -0.65)
{ import middleEast._
  override val ePolys = RArr(AnatoliaNW, AnatoliaSW, AnatoliaCentral, LakeEgirdir, Kurdistan, LakeVan, Armenia, Levant, ArabiaNorth, ArabiaSouth, Persia, Iraq,
    LakeTuz)
}

object Alaska extends EarthRegion("Alaska", 66.276 ll -151.410)
{ import pAmericas._
  override val ePolys: RArr[EarthPoly] = RArr(StLawrenceIsland, AlaskaNorth, AlaskaSouth, Nunivak, AleutPenisula, KodiakIsland)
}

object CanadaIslands extends EarthRegion("Canada Islands", 75.16 ll -94.87)
{ import pAmericas._
  override val ePolys: RArr[EarthPoly] = RArr(EllesmereIsland, RingnesIslands, MackenzieIslands, BanksIsland, MelvilleIsland, VictoriaIsland, PrinceWalesIsland,
    SouthamptonIsland,  DevonIsland, BaffinIsland)
}

object Canada extends EarthRegion("Canada", 61 ll -109)
{ import pAmericas._
  val ensenada: LatLong = 31.74 ll -116.73
  val greatLakes: RArr[EarthPoly] = RArr(LakeSuperior, LakeHuron, LakeMichigan, LakeErie, LakeOntario)

  override val ePolys: RArr[EarthPoly] = greatLakes ++ RArr(Yukon, BearSlaveLand, BearSlaveLand, GreatBearLake, GreatSlaveLake, SlaveAthabascaLand, LakeWinnipeg,
    NunavutNorth, NunavutSouth, LakeAthabasaca, CanadaRockies, AlbertaSask, Manitoba, ReindeerLake, CanadaCentral, CanadaSouthEast, NewBrunswick, NovaScotia,
    Quebecia, NewFoundland, Ungava)
}

object AmericasNearNorth extends EarthRegion("North America", 49 ll -100)
{ import pAmericas._
  override val ePolys: RArr[EarthPoly] = RArr(UsaSouthWest, UsaNorthWest, UsaMidWest, UsaNorthEast, MicheganLower, Delmarva, UsaSouth, UsaPrariesSouth, Florida)
}

object AmericasCentral extends EarthRegion("Americas Central", 19.563 ll -89.167)
{ import pAmericas._
  override val ePolys: RArr[EarthPoly] = RArr(Baja, Mexico, MexicoEast, ElSalPanama, Cuba, Hispaniola, Jamaica)
}

object AmericasSouth extends EarthRegion("SAmericas", -6.52 ll -62.28)
{ import pAmericas._
  override val ePolys: RArr[EarthPoly] = RArr(AndesFarNorth, ColomVenez, Guyana, AndesNearNorth, AmazonWest, AmazonEast, SouthAmericaCentral, SouthAmericaEast,
    AndesMiddle, SouthAmericaMiddle, SouthAmericaNS, SouthAmericaFS, DelFuego, Falklands)
}

object AsiaMain extends EarthRegion("Asia", 60 ll 65)
{ import pAsia._
  override val ePolys: RArr[EarthPoly] = RArr(RusNorth, SiberiaWest, SiberiaNorth, SevernyIsland, Pakistan, India, SriLanka, Burma, Himalayas, Kyrgyyzstan,
    SiberiaSouth)
}

/** Asia East. North of 25.4N degs East of 66.3E west of 141.6E 33.3N */
object AsiaEast extends EarthRegion("Asia", 60 ll 100)
{ import pAsia._
  override val ePolys: RArr[EarthPoly] = RArr(Manchuria, Mongolia, Xinjiang, ChinaNorth, China, Yunnan, TibetEast, Hainan, IndoChina, Korea, Yakutia, FeAsia,
    Kamchatka, sakhalin, Hokkaido, Honshu, Kyshu, Taiwan, Kazak, MalayPeninsula, LakeBaikal)
}

/** Australasia. Australia and New Zealand. */
object Australasia extends EarthRegion("Australasia", -23 ll 130)
{ import soceans._
  override val ePolys: RArr[EarthPoly] = RArr(WesternAustralia, AustraliaNorthTerr, Queensland, SouthAustralia, AustraliaSouthEast, Tasmania, NewCaldedonia,
    NZNorthIsland, NZSouthIsland)
}

/** Malay archipelago. */
object MalayArch extends EarthRegion("MalayArchAustralasia", 0.762 ll 123.068)
{ import pMalay._
  override val ePolys: RArr[EarthPoly] = RArr(Sumatra, Borneo, Sulawesi, javaIsland, BaliIsland, Sumbawa, Lambok, Sumba, Flores, AlorIsland, Wetar, Timor,
    GuineaWest, PapuaNewGuinea, NewBritain, NewIreland, Luzon, Palawan, VisayasWest, SamarLeyte, Mindano)
}

object PacificTop extends EarthRegion("Pacific", 0 ll 175)
{ import noceans._
  override val ePolys: RArr[EarthPoly] = RArr(Hawaii)
}

object PolarSouth extends EarthRegion("PolarSouth", -89.9 ll 0)
{ import soceans._
  override val ePolys: RArr[EarthPoly] = RArr(AntarticaEast, AntarticaWest, RossSeaIce, WeddelSeaIce)
}