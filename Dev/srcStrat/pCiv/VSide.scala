/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid.phex._, Colour._

trait VSide extends Coloured// extends HSideGeom

case object VSideNone extends VSide //with HSideNone
{ override val colour: Colour = Black
}

trait VSideSome extends VSide with HSideSome
{ def terr: VSTerr
  override def colour: Colour = terr.colour
}

case class VSideMid(terr: VSTerr = Sea) extends VSideSome with HSideMid
case class VSideLt(terr: VSTerr = Sea) extends VSideSome// with HSideLeft
case class VSideRt(terr: VSTerr = Sea) extends VSideSome// with HSideRight
case class VSideLtRt(terr: VSTerr = Sea) extends VSideSome// with HSideLeftRight

trait VSTerr extends Coloured

/*object SSea extends VSTerr
{ override def colour: Colour = DarkBlue
}*/

object River extends VSTerr
{ override def colour: Colour = Blue
}