/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
import geom._, Colour._

/** The package attempts to encapsulate the various abstract canvas traits while the actual objects that populate a canvas go in package geom. */
package object pCanv 
{
  type MenuSeq = Seq[MenuNode]
  type MouseCmd = MouseButton => Unit

  /** A simple button that does not depend on which mouse button is pressed. */
  def simpleButton(str: String, cmd: AnyRef) = Rectangle.curvedCornersCentred(50, 25, 5).parentAll(cmd, White, 3, Black, 25, str)

  /** A button to react to different mouse buttons. The length varies with the String. */
  def clickButton(str: String, cmd: MouseCmd, backColour: Colour = Colour.White) =
    Rectangle.curvedCornersCentred((str.length + 1).max(2) * 17, 30, 5).parentAll(MButtonCmd(cmd), backColour, 3, backColour.contrastBW, 25, str)

  /** A button to react to different mouse buttons. The length is fixed regardless of the length of the String. */
  def clickButtonStd(str: String, cmd: MouseCmd, backColour: Colour = Colour.White) =
    Rectangle.curvedCornersCentred(100, 25, 5).parentAll(cmd, backColour, 3, backColour.contrastBW, 20, str)
}