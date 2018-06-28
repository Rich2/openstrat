/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs

object JsDevApp
{
   def main(args: Array[String]): Unit =
   {
      val appNum = args.headOnly(1, _.findTypeElse(1))
      pDev.Play.curr(appNum)._1(CanvasJs)
   }
}