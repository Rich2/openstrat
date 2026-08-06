/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Trait for XML /HTML attributes that take an integer value. */
trait XAttInt extends XAttShort
{ /** The integer value of this attribute. */
  def num: Int

  override def valueStr: String = num.str
}

object XAttInt
{ /** Factory apply method for XML / HTML  attribute that takes an integer value. */
  def apply(name: String, num: Int): XAttInt = new XAttIntGen(name, num)

  /** General implementation class for attribute that takes an integer value. */
  case class XAttIntGen(name: String, num: Int) extends XAttInt
}

/** Maximum length XML / HTML attribute. */
case class MaxLengthAtt(num: Int) extends XAttInt
{ override def name: String = "maxlength"
}