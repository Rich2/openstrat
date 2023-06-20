/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package puloc
import Colour._

trait Nation extends Coloured
{
  def name: String
}

object Germany extends Nation
{

  override def name: String = "Germany"

  override def colour: Colour = Gray
}
object France extends Nation
{
  override def name: String = "France"

  override def colour: Colour = LightBlue
}