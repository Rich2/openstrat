/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pAltReact
import pCanv._

case class AltReacGui(canv: CanvasPlatform, rows: Int, Columns: Int) extends CmdBarGui("Alternative Reactor")
{
  var statusText = "To be changed."
  reTop(Arr())
}
