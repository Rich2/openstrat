/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pDisp

sealed class MouseButton
{
   def apply[A](leftVal: => A, middleVal: => A, rightVal: => A, otherVal: => A) = this match
   {
         case LeftButton => leftVal
         case MiddleButton => middleVal
         case RightButton => rightVal            
         case _ => otherVal
   }
}
case object LeftButton extends MouseButton
case object MiddleButton extends MouseButton
case object RightButton extends MouseButton
case object NoButton extends MouseButton

sealed trait TextAlign
{
   def jsStr: String
}
case object TextCen extends TextAlign { def jsStr = "center" }
case object TextLeft extends TextAlign { def jsStr = "left" }
case object TextRight extends TextAlign { def jsStr = "right" }