/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pStrat

trait Munit extends Coloured
{ def ident: MuIdentity
  def desig: MuDesig
  override def colour: Colour = desig.colour
  def counter: UnitCounter = desig.counter
  override def toString: String = desig.toString
}

trait Lunit extends Munit
{ override def ident: LuIdentity
  override def desig: LuDesig
}

object Lunit
{
  def apply(ident: LuIdentity, desig: LuDesig): Lunit = new LunitImut(ident, desig)
}

class LunitImut(val ident: LuIdentity, val desig: LuDesig) extends Lunit


/** Military unit designation such as the Wodrig Korps, RAF 303 squadron, 10th Cruiser Squadron. */
trait MuDesig extends Coloured
{ /** The polity or nation to which this military unit belongs. */
  def polity: Polity

  /** The universal military hierarchy category. */
  def uniLevel: MuUniLevel

  /** The name of the type of structure, Squadron Corps Army PanzerArmee etc. */
  def structStr: String

  /** Usually a number. */
  def idStr: String

  def counter: UnitCounter

  override def colour: Colour = polity.colour
  override def toString: String = idStr -- structStr
}

/** Military unit designation such as the British 30 Corps, RAF 303 squadron, 10th Cruiser Squadron. */
trait MuNumberedDesig extends MuDesig
{ /** The ordinal designation. */
  def num: Int

  override def idStr: String = num.toString
}

trait LuDesig extends MuDesig
{ override def uniLevel: LuUniLevel
  //override def toString: String = levelStr + polity.toString.enParenth
}

trait LuNumberedDesig extends LuDesig with MuNumberedDesig

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
{ override def desig0: LuDesig
}

object LuIdentity
{
  def apply(desig0In: LuDesig, date0In: MTime): LuIdentity = new LuIdentity
  { override def desig0: LuDesig = desig0In
    override def date0: MTime = date0In
  }
}

trait MuStruct
{ def uniLevel: MuUniLevel
  def counter: UnitCounter
}

trait LuStruct extends MuStruct
{
  override def uniLevel: LuUniLevel
}