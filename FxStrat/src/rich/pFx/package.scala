/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package object pFx
{   
   implicit class ImpScalaFxColour(colour: Colour)
   {
      import scalafx.scene._
      def fx: paint.Color = paint.Color.rgb(colour.red, colour.green, colour.blue, colour.alpha/ 255.0)
   }  
}