/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VSep extends Coloured

object VSep
{
  implicit val defaultValueEv: DefaultValue[VSep] = new DefaultValue[VSep]
  { override def default: VSep = VSepNone
  }
}

case object VSepNone extends VSep with HSepNone
{ override val colour: Colour = Black
}

trait VSepSome extends VSep with HSepSome

object River extends VSepSome
{ override def colour: Colour = Blue
}