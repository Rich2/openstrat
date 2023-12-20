/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pStrat

/** Military unit designation such as the Wodrig Korps, RAF 303 squadron, 10th Cruiser Squadron. */
trait MuDesig extends Coloured
{ /** The polity or nation to which this military unit belongs. */
  def polity: Polity

  //def struct: MuStruct

  /** The universal military hierarchy category. */
  def level: MuUniLevel
  def levelStr: String

  /** Usually a number. */
  def idStr: String

  def counter: UnitCounter

  override def colour = polity.colour
}

/** Military unit designation such as the British 30 Corps, RAF 303 squadron, 10th Cruiser Squadron. */
trait MuNumberedDesig extends MuDesig
{
  def num: Int

  override def idStr: String = num.toString
}

trait LunitDesig extends MuDesig
{
  //override def struct: LuStruct
  override def level: LuUniLevel

  override def toString: String = levelStr + polity.toString.enParenth
}

trait LunitNumberedDesig extends LunitDesig with MuNumberedDesig
{


  //override def counter: UnitCounter = InfantryCounter
}
/*trait Munit
{ def identity: MuIdentity
  def struct: MuStruct
}

trait Lunit extends Munit
{ override def identity: LuIdentity
  override def struct: LuStruct
}*/

/** Military Unit Identity. */
trait MuIdentity
{ def date0: MTime
  def desig0: MuDesig
}

trait LuIdentity extends MuIdentity
{ override def desig0: LunitDesig
}

trait MuStruct
{ def uniLevel: MuUniLevel
  def counter: UnitCounter
}

trait LuStruct extends MuStruct
{
  override def uniLevel: LuUniLevel
}