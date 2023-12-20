/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import pStrat._



case class BrArmy(num: Int, polity: Polity = Britain) extends LunitNumberedDesig
{ override def level: LuUniLevel = FieldArmy
  override def levelStr: String = "Army"
  override def counter: UnitCounter = InfantryCounter
}

/** British 8th Army. */
//object BrAr8 extends BrArmy(8)

case class BrCorps(num: Int, polity: Polity = Britain) extends LunitNumberedDesig
{ override def level: LuUniLevel = Corps
  override def levelStr: String = "Corps"
  override def counter: UnitCounter = InfantryCounter
}

//object BrCorps5 extends BrCorps(5)

trait DeKorps extends LunitDesig
{ override def level: LuUniLevel = Corps
  override def levelStr: String = "Korps"
  override def counter: UnitCounter = InfantryCounter
  override def polity: Polity = Germany
}

case class DeKorpsUnNum(idStr: String) extends DeKorps
case class DeKorpsNum(num: Int) extends DeKorps with LunitNumberedDesig

object AfricaKorps extends LuIdentity{
  override def desig0: LunitDesig = DeKorpsUnNum("Africa")
  override def date0: MTime = MTime(1941, 3)
}

trait DeArmee extends LunitDesig
{ override def level: LuUniLevel = FieldArmy
  override def levelStr: String = "Armee"
  override def counter: UnitCounter = InfantryCounter
  override def polity: Polity = Germany
}

case class DeArmeeUnNum(idStr: String) extends DeArmee
case class DeArmeeNum(num: Int) extends DeKorps with LunitNumberedDesig

//object DeArmee1 extends DeArmee(1)
//object DeArmee7 extends DeArmee(7)
//object DeArmee15 extends DeArmee(15)

trait PzArmy extends LunitDesig
{ override def level: LuUniLevel = FieldArmy
  override def levelStr: String = "Panzer Armee"
  override def polity: Polity = Germany
  override def counter: UnitCounter = CavalryCounter
}
case class PzArmeeUnNum(idStr: String) extends DeArmee
/** Panzer Armee North Africa. */
//object PzAr5 extends PzArmy(5)