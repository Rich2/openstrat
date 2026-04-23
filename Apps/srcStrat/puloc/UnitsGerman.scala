/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom.*, pStrat.*, pglobe.*, pEarth.*, pEurope.*

abstract class DeuArmee(val startDate: TimeMin, val endDate: Option[TimeMin], val armeeNum: Int) extends LunitLocHist
{ override val polity: TimeMinSeries[Polity] = TimeMinSeries(Deutch)
  override def uniLevel: TimeMinSeries[LuUniLevel] = TimeMinSeries(FieldArmy)
  override def levelName: TimeMinSeries[String] = TimeMinSeries("Armee")
  override def idStr: String = armeeNum.ordAbbr
}

object DeuArmy3 extends DeuArmee(TimeMin(1939, 9), Some(TimeMin(1939, 11, 5)), 3)
{ override def locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Polandia.osteroda)
  override val subUnits: TimeMinSeries[RArr[LunitLocHist]] = TimeMinSeries(RArr(DeuKorps26))
}

abstract class DeuKorps(val startDate: TimeMin, val endDate: Option[TimeMin]) extends LunitLocHist
{ override val polity: TimeMinSeries[Polity] = TimeMinSeries(Deutch)
  override def uniLevel: TimeMinSeries[LuUniLevel] = TimeMinSeries(Corps)
  override def levelName: TimeMinSeries[String] = TimeMinSeries("Korps")
}

abstract class DeuNumberedKorps(startDate: TimeMin, endDate: Option[TimeMin], val korpsNum: Int) extends DeuKorps(startDate, endDate)
{ override def idStr: String = korpsNum.ordAbbr
}

/** 1st German corps of the 3rd Reich. */
object DeuCp1 extends DeuNumberedKorps(TimeMin(1934, 10), Some(TimeMin(1945, 5, 8)), 1)
{ override val locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Baltland.konigsberg, (TimeMin(1939, 8, 1), Polandia.neidenburg))
  override def supUnit: TimeMinSeries[JustOrName[LunitLocHist]] = TimeMinSeries(JustNone, (TimeMin(1939, 9), Just(DeuArmy3)))
}

/** 2nd German corps of the 3rd Reich. */
object DeuCp2 extends DeuNumberedKorps(TimeMin(1935, 4), Some(TimeMin(1945, 5, 8)), 2)
{ override val locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Baltland.konigsberg, (TimeMin(1939, 8, 1), Polandia.schlochau))
}

/** 3rd German corps of the 3rd Reich. */
object DeuCp3 extends DeuNumberedKorps(TimeMin(1935, 4), Some(TimeMin(1945, 5, 8)), 3)
{ override val locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Polandia.stettin)
}

/** 4th German corps of the 3rd Reich. */
object DeuCp4 extends DeuNumberedKorps(TimeMin(1935, 4), Some(TimeMin(1945, 5, 8)), 4)
{ override val locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Germania.dresden.latLong, (TimeMin(1939, 10), Baltland.konigsberg))
}

/** 4th German corps of the 3rd Reich. */
object DeuCp21 extends DeuNumberedKorps(TimeMin(1939, 8, 10), Some(TimeMin(1941)), 4)
{ override val locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Germania.dresden.latLong, (TimeMin(1939, 10), Baltland.konigsberg))
}

object DeuKorps26 extends DeuNumberedKorps(TimeMin(1939, 8, 22), Some(deu45Surr), 26)
{ override def locPosns: TimeMinSeries[LatLong] = TimeMinSeries(Polandia.allenstein)
  override def timeDesig(date: TimeMin): String = ife(date < TimeMin(1939, 10), s"Wodrig ($idStr)", idStr)
}