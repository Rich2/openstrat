/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom

/** A text cell is text in box, whose scaling is determined at display time. It will normally be displayed in some kind of command bar or table
 * structure. */
trait TextCell
{ def textStr: String
}

/** Implementation class for [[TextCell]]. */
case class TextBox(textStr: String) extends TextCell

/** A graphical control, whose scaling is determined at display time. It will  that will normally be displayed in some kind of command bar or table
 * structure. */
case class TextCtrl(textStr: String, action: MouseButton => Unit) extends TextCell
{

}
