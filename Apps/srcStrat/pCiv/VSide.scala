/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VSide extends Coloured

object VSide
{
  implicit val defaultValueEv: DefaultValue[VSide] = new DefaultValue[VSide]
  { override def default: VSide = VSepNone
  }
}

case object VSepNone extends VSide with HSepNone
{ override val colour: Colour = Black
}

trait VSepSome extends VSide with HSepSome

object River extends VSepSome
{ override def colour: Colour = Blue
}