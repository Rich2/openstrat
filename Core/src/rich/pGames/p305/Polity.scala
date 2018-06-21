/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package pGames
package p305
import Colour._

trait Polity extends PersistSingle
{
   def colour: Colour
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
