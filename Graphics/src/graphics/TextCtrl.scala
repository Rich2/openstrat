/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

trait TextBox
{ def textStr: String
}

case class TextCell(textStr: String) extends TextBox

case class TextCtrl(textStr: String, action: pCanv.MouseButton => Unit) extends TextBox
{

}
