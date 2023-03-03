/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._, Colour._

/** The package attempts to encapsulate the various abstract canvas traits while the actual objects that populate a canvas go in package geom. */
package object pgui
{
  type MenuSeq = Seq[MenuNode]
  type MouseCmd = MouseButton => Unit

  /** A button to react to different mouse buttons. The length varies with the String. */
  def clickButton(str: String, backColour: Colour = White)(cmd: MouseCmd): PolygonCompound =
    Rect((str.length + 1).max(2) * 17, 30).fillDrawTextActive(backColour, MouseButtonCmd(cmd), str, 25, 3, backColour.contrastBW)

  /** A button to react to different mouse buttons. The length varies with the String. */
  def simpleButton(str: String, backColour: Colour = White)(cmd: => Unit): PolygonCompound =
    Rect((str.length + 1).max(2) * 17, 30).fillDrawTextActive(backColour, MouseButtonCmd(MouseButton => cmd), str, 25, 3, backColour.contrastBW)
}