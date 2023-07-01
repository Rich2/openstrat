/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom.pglobe._

/** Unit locations throughout history. Starting with WW2. */
package object puloc
{
  def descrip: String =
    """Unit locations throughout history
      |""".stripMargin

  def units: RArr[Lunit] = RArr(DeuCp1, DeuCp2, DeuCp4, FraCp1, FraCp2)
  def allLocs(date: MTime): RArr[(Lunit, LatLong)] = units.optMap(_.locationFind(date))
  def allLocs2(date: MTime): RArr[UnitState] = units.optMap(_.dateFind(date))
}