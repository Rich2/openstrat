/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pStrat

//import ostrat.pww2.LunitDesig


/** Military unit designation such as the Wodrig Korps, RAF 303 squadron, 10th Cruiser Squadron. */
trait MuDesig extends Coloured
{ /** The polity or nation to which this military unit belongs. */
  def polity: Polity

  //def struct: MuStruct

  /** The universal military heiracrhcy category. */
  def level: MuUniLevel

  override def colour = polity.colour

}

/** Military unit designation such as the British 30 Corps, RAF 303 squadron, 10th Cruiser Squadron. */
trait MuNumberedDesig extends MuDesig
{
  def num: Int
}

trait LunitDesig extends MuNumberedDesig
{
  //override def struct: LuStruct
  override def level: LuUniLevel
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