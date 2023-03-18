/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VSide extends Coloured
{
  def terr: VSTerr
  override def colour: Colour = terr.colour
}

case class VSideCen(terr: VSTerr = Sea) extends VSide
case class VSideLt(terr: VSTerr = Sea) extends VSide with HSideLeft
case class VSideRt(terr: VSTerr = Sea) extends VSide with HSideRight

trait VSTerr extends Coloured

/*object SSea extends VSTerr
{ override def colour: Colour = DarkBlue
}*/

object River extends VSTerr
{ override def colour: Colour = Blue
}