/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package p1783
import Colour._

trait Polity extends PersistSingle
{
   def colour: Colour
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
