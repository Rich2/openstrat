/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

trait WSide extends Coloured// with ShowSimple
{

}

trait WSideCentral extends WSide

trait SideSea extends WSide
{ override def colour = DarkBlue
}

trait SideLake extends WSide
{ override def colour = Blue
}

object SCSea extends WSideCentral with SideSea
object SCLake extends WSide with SideLake