/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

trait WSidea extends Coloured// with ShowSimple
{

}

trait WSideCentral extends WSidea

trait SideSea extends WSidea
{ override def colour = DarkBlue
}

trait SideLake extends WSidea
{ override def colour = Blue
}

object SCSea extends WSidea with SideSea
object SCLake extends WSidea with SideLake