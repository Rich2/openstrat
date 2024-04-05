/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Object to provide short names for various [[WTile]] values. */
object WTiles
{ /** [[Plain]] [[IceCap]] [[LandFree]] */
  val ice: Land = Land(Plain, IceCap, LandFree)

  /** [[Plain]] [[Tundra]] [[LandFree]] */
  val tundra: Land = Land(Plain, Tundra, LandFree)

  /** [[Plain]] [[Taiga]] [[Forest]] */
  val taiga: Land = Land(Plain, Boreal, Forest)

  /** [[Plain]] [[Steppe]] [[LandFree]] */
  val steppe: Land = Land(Plain, Steppe, LandFree)

  /** [[Plain]] [[Continental]] [[CivMix]] */
  val continental: Land = Land(Plain, Continental, CivMix)

  /** [[Plain]] [[Continental]] [[Forest]] */
  val contForest: Land = Land(Plain, Continental, Forest)

  /** [[Plain]] [[DesertCold]] [[LandFree]] */
  val descold: Land = Land(Plain, DesertCold, LandFree)

  /** [[Plain]] [[Oceanic]] [[CivMix]] */
  val oceanic: Land = Land(Plain, Oceanic, CivMix)

  /** [[Plain]] [[Oceanic]] [[Forest]] */
  val oceForest: Land = Land(Plain, Oceanic, Forest)

  /** [[Plain]] [[Subtropical]] [[CivMix]] */
  val subtrop: Land = Land(Plain, Subtropical, CivMix)

  val savannah: Land = Land(Plain, Savannah, CivMix)
  val sahel: Land = Land(Plain, Sahel, CivMix)

  /** [[Plain]] [[DesertHot]] [[LandFree]] */
  val deshot: Land = Land(Plain, DesertHot, LandFree)

  /** [[Plain]] [[Tropical]] [[Forest]] */
  val jungle: Land = Land(Plain, Tropical, Forest)

  /** [[PlainLakes]] [[Tundra]] [[LandFree]] */
  val lakesTundra: Land = Land(PlainLakes, Tundra, LandFree)

  /** [[PlainLakes]] [[Boreal]] [[Forest]] */
  val lakesTaiga: Land = Land(PlainLakes, Boreal, Forest)

  /** [[Plain]] [[Continental]] [[Forest]] */
  val lakesContForest: Land = Land(PlainLakes, Continental, Forest)

  /** [[PlainLakes]] [[Tropical]] [[Forest]] */
  val lakesJungle: Land = Land(PlainLakes, Tropical, Forest)

  /** [[Hilly]] [[Tundra]] [[LandFree]] */
  val hillyTundra: Land = Land(Hilly, Tundra, LandFree)

  /** [[Hilly]] [[Boreal]] [[Forest]] */
  val hillyTaiga: Land = Land(Hilly, Boreal, Forest)

  /** [[Hilly]] [[Steppe]] [[CivMix]] */
  val hillySteppe: Land = Land(Hilly, Steppe, CivMix)

  /** [[Hilly]] [[Continental]] [[CivMix]] */
  val hillyCont: Land = Land(Hilly, Continental, CivMix)

  /** [[Hilly]] [[Continental]] [[Forest]] */
  val hillyContForest: Land = Land(Hilly, Continental, Forest)

  /** [[Hilly]] [[DesertCold]] [[LandFree]] */
  val hillyDescold: Land = Land(Hilly, DesertHot, LandFree)

  /** [[Hilly]] [[Oceanic]] [[CivMix]] */
  val hillyOce: Land = Land(Hilly, Oceanic, CivMix)

  /** [[Hilly]] [[Oceanic]] [[Forest]] */
  val hillyOceForest: Land = Land(Hilly, Oceanic, Forest)

  /** [[Hilly]] [[Subtropical]] [[CivMix]] */
  val hillySub: Land = Land(Hilly, Subtropical)

  /** [[Hilly]] [[DesertHot]] [[LandFree]] */
  val hillyDeshot: Land = Land(Hilly, DesertHot, LandFree)

  val hillySavannah: Land = Land(Hilly, Savannah, CivMix)
  val hillySahel: Land = Land(Hilly, Sahel, CivMix)
  val hillySubForest: Land = Land(Hilly, Subtropical, Forest)
  val hillyTrop: Land = Land(Hilly, Tropical)
  val hillyJungle: Land = Land(Hilly, Tropical, Forest)

  /** [[HillyLakes]] [[Continental]] [[Forest]] */
  val hillyLakesContForest: Land = Land(HillyLakes, Continental, Forest)

  /** [[HillyLakes]] [[Boreal]] [[Forest]] */
  val hillyLakesTaiga: Land = Land(HillyLakes, Boreal, Forest)

  /** [[HillyLakes]] [[Oceanic]] [[CivMix]] */
  val hillyLakesOce: Land = Land(HillyLakes, Oceanic, CivMix)

  /** [[HillyLakes]] [[Oceanic]] [[Forest]] */
  val hillyLakesOceForest: Land = Land(HillyLakes, Oceanic, Forest)

  /** deprecated use one of the specialised shorthands instead.  */
  val mtainDepr: Land = Land(Mountains, Boreal, Forest)

  val mtainIce: Land = Land(Mountains, IceCap, LandFree)
  val mtainTundra: Land = Land(Mountains, Tundra, LandFree)
  val mtainBoreal: Land = Land(Mountains, Boreal, LandFree)
  val mtainTaiga: Land = Land(Mountains, Boreal, Forest)
  val mtainSteppe: Land = Land(Mountains, Steppe, LandFree)
  val mtainContForest: Land = Land(Mountains, Continental, Forest)
  val mtainOce: Land = Land(Mountains, Oceanic, CivMix)
  val mtainOceForest: Land = Land(Mountains, Oceanic, Forest)
  val mtainSub: Land = Land(Mountains, Subtropical, CivMix)
  val mtainSubForest: Land = Land(Mountains, Subtropical, Forest)
  val mtainSavannah: Land = Land(Mountains, Savannah)
  val mtainSahel: Land = Land(Mountains, Sahel, LandFree)
  val mtainDesert: Land = Land(Mountains, DesertHot, LandFree)
  val mtainJungle: Land = Land(Mountains, Tropical, Forest)
  val mtainLakesTaiga: Land = Land(MountLakes, Boreal, Forest)
  val mtainLakesCont: Land = Land(MountLakes, Continental, LandFree)
  val mtainLakesContForest: Land = Land(MountLakes, Continental, Forest)
  val mtainLakesOceForest: Land = Land(MountLakes, Oceanic, Forest)

  /** Sequence of short hand words for [[Land]]. */
  lazy val landWordTuples: Seq[(String, Land)] = identStrs[Land](lakesTundra, lakesTaiga,
    ice, tundra, taiga, steppe, oceanic, oceForest, savannah, sahel, deshot, jungle,
    hillyTundra, hillyTaiga, hillyContForest, hillyOce, hillyOceForest, hillySub, hillySavannah, hillySahel, hillyDeshot, hillyJungle, hillyLakesTaiga,
    mtainIce, mtainTundra, mtainTaiga, mtainContForest, mtainOceForest, mtainSubForest, mtainDesert, mtainJungle)

  /** Sequence of short hand words for [[Land]]. */
  lazy val landWords: ArrPairStr[Land] = landWordTuples.mapPairArr(_._1, _._2)

  /** Short hand for [[SeaIceWinter]]. */
  val siceWin: Water = SeaIceWinter

  val sea: Water = Sea
  val lake: Water = Lake

  /** Sequence of short hand words for [[Water]]. */
  lazy val waterWords: ArrPairStr[Water] = ArrPairStr[Water](("sea", sea), ("lake", lake), ("wice,", siceWin))
}