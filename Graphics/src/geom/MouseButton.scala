/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A mouse button, used to return events from a graphical user interface. */
sealed class MouseButton
{
  def apply[A](leftVal: => A, middleVal: => A, rightVal: => A, otherVal: => A) = this match
  { case LeftButton => leftVal
    case MiddleButton => middleVal
    case RightButton => rightVal
    case _ => otherVal
  }
}

/** Indicates the left mouse button was pressed. */
case object LeftButton extends MouseButton

/** Indicates the middel mouse button was pressed. */
case object MiddleButton extends MouseButton

/** Indicates the right mouse button was pressed. */
case object RightButton extends MouseButton

/** Indicates no mouse button was pressed. */
case object NoButton extends MouseButton

/** Indicates the back mouse button was pressed. */
case object BackButton extends MouseButton

/** Indicates the forward mouse button was pressed. */
case object ForwardButton extends MouseButton

/** MultipleButton is needed for completeness for mouse events in js when the Event is NOT instigated by a mouse button. */
case object MultipleButton extends MouseButton

/** Indicates the mouse button is unknown. */
case object UnknownButton extends MouseButton