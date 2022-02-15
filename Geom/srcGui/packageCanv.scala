/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import geom._, Colour._

/** The package attempts to encapsulate the various abstract canvas traits while the actual objects that populate a canvas go in package geom. */
package object pgui
{
  type MenuSeq = Seq[MenuNode]
  type MouseCmd = MouseButton => Unit

  /** A simple button that does not depend on which mouse button is pressed. */
  def simpleButtonOld(str: String, cmd: AnyRef): PolyCurveAllOld = Rectangle.curvedCornersCentred(50, 25, 5).allElems(cmd, White, 3, Black, 25, str)

  /** A button to react to different mouse buttons. The length varies with the String. */
  def clickButtonOld(str: String, cmd: MouseCmd, backColour: Colour = Colour.White): PolyCurveAllOld =
    Rectangle.curvedCornersCentred((str.length + 1).max(2) * 17, 30, 5).allElems(MouseButtonCmd(cmd), backColour, 3, backColour.contrastBW, 25, str)

  /** A button to react to different mouse buttons. The length varies with the String. */
  def clickButton(str: String, backColour: Colour = Colour.White)(cmd: MouseCmd): PolygonCompound =
    Rect((str.length + 1).max(2) * 17, 30).fillDrawTextActive(backColour, MouseButtonCmd(cmd), str, 25, 3, backColour.contrastBW)
}