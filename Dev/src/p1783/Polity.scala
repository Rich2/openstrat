/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package p1783
import Colour._

trait Polity extends PersistSingleton
{
   def colour: Colour
  // def typeSym = 'Polity
}

object Britain extends Polity
{
   def str: String = "Britain"
   def colour = Red
}

 object France extends Polity
{
   def str: String = "France"
   def colour = Blue
}
