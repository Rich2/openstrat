/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VSide extends Coloured

object VSide
{
  implicit val defaultValueEv: DefaultValue[VSide] = new DefaultValue[VSide]
  { override def default: VSide = VSideNone
  }
}

case object VSideNone extends VSide with HSideNone
{ override val colour: Colour = Black
}

trait VSideSome extends VSide with HSideSome

object River extends VSideSome
{ override def colour: Colour = Blue
}