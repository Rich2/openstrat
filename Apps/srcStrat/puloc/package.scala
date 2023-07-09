/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Unit locations throughout history. Starting with WW2. */
package object puloc
{
  def descrip: String =
    """Unit locations throughout history
      |""".stripMargin

  def germanUnits: RArr[Lunit] = RArr(PruCp1, PruCp2, DeuArmy3, DeuCp1, DeuCp2, DeuCp3, DeuCp4, DeuKorps26)
  def sovietUnits: RArr[Lunit] = RArr(SovArmy3)
  def otherUnits: RArr[Lunit] = RArr(FraCp1, FraCp2)
  def units: RArr[Lunit] = germanUnits ++ sovietUnits ++ otherUnits
  
  def unitsAt(date: MTime): RArr[LunitState] = units.optMap(_.dateFind(date))

  def deu45Surr: MTime = MTime(1945, 5, 8, 23, 1)
}