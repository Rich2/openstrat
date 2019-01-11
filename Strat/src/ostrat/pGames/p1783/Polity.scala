/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p1783
import Colour._

trait Polity extends SingletonLeaf
{
   def colour: Colour
   def typeSym = 'Polity
}

object Britain extends Polity
{
   def objSym = 'Britain
   def colour = Red
}

 object France extends Polity
{
   def objSym = 'France
   def colour = Blue
}
