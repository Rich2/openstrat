/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import geom._, pStrat._, pglobe._, pEarth._, pEurope._

abstract class DeuArmee(val startDate: MTime, val endDate: Option[MTime], val armeeNum: Int) extends LunitLocHist
{ override val polity: MTimeSeries[Polity] = MTimeSeries(Deutch)
  override def uniLevel: LuUniLevel = FieldArmy
  override def levelName: String = "Armee"
  override def desig: String = armeeNum.ordAbbr
}

object DeuArmy3 extends DeuArmee(MTime(1939, 9), Some(MTime(1939, 11, 5)), 3)
{ override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Polandia.osteroda)
  override val subUnits: MTimeSeries[RArr[LunitLocHist]] = MTimeSeries(RArr(DeuKorps26))
}

abstract class DeuKorps(val startDate: MTime, val endDate: Option[MTime]) extends LunitLocHist
{ override val polity: MTimeSeries[Polity] = MTimeSeries(Deutch)
  override def uniLevel: LuUniLevel = Corps
  override def levelName: String = "Korps"
}

abstract class DeuNumberedKorps(startDate: MTime, endDate: Option[MTime], val korpsNum: Int) extends DeuKorps(startDate, endDate)
{ override def desig: String = korpsNum.ordAbbr
}

/** 1st German corps of the 3rd Reich. */
object DeuCp1 extends DeuNumberedKorps(MTime(1934, 10), Some(MTime(1945, 5, 8)), 1)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.konigsberg, (MTime(1939, 8, 1), Polandia.neidenburg))
  override def supUnit: MTimeSeries[JustOrName[LunitLocHist]] = MTimeSeries(JustNone, (MTime(1939, 9), Just(DeuArmy3)))
}

/** 2nd German corps of the 3rd Reich. */
object DeuCp2 extends DeuNumberedKorps(MTime(1935, 4), Some(MTime(1945, 5, 8)), 2)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Baltland.konigsberg, (MTime(1939, 8, 1), Polandia.schlochau))
}

/** 3rd German corps of the 3rd Reich. */
object DeuCp3 extends DeuNumberedKorps(MTime(1935, 4), Some(MTime(1945, 5, 8)), 3)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Polandia.stettin)
}

/** 4th German corps of the 3rd Reich. */
object DeuCp4 extends DeuNumberedKorps(MTime(1935, 4), Some(MTime(1945, 5, 8)), 4)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Germania.dresden.latLong, (MTime(1939, 10), Baltland.konigsberg))
}

/** 4th German corps of the 3rd Reich. */
object DeuCp21 extends DeuNumberedKorps(MTime(1939, 8, 10), Some(MTime(1941)), 4)
{ override val locPosns: MTimeSeries[LatLong] = MTimeSeries(Germania.dresden.latLong, (MTime(1939, 10), Baltland.konigsberg))
}

object DeuKorps26 extends DeuNumberedKorps(MTime(1939, 8, 22), Some(deu45Surr), 26)
{ override def locPosns: MTimeSeries[LatLong] = MTimeSeries(Polandia.allenstein)
  override def timeDesig(date: MTime): String = ife(date < MTime(1939, 10), s"Wodrig ($desig)", desig)
}