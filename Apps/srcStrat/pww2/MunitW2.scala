/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pww2
import pStrat._

case class BrArmy(num: Int, polity: Polity = Britain) extends LuNumberedDesig
{ override def level: LuUniLevel = FieldArmy
  override def levelStr: String = "Army"
  override def counter: UnitCounter = InfantryCounter
}

/** British 8th Army. */
//object BrAr8 extends BrArmy(8)

case class BrCorps(num: Int, polity: Polity = Britain) extends LuNumberedDesig
{ override def level: LuUniLevel = Corps
  override def levelStr: String = "Corps"
  override def counter: UnitCounter = InfantryCounter
}

//object BrCorps5 extends BrCorps(5)

trait DeKorps extends LuDesig
{ override def level: LuUniLevel = Corps
  override def levelStr: String = "Korps"
  override def counter: UnitCounter = InfantryCounter
  override def polity: Polity = Germany
}

case class DeKorpsUnNum(idStr: String) extends DeKorps
{
  override def toString: String = levelStr -- idStr
}
case class DeKorpsNum(num: Int) extends DeKorps with LuNumberedDesig

object AfricaKorps extends LuIdentity
{ override def desig0: LuDesig = DeKorpsUnNum("Africa")
  override def date0: MTime = MTime(1941, 3)
}

trait DeArmeeId extends LuDesig
{ override def level: LuUniLevel = FieldArmy
  override def levelStr: String = "Armee"
  override def counter: UnitCounter = InfantryCounter
  override def polity: Polity = Germany
}

trait DeMuDesigNum extends MuNumberedDesig
{
  override def idStr: String = num.toString + "."
}
trait DeLuDesigNum extends DeMuDesigNum with LuNumberedDesig
case class DeArmee(ident: LuIdentity, desig: LuDesig, NumKorps: Int = 3) extends Lunit

case class DeArmeeUnNum(idStr: String) extends DeArmeeId
case class DeArmeeNum(num: Int) extends DeArmeeId with DeLuDesigNum

object DeArmee1 extends LuIdentity
{ override def desig0: LuDesig = DeArmeeNum(1)
  override def date0: MTime = MTime(1939, 8, 26)
}

object DeArmee7 extends LuIdentity
{ override def desig0: LuDesig = DeArmeeNum(7)
  override def date0: MTime = MTime(1939, 8 , 25)
}

object DeArmee15 extends LuIdentity
{ override def desig0: LuDesig = DeArmeeNum(15)
  override def date0: MTime = MTime(1941, 1 , 15)
}

//object DeArmee1 extends DeArmee(1)
//object DeArmee7 extends DeArmee(7)
//object DeArmee15 extends DeArmee(15)

trait PzArmy extends LuDesig
{ override def level: LuUniLevel = FieldArmy
  override def levelStr: String = "PanzerArmee"
  override def polity: Polity = Germany
  override def counter: UnitCounter = CavalryCounter
}

case class PzArmeeUnNum(idStr: String) extends PzArmy
{
  override def toString: String = levelStr -- idStr
}
/** Panzer Armee North Africa. */
//object PzAr5 extends PzArmy(5)