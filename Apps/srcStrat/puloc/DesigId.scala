/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc

/** Designation identifier. Examples "8th" in "8th Army. "Afrika" in "Korps Africa"  */
trait DesigId
{ /** Does the ID string go before are after the level [[String]]. */
  def isPre: Boolean
  def str: String

  def desig(level: String): String = ife(isPre, str -- level, level -- str)
}

/** Standard English Ordinal numbered identifier "1st" in "1st Army", "7th" in "7th Armoured Division". */
case class DesigOrd(ord: Int) extends DesigId
{ override def str: String = ord.ordAbbr
  override def isPre: Boolean = true
}

/** Standard German Ordinal numbered identifier "1." in "1. Armee", "3." in "3. Infanterie-Division". */
case class DesigDeOrd(ord: Int) extends DesigId
{ override def str: String = ord.toString + "."
  override def isPre: Boolean = true
}