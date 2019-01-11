/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package p305
import Colour._

trait Polity extends SingletonLeaf
{
   def colour: Colour
   def typeSym: Symbol = 'Polity
}

object Rome extends Polity
{
   def objSym = 'Rome
   def colour = Red
}

 object Macedon extends Polity
{
   def objSym = 'Macedon
   def colour = Blue
}
