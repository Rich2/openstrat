/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package p305
import Colour._

trait Polity extends PersistSingleton
{
   def colour: Colour
  // def typeSym: Symbol = 'Polity
}

object Rome extends Polity
{
   def str: String = "Rome"
   def colour = Red
}

 object Macedon extends Polity
{
   def str: String = "Macedon"
   def colour = Blue
}
