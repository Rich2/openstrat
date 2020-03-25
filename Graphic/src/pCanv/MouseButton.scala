/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv

sealed class MouseButton
{
  def apply[A](leftVal: => A, middleVal: => A, rightVal: => A, otherVal: => A) = this match
  { case LeftButton => leftVal
    case MiddleButton => middleVal
    case RightButton => rightVal
    case _ => otherVal
  }
}
case object LeftButton extends MouseButton
case object MiddleButton extends MouseButton
case object RightButton extends MouseButton
case object NoButton extends MouseButton

