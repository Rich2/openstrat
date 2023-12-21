/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import pStrat._

trait BrArmyDesig extends LuDesig
{ override def uniLevel: LuUniLevel = FieldArmy
  override def structStr: String = "Army"
  override def counter: UnitCounter = InfantryCounter
}

case class BrArmyDesigNum(num: Int, polity: Polity = Britain) extends BrArmyDesig with LuNumberedDesig

case class BrArmyDesigUnNum(idStr: String, polity: Polity = Britain) extends BrArmyDesig

case class BrArmy(ident: LuIdentity, desig: LuDesig, numCorps: Int) extends Lunit

object BrArmy
{ def num(ident: LuIdentity, desigNum: Int, numCorps: Int): BrArmy = BrArmy(ident, BrArmyDesigNum(desigNum), numCorps)
}

object BrArmy8 extends LuIdentity
{ override def desig0: LuDesig = BrArmyDesigUnNum("Western")
  override def date0: MTime = MTime(1941, 9, 10)
}

case class BrCorpsDesig(num: Int, polity: Polity = Britain) extends LuNumberedDesig
{ override def uniLevel: LuUniLevel = Corps
  override def structStr: String = "Corps"
  override def counter: UnitCounter = InfantryCounter
}

//object BrCorps5 extends BrCorps(5)
