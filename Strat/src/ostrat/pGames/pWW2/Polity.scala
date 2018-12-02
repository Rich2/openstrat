/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pWW2

trait Polity extends StringerSingleton
{ def typeSym = 'Polity
  def colour: Colour
}

object Germany extends Polity
{
   def objSym = 'Germany
   def colour = Colour.fromInts(128, 177, 179)//CadetBlue 60% shade
}

object Britain extends Polity
{
   def objSym = 'Britain
   def colour = Colour.fromInts(255, 232, 184)
}

object France extends Polity
{
   def objSym = 'France
   def colour = Colour.fromInts(125, 255, 255)
}
